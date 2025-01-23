package com.newenergy.inspecao_rei.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_inspecao")
public class Inspecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private String cliente;
    private String pedidoCompra;

    @OneToOne(mappedBy = "inspecao")
    private Relatorio relatorio;

    public Inspecao() {}

    public Inspecao(Long id, LocalDate data, String cliente, String pedidoCompra, Relatorio relatorio) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.pedidoCompra = pedidoCompra;
        this.relatorio = relatorio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getPedidoCompra() {
        return pedidoCompra;
    }

    public void setPedidoCompra(String pedidoCompra) {
        this.pedidoCompra = pedidoCompra;
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inspecao inspecao = (Inspecao) o;
        return Objects.equals(id, inspecao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
