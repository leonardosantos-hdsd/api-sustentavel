package com.api.sustentavel.model;

import com.api.sustentavel.model.enums.CategoriaAcao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "acoes_sustentaveis")
public class AcaoSustentavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private CategoriaAcao categoria;

    @NotNull
    @Column(name = "data_realizacao", nullable = false)
    private LocalDate dataRealizacao;

    @NotBlank
    @Column(nullable = false)
    private String responsavel;

    // Getters e Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public CategoriaAcao getCategoria() { return categoria; }

    public void setCategoria(CategoriaAcao categoria) { this.categoria = categoria; }

    public LocalDate getDataRealizacao() { return dataRealizacao; }

    public void setDataRealizacao(LocalDate dataRealizacao) { this.dataRealizacao = dataRealizacao; }

    public String getResponsavel() { return responsavel; }

    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
}
