package mx.tecnm.voaxaca.agenda.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import mx.tecnm.voaxaca.agenda.entidades.Contactos;

public class DbContactos extends DbHelper {

    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String numero_control, String nombre, String carrera, String semestre, String grupo){

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("numero_control", numero_control);
            values.put("nombre", nombre);
            values.put("carrera", carrera);
            values.put("semestre", semestre);
            values.put("grupo", grupo);

            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch(Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<Contactos> mostrarContactos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos = null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null);

        if(cursorContactos.moveToFirst()){
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNumero_control(cursorContactos.getString(1));
                contacto.setNombre(cursorContactos.getString(2));
                contacto.setCarrera(cursorContactos.getString(3));
                contacto.setSemestre(cursorContactos.getString(4));
                contacto.setGrupo(cursorContactos.getString(5));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }

    public Contactos verContacto(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contactos contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

        if(cursorContactos.moveToFirst()){
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNumero_control(cursorContactos.getString(1));
            contacto.setNombre(cursorContactos.getString(2));
            contacto.setCarrera(cursorContactos.getString(3));
            contacto.setSemestre(cursorContactos.getString(4));
            contacto.setGrupo(cursorContactos.getString(5));
        }

        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(int id, String numero_control, String nombre, String carrera, String semestre, String grupo){

        Boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET numero_control = '" + numero_control + "',nombre = '" + nombre + "',carrera = '" + carrera + "',semestre = '" + semestre + "',grupo = '" + grupo + "' WHERE id = '" + id + "' ");
            correcto = true;
        } catch(Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarContacto(int id){

        Boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch(Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
