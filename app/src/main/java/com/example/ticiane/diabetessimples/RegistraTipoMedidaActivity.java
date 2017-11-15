package com.example.ticiane.diabetessimples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ticiane.diabetessimples.bean.TipoMedida;
import com.example.ticiane.diabetessimples.dao.TipoMedidaDAO;

public class RegistraTipoMedidaActivity extends AppCompatActivity {

    TipoMedida mTipoMedida;
    EditText etDescricao;
    EditText etIdeal;
    EditText etOtimo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_tipo_medida);


        mTipoMedida = (TipoMedida)getIntent().getSerializableExtra("REGISTRA");
        if (mTipoMedida == null){
            mTipoMedida = new TipoMedida();
            mTipoMedida.setCodigo(-1);
        }

        etDescricao = (EditText)findViewById(R.id.et_descricao);
        etIdeal= (EditText)findViewById(R.id.et_valorIdeal);
        etOtimo= (EditText)findViewById(R.id.et_valorOtimo);

        setListeners();
        atualizaCampos();


    }

    private void atualizaCampos() {
        etDescricao.setText( mTipoMedida.getDescricao() );
        etIdeal.setText( String.valueOf(mTipoMedida.getValorIdeal()) );
        etOtimo.setText( String.valueOf(mTipoMedida.getValorOtimo()) );
    }

    private void cancelar() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void salvar() {
        mTipoMedida.setDescricao( etDescricao.getText().toString() );
        mTipoMedida.setValorIdeal( Integer.valueOf(etIdeal.getText().toString()) );
        mTipoMedida.setValorOtimo( Integer.valueOf(etOtimo.getText().toString()) );

        TipoMedidaDAO tipoMedidaDAO = new TipoMedidaDAO(this);
        long r=1;
        if (mTipoMedida.getCodigo()<0){
            tipoMedidaDAO.insert(mTipoMedida);
        } else {
            r = tipoMedidaDAO.update(mTipoMedida);
        }

        if (r == 1){
            Toast.makeText(this,getString(R.string.msg_alterado),Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,getString(R.string.msg_erro_alterado),Toast.LENGTH_LONG).show();
        }

        Intent result = new Intent();
        result.putExtra("RESULT", mTipoMedida);
        setResult(RESULT_OK, result);
        finish();
    }

    private void setListeners() {
        Button btSalvar = (Button)findViewById(R.id.bt_salvar);
        Button btCancelar = (Button)findViewById(R.id.bt_cancelar);

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
    }

}
