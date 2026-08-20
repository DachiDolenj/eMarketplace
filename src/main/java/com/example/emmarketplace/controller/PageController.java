package com.example.emmarketplace.controller;

import com.example.emmarketplace.entity.MarketItem;
import com.example.emmarketplace.service.MarketItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    private final MarketItemService service;

    public PageController(MarketItemService service){
        this.service = service;
    }

    @GetMapping("/items")
    public String showItemsPage() {
        return "items";
    }

    @GetMapping("/item/{id}")
    public String showItemDetail(@PathVariable long id, Model model) {
        MarketItem item = service.getItem(id);
        model.addAttribute("item", item);
        return "item";
    }
}