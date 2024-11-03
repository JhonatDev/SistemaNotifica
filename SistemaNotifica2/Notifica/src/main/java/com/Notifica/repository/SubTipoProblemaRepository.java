package com.Notifica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Notifica.entity.SubTipoProblema;
import com.Notifica.entity.SubTipoProblema.TipoProblema;

import java.util.List;

@Repository
public interface SubTipoProblemaRepository extends JpaRepository<SubTipoProblema, Long> {

    List<SubTipoProblema> findByTipoProblema(TipoProblema tipo);

    void deleteBySubtipoProblema(String subtipoProblema);

}