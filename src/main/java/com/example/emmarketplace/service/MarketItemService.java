package com.example.emmarketplace.service;


import com.example.emmarketplace.dto.MarketItemRequest;
import com.example.emmarketplace.entity.MarketItem;
import com.example.emmarketplace.entity.User;
import com.example.emmarketplace.repository.MarketItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MarketItemService {

    private final MarketItemRepository repository;


    @Autowired
    public MarketItemService(MarketItemRepository repository) {
        this.repository = repository;
    }

    public Page<MarketItem> getAllItems(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public MarketItem getItem(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
    }

    public MarketItem createItem(MarketItemRequest request, User user) {
        MarketItem item = new MarketItem();
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        item.setDescription(request.getDescription());
        item.setPhotoUrl(request.getPhotoUrl());
        item.setSubmissionTime(LocalDateTime.now());

        item.setUser(user);

        return repository.save(item);
    }

    public Page<MarketItem> getSortedItems(String sortKey, boolean asc, Pageable pageable) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortKey);
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );

        return repository.findAll(sortedPageable);

    }
}















