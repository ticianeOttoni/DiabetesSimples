package com.example.ticiane.diabetessimples;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticiane.diabetessimples.bean.Medida;
import com.example.ticiane.diabetessimples.bean.TipoMedida;
import com.example.ticiane.diabetessimples.bean.TipoRefeicao;
import com.example.ticiane.diabetessimples.dao.MedidaDAO;
import com.example.ticiane.diabetessimples.dao.TipoMedidaDAO;
import com.example.ticiane.diabetessimples.dao.TipoRefeicaoDAO;
import com.example.ticiane.diabetessimples.Util.DataUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegistraMedicaoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener,DialogInterface.OnCancelListener {

    Spinner spiTipoMedida;
    Spinner spiTipoRefeicao;
    EditText txtValor;
    TextView lblStatus;

    private  Medida medidaSelecionada = null;

    private TextView lblDataMedida;
    private Button btnDataMedida;
    private int year=0, month=0, day=0, hour=0, minute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_medicao);

        spiTipoMedida = (Spinner)
                findViewById(R.id.spiTipoMedida);

        spiTipoRefeicao = (Spinner)
                findViewById(R.id.spiTipoRefeicao);

        txtValor = (EditText) findViewById(R.id.txtValor);

        lblStatus = (TextView) findViewById(R.id.lblStatus);

        lblDataMedida = (TextView) findViewById(R.id.lblDataMedida);
        btnDataMedida = (Button) findViewById(R.id.btnDataMedia);
        btnDataMedida.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                selecionarDataHora(v);
            }
        });


        try {
         medidaSelecionada =    (Medida) getIntent().getSerializableExtra("REGISTRA");
            txtValor.setText(( String.valueOf(medidaSelecionada.getValor())));

        } catch ( Exception e) {
            e.printStackTrace();
        }

        carregarTipoMedida(medidaSelecionada);
        carregarTipoRefeicao(medidaSelecionada);

    }

    public void carregarTipoMedida(Medida medidaSelecionada)
    {
        TipoMedidaDAO tipoMedidaDAO = new TipoMedidaDAO(this);
        List<TipoMedida> lista =
                tipoMedidaDAO.selectAllTipoMedida();
        List<String> labels = new ArrayList<String>();
        for(TipoMedida tipoMedida : lista)
        {
            labels.add(tipoMedida.getDescricao());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spiTipoMedida.setAdapter(dataAdapter);

        if (medidaSelecionada != null){
            int spinnerPosition = dataAdapter.getPosition(medidaSelecionada.getTipoMedida().getDescricao());
            spiTipoMedida.setSelection(spinnerPosition);
        }
    }

    public void carregarTipoRefeicao( Medida medidaSelecionada)
    {
        TipoRefeicaoDAO tipoRefeicaoDAO = new TipoRefeicaoDAO(this);
        List<TipoRefeicao> lista =
                tipoRefeicaoDAO.selectAllTipoRefeicao();
        List<String> labels = new ArrayList<String>();
        for(TipoRefeicao tipoRefeicao : lista)
        {
            labels.add(tipoRefeicao.getDescricao());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spiTipoRefeicao.setAdapter(dataAdapter);

        if (medidaSelecionada != null){
            int spinnerPosition = dataAdapter.getPosition(medidaSelecionada.getTipoRefeicao().getDescricao());
            spiTipoRefeicao.setSelection(spinnerPosition);
        }

    }

    public void novaMedicao(View v){
        // Chama a outra atividade
        Intent intent = new Intent(this, RegistraMedicaoActivity.class);
        startActivity(intent);
    }

    public void listarMedicao(View v){
        // Chama a outra atividade
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
    }

    public void configurar(View v){
        Intent intent = new Intent(this, ListaTipoMedidaActivity.class);
        startActivity(intent);
    }

    public void registrarMedicao(View v)
    {
        TipoMedidaDAO tipoMedidaDAO = new TipoMedidaDAO(this);
        TipoMedida tipoMedida =
                tipoMedidaDAO.findByDescricao(spiTipoMedida.getSelectedItem().toString());

        TipoRefeicaoDAO tipoRefeicaoDAO = new TipoRefeicaoDAO(this);
        TipoRefeicao tipoRefeicao =
                tipoRefeicaoDAO.findByDescricao(spiTipoRefeicao.getSelectedItem().toString());

        MedidaDAO drDAO = new MedidaDAO(this);

        if (medidaSelecionada != null){
            medidaSelecionada.setDataHora(DataUtil.formataDataSelecionadaParaSqlite(year,month,day,hour,minute));
            medidaSelecionada.setTipoMedida(tipoMedida);
            medidaSelecionada.setTipoRefeicao(tipoRefeicao);
            medidaSelecionada.setValor(Integer.parseInt(txtValor.getText().toString()));
            int retorno = drDAO.update(medidaSelecionada);

            if (retorno == 1){
                Toast.makeText(RegistraMedicaoActivity.this,getString(R.string.msg_alterado),Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(RegistraMedicaoActivity.this,getString(R.string.msg_erro_alterado),Toast.LENGTH_LONG).show();
            }

        }else{

            String msgErro = validaDadosMedida(txtValor.getText().toString());
            if (!msgErro.equals("")){
                Toast.makeText(RegistraMedicaoActivity.this,msgErro,Toast.LENGTH_LONG).show();
            }
            else{
                Medida oDr = new Medida(0, DataUtil.formataDataSelecionadaParaSqlite(year,month,day,hour,minute), tipoMedida, tipoRefeicao, Integer.parseInt(txtValor.getText().toString()));

                drDAO.insert(oDr);

                lblStatus.setText("Inclusao realizada com sucessso. Resultado:" + oDr.getResultado());
            }
        }
        // Chama a outra atividade
        //Intent intent = new Intent(this, ListaActivity.class);
        //startActivity(intent);
    }


    private String validaDadosMedida(String valor){
        String msgErro = "";
        if ( year == 0)
        {
            msgErro =  getString(R.string.msg_erro_data);
        }
        else if (valor.trim().equals("")) {
            msgErro =  getString(R.string.msg_erro_valor);
        }

        return msgErro;
    }

    private void selecionarDataHora(View view){
        initDateTimeData();
        Calendar cDefault = Calendar.getInstance();
        cDefault.set(year,month,day);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                                                                         cDefault.get(Calendar.YEAR),
                                                                         cDefault.get(Calendar.MONTH),
                                                                         cDefault.get(Calendar.DAY_OF_MONTH)
        );
        Calendar cMax = Calendar.getInstance();
        datePickerDialog.setMaxDate(cMax);
        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    private void initDateTimeData(){
        if ( year == 0){
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        initDateTimeData();
        lblDataMedida.setText(DataUtil.formataDataSelecionadaParaExibir(year,month,day,hour,minute));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int pMinute, int second) {

        Calendar cAtual = Calendar.getInstance();
        Calendar cSelecionado = Calendar.getInstance();
        cSelecionado.set(year,month+1,day,hourOfDay,pMinute);
        //if (cSelecionado.getTimeInMillis() > cAtual.getTimeInMillis()){
            //lblDataMedida.setText("A data e hora selecionadas devem ser maiors que atual");
            //return;
        //}
        //else {

            hour = hourOfDay;
            minute = pMinute;

            lblDataMedida.setText(DataUtil.formataDataSelecionadaParaExibir(year,month,day,hour,minute));
        //}
    }

    @Override
    public void onDateSet(DatePickerDialog view, int pYear, int monthOfYear, int dayOfMonth) {
        Calendar tDefault = Calendar.getInstance();
        tDefault.set(year,month,day,hour,minute);

        year = pYear;
        month = monthOfYear;
        day = dayOfMonth;

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this,
                tDefault.get(Calendar.HOUR_OF_DAY),
                tDefault.get(Calendar.MINUTE),
                true
        );

        Calendar cMax = Calendar.getInstance();
        timePickerDialog.setOnCancelListener(this);
        timePickerDialog.show(getFragmentManager(), "timePickerDialog");

    }


}
