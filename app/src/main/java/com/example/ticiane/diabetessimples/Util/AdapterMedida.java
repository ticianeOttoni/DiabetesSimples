package com.example.ticiane.diabetessimples.Util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ticiane.diabetessimples.R;
import com.example.ticiane.diabetessimples.Util.DataUtil;
import com.example.ticiane.diabetessimples.bean.Medida;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ticiane on 06/12/2016.
 */

public class AdapterMedida extends BaseAdapter {
    private Context ctx;
    private List<Medida> listaMedidas;

    public AdapterMedida(Context ctx, List<Medida> listaMedidas){
        this.ctx = ctx;
        this.listaMedidas = listaMedidas;
    }

    @Override
    public int getCount() {
        return listaMedidas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaMedidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaMedidas.get(position).getCodigo();
    }


    class ViewHolder{
        TextView data;
        TextView hora;
        TextView tipo;
        TextView refeicao;
        TextView valor;
        TextView resultado;
    }

    @Override
    public View getView(int position, View cView, ViewGroup parent) {
        Medida medida = listaMedidas.get(position);
        ViewHolder vHolder;
        if (cView == null){
            cView = LayoutInflater.from(ctx).inflate(R.layout.layout_medida_view, parent, false);
            vHolder = new ViewHolder();
            vHolder.data = (TextView)cView.findViewById(R.id.tv_medida_data);
            vHolder.hora = (TextView)cView.findViewById(R.id.tv_medida_hora);
            vHolder.tipo = (TextView)cView.findViewById(R.id.tv_medida_tipo);
            vHolder.refeicao = (TextView)cView.findViewById(R.id.tv_medida_refeicao);
            vHolder.valor = (TextView)cView.findViewById(R.id.tv_medida_valor);
            vHolder.resultado = (TextView)cView.findViewById(R.id.tv_medida_resultado);
            cView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder)cView.getTag();
        }

        Date date = DataUtil.recuperaDataDeSqlite(medida.getDataHora());
        if (date == null) {
            vHolder.data.setText("");
            vHolder.hora.setText("");
        } else {
            String d = (new SimpleDateFormat("dd/MM/yy")).format(date);
            vHolder.data.setText(d);
            vHolder.hora.setText((new SimpleDateFormat("hh:mm")).format(date));
        }
        vHolder.tipo.setText(medida.getTipoMedida().getDescricao() );
        vHolder.refeicao.setText(medida.getTipoRefeicao().getDescricao());
        vHolder.valor.setText( String.valueOf( medida.getValor() ) );
        vHolder.resultado.setText(medida.getResultado());
        String resultado = medida.getResultado();
        if(resultado.equals("Ruim"))
            vHolder.resultado.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorResultadoRuim));
        else if(resultado.equals("Ideal"))
            vHolder.resultado.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorResultadoIdeal));
        else if(resultado.equals("Otimo"))
            vHolder.resultado.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorResultadoOtimo));
        else
            vHolder.resultado.setBackgroundColor(Color.WHITE);
        return cView;
    }
}
