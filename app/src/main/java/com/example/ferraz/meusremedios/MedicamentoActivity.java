package com.example.ferraz.meusremedios;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ferraz.meusremedios.dataBase.DataBase;
import com.example.ferraz.meusremedios.model.Medicamento;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicamentoActivity extends AppCompatActivity {

    EditText nome, farmaco, concentracao, detentor, formaFarm, registro;
    Button btnSalvar;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        binding();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Medicamento m = new Medicamento();



                m.setNome(nome.getText().toString());
                m.setFarmaco(farmaco.getText().toString());
                m.setDetentor(detentor.getText().toString());
                m.setRegistro(registro.getText().toString());
                m.setConcentracao(concentracao.getText().toString());
                m.setFormaFarmaceutica(formaFarm.getText().toString());
                m.setDataCadastro(GetToday());

                writeNewMedicamento(m);
                //DataBase connect = new DataBase(getApplicationContext());
                //new MedicamentoDAO(connect.context).inserir(medicamento);


                finish();
            }
        });
    }

    private void writeNewMedicamento(Medicamento medicamento) {

        DatabaseReference reference = firebaseDatabase.getReference("medicamentos");
        reference.push().setValue(medicamento, new DatabaseReference.CompletionListener(){

            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getApplicationContext(), "Houve um erro! Tente novamente mais tarde", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Medicamento salvo com sucesso!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static String GetToday(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();

        return df.format(dateobj).toString();

    }
    private void binding() {
        firebaseDatabase = DataBase.getDatabase();
        nome = (EditText) findViewById(R.id.edTxtNome);
        farmaco = (EditText) findViewById(R.id.edTxtFarmaco);
        concentracao = (EditText) findViewById(R.id.edTxtConcentracao);
        detentor = (EditText) findViewById(R.id.edTxtDetentor);
        formaFarm = (EditText) findViewById(R.id.edTxtFormaFarm);
        registro = (EditText) findViewById(R.id.edTxtRegistro);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
    }
}
