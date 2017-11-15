package com.example.ticiane.diabetessimples;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.ticiane.diabetessimples.Util.AdapterMedida;
import com.example.ticiane.diabetessimples.bean.Medida;
import com.example.ticiane.diabetessimples.bean.TipoMedida;
import com.example.ticiane.diabetessimples.dao.MedidaDAO;
import com.example.ticiane.diabetessimples.dao.TipoMedidaDAO;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private List<TipoMedida> listaTipos;
    private ArrayList<Medida> listaMedidas;
    private ListView listView;

    Spinner spiTipoMedida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        listView = (ListView) findViewById(R.id.listViewMedida);

        spiTipoMedida = (Spinner)
                findViewById(R.id.spiTipoMedida2);

        carregarTipoMedida();
//        preencheLista();
        setupListeners();
    }

    public void carregarTipoMedida() {
        TipoMedidaDAO tipoMedidaDAO = new TipoMedidaDAO(this);
        listaTipos = tipoMedidaDAO.selectAllTipoMedida();
        List<String> labels = new ArrayList<String>();
        labels.add("-");
        for (TipoMedida tipoMedida : listaTipos) {
            labels.add(tipoMedida.getDescricao());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spiTipoMedida.setAdapter(dataAdapter);
    }

    private void preencheLista () {
        TipoMedida tipoMedida=null;
        int selected = spiTipoMedida.getSelectedItemPosition();
        if (selected!=AdapterView.INVALID_POSITION && selected>0)
            tipoMedida = listaTipos.get(selected-1);

        MedidaDAO medidaDAO = new MedidaDAO(this);
        listaMedidas = new ArrayList<Medida>();
        listaMedidas = medidaDAO.selectAll(tipoMedida);

        AdapterMedida adapterMedida = new AdapterMedida(ListaActivity.this, listaMedidas);
        listView.setAdapter(adapterMedida);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        preencheLista();
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void setupListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Medida medida = (Medida) parent.getAdapter().getItem(position);
                final CharSequence[] itens = {getString(R.string.alterar),getString(R.string.excluir),getString(R.string.cancelar)};

                AlertDialog.Builder opcoes = new AlertDialog.Builder(ListaActivity.this);
                opcoes.setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String opcao = (String) itens[which];
                        if (opcao.equals(getString(R.string.alterar))){
                            Intent myIntent = new Intent(ListaActivity.this, RegistraMedicaoActivity.class);
                            myIntent.putExtra("REGISTRA", medida);
                            startActivityForResult(myIntent,2);


                        } else if (opcao.equals(getString(R.string.excluir))){
                            mostraSimNao(medida);
                        }if (opcao.equals(getString(R.string.cancelar))){
                            dialog.cancel();
                        }
                    }
                });
                opcoes.setTitle(R.string.opcoes);
                AlertDialog alerta = opcoes.create();
                alerta.show();
            }
        });

        spiTipoMedida.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                preencheLista();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                preencheLista();
            }
        });

    }

    private void  mostraSimNao ( final Medida medida){
        AlertDialog.Builder  mensagem = new AlertDialog.Builder(ListaActivity.this);
        mensagem.setMessage(getString(R.string.conf_excluir));
        mensagem.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               long retorno = 0;
                retorno = new MedidaDAO(ListaActivity.this).delete(medida);
                if (retorno == 1){
                    Toast.makeText(ListaActivity.this,getString(R.string.msg_excluido),Toast.LENGTH_LONG).show();
                    preencheLista();
                } else if (retorno == 2){
                    Toast.makeText(ListaActivity.this,getString(R.string.msg_erro_excluido),Toast.LENGTH_LONG).show();
                }
            }
        });
        mensagem.setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        mensagem.create().show();
    }


}
