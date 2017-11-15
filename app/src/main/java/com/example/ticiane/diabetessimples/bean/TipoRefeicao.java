package com.example.ticiane.diabetessimples.bean;


import java.io.Serializable;

public class TipoRefeicao implements Serializable {

    private static  final long serialVersionUID = 1L;
    private int codigo;
    private String descricao;

    public TipoRefeicao()
    {
        super();
    }
    public TipoRefeicao(int codigo, String descricao) {
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
}
