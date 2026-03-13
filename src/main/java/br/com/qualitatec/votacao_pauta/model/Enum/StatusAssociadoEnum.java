package br.com.qualitatec.votacao_pauta.model.Enum;

public enum StatusAssociadoEnum {
    ABLE_TO_VOTE("Habilitado"),
    UNABLE_TO_VOTE("Desabilitado");

    private final String label;

    StatusAssociadoEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
