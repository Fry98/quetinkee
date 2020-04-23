package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.Category;
import com.quetinkee.eshop.model.Order;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.service.OrderService;
import com.quetinkee.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<Order> getPage(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        return this.orderService.findAll(page, size, false);
    }









}
