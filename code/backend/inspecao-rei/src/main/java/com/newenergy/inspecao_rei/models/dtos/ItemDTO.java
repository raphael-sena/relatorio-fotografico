package com.newenergy.inspecao_rei.models.dtos;

import com.newenergy.inspecao_rei.models.Relatorio;

public class ItemDTO {

    private Long id;
    private byte[] imagem;
    private Relatorio relatorio;

    public ItemDTO() {
    }

    public ItemDTO(Long id, byte[] imagem, Relatorio relatorio) {
        this.id = id;
        this.imagem = imagem;
        this.relatorio = relatorio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }
}
