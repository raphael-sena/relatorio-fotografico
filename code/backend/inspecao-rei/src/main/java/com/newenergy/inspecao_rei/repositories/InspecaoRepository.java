package com.newenergy.inspecao_rei.repositories;

import com.newenergy.inspecao_rei.models.Inspecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspecaoRepository extends JpaRepository<Inspecao, Long> {
}
