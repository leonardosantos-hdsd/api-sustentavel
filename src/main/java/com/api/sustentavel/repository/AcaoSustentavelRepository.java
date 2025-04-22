package com.api.sustentavel.repository;

import com.api.sustentavel.model.AcaoSustentavel;
import com.api.sustentavel.model.enums.CategoriaAcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcaoSustentavelRepository extends JpaRepository<AcaoSustentavel, Long> {
    List<AcaoSustentavel> findByCategoria(CategoriaAcao categoria);
}
