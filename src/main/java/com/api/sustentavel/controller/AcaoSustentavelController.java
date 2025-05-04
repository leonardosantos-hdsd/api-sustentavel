package com.api.sustentavel.controller;

import com.api.sustentavel.dto.AcaoSustentavelRequest;
import com.api.sustentavel.dto.AcaoSustentavelResponse;
import com.api.sustentavel.model.enums.CategoriaAcao;
import com.api.sustentavel.service.AcaoSustentavelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/acoes")
public class AcaoSustentavelController {

    private final AcaoSustentavelService service;

    public AcaoSustentavelController(AcaoSustentavelService service) {
        this.service = service;
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<AcaoSustentavelResponse>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<AcaoSustentavelResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AcaoSustentavelResponse> criar(@RequestBody @Valid AcaoSustentavelRequest request) {
        return ResponseEntity.ok(service.criarAcao(request));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AcaoSustentavelResponse> atualizar(@PathVariable Long id,
                                                             @RequestBody @Valid AcaoSustentavelRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/categoria")
    public ResponseEntity<List<AcaoSustentavelResponse>> buscarPorCategoria(
            @RequestParam("tipo") CategoriaAcao categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }

}
