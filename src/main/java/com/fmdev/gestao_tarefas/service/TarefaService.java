package com.fmdev.gestao_tarefas.service;

import com.fmdev.gestao_tarefas.dto.StatusDto;
import com.fmdev.gestao_tarefas.dto.TarefaDto;
import com.fmdev.gestao_tarefas.exception.NotFoundObjectException;
import com.fmdev.gestao_tarefas.exception.NotNullableFieldsException;
import com.fmdev.gestao_tarefas.model.Tarefa;
import com.fmdev.gestao_tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.fmdev.gestao_tarefas.enums.StatusTarefa.fromDescricao;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository rep;

    @Transactional
    public TarefaDto save(TarefaDto tarefaDto) {

        Tarefa tarefa = new Tarefa(
                tarefaDto.getTitulo(),
                tarefaDto.getDescricao(),
                fromDescricao(tarefaDto.getStatus()));

        validateFields(tarefa);
        rep.save(tarefa);

        return new TarefaDto(
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getStatus().toString());
    }

    public List<TarefaDto> listAll() {
        return rep.findAll()
                .stream()
                .map(tarefa -> new TarefaDto(
                        tarefa.getTitulo(),
                        tarefa.getDescricao(),
                        tarefa.getStatus().getDescricaoStatus()))
                .toList();
    }

    public Tarefa getTarefaById(Long id) {

        Optional<Tarefa> tarefaOpt = rep.findById(id);

        if(tarefaOpt.isEmpty()) {
            throw new NotFoundObjectException("O id: "+ id.toString() +" não se encontra na base de dados!");
        }
        return tarefaOpt.get();
    }

    @Transactional
    public Tarefa updateTarefaById(Long id, StatusDto statusDto) {

        Tarefa uptTarefa = this.getTarefaById(id);

        uptTarefa.setStatus(fromDescricao(statusDto.getStatus()));
        uptTarefa.setId(id);

        return rep.save(uptTarefa);
    }

    @Transactional
    public void deleteTarefaById(Long id) {

        Tarefa deleteTarefa = this.getTarefaById(id);
        rep.deleteById(deleteTarefa.getId());
    }

    private void validateFields(Tarefa tarefa) {

        if(tarefa.getTitulo() == null || tarefa.getTitulo().isBlank()) {
            throw new NotNullableFieldsException("O campo de título não pode ser nulo ou vazio!");
        } else if(tarefa.getStatus() == null) {
            throw new NotNullableFieldsException("O campo de status não pode ser nulo ou vazio!");
        }
    }
}
