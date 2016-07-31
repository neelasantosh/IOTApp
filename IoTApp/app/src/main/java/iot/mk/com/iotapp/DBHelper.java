package iot.mk.com.iotapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajesh on 20/01/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String CONFIG_TABLE = "configuration";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBHelper(Context context) {
        this(context, "iot_db.sqlite", null, 1);
    }
    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CONFIG_TABLE + "(" +
                "id_address text," +
                "port text," +
                "clientId text," +
                "clieanSession text" +
                ");");
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public List<ConnectionConfiguration> getAllConfiguration() {
        SQLiteDatabase db = getWritableDatabase();
        List<ConnectionConfiguration> list = new ArrayList<>();
        Cursor result = db.query(CONFIG_TABLE, new String[]{}, null, null, "id_address", null, null);
        while(result.moveToNext()) {
            list.add(new ConnectionConfiguration(result.getString(0), Integer.parseInt(result.getString(1)), Boolean.parseBoolean(result.getString(3)), result.getString(2)));
        }
        db.close();
        return list;
    }
    public void saveConfiguration(ConnectionConfiguration config) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor tempResult = db.query(CONFIG_TABLE, new String[]{"id_address"}, null, null, null, null, null);
        while(tempResult.moveToNext()) {
            if(config.getIpAddress().equals(tempResult.getString(0))) {
                return;
            }
        }
        String insertQuerry = "insert into "+CONFIG_TABLE+" values('"+config.getIpAddress()+"', '"+config.getPort()+"', '"+config.getClientId()+"', '"+config.isCleanSession()+"');";
        db.execSQL(insertQuerry);
        db.close();
    }
}
