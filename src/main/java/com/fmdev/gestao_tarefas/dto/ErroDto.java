package com.fmdev.gestao_tarefas.dto;

public class ErroDto {
    private String message;
    private Integer codeHttpError;

    public ErroDto() {
    }

    public ErroDto(String message, Integer codeHttpError) {
        this.message = message;
        this.codeHttpError = codeHttpError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCodeHttpError() {
        return codeHttpError;
    }

    public void setCodeHttpError(Integer codeHttpError) {
        this.codeHttpError = codeHttpError;
    }
}
