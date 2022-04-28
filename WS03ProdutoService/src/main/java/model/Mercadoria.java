package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Mercadoria {
    private int id;
    private String descricao;
    private float valor;
    private int quantidade;
    private LocalDateTime dataProducao;
    private LocalDate Validade;

    public Mercadoria() {
        id = -1;
        descricao = "";
        valor = 0.00F;
        quantidade = 0;
        dataProducao = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Validade = LocalDate.now().plusMonths(6);
    }

    public Mercadoria(int id, String descricao, float valor, int quantidade, LocalDateTime producao, LocalDate v) {
        setId(id);
        setDescricao(descricao);
        setValor(valor);
        setQuantidade(quantidade);
        setDataProducao(producao);
        setValidade(v);
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getValidade() {
        return Validade;
    }

    public LocalDateTime getDataProducao() {
        return dataProducao;
    }

    public void setDataProducao(LocalDateTime dataProducao) {

        LocalDateTime agora = LocalDateTime.now();

        if (agora.compareTo(dataProducao) >= 0)
            this.dataProducao = dataProducao;
    }

    public void setValidade(LocalDate Validade) {

        if (getDataProducao().isBefore(Validade.atStartOfDay()))
            this.Validade = Validade;
    }

    public boolean emValidade() {
        return LocalDateTime.now().isBefore(this.getValidade().atTime(23, 59));
    }

    @Override
    public String toString() {
        return "Mercadoria: " + descricao + "   Valor: R$" + valor + "   Quantidade.: " + quantidade + "   Produção: "
                + dataProducao + "  Validade: " + Validade;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getID() == ((Mercadoria) obj).getID());
    }
}