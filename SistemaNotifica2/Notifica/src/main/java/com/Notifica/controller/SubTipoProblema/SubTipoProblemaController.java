package com.Notifica.controller.SubTipoProblema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.Notifica.entity.SubTipoProblema;
import com.Notifica.entity.SubTipoProblema.TipoProblema;
import com.Notifica.service.SubTipoProblemaService;

import jakarta.validation.Valid;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Validated
@RequestMapping("/subtipoproblemas")
public class SubTipoProblemaController {

    @Autowired
    private SubTipoProblemaService subTipoProblemaService;

    // cria um novo subtipo de problema
    @PostMapping("/criar/{tipoProblema}/{subtipoProblema}")
    public ResponseEntity<SubTipoProblema> criarSubTipoProblema(@Valid @PathVariable String tipoProblema, @PathVariable String subtipoProblema) {
        SubTipoProblema subTipoProblema = new SubTipoProblema();
        subTipoProblema.setTipoProblema(SubTipoProblema.TipoProblema.valueOf(tipoProblema));
        subTipoProblema.setSubtipoProblema(subtipoProblema);
       try {
            SubTipoProblema novoSubTipoProblema = subTipoProblemaService.criarSubTipoProblema(subTipoProblema);
            return new ResponseEntity<>(novoSubTipoProblema, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    

    @GetMapping("/listar")
    public ResponseEntity<List<SubTipoProblema>> getSubTipoProblemas() {
        try {
            List<SubTipoProblema> subTipos = subTipoProblemaService.getSubTipoProblemas();
            return new ResponseEntity<>(subTipos, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // retorna um subtipo de problema por tipo de problema
    @GetMapping("/listar/{tipoProblema}")
    public ResponseEntity<List<SubTipoProblema>> getSubTipoProblemaByTipoProblema(@Valid @PathVariable String tipoProblema) {
        try {
            TipoProblema tipo = TipoProblema.valueOf(tipoProblema.toUpperCase());
            List<SubTipoProblema> subTipos = subTipoProblemaService.getSubTipoProblemaByTipoProblema(tipo);
            return new ResponseEntity<>(subTipos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // exclui um subtipo de problema por SubtipoProblema
    @DeleteMapping("/deletar/{subtipoProblema}")
    public ResponseEntity<Status> deleteSubTipoProblema(@PathVariable String subtipoProblema) {
        try {
            subTipoProblemaService.deleteSubTipoProblema(subtipoProblema);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    
}
