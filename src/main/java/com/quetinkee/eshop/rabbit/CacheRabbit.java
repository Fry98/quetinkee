package com.quetinkee.eshop.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.HashMap;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Component
public class CacheRabbit {

  @Autowired
  private Queue queueCache;

  @Autowired
  private RabbitTemplate template;

  public <T> T find(Class<T> returnType, Integer id) {
    RestTemplate rest = new RestTemplate();
    HashMap params = new HashMap<String,String>();
    params.put("find", this.makeKey(returnType, id));

    try {
      return (T) rest.getForObject("http://localhost:4200/api/cache?id={find}", returnType, params);
    } catch (RestClientResponseException ex) {
      // ignore NOT_FOUND - currently not in cache
      if (ex.getRawStatusCode() != HttpStatus.NOT_FOUND.value()) {
        LoggerFactory.getLogger(SearchRabbit.class).warn(ex.getMessage());
      }
    } catch (ResourceAccessException ex) {
      LoggerFactory.getLogger(SearchRabbit.class).error(ex.getMessage());
    }

    return null;
  }

  @Async
  public void save(Class classType, Integer id, Serializable content) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JSONObject json = new JSONObject().put("body", mapper.writeValueAsString(content));
      this.send(content.getClass(), id, json);
    }
    catch (JsonProcessingException ex) {
      LoggerFactory.getLogger(SearchRabbit.class).error(ex.getMessage());
    }
  }

  @Async
  public void delete(Class classType, Integer id) {
    JSONObject json = new JSONObject();
    json.put("remove", true);
    this.send(classType, id, json);
  }

  private void send(Class classType, Integer id, JSONObject json) {
    json.put("id", this.makeKey(classType, id));
    this.template.convertAndSend(this.queueCache.getName(), json.toString());
  }

  private String makeKey(Class classType, Integer id) {
    return classType.getSimpleName() + "#" + (id == null ? 0 : id);
  }
}
