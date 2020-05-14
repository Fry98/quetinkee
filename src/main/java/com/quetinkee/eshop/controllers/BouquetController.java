package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Bouquet;
import com.quetinkee.eshop.service.BouquetService;
import com.quetinkee.eshop.utils.helpers.BouquetEdit;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.quetinkee.eshop.model.projection.BouquetList;
import com.quetinkee.eshop.model.projection.OptionList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/bouquets")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class BouquetController {

  @Autowired
  protected BouquetService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Slice<BouquetList> getSlice(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
    return this.service.getSlice(page, size);
  }

  @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<OptionList> getList() {
    return this.service.getList();
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity create(@Valid @RequestPart("bouquet") BouquetEdit bouquet, @RequestPart(name =  "blob", required = false) MultipartFile file) {
    this.service.create(bouquet, file);
    return new ResponseEntity(bouquet.getId(), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public BouquetEdit getId(@PathVariable("id") Integer id) {
    return new BouquetEdit(this.getRecord(id));
  }

  @PatchMapping(value = "/{id:[\\d]+}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public BouquetEdit updateId(@PathVariable("id") Integer id, @RequestPart("bouquet") BouquetEdit bouquet, @RequestPart(name =  "blob", required = false) MultipartFile file) {
    Bouquet original = this.getRecord(id);
    original = this.service.update(original, bouquet, file);
    return new BouquetEdit(original);
  }

  @DeleteMapping(value = "/{id:[\\d]+}")
  public ResponseEntity deleteId(@PathVariable("id") Integer id) {
    Bouquet record = this.getRecord(id);
    this.service.delete(record);
    return new ResponseEntity(HttpStatus.OK);
  }

  protected Bouquet getRecord(Integer id) throws ResponseStatusException {
    Bouquet record = this.service.find(id);
    if (record == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Záznam nenalezen");
    }
    return record;
  }
}
