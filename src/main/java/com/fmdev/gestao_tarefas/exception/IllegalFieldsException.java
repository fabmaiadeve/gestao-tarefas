package com.fmdev.gestao_tarefas.exception;

public class IllegalFieldsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public IllegalFieldsException(String message) {
        super(message);
    }
}

