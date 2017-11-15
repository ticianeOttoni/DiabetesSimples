package com.example.ticiane.diabetessimples.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ticiane.diabetessimples.R;
import com.example.ticiane.diabetessimples.bean.TipoMedida;

import java.util.List;

/**
 * Created by julio on 10/05/2017.
 */

public class AdapterTipoMedida extends BaseAdapter{

    private Context mContext;
    private List<TipoMedida> mListaTipos;

    public AdapterTipoMedida(Context context, List<TipoMedida> listaTipos){
        this.mContext = context;
        this.mListaTipos = listaTipos;
    }

    @Override
    public int getCount() {
        return mListaTipos.size();
    }

    @Override
    public Object getItem(int i) {
        return mListaTipos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mListaTipos.get(i).getCodigo();
    }

    static class ViewHolder{
        TextView descricao;
        TextView ideal;
        TextView otimo;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder vHolder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_tipo_medida_view, parent, false);
            vHolder = new ViewHolder();
            vHolder.descricao = (TextView)view.findViewById(R.id.tvDescricao);
            vHolder.ideal = (TextView)view.findViewById(R.id.tvIdeal);
            vHolder.otimo = (TextView)view.findViewById(R.id.tvOtimo);
            view.setTag(vHolder);
        } else {
            vHolder = (ViewHolder)view.getTag();
        }
        TipoMedida tipo = mListaTipos.get(i);
        vHolder.descricao.setText( tipo.getDescricao() );
        vHolder.ideal.setText( String.valueOf(tipo.getValorIdeal()) );
        vHolder.otimo.setText( String.valueOf(tipo.getValorOtimo()) );
        return view;
    }
}
