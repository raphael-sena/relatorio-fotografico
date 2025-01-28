package com.newenergy.inspecao_rei.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InspecaoDTO {
    private Long id;
    private LocalDate data;
    private String cliente;
    private String pedidoCompra;
    private List<ItemMinDTO> itens;
}
