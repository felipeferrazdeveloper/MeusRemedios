package com.example.ferraz.meusremedios;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ferraz.meusremedios.dataBase.DataBase;
import com.example.ferraz.meusremedios.model.Medicamento;
import com.example.ferraz.meusremedios.model.Receita;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UsarMedicamentoActivity extends AppCompatActivity {

    //region CONSTANTS AND VARIABLES
    //setting viewElements
    private Button btnChangeDateInicio, btnSalvar, btnHorario;
    private Spinner spinnerMedicamento, spinnerPeriodo, spinnerFrequencia, spinnerFrequenciaTempo;
    private EditText editTextPeriodo, editTextObs;

    //database stuff
    private FirebaseDatabase database;
    private DatabaseReference refMedicamentos;
    private DatabaseReference refLoggedUser;

    //this array just store all medicamentos from firebase to show on Spinner
    private List<Medicamento> medicamentos = new ArrayList<>();

    private Medicamento selectedMedicamento;


    ArrayAdapter<String>  frequenciaAdapter;
    ArrayAdapter<String>  frequenciaTempoAdapter;
    ArrayAdapter<String>  periodoAdapter;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;


    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 888;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usar_medicamento);

        binding();
        //these methods set everything on view except MedicamentosSpinner
        setCurrentDateOnView();
        addListenerOnDateButton();
        setSpinnersStuffs();
        setCurrentTimeOnView();
        addListenerOnTimeButton();



        spinnerFrequencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spinnerFrequenciaTempo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        refMedicamentos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    fillSpinnerMedicamentos(dataSnapshot);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        spinnerMedicamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                String selectedItem = item.substring(0, item.indexOf("-")).trim();
                refMedicamentos.orderByChild("nome").equalTo(selectedItem).limitToFirst(1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {
                        for (DataSnapshot ds: data.getChildren()) {
                            selectedMedicamento = ds.getValue(Medicamento.class);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Receita rec = prepareForPersistNewRecipe();
                writeNewReceita(rec);
                scheduleNotification(getNotification("Hora do seu "+rec.getMedicamento().getNome()), rec.getNextDateTimeToMedicate());
                finish();
            }
        });
    }

    private void writeNewReceita(Receita receita) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = database.getReference("users").child(uid).child("receitas");
        reference.push().setValue(receita, new DatabaseReference.CompletionListener(){

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

    private Receita prepareForPersistNewRecipe() {
        Receita r = new Receita();
        r.setMedicamento(selectedMedicamento);
        r.setDataInicio(year, month, day, hour, minute);
        r.setDataTermino(Integer.valueOf(editTextPeriodo.getText().toString()), spinnerPeriodo.getSelectedItem().toString());
        String freq = spinnerFrequencia.getSelectedItem().toString();
        int frequencia = Integer.valueOf(freq.substring(0, freq.indexOf("/")).trim());
        r.setFrequencia(frequencia, spinnerFrequenciaTempo.getSelectedItem().toString());
        r.setObservacoes(editTextObs.getText().toString());

        return r;

    }

    private void setSpinnersStuffs() {
        periodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.Periodo));
        spinnerPeriodo.setAdapter(periodoAdapter);

        frequenciaTempoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.EFrequencia));
        spinnerFrequenciaTempo.setAdapter(frequenciaTempoAdapter);

        frequenciaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Frequencia));
        frequenciaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequencia.setAdapter(frequenciaAdapter);
    }

    private void fillSpinnerMedicamentos(DataSnapshot data) throws ParseException {
        medicamentos = new ArrayList<>();
        for (DataSnapshot ds: data.getChildren()) {
            Medicamento m = ds.getValue(Medicamento.class);
            medicamentos.add(m);
        }
        String[] vetor = new String[medicamentos.size()];
        int i = 0;
        for(Medicamento m : medicamentos){
            vetor[i++] = m.getNome() +" - "+m.getConcentracao();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, vetor);
        spinnerMedicamento.setAdapter(adapter);
    }


    //there's not really important above, just DatePicker and TimePicker stuff
    private void binding() {
        database = DataBase.getDatabase();

        spinnerMedicamento = (Spinner) findViewById(R.id.spinnerMedicamento);
        spinnerPeriodo = (Spinner) findViewById(R.id.spinnerPeriodo);
        spinnerFrequencia = (Spinner) findViewById(R.id.spinnerFrequencia);
        spinnerFrequenciaTempo = (Spinner) findViewById(R.id.spinnerFrequenciaTempo);
        editTextObs = (EditText) findViewById(R.id.editTextObservacao);

        editTextPeriodo = (EditText) findViewById(R.id.editTextPeriodoUso);

        btnChangeDateInicio = (Button) findViewById(R.id.btnChangeDateInicio);
        btnSalvar = (Button) findViewById(R.id.btnSalvarReceita);
        btnHorario = (Button) findViewById(R.id.btnHorario);

        //references the logged user. It's necessary to set a 'receita' child on User

        refMedicamentos = database.getReference("medicamentos");

    }

    public void setCurrentTimeOnView(){
        final Calendar c = Calendar.getInstance();
        Date time = c.getTime();

        hour = time.getHours();
        minute = time.getMinutes();
        btnHorario.setText(String.format("%02d", hour)+":"+String.format("%02d", minute));
    }

    public void setCurrentDateOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        btnChangeDateInicio.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("/")
                .append(month + 1).append("/")
                .append(year));

    }

    public void addListenerOnTimeButton(){
        btnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    public void addListenerOnDateButton() {
        btnChangeDateInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    private  TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;
            btnHorario.setText(String.format("%02d", hour)+":"+String.format("%02d", minute));
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);

            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, timePickerListener, hour, minute, true);

        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            btnChangeDateInicio.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(day).append("/")
                    .append(month + 1).append("/")
                    .append(year));
        }
    };

    private void scheduleNotification(Notification notification, Date date) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, date.getTime(), pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.notification_icon);
        return builder.build();
    }

}
