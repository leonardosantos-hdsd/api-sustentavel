package com.api.sustentavel.dto;

import com.api.sustentavel.model.enums.CategoriaAcao;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AcaoSustentavelRequest {

    @NotBlank(message = "O título é obrigatório.")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres.")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(min = 10, message = "A descrição deve ter pelo menos 10 caracteres.")
    private String descricao;

    @NotNull(message = "A categoria é obrigatória.")
    private CategoriaAcao categoria;

    @NotNull(message = "A data de realização é obrigatória.")
    @PastOrPresent(message = "A data de realização deve ser hoje ou no passado.")
    private LocalDate dataRealizacao;

    @NotBlank(message = "O responsável é obrigatório.")
    @Size(max = 100, message = "O nome do responsável deve ter no máximo 100 caracteres.")
    private String responsavel;

    // Getters e Setters
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
