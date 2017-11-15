package com.example.ticiane.diabetessimples.bean;

import java.io.Serializable;

public class TipoMedida implements Serializable {

    private static  final long serialVersionUID = 1L;
    private int codigo;
    private String descricao;
    private int valorOtimo;
    private int valorIdeal;

    public TipoMedida()
    {
        super();
    }
    public TipoMedida(int codigo, String descricao) {
        super();
        this.codigo = codigo;
        this.descricao = descricao;

    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getValorIdeal() {
        return valorIdeal;
    }

    public void setValorIdeal(int valorIdeal) {
        this.valorIdeal = valorIdeal;
    }

    public int getValorOtimo() {
        return valorOtimo;
    }

    public void setValorOtimo(int valorOtimo) {
        this.valorOtimo = valorOtimo;
    }

}
