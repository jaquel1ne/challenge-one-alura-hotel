package model.entities;

public enum FormaPagamento {
    CARTAO_DEBITO("Cartão de Débito"),
    CARTAO_CREDITO("Cartão de Crédito"),
    DINHEIRO("Dinheiro");

    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
    
    public static FormaPagamento getByDescricao(String descricao) {
        for (FormaPagamento forma : values()) {
            if (forma.getDescricao().equals(descricao)) {
                return forma;
            }
        }
        return null;
    }

}
