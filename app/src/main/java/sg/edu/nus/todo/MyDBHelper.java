package sg.edu.nus.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final int datbaseVersion = 1;
    public static final String databaseName = "todo.db";
    public static final String tableName = "todo_list";
    public static final String columnName1 = "_id";
    public static final String columnName2 = "name";
    public static final String columnName3 = "description";
    public static final String columnName4 = "endDate";
    public static final String columnName5 = "endTime";
    public static final String columnName6 = "location";
    public static final String columnName7 = "status";
    public static final String columnName8 = "contactName";
    public static final String columnName9 = "contactNumber";
    public static final String columnName10 = "reminder";
    private static final String SQLite_CREATE = "Create Table " + tableName + " (" + columnName1 +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + columnName2 + " TEXT NOT NULL, " + columnName3 +
            " TEXT NOT NULL, " + columnName4 + " DATE NOT NULL, " + columnName5 + " IIME, " + columnName6 + " TEXT NOT NULL, " +
            columnName7 + " TEXT, " + columnName8 + " TEXT, " + columnName9 + " TEXT, " + columnName10 + " TEXT );";
    private static final String SQLite_DELETE = "DROP TABLE IF EXISTS " + tableName;
    Calendar myCalender = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public MyDBHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLite_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLite_DELETE);
        onCreate(db);
    }

    public boolean insertData(String name, String description, String endDate, String endTime, String location, String status,
                              String contactName, String contactNumber, String reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName2, name);
        contentValues.put(columnName3, description);
        contentValues.put(columnName4, endDate);
        contentValues.put(columnName5, endTime);
        contentValues.put(columnName6, location);
        contentValues.put(columnName7, status);
        contentValues.put(columnName8, contactName);
        contentValues.put(columnName9, contactNumber);
        contentValues.put(columnName10, reminder);
        Long result = (db.insert(tableName, null, contentValues));
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean editData(String id, String name, String description, String endDate, String endTime, String location, String status,
                            String contactName, String contactNumber, String reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName2, name);
        contentValues.put(columnName3, description);
        contentValues.put(columnName4, endDate);
        contentValues.put(columnName5, endTime);
        contentValues.put(columnName6, location);
        contentValues.put(columnName7, status);
        contentValues.put(columnName8, contactName);
        contentValues.put(columnName9, contactNumber);
        contentValues.put(columnName10, reminder);
        int result = db.update(tableName, contentValues, columnName1 + " " + "=" + id, null);
        if (result == 1)
            return true;
        else
            return false;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " +tableName, null);
        return res;
    }

    public Cursor getToday() {
        SQLiteDatabase db = this.getWritableDatabase();
        String today = df.format(myCalender.getTime());
        String time = String.format("%02d:%02d", myCalender.get(Calendar.HOUR_OF_DAY), myCalender.get(Calendar.MINUTE));
        String[] args={today, time};
        Cursor res = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName4 + " = ? AND " + columnName5 + " > ? AND " + columnName7 + " IS NULL", args);
        return res;
    }

    public Cursor getUpcoming() {
        SQLiteDatabase db = this.getWritableDatabase();
        String today = df.format(myCalender.getTime());
        String[] args={today};
        Cursor res = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName4 + " > ? AND " + columnName7 + " IS NULL", args);
        return res;
    }

    public Cursor getExpired() {
        SQLiteDatabase db = this.getWritableDatabase();
        String today = df.format(myCalender.getTime());
        String time = String.format("%02d:%02d", myCalender.get(Calendar.HOUR_OF_DAY), myCalender.get(Calendar.MINUTE));
        String[] args={today, time};
        Cursor res = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName4 + " <= ? AND " + columnName5 + " <= ? AND " + columnName7 + " IS NULL", args);
        return res;
    }

    public Cursor getCompleted() {
        SQLiteDatabase db = this.getWritableDatabase();
        String done = "done";
        String[] args={done};
        Cursor res = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName7 + " = ?", args);
        return res;
    }

    public int getID(String name) {
        Log.d("db helper", "NAMMEEEEEEEE ISSS " +name);
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args={name};
        Cursor res = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName2 + " = ?", args);
        res.moveToFirst();
        return res.getInt(0);
    }

    public String getName(int id) {
        Log.d("db helper", "id is " +id);
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args={Integer.toString(id)};
        Cursor res = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + columnName1 + " = ?", args);
        res.moveToFirst();
        return res.getString(1);
    }

    public void removeAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, null, null);
    }

    public void delete (String index) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, columnName1 + " = " + index, null);
    }
    public void done (String index) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(columnName7,"done");
        db.update(tableName, cv, "_id " + "=" + index, null);
    }

}

