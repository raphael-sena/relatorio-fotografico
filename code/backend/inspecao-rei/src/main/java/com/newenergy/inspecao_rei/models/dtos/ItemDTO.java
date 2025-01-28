package com.newenergy.inspecao_rei.models.dtos;

import com.newenergy.inspecao_rei.models.Inspecao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.net.ssl.SSLSession;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {

    private Long id;
    private String codigo;
    private byte[] imagem;
    private Long inspecaoId;
}
