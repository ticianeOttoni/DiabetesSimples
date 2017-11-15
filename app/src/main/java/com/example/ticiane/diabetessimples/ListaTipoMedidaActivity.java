package com.example.ticiane.diabetessimples;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ticiane.diabetessimples.Util.AdapterTipoMedida;
import com.example.ticiane.diabetessimples.bean.TipoMedida;
import com.example.ticiane.diabetessimples.dao.TipoMedidaDAO;

import java.util.List;

public class ListaTipoMedidaActivity extends AppCompatActivity {

    private ListView listView;
    TipoMedidaDAO tipoMedidaDAO;
    private final static int REGISTRA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tipo_medida);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        listView = (ListView) findViewById(R.id.listViewTipoMedida);
        tipoMedidaDAO = new TipoMedidaDAO(this);
        carregarLista();
        criarListeners();
    }

    private void criarListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final TipoMedida tipo = (TipoMedida) parent.getAdapter().getItem(position);
                final CharSequence[] itens = {getString(R.string.alterar),getString(R.string.excluir),getString(R.string.cancelar)};

                AlertDialog.Builder opcoes = new AlertDialog.Builder(ListaTipoMedidaActivity.this);
                opcoes.setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String opcao = (String) itens[which];
                        if (opcao.equals(getString(R.string.alterar))){
                            Intent myIntent = new Intent(ListaTipoMedidaActivity.this, RegistraTipoMedidaActivity.class);
                            myIntent.putExtra("REGISTRA", tipo);
                            startActivityForResult(myIntent, REGISTRA_REQUEST);
                        } else if (opcao.equals(getString(R.string.excluir))){
                            confirmaExclui(tipo);
                        }if (opcao.equals(getString(R.string.cancelar))){
                            dialog.cancel();
                        }
                        dialog.cancel();
                    }
                });
                opcoes.setTitle(R.string.opcoes);
                AlertDialog alerta = opcoes.create();
                alerta.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ListaTipoMedidaActivity.this, RegistraTipoMedidaActivity.class);
                startActivityForResult(myIntent, REGISTRA_REQUEST);
            }
        });

    }

    private void confirmaExclui(final TipoMedida tipo) {
        AlertDialog.Builder  mensagem = new AlertDialog.Builder(this);
        mensagem.setMessage(getString(R.string.conf_excluir));
        mensagem.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long retorno = 0;
                retorno = new TipoMedidaDAO(ListaTipoMedidaActivity.this).delete(tipo);
                if (retorno == 1){
                    Toast.makeText(ListaTipoMedidaActivity.this,getString(R.string.msg_excluido),Toast.LENGTH_LONG).show();
                    carregarLista();
                } else if (retorno == 2){
                    Toast.makeText(ListaTipoMedidaActivity.this,getString(R.string.msg_erro_excluido),Toast.LENGTH_LONG).show();
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

    void carregarLista(){
        List<TipoMedida> listaTipos = tipoMedidaDAO.selectAllTipoMedida();
        AdapterTipoMedida adapterTipoMedida = new AdapterTipoMedida(this, listaTipos);
        listView.setAdapter(adapterTipoMedida);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTRA_REQUEST){
            if (resultCode == RESULT_OK)
                carregarLista();
        }
    }
}
