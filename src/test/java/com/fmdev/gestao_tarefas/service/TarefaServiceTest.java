package com.fmdev.gestao_tarefas.service;

import com.fmdev.gestao_tarefas.dto.StatusDto;
import com.fmdev.gestao_tarefas.dto.TarefaDto;
import com.fmdev.gestao_tarefas.enums.StatusTarefa;
import com.fmdev.gestao_tarefas.exception.IllegalFieldsException;
import com.fmdev.gestao_tarefas.exception.NotFoundObjectException;
import com.fmdev.gestao_tarefas.exception.NotNullableFieldsException;
import com.fmdev.gestao_tarefas.model.Tarefa;
import com.fmdev.gestao_tarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock private TarefaRepository rep;
    @InjectMocks private TarefaService service;

    private TarefaDto validTarefaDto;
    private TarefaDto validTarefaDto01;
    private Tarefa validTarefa;
    private Tarefa validTarefa01;

    private StatusDto uptStatusDto;
    private Tarefa existingTarefa;

    @BeforeEach
    public void setUp() {
        validTarefaDto = new TarefaDto("TarefaSuccess", "TarefaSuccessDescription", "Pendente");
        validTarefaDto01 = new TarefaDto("TarefaSuccess01", "TarefaSuccessDescription01", "Concluída");
        validTarefa = new Tarefa("TarefaSuccess", "TarefaSuccessDescription", StatusTarefa.PENDENTE);
        validTarefa01 = new Tarefa("TarefaSuccess01", "TarefaSuccessDescription01", StatusTarefa.CONCLUIDA);
        uptStatusDto = new StatusDto("Pendente");
        existingTarefa = new Tarefa("existingTarefaSuccess", "existingTarefaSuccessDescription", StatusTarefa.PENDENTE);
    }

    @Test
    public void testDeveSalvarTarefaComSucesso() {

        Tarefa expectedTarefa = new Tarefa(
                validTarefaDto.getTitulo(),
                validTarefaDto.getDescricao(),
                StatusTarefa.fromDescricao(validTarefaDto.getStatus()));

        TarefaDto expectedTarefaDto = new TarefaDto(
                expectedTarefa.getTitulo(),
                expectedTarefa.getDescricao(),
                expectedTarefa.getStatus().toString());

        when(rep.save(any())).thenReturn(expectedTarefa);

        TarefaDto savedTarefaDto = service.save(validTarefaDto);

        assertNotNull(savedTarefaDto);
        assertEquals(expectedTarefaDto, savedTarefaDto);
    }

    @Test
    public void testDeveLancarExceptionQuandoTituloEstaNulo() {

        validTarefaDto.setTitulo(null);
        NotNullableFieldsException exception = assertThrows(NotNullableFieldsException.class,
                () -> service.save(validTarefaDto));

        assertEquals("O campo de título não pode ser nulo ou vazio!", exception.getMessage());
    }

    @Test
    public void testDeveLancarExceptionQuandoStatusEstaNulo() {

        validTarefaDto.setStatus(null);
        IllegalFieldsException exception = assertThrows(IllegalFieldsException.class,
                () -> service.save(validTarefaDto));

        assertEquals("Nenhum StatusTarefa encontrado para o label: null", exception.getMessage());
    }

    @Test
    public void testDeveLancarExceptionQuandoStatusEstaIncorreto() {

        validTarefaDto.setStatus("Pendent");
        IllegalFieldsException exception = assertThrows(IllegalFieldsException.class,
                () -> service.save(validTarefaDto));

        assertEquals("Nenhum StatusTarefa encontrado para o label: Pendent", exception.getMessage());
    }

    @Test
    void testDeveRetornarListaVaziaQuandoNaoHouverTarefas() {

        when(rep.findAll()).thenReturn(List.of());

        List<TarefaDto> result = service.listAll();

        assertTrue(result.isEmpty());
        verify(rep, times(1)).findAll();
        verifyNoMoreInteractions(rep);
    }

    @Test
    void testDeveRetornarListaComUmaTarefa() {

        when(rep.findAll()).thenReturn(List.of(validTarefa));

        List<TarefaDto> result = service.listAll();

        assertEquals(1, result.size());
        assertEquals(validTarefaDto, result.get(0));
        verify(rep, times(1)).findAll();
    }

    @Test
    void testDeveRetornarListaComMultiplasTarefas() {

        when(rep.findAll()).thenReturn(List.of(validTarefa, validTarefa01));

        List<TarefaDto> result = service.listAll();

        assertEquals(2, result.size());
        assertThat(result).containsExactly(validTarefaDto, validTarefaDto01);
        verify(rep, times(1)).findAll();
    }

    @Test
    void testDeveMapearCorretamenteTodosOsCampos() {

        Tarefa tarefa = new Tarefa("Título Teste", "Descrição Teste", StatusTarefa.EM_ANDAMENTO);
        when(rep.findAll()).thenReturn(List.of(tarefa));

        TarefaDto resultDto = service.listAll().get(0);

        assertEquals("Título Teste", resultDto.getTitulo());
        assertEquals("Descrição Teste", resultDto.getDescricao());
        assertEquals(StatusTarefa.EM_ANDAMENTO.getDescricaoStatus(), resultDto.getStatus());
    }

    @Test
    public void testDeveRetornarTarefaPorIdComSucesso() {

        validTarefa.setId(1L);
        Optional<Tarefa> expectedTarefaOpt = Optional.of(validTarefa);

        when(rep.findById(validTarefa.getId())).thenReturn(expectedTarefaOpt);

        Tarefa savedTarefa = service.getTarefaById(validTarefa.getId());

        assertEquals(expectedTarefaOpt.get(), savedTarefa);
    }

    @Test
    public void testDeveRetornarExceptionQuandoTarefaNaoEncontrada() {

        Long invalidId = 99L;

        when(rep.findById(invalidId)).thenReturn(Optional.empty());

        NotFoundObjectException exception = assertThrows(NotFoundObjectException.class, () -> service.getTarefaById(invalidId));

        assertEquals("O id: 99 não se encontra na base de dados!", exception.getMessage());
    }

    @Test
    public void testDeveAlterarTarefaComSucesso() {

        Long validId = 1L;

        when(rep.findById(validId)).thenReturn(Optional.of(existingTarefa));

        when(rep.save(any(Tarefa.class))).thenReturn(existingTarefa);

        Tarefa updatedTarefa = service.updateTarefaById(validId, uptStatusDto);

        assertNotNull(updatedTarefa);
        assertEquals(updatedTarefa.getStatus().getDescricaoStatus(), uptStatusDto.getStatus());
    }

    @Test
    public void testNaoDeveAlterarTarefaPorId() {

        Long invalidId = 99L;

        StatusDto statusDto = new StatusDto("Pendente");

        when(rep.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(NotFoundObjectException.class, () -> service.updateTarefaById(invalidId, statusDto));

        verify(rep).findById(invalidId);
        verify(rep, never()).save(any(Tarefa.class));
    }

    @Test
    public void testDeveDeletarTarefaComSucesso() {

        Long validId = 1L;

        Tarefa tarefa = new Tarefa("TítuloSuccess", "DescriçãoSuccess", StatusTarefa.PENDENTE);

        tarefa.setId(validId);

        when(rep.findById(validId)).thenReturn(Optional.of(tarefa));

        service.deleteTarefaById(validId);

        verify(rep).findById(validId);
        verify(rep).deleteById(validId);
    }

    @Test
    public void testNaoDeveDeletarTarefaQuandoIdNaoEncontrado() {

        Long invalidId = 99L;

        when(rep.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(NotFoundObjectException.class, () -> service.deleteTarefaById(invalidId));

        verify(rep).findById(invalidId);
        verify(rep, never()).deleteById(anyLong());
    }
}
