package com.fmdev.gestao_tarefas.controller;

import com.fmdev.gestao_tarefas.dto.StatusDto;
import com.fmdev.gestao_tarefas.dto.TarefaDto;
import com.fmdev.gestao_tarefas.model.Tarefa;
import com.fmdev.gestao_tarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService service;

    @PostMapping
    public ResponseEntity<TarefaDto> saveTarefa(@RequestBody TarefaDto tarefaDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(tarefaDto));
    }

    @GetMapping
    public ResponseEntity<List<TarefaDto>> buscarTodas() {

        return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDto> getTarefaById(@PathVariable (value = "id") Long id) {

        Tarefa tarefa = service.getTarefaById(id);
        TarefaDto tarefaResponse = new TarefaDto(tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getStatus().toString());
        return ResponseEntity.status(HttpStatus.OK).body(tarefaResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusDto> updateTarefaById(@PathVariable (value = "id") Long id, @RequestBody StatusDto statusDto) {

        Tarefa tarefa = service.updateTarefaById(id, statusDto);
        StatusDto statusResponse = new StatusDto(tarefa.getStatus().getDescricaoStatus());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(statusResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefaById(@PathVariable (value = "id") Long id) {

        service.deleteTarefaById(id);
        return ResponseEntity.noContent().build();
    }




}
