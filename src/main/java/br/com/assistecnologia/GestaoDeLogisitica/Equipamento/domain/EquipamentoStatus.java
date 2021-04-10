package br.com.assistecnologia.GestaoDeLogisitica.Equipamento.domain;

public enum EquipamentoStatus {
    Garagem("Garagem"),
    EmServico("Em Serviço"),
    EmManutencao("Em Mantutenção"),
    EmTransferencia("Em Transferência");

    private String descricao;

    EquipamentoStatus(String descricao) {
        this.descricao =descricao;
    }

    public String getStatus() {
        return descricao;
    }
}
