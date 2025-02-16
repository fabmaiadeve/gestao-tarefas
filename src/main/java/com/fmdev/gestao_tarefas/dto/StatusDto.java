package com.fmdev.gestao_tarefas.dto;

import java.util.Objects;

public class StatusDto {

    private String status;

    public StatusDto() {
    }

    public StatusDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusDto statusDto = (StatusDto) o;
        return Objects.equals(status, statusDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "StatusDto{" +
                "status='" + status + '\'' +
                '}';
    }
}
