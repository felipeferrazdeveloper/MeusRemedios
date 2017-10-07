package com.example.ferraz.meusremedios.dataBase;

import com.google.firebase.database.FirebaseDatabase;

//package com.example.ferraz.meusremedios.dataBase;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * Created by ferraz on 24/09/17.
// */
//
//public class DataBase extends SQLiteOpenHelper {
//
//    public static final String DBName =  "medicamentos.db";
//    public static final int DBVersion = 1;
//    public static Context context;
//
//    public DataBase(Context context){
//        super(context, DBName, null, DBVersion);
//        this.context = context;
//    }
//
//    public static final String TABLE_MEDICAMENTO = "medicamento";
//
//    public static final String MEDICAMENTO_ID = "id";
//    public static final String MEDICAMENTO_INICIOUSO = "inicioUso";
//    public static final String MEDICAMENTO_FINALUSO = "finalUso";
//    public static final String MEDICAMENTO_NOME = "nome";
//    public static final String MEDICAMENTO_HORARIO = "horario";
//    public static final String MEDICAMENTO_DOSAGEM = "dosagem";
//    public static final String MEDICAMENTO_DOSAGEMMEDIDA = "dosagemMedida";
//    public static final String MEDICAMENTO_PERIODO = "periodo";
//    public static final String MEDICAMENTO_PERIODOMEDIDA = "PeriodoMedida";
//
//    static final String CreateTableMedicamentos = "CREATE TABLE " + TABLE_MEDICAMENTO + " (" +
//            MEDICAMENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//            MEDICAMENTO_INICIOUSO + " TEXT NOT NULL, " +
//            MEDICAMENTO_FINALUSO + " TEXT, " +
//            MEDICAMENTO_NOME + " TEXT, " +
//            MEDICAMENTO_HORARIO + " TEXT NOT NULL, " +
//            MEDICAMENTO_DOSAGEM + " INTEGER NOT NULL, " +
//            MEDICAMENTO_DOSAGEMMEDIDA + " TEXT NOT NULL, " +
//            MEDICAMENTO_PERIODO + " INTEGER," +
//            MEDICAMENTO_PERIODOMEDIDA + " TEXT)";
//
//    public static final String TABLE_USUARIO = "usuario";
//
//    static final String CreateTableUsuario = "CREATE TABLE " + TABLE_USUARIO + " (" +
//            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//            "nome TEXT, " +
//            "email TEXT)";
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(CreateTableMedicamentos);
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//}
public class DataBase {

    private static FirebaseDatabase mData;

    public static FirebaseDatabase getDatabase() {
        if (mData == null) {

            mData = FirebaseDatabase.getInstance();
            mData.setPersistenceEnabled(true);
        }
        return mData;
    }


}