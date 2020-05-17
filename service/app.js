const amqp = require('amqplib');
const express = require('express');
const elasticsearch = require('elasticsearch');
const sanitize = require('elasticsearch-sanitize');
const fold = require('fold-to-ascii');
const redis = require('redis');

(async () => {
  const app = express();
  const redisClient = redis.createClient();
  const connection = await amqp.connect("amqp://localhost:5672");
  const channel = await connection.createChannel();
  const client = new elasticsearch.Client({
    host: 'localhost:9200'
  });

  /**
   * Search in
   */
  await channel.assertQueue('items');  
  channel.consume('items', message => {
    try {
      channel.ack(message); // accept even if wrong format
      const data = JSON.parse(fold.foldMaintaining(message.content.toString()));
      if (data.id === undefined || (!data.remove && data.body === undefined)) return;

      if (data.remove) {
        client.delete({
          index: 'items',
          type: 'item',
          id: data.id
        }).catch(_ => {});
        return;
      }

      client.index({
        index: 'items',
        type: 'item',
        id: data.id,
        body: data.body
      });
    }
    catch (err) {
      console.error(err);
      res.status(400).send('Bad API request');
    }
  });

  /**
   * Cache in
   */
  await channel.assertQueue('cache');
  channel.consume('cache', message => {
    const data = JSON.parse(message.content.toString());
    channel.ack(message);
    if (data.id === undefined || data.data === undefined || data.ttl === undefined) return;
    redisClient.set(`req(${data.id})`, JSON.stringify(data.data), 'EX', data.ttl);
  });

  /**
   * Cache out
   */
  app.get('/api/cache', (req, res) => {
    if (req.query.id === undefined) {
      res.status(400).send('Invalid API request');
      return;
    }

    redisClient.get(`req(${req.query.id})`, (err, cache) => {
      if (cache === null) {
        res.status(404).send(`No results for ID ${req.query.id}`);
        return;
      }

      res.set('Content-Type', 'application/json');
      res.send(cache);
    });
  });

  /**
   * Search out
   */
  app.get('/api/search', async (req, res) => {
    if (req.query.q === undefined || req.query.q.length === 0) {
      res.status(400).send('Invalid API request');
      return;
    }

    try {
      const find = sanitize(fold.foldMaintaining(req.query.q));

      const result = await client.search({
        index: 'items',
        type: 'item',
        body: {
          query: {
            bool: {
              should:[
                {wildcard: {
                  name: {
                    value: `*${find}*`,
                    boost: 1.2
                  }
                }},
                {wildcard: {
                  perex: {
                    value: `*${find}*`,
                    boost: 1.0
                  }
                }},
                {wildcard: {
                  description: {
                    value: `*${find}*`,
                    boost: 0.8
                  }
                }}
              ]
            }
          }
        }
      });

      res.json(result.hits.hits.map(x => Number(x._id)));
    }
    catch (err) {
      console.log(err);
      res.status(503).send('Elasticsearch offline');
    }
  });

  app.use((req, res) => {
    res.status(404).send("API Endpoint doesn't exist");
  });

  app.listen(4200, () => {
    console.log("Service running on port 4200");
  });
})();
