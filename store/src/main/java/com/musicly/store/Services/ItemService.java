package com.musicly.store.Services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.musicly.store.Domain.Item.Item;
import com.musicly.store.Domain.Item.ItemRepository;
import com.musicly.store.Models.ItemDTO;

@Service
public class ItemService {
    private final ItemRepository itemRepository; 

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public List<Item> GetAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> GetItemById(int id) {
        return itemRepository.findById(id);
    }

    public Item AddItem(Item item){
        return itemRepository.save(item);
    }

    public Item UpdateItem(Item item){
        return itemRepository.save(item);
    }

    public void DeleteItem(int id){
        itemRepository.deleteById(id);
    }
}
