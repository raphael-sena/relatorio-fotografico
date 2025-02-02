package com.newenergy.inspecao_rei.repositories;

import com.newenergy.inspecao_rei.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
