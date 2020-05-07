package com.quetinkee.eshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "quetinkee.upload")
public class UploadProperties {

  private String root;
  private String path;
  private Integer imgSize;

  public String getRoot() {
    return root;
  }

  public void setRoot(String root) {
    this.root = root;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Integer getImgSize() {
    return imgSize;
  }

  public void setImgSize(Integer imgSize) {
    this.imgSize = imgSize;
  }
}
