package com.example.emmarketplace.controller;


import com.example.emmarketplace.dto.MarketItemRequest;
import com.example.emmarketplace.entity.MarketItem;
import com.example.emmarketplace.entity.User;
import com.example.emmarketplace.service.MarketItemService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/market")
public class MarketController {
    private final MarketItemService service;

    @Autowired
    public MarketController(MarketItemService service){
        this.service = service;
    }

    @GetMapping
    public Map<String, Object> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDesc") String sort
    ) {
        Sort sorting;

        switch (sort) {
            case "dateAsc" -> sorting = Sort.by("submissionTime").ascending();
            case "priceAsc" -> sorting = Sort.by("price").ascending();
            case "priceDesc" -> sorting = Sort.by("price").descending();
            default -> sorting = Sort.by("submissionTime").descending();
        }

        Page<MarketItem> pageData = service.getAllItems(PageRequest.of(page, size, sorting));

        Map<String, Object> body = new HashMap<>();
        body.put("items", pageData.getContent());
        body.put("currentPage", pageData.getNumber());
        body.put("totalPages", pageData.getTotalPages());
        return body;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MarketItemRequest request, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to post an item.");
        }


        MarketItem saved = service.createItem(request, loggedInUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/item/" + saved.getId())
                .body(saved);
    }

}
