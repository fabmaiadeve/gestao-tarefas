package com.fmdev.gestao_tarefas.enums;

import com.fmdev.gestao_tarefas.exception.IllegalFieldsException;

public enum StatusTarefa {

    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDA("Concluída");

    private String descricaoStatus;

    StatusTarefa(String descricaoStatus) {
        this.descricaoStatus = descricaoStatus;
    }

    public String getDescricaoStatus() {
        return descricaoStatus;
    }

    public void setDescricaoStatus(String descricaoStatus) {
        this.descricaoStatus = descricaoStatus;
    }

    public static StatusTarefa fromDescricao(String descricaoStatus) {
        for (StatusTarefa status : StatusTarefa.values()) {
            if (status.descricaoStatus.equalsIgnoreCase(descricaoStatus)) {
                return status;
            }
        }
        throw new IllegalFieldsException("Nenhum StatusTarefa encontrado para o label: " + descricaoStatus);
    }
}
