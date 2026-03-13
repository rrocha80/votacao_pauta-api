package br.com.qualitatec.votacao_pauta.model.Enum;

public enum VotoEnum {
    SIM("Sim"),
    NAO("Não");

    private final String label;

    VotoEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
