package com.quetinkee.eshop.utils;

import com.quetinkee.eshop.config.UploadProperties;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Upload and crop image to path specified in application.properties
 */
@Component
public class UploadImage {

  private final UploadProperties settings;
  private final Path uploadLocation;

  @Autowired
  public UploadImage(UploadProperties settings) {
    this.settings = settings;
    this.uploadLocation = Paths.get(settings.getRoot() + settings.getPath()).toAbsolutePath().normalize();

    try {
      Files.createDirectories(this.uploadLocation);
    }
    catch (IOException ex) {
      throw new UploadException("Nelze založit upload directory", ex);
    }
  }

  public String getPath() {
    return this.settings.getPath();
  }

  public String store(Integer id, MultipartFile file) throws UploadException {
    String fileName = StringUtils.cleanPath(id + "_" + file.getOriginalFilename());
    try {
      Path target = this.uploadLocation.resolve(fileName);
      BufferedImage img = this.cropImage(file, this.settings.getImgSize());

      File outputfile = new File(target.toString());
      ImageIO.write(img, "jpg", outputfile);
    }
    catch (IOException ex) {
      throw new UploadException("Nelze nahrát sobor: " + fileName, ex);
    }
    return fileName;
  }

  public boolean remove (String path, String fileName) throws UploadException {
    try {
      Path targetLocation = Paths.get(this.settings.getRoot() + path).toAbsolutePath().normalize().resolve(fileName);
      if (Files.exists(targetLocation)) {
        Files.delete(targetLocation);
        return true;
      }
    }
    catch (IOException ex) {
      throw new UploadException("Nelze smazat sobor: " + fileName, ex);
    }
    return false;
  }

  private BufferedImage cropImage (MultipartFile file, Integer maxSize) throws IOException {
    BufferedImage img = ImageIO.read(file.getInputStream());
    int height = img.getHeight();
    int width = img.getWidth();

    int size = (height > width ? width : height);
    if (size > maxSize) size = maxSize;
    // Crop
    return img.getSubimage((width / 2) - (size / 2), (height / 2) - (size / 2), size, size);
  }
}
