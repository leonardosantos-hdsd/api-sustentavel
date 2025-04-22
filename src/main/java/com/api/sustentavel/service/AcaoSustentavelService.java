package com.api.sustentavel.service;

import com.api.sustentavel.dto.AcaoSustentavelRequest;
import com.api.sustentavel.dto.AcaoSustentavelResponse;
import com.api.sustentavel.model.AcaoSustentavel;
import com.api.sustentavel.repository.AcaoSustentavelRepository;
import jakarta.persistence.EntityNotFoundException;
import com.api.sustentavel.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import com.api.sustentavel.model.enums.CategoriaAcao;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcaoSustentavelService {

    private final AcaoSustentavelRepository repository;

    public AcaoSustentavelService(AcaoSustentavelRepository repository) {
        this.repository = repository;
    }

    public List<AcaoSustentavelResponse> listarTodas() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AcaoSustentavelResponse buscarPorId(Long id) {
        AcaoSustentavel acao = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ação com ID " + id + " não encontrada"));
//                .orElseThrow(() -> new EntityNotFoundException("Ação com ID " + id + " não encontrada"));
        return toResponse(acao);
    }

    public AcaoSustentavelResponse criarAcao(AcaoSustentavelRequest request) {
        AcaoSustentavel novaAcao = toEntity(request);
        return toResponse(repository.save(novaAcao));
    }

    public AcaoSustentavelResponse atualizar(Long id, AcaoSustentavelRequest request) {
        AcaoSustentavel existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ação com ID " + id + " não encontrada"));

        existente.setTitulo(request.getTitulo());
        existente.setDescricao(request.getDescricao());
        existente.setCategoria(request.getCategoria());
        existente.setDataRealizacao(request.getDataRealizacao());
        existente.setResponsavel(request.getResponsavel());

        return toResponse(repository.save(existente));
    }

    public void remover(Long id) {
        if (!repository.existsById(id)) {
//            throw new EntityNotFoundException("Ação com ID " + id + " não encontrada");
            throw new RecursoNaoEncontradoException("Ação com ID " + id + " não encontrada");
        }
        repository.deleteById(id);
    }

    // Conversores auxiliares
    private AcaoSustentavelResponse toResponse(AcaoSustentavel acao) {
        return new AcaoSustentavelResponse(
                acao.getId(),
                acao.getTitulo(),
                acao.getDescricao(),
                acao.getCategoria(),
                acao.getDataRealizacao(),
                acao.getResponsavel()
        );
    }

    private AcaoSustentavel toEntity(AcaoSustentavelRequest dto) {
        AcaoSustentavel acao = new AcaoSustentavel();
        acao.setTitulo(dto.getTitulo());
        acao.setDescricao(dto.getDescricao());
        acao.setCategoria(dto.getCategoria());
        acao.setDataRealizacao(dto.getDataRealizacao());
        acao.setResponsavel(dto.getResponsavel());
        return acao;
    }

    public List<AcaoSustentavelResponse> buscarPorCategoria(CategoriaAcao categoria) {
        return repository.findByCategoria(categoria).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

}
