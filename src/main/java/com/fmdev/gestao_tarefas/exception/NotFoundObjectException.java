package com.fmdev.gestao_tarefas.exception;

public class NotFoundObjectException  extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public NotFoundObjectException(String message) {
        super(message);
    }
}
