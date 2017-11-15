package com.example.ticiane.diabetessimples.bean;


import java.io.Serializable;

public class Medida implements Serializable {

    private static  final long serialVersionUID = 1L;
    private int codigo;
    private String dataHora;
    private TipoMedida tipoMedida;
    private TipoRefeicao tipoRefeicao;
    private int valor;
    private String resultado;


    public Medida()
    {
        super();
    }
    public Medida(int codigo, String dataHora,
                  TipoMedida tipoMedida,TipoRefeicao tipoRefeicao , int valor) {
        super();
        this.codigo = codigo;
        this.dataHora = dataHora;
        this.tipoMedida = tipoMedida;
        this.tipoRefeicao = tipoRefeicao;
        this.valor = valor;
        this.resultado = calculaResultado();
    }

    public int getCodigo() {
        return codigo;
    }
    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public TipoMedida getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(TipoMedida tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public TipoRefeicao getTipoRefeicao() {
        return tipoRefeicao;
    }

    public void setTipoRefeicao(TipoRefeicao tipoRefeicao) {
        this.tipoRefeicao = tipoRefeicao;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getResultado() {
        return resultado;
    }

    private String calculaResultado() {
        String resultado;
        int valor = this.getValor();
        if (valor < this.getTipoMedida().getValorOtimo())
            resultado = "Otimo";
        else if (valor > this.getTipoMedida().getValorOtimo() && valor < this.getTipoMedida().getValorIdeal())
            resultado = "Ideal";
        else
            resultado = "Ruim";
        return  resultado;
    }

}
