package com.example.ferraz.meusremedios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ferraz.meusremedios.dataBase.DataBase;
import com.example.ferraz.meusremedios.model.Medicamento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AnvisaActivity extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference refMedicamentos;
    List<Medicamento> listaAnvisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anvisa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAnvisa);
        setSupportActionBar(toolbar);

        binding();


        refMedicamentos = database.getReference("medicamentos");
        refMedicamentos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    preencherListView(dataSnapshot);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAnvisa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itn = new Intent(getApplicationContext(), MedicamentoActivity.class);
                startActivityForResult(itn, 0);
            }
        });
    }

    private void preencherListView(DataSnapshot data) throws ParseException {
        listaAnvisa = new ArrayList<>();
        for (DataSnapshot ds: data.getChildren()) {
            Medicamento m = ds.getValue(Medicamento.class);
            listaAnvisa.add(m);
        }
        String[] vetor = new String[listaAnvisa.size()];
        int i = 0;
        for(Medicamento m : listaAnvisa){
            vetor[i++] = m.getNome() + " - "+m.getFarmaco()+" - "+m.getConcentracao();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, vetor);
        listView.setAdapter(adapter);
    }

    private void binding() {
        listView = (ListView) findViewById(R.id.listViewMedicamentosAnvisa);
        database = DataBase.getDatabase();
    }
}

