//package com.example.ferraz.meusremedios.dao;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.example.ferraz.meusremedios.dataBase.DataBase;
//import com.example.ferraz.meusremedios.model.EDosagem;
//import com.example.ferraz.meusremedios.model.EPeriodo;
//import com.example.ferraz.meusremedios.model.Medicamento;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by ferraz on 24/09/17.
// */
//
public class MedicamentoDAO {
//    Context context;
//    DataBase banco;
//    SQLiteDatabase sql;
//
//    public MedicamentoDAO(Context context) {
//        this.context = context;
//        banco = new DataBase(context);
//
//
//    }
//    public void open(){
//        sql = banco.getWritableDatabase();
//
//    }
//    public void close(){
//        sql.close();
//        banco.close();
//    }
//
//
//    public void inserir(Medicamento obj){
//        open();
//        try{
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
//            ContentValues cv = new ContentValues();
//            cv.put(DataBase.MEDICAMENTO_INICIOUSO, sdf.format(obj.getInicioUso()));
//            cv.put(DataBase.MEDICAMENTO_FINALUSO, sdf.format(obj.getInicioUso()));
//            cv.put(DataBase.MEDICAMENTO_NOME, obj.getNome());
//            cv.put(DataBase.MEDICAMENTO_HORARIO, stf.format(obj.getHorario()));
//            cv.put(DataBase.MEDICAMENTO_DOSAGEM, obj.getDosagem());
//            cv.put(DataBase.MEDICAMENTO_DOSAGEMMEDIDA, obj.getDosagemMedida().toString());
//            cv.put(DataBase.MEDICAMENTO_PERIODO, obj.getPeriodo());
//            cv.put(DataBase.MEDICAMENTO_PERIODOMEDIDA, obj.getPeriodoMedida().toString());
//
//            sql.insert(DataBase.TABLE_MEDICAMENTO, null, cv);
//        }catch(Exception ex){
//
//        }
//        finally {
//            close();
//        }
//    }
//
//    public List<Medicamento> findAll(){
//        open();
//        try{
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
//            String query = "SELECT * from "+DataBase.TABLE_MEDICAMENTO;
//            Cursor cursor = sql.rawQuery(query, null);
//
//            List<Medicamento> lista = new ArrayList<>();
//            while(cursor.moveToNext()){
//                Medicamento m = new Medicamento();
//
//                m.setId(cursor.getInt(cursor.getColumnIndex(DataBase.MEDICAMENTO_ID)));
//                m.setInicioUso(sdf.parse(cursor.getString(cursor.getColumnIndex(DataBase.MEDICAMENTO_INICIOUSO))));
//                m.setFinalUso(sdf.parse(cursor.getString(cursor.getColumnIndex(DataBase.MEDICAMENTO_FINALUSO))));
//                m.setNome(cursor.getString(cursor.getColumnIndex(DataBase.MEDICAMENTO_NOME)));
//                m.setHorario(stf.parse(cursor.getString(cursor.getColumnIndex(DataBase.MEDICAMENTO_HORARIO))));
//                m.setDosagem(cursor.getInt(cursor.getColumnIndex(DataBase.MEDICAMENTO_DOSAGEM)));
//                m.setDosagemMedida(EDosagem.valueOf(cursor.getString(cursor.getColumnIndex((DataBase.MEDICAMENTO_DOSAGEMMEDIDA)))));
//                m.setPeriodo(cursor.getInt(cursor.getColumnIndex(DataBase.MEDICAMENTO_PERIODO)));
//                m.setPeriodoMedida(EPeriodo.valueOf(cursor.getString(cursor.getColumnIndex(DataBase.MEDICAMENTO_PERIODOMEDIDA))));
//
//                lista.add(m);
//            }
//
//            return  lista;
//        }catch (ParseException ex){
//            ex.printStackTrace();
//            return null;
//        }
//        finally {
//            close();
//        }
//    }
//
//    public List<String> findAllNames(){
//        List<Medicamento> lista = findAll();
//        List<String> nomes = new ArrayList<String>();
//
//        while (lista.size() > 0){
//            nomes.add(lista.iterator().next().getNome());
//        }
//
//        return nomes;
//    }
//
//    public List<Medicamento> findByName(String nome){
//        return null;
//    }
//
//    public Medicamento findOne(int id){
//        return null;
//    }
}
