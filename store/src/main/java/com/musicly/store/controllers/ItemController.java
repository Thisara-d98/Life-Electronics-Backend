package com.musicly.store.controllers;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.musicly.store.Domain.Item.Item;
import com.musicly.store.Services.ItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // @GetMapping("/item")
    // public Item getItem(@RequestParam int Id) {
    //     Optional<Item> item = itemService.GetItemById(Id);
    //     return item.orElse(null);
    // }

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return  itemService.GetAllItems();
    }
    
    @PostMapping("/items")
    public Item postItem(@RequestBody Item item) {
        return itemService.AddItem(item);
    }

    @PutMapping("item/{id}")
    public Item updateItem(@PathVariable int id, @RequestBody Item item) {
        return itemService.UpdateItem(item);
    }

    // @DeleteMapping("item/{id}")
    // public Item deleteItem(@PathVariable int id) {
    //     return itemService.DeleteItem(id);
    // }
}
