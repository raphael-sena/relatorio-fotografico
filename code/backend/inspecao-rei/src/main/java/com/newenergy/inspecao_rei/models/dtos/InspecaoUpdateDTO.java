package com.newenergy.inspecao_rei.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InspecaoUpdateDTO {
    private Long id;
    private String cliente;
    private String pedidoCompra;
}
