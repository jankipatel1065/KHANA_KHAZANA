package com.humber.khana_khazana.repositories;

import com.humber.khana_khazana.models.Item;
import com.humber.khana_khazana.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends CrudRepository<Item, Long> {
    List<Item> findByProduct_Id(Long productId);

    List<Item> findByProduct(Product product);
    List<Item> findByOrderOrderId(Long orderId);
}
