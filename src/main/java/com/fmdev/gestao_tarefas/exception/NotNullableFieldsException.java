package com.fmdev.gestao_tarefas.exception;

public class NotNullableFieldsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public NotNullableFieldsException(String message) {
        super(message);
    }
}
