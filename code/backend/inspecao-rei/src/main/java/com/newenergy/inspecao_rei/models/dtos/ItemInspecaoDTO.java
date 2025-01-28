package com.newenergy.inspecao_rei.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemInspecaoDTO {
    private String codigo;
    private String imagemBase64;
}
