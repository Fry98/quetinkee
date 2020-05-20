package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Order;
import com.quetinkee.eshop.model.enums.OrderStatus;
import com.quetinkee.eshop.model.projection.OrderList;
import com.quetinkee.eshop.service.OrderService;
import com.quetinkee.eshop.utils.helpers.OrderEdit;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/orders")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping(value = {"","{status}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<OrderList> getSlice(@PathVariable(name = "status", required = false) OrderStatus status, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        if (status == null) return this.service.getSlice(page, size);
        return this.service.getSlice(status, page, size);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@Valid @RequestBody OrderEdit record) {
        this.service.create(record);
        return new ResponseEntity(record.getId(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id:[\\d]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderEdit getId(@PathVariable("id") Integer id) {
        return new OrderEdit(this.getRecord(id));
    }

    @GetMapping(value = "/{id:[\\d]+}/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order getIdInfo(@PathVariable("id") Integer id) {
        return this.getRecord(id);
    }

    @PatchMapping(value = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderEdit updateId(@PathVariable("id") Integer id, @RequestBody OrderEdit record) {
        Order original = this.getRecord(id);
        original = this.service.update(original, record);
        return new OrderEdit(original);
    }

    @PatchMapping(value = "/{id:[\\d]+}/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(@PathVariable("id") Integer id, @PathVariable("status") OrderStatus status) {
        Order order = this.getRecord(id);
        this.service.changeStatus(order, status);
    }

    @DeleteMapping(value = "/{id:[\\d]+}")
    public ResponseEntity deleteId(@PathVariable("id") Integer id) {
        Order record = this.getRecord(id);
        this.service.delete(record);
        return new ResponseEntity(HttpStatus.OK);
    }

    private Order getRecord(Integer id) throws ResponseStatusException {
        Order record = this.service.find(id);
        if (record == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Záznam nenalezen");
        }
        return record;
    }
}
