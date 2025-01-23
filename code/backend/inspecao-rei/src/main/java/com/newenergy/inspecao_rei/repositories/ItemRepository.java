package com.newenergy.inspecao_rei.repositories;

import com.newenergy.inspecao_rei.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
