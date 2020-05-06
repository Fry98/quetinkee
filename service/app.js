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

  await channel.assertQueue('items');  
  channel.consume('items', message => {
    const data = JSON.parse(message.content.toString());
    channel.ack(message);
    if (data.id === undefined || (!data.remove && data.name === undefined)) return;

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
      body: {
        name: fold.foldMaintaining(data.name)
      }
    });
  });
  
  await channel.assertQueue('cache');
  channel.consume('cache', message => {
    const data = JSON.parse(message.content.toString());
    channel.ack(message);
    if (data.id === undefined || data.data === undefined || data.ttl === undefined) return;
    redisClient.set(`req(${data.id})`, JSON.stringify(data.data), 'EX', data.ttl);
  });

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
      res.send(cache);
    });
  });

  app.get('/api/search', async (req, res) => {
    if (req.query.q === undefined || req.query.q.length === 0) {
      res.status(400).send('Invalid API request');
      return;
    }

    const query = sanitize(fold.foldMaintaining(req.query.q));
    const result = await client.search({
      index: 'items',
      type: 'item',
      body: {
        query: {
          wildcard: {
            name: `*${query}*`
          }
        }
      }
    });

    res.json(result.hits.hits.map(x => Number(x._id)));
  });

  app.use((req, res) => {
    res.status(404).send("API Endpoint doesn't exist");
  });

  app.listen(4200, () => {
    console.log("Service running on port 4200");
  });
})();
