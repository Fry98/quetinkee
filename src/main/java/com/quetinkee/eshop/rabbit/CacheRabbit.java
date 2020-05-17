package com.quetinkee.eshop.rabbit;

import com.quetinkee.eshop.model.Bouquet;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CacheRabbit {

  @Autowired
  private Queue queueCache;

  @Autowired
  private RabbitTemplate template;

  @Async
  public void save(Bouquet bouquet) {
//    if (bouquet.getId() == null) throw new NullPointerException("Kytice musí mít identifikátor");
//    this.send(bouquet.getId());
  }

  @Async
  public void delete(Integer id) {
  }

  private void send(Integer id) {
    String message = "{\"id\": 1, \"body\":{\"name\":\"test\",\"description\":\"popis\"}}";
    /*Queue q = new Queue("item");*/
    this.template.convertAndSend(this.queueCache.getName(), message);
    System.out.println(" [x] Sent '" + message + "'");
  }
}
