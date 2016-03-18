package com.study.jam.weather.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.study.jam.weather.model.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cikeron 2016
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    public static final String TABLA_WEATHER = "cutreweather";
    public static final String KEY_id="id";
    public static final String KEY_titulo="Titulo";
    public static final String KEY_descripcion="Descripcion";
    public static final String KEY_estado="Estado";
    public static final String KEY_humedad="Humedad";
    public static final String KEY_max="Max";
    public static final String KEY_min="Min";

    private static final String DATABASE_NAME = "cutreweather.db";
    private static final int DATABASE_VERSION = 1;

    // String de creacion de la BD
    private static final String DATABASE_CREATE = "create table "
            + TABLA_WEATHER + "(" + KEY_id
            + " integer primary key autoincrement, " +
            KEY_titulo + " text not null,"+
            KEY_descripcion + " text,"+
            KEY_estado + " text,"+
            KEY_humedad + " text,"+
            KEY_max + " text,"+
            KEY_min + " text"+
            ");";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Metodo llamado en la creacion de la BD
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }
    // Metodo llamado durante la actualización de la BD
    // ej. Si se incrmenta la version de la BD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_WEATHER);
        onCreate(db);
    }

    /**
     * OPERACIONES CRUD(Create, Read, Update, Delete)
     */
    // Adding new Weather
    public void addWeather(Weather prmmeta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbHelper.KEY_titulo, prmmeta.getTitle());
        values.put(DbHelper.KEY_descripcion, prmmeta.getDescripcion());
        values.put(DbHelper.KEY_estado, prmmeta.getEstado());
        values.put(DbHelper.KEY_humedad, prmmeta.getHumedad());
        values.put(DbHelper.KEY_max, prmmeta.getMaxtemp());
        values.put(DbHelper.KEY_min, prmmeta.getMintemp());
        int i=this.getID(prmmeta);
        if (i==-1){
            // Inserting Row
            db.insert(TABLA_WEATHER, null, values);
        } else {
            updateWeather(prmmeta);
        }
        db.close(); // Closing database connection
    }

    // Getting single Weather
    public Weather getWeather(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLA_WEATHER, new String[] { KEY_id,
                        KEY_titulo, KEY_descripcion,KEY_estado,KEY_humedad,
                        KEY_max,KEY_min}, KEY_id + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Weather contact = new Weather(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6));
        // return contact
        return contact;
    }

    // Getting All
    public List<Weather> getAllwheaters() {
        List<Weather> contactList = new ArrayList<Weather>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLA_WEATHER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Weather comment = new Weather();
                comment.setId(String.valueOf(cursor.getLong(0)));
                comment.settTitle(cursor.getString(1));
                comment.setDescripcion(cursor.getString(2));
                comment.setEstado(cursor.getString(3));
                comment.setHumedad(cursor.getString(4));
                comment.setMaxtemp(cursor.getString(5));
                comment.setMintemp(cursor.getString(6));
                //COMPROBACION Log.d(TAG,comment.toString());
                // Adding Weather to list
                contactList.add(comment);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single Waetaher
    public int updateWeather(Weather prmmeta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_titulo, prmmeta.getTitle());
        values.put(KEY_descripcion, prmmeta.getDescripcion());
        values.put(KEY_estado, prmmeta.getEstado());
        values.put(KEY_humedad, prmmeta.getHumedad());
        values.put(KEY_max, prmmeta.getMaxtemp());
        values.put(KEY_min, prmmeta.getMintemp());

        // updating row
        return db.update(TABLA_WEATHER, values, KEY_id + " = ?",
                new String[] { prmmeta.getId() });
    }

    // Deleting single Weather
    public void deleteWeather(Weather contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_WEATHER, KEY_id + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }
    // Deleting single Weather por ID
    public void deleteWeatherbyId(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLA_WEATHER, KEY_id + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


    // Getting Weather's Count
    public int getWeatherCount() {
        String countQuery = "SELECT  * FROM " + TABLA_WEATHER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    private int getID(Weather meta){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLA_WEATHER,new String[]{"id"},"id =? ",new String[]{meta.getId()},null,null,null,null);
        //db.close();
        try {
            if (c.moveToFirst()) {//if the row exist then return the id
                return c.getInt(c.getColumnIndex("id"));
            }
        } catch (Exception e) {
            Log.d(TAG,"Id no encontrado");
            //return -1;
        }
        return -1;
    }
/*    public int getNextKey() {
//        String num= String.valueOf(realm.where(LaetusmRealm.class).max("idlaetusm").intValue() + 1);
        int num= realm.where(LaetusmRealm.class).max("idlaetusm").intValue() + 1;
//        return String.valueOf(num);
        return num;
    }
*/
}