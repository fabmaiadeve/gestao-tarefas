package com.fmdev.gestao_tarefas.dto;

import java.util.Objects;

public class TarefaDto {

    private String titulo;

    private String descricao;

    private String status;

    public TarefaDto() {
    }

    public TarefaDto(String titulo, String descricao, String status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TarefaDto tarefaDto = (TarefaDto) o;
        return Objects.equals(titulo, tarefaDto.titulo) &&
                Objects.equals(descricao, tarefaDto.descricao) &&
                Objects.equals(status, tarefaDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, descricao, status);
    }

    @Override
    public String toString() {
        return "TarefaDto{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
