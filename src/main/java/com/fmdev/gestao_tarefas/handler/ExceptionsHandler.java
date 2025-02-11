package com.fmdev.gestao_tarefas.handler;

import com.fmdev.gestao_tarefas.dto.ErroDto;
import com.fmdev.gestao_tarefas.exception.IllegalFieldsException;
import com.fmdev.gestao_tarefas.exception.NotFoundObjectException;
import com.fmdev.gestao_tarefas.exception.NotNullableFieldsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    @ExceptionHandler(NotNullableFieldsException.class)
    public ErroDto handler(NotNullableFieldsException ex) {
        return new ErroDto(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(IllegalFieldsException.class)
    public ErroDto handler(IllegalFieldsException ex) {
        return new ErroDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotFoundObjectException.class)
    public ErroDto handler(NotFoundObjectException ex) {
        return new ErroDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }
}
