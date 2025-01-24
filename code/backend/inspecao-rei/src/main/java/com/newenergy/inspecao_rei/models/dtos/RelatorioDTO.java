package com.newenergy.inspecao_rei.models.dtos;

import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RelatorioDTO {

    private Long id;
    private Inspecao inspecao;
    private List<Item> itens;
}
