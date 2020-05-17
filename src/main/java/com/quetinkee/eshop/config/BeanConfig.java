package com.quetinkee.eshop.config;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class BeanConfig  {

  @Bean
  public Queue queueSearch() {
    return new Queue("items");
  }

  @Bean
  public Queue queueCache() {
    return new Queue("cache");
  }

  @Bean
  public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
    return container -> {
      container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
    };
  }
}
