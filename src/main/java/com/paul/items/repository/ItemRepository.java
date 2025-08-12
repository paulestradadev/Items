package com.paul.items.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paul.items.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

}
