package com.fmdev.gestao_tarefas.controller;

import com.fmdev.gestao_tarefas.dto.StatusDto;
import com.fmdev.gestao_tarefas.dto.TarefaDto;
import com.fmdev.gestao_tarefas.enums.StatusTarefa;
import com.fmdev.gestao_tarefas.exception.IllegalFieldsException;
import com.fmdev.gestao_tarefas.exception.NotFoundObjectException;
import com.fmdev.gestao_tarefas.exception.NotNullableFieldsException;
import com.fmdev.gestao_tarefas.model.Tarefa;
import com.fmdev.gestao_tarefas.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TarefaControllerTest {

    @Mock private TarefaService service;
    @InjectMocks private TarefaController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarTarefaDeveRetornarHttpStatus201() {

        TarefaDto validTarefaDto = new TarefaDto("TarefaSuccess", "TarefaSuccessDescription", "Pendente");
        TarefaDto validTarefaSaved = new TarefaDto("TarefaSuccess", "TarefaSuccessDescription", "Pendente");

        when(service.save(validTarefaSaved)).thenReturn(validTarefaSaved);

        ResponseEntity<TarefaDto> response = controller.saveTarefa(validTarefaDto);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(validTarefaSaved, response.getBody());
    }

    @Test
    public void testSalvarTarefaDeveRetornarHttpStatus406() {

        TarefaDto invalidTarefaDto = null;

        doThrow(NotNullableFieldsException.class).when(service).save(null);

        assertThrows(NotNullableFieldsException.class, () -> controller.saveTarefa(invalidTarefaDto));
    }

    @Test
    public void testEncontrarTarefaPorIdDeveRetornarHttp200() {

        Long validId = 1L;
        Tarefa validTarefa = new Tarefa("Título", "Descrição", StatusTarefa.PENDENTE);
        validTarefa.setId(validId);
        Optional<Tarefa> expectedTarefa = Optional.of(validTarefa);
        TarefaDto validTarefaDto = new TarefaDto(
                expectedTarefa.get().getTitulo(),
                expectedTarefa.get().getDescricao(),
                expectedTarefa.get().getStatus().toString());

        when(service.getTarefaById(validId)).thenReturn(expectedTarefa.get());
        ResponseEntity<TarefaDto> response = controller.getTarefaById(validId);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(validTarefaDto, response.getBody());
    }

    @Test
    public void testEncontrarTarefaPorIdDeveRetornarHttp404() {

        Long invalidId = 99L;

        doThrow(NotFoundObjectException.class).when(service).getTarefaById(invalidId);

        assertThrows(NotFoundObjectException.class, () -> controller.getTarefaById(invalidId));
    }

    @Test
    public void testUpdateMoradorByIdDeveRetornarHttp204() {

        Long validId = 1L;
        StatusDto uptStatusDto = new StatusDto("Pendente");
        Tarefa updatedTarefa = new Tarefa("Título", "Descrição", StatusTarefa.PENDENTE);

        when(service.updateTarefaById(validId, uptStatusDto)).thenReturn(updatedTarefa);

        ResponseEntity<StatusDto> response = controller.updateTarefaById(validId, uptStatusDto);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(uptStatusDto, response.getBody());
    }

    @Test
    public void testUpdateTarefaPorIdInvalidoDeveRetornarHttp404() {

        Long invalidId = 99L;
        StatusDto uptStatusDto = new StatusDto("Pendente");

        doThrow(NotFoundObjectException.class).when(service).updateTarefaById(invalidId, uptStatusDto);

        assertThrows(NotFoundObjectException.class, () -> controller.updateTarefaById(invalidId, uptStatusDto));
    }

    @Test
    public void testUpdateTarefaPorStatusInvalidoDeveRetornarHttp404() {

        Long validId = 1L;
        StatusDto uptInvalidStatusDto = new StatusDto("Pendent");

        doThrow(IllegalFieldsException.class).when(service).updateTarefaById(validId, uptInvalidStatusDto);

        assertThrows(IllegalFieldsException.class, () -> controller.updateTarefaById(validId, uptInvalidStatusDto));
    }

    @Test
    public void testDeleteTarefaPorIdDeveRetornarHttp204() {

        Long validId = 1L;

        doNothing().when(service).deleteTarefaById(validId);

        ResponseEntity<Void> response = controller.deleteTarefaById(validId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(service).deleteTarefaById(validId);
    }

    @Test
    public void testDeleteTarefaPorIdInvalidoDeveRetornarHttp404() {

        Long invalidId = 99L;
        TarefaDto delTarefaDto = new TarefaDto("TarefaSuccess", "TarefaSuccessDescription", "Pendente");

        doThrow(NotFoundObjectException.class).when(service).deleteTarefaById(invalidId);

        assertThrows(NotFoundObjectException.class, () -> controller.deleteTarefaById(invalidId));
    }
}
