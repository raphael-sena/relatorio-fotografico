package com.newenergy.inspecao_rei.models.dtos;

import com.newenergy.inspecao_rei.models.Relatorio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InspecaoCreateDTO {

    private LocalDate data;
    private String cliente;
    private String pedidoCompra;
    private Relatorio relatorio;
}
