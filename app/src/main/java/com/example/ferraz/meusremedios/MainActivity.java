package com.example.ferraz.meusremedios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ferraz.meusremedios.dataBase.DataBase;
import com.example.ferraz.meusremedios.model.Receita;
import com.example.ferraz.meusremedios.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView lblUserName;
    ListView list;
    List<Receita> listaReceitas;
    FirebaseAuth fireUser;
    FirebaseDatabase fireDatabase;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        binding();

        //Configurando o que o botão flutuante irá fazer
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent itn = new Intent(getApplicationContext(), UsarMedicamentoActivity.class);
                 startActivityForResult(itn, 0);
            }
        });

        lblUserName.setText("Remédios de " + fireUser.getCurrentUser().getDisplayName() + ":");

        //grava uma informação no banco.
        writeNewUser(fireUser.getCurrentUser().getUid(), fireUser.getCurrentUser().getDisplayName(),
                fireUser.getCurrentUser().getEmail());

        DatabaseReference refMedicamentos = fireDatabase.getReference("users").child(fireUser.getCurrentUser().getUid()).child("receitas");
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

    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        DatabaseReference mRef = fireDatabase.getReference("users");
        mRef.child(userId).setValue(user);
    }

    private void preencherListView(DataSnapshot data) throws ParseException {
        listaReceitas = new ArrayList<>();
        for (DataSnapshot ds: data.getChildren()) {
            Receita m = ds.getValue(Receita.class);

            listaReceitas.add(m);
        }
        String[] vetor = new String[listaReceitas.size()];
        int i = 0;
        for(Receita r : listaReceitas){
            vetor[i++] = r.getMedicamento().getNome();
        }





        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, vetor);
        list.setAdapter(adapter);




        // ListView Item Click Listener
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) list.getItemAtPosition(position);
                Receita r = listaReceitas.get(itemPosition);

                // Show Alert
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                Toast.makeText(getApplicationContext(),
                        "Você deve tomar "+r.getMedicamento().getNome()+" em "+sdf.format(r.getNextDateTimeToMedicate()), Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sair) {
            fireUser.signOut();
            Intent itn = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(itn);
        }else if(id == R.id.medicamentosAnvisa){
            Intent itn = new Intent(this, AnvisaActivity.class);
            startActivity(itn);
        }

        return super.onOptionsItemSelected(item);
    }

    private void binding() {
        fireUser = FirebaseAuth.getInstance();
        fireDatabase = DataBase.getDatabase();
        list = (ListView) findViewById(R.id.receitasListView);
        lblUserName = (TextView) findViewById(R.id.lblUserName);
    }
}
