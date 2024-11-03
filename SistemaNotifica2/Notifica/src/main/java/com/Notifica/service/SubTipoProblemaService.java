package com.Notifica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Notifica.entity.SubTipoProblema;
import com.Notifica.entity.SubTipoProblema.TipoProblema;
import com.Notifica.repository.SubTipoProblemaRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class SubTipoProblemaService {

    @Autowired
    private SubTipoProblemaRepository subTipoProblemaRepository;

    public SubTipoProblema criarSubTipoProblema(SubTipoProblema subTipoProblema) {
        return subTipoProblemaRepository.save(subTipoProblema);
    }

    // retorna todos os subtipos de problema
    public List<SubTipoProblema> getSubTipoProblemas() {
        return subTipoProblemaRepository.findAll();
    }

    // retorna um subtipo de problema por tipo de problema
    public List<SubTipoProblema> getSubTipoProblemaByTipoProblema(TipoProblema tipo) {
        return subTipoProblemaRepository.findByTipoProblema(tipo);
    }

    // exclui um subtipo de problema por SubtipoProblema
    @Transactional
    public void deleteSubTipoProblema(String subtipoProblema) {
        subTipoProblemaRepository.deleteBySubtipoProblema(subtipoProblema);
    }

}
