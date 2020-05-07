package com.quetinkee.eshop;

import com.quetinkee.eshop.config.UploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
  UploadProperties.class
})
public class App {
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
