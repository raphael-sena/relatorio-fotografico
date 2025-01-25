package com.newenergy.inspecao_rei.models.dtos;

import com.newenergy.inspecao_rei.models.Relatorio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {

    private Long id;
    private String codigo;
    private byte[] imagem;
    private Long relatorioId;
}
