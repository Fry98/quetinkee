package com.quetinkee.eshop.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  @GetMapping("/404")
  public ResponseEntity<String> fallback() {
    try {
      Resource resource = new ClassPathResource("static/index.html");
      InputStream inpStr = resource.getInputStream();
      String body = StreamUtils.copyToString(inpStr, Charset.defaultCharset());
      return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(body);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("This error is ok in dev mode.<br>If you're seeing this in prod tho then we're pretty much fucked!");
    }
  }
}
