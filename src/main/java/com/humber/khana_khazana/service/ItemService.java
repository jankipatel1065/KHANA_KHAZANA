package com.humber.khana_khazana.service;

import com.humber.khana_khazana.models.Item;
import com.humber.khana_khazana.models.Order;
import com.humber.khana_khazana.models.Product;
import com.humber.khana_khazana.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private ItemRepo itemRepo;
    @Autowired
    private ItemService(ItemRepo itemRepo)
    {
        this.itemRepo=itemRepo;
    }
    public List<Item> getAllItems() {
        return (List<Item>) itemRepo.findAll();
    }
    public void addItems(Item p)
    {
        this.itemRepo.save(p);
    }

    public void deleteItemsByProductId(Long productId) {
        List<Item> items = itemRepo.findByProduct_Id(productId);
        for (Item item : items) {
            itemRepo.delete(item);
        }
    }

    public void deleteItemsByProduct(Product product) {
        List<Item> items = itemRepo.findByProduct(product);
        itemRepo.deleteAll(items);
    }
    public List<Item> getItemsByOrderId(Long orderId) {
        return itemRepo.findByOrderOrderId(orderId);
    }
}
