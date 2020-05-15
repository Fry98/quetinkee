package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Order;
import com.quetinkee.eshop.model.projection.OrderList;
import com.quetinkee.eshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<OrderList> getPage(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        return this.orderService.getSlice(page, size);
    }

    @PutMapping(value = "/{id:[\\d]+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateId(@PathVariable("id") Integer id, @RequestBody Order order) {
        Order current = this.getOrder(id);
        // TODO
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id:[\\d]+}")
    public ResponseEntity deleteId(@PathVariable("id") Integer id) {
        Order order = this.getOrder(id);
        orderService.delete(order);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private Order getOrder(Integer id) throws ResponseStatusException {
        Order order = this.orderService.find(id);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order nenalezen");
        }
        return order;
    }

}
