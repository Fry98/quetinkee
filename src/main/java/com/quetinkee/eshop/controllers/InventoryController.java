package com.quetinkee.eshop.controllers;

import com.quetinkee.eshop.model.projection.FlowersInStockList;
import com.quetinkee.eshop.model.projection.FlowersToRestockList;
import com.quetinkee.eshop.service.InventoryService;
import com.quetinkee.eshop.utils.helpers.FlowerSInStockEdit;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Slice<FlowersInStockList> getSlice(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        return this.service.findAll(page, size);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<FlowersInStockList> patchAll(@Valid @RequestBody Set<FlowerSInStockEdit> newStock) {
        return this.service.updateStock(newStock);
    }

    @GetMapping(value = "/restock", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<FlowersToRestockList> getWhatRestock() {
        return this.service.getWhatRestock();
    }
}