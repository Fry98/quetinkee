package com.quetinkee.eshop.rabbit;

import com.quetinkee.eshop.model.Bouquet;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class SearchRabbit {

  @Autowired
  private Queue queueSearch;

  @Autowired
  private RabbitTemplate template;

  public List<Integer> find(String find) {
    RestTemplate rest = new RestTemplate();
    HashMap params = new HashMap<String,String>();
    params.put("find", find);

    try {
      return rest.getForObject("http://localhost:4200/api/search?q={find}", List.class, params);
    }
    catch (RestClientException ex) {
      // TODO: convert to log?
      System.out.println("Search offline: " + ex.getMessage());
    }
    return null;
  }

  @Async
  public void save(Bouquet bouquet) {
    if (bouquet.getId() == null) throw new NullPointerException("Kytice musí mít identifikátor");

    JSONObject jsonBody = new JSONObject();
    jsonBody.put("name", bouquet.getName());
    jsonBody.put("perex", bouquet.getPerex());
    jsonBody.put("description", bouquet.getDescription());
    JSONObject json = new JSONObject().put("body", jsonBody);

    this.send(bouquet.getId(), json);
  }

  @Async
  public void delete(Integer id) {
    JSONObject json = new JSONObject();
    json.put("remove", true);
    this.send(id, json);
  }

  private void send(Integer id, JSONObject json) {
    json.put("id", id);
    this.template.convertAndSend(this.queueSearch.getName(), json.toString());
  }
}
