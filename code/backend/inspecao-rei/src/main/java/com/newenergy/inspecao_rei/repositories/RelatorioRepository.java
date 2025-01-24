package com.newenergy.inspecao_rei.repositories;

import com.newenergy.inspecao_rei.models.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
}
