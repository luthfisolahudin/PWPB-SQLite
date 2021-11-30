package id.luthfisolahudin.smkn4.pwpb.sqlite.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import id.luthfisolahudin.smkn4.pwpb.sqlite.model.Person;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PersonInformation";
    private static final String TABLE_NAME = "persons";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryCreateUserTable = "CREATE TABLE " + TABLE_NAME + "(" + KEY_NAME + " TEXT PRIMARY KEY, " + KEY_AGE + " INTEGER)";
        sqLiteDatabase.execSQL(queryCreateUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = ("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert(Person person) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_NAME, person.getName());
        contentValues.put(KEY_AGE, person.getAge());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public List<Person> getPersonList() {
        List<Person> users = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {KEY_NAME, KEY_AGE};

        Cursor c = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);

        while (c.moveToNext()) {
            String name = c.getString(0);
            int age = c.getInt(1);

            users.add(new Person().setName(name).setAge(age));
        }

        return users;
    }

    public void delete(String name) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String whereClause = KEY_NAME + " = '" + name + "'";

        sqLiteDatabase.delete(TABLE_NAME, whereClause, null);
    }

    public void update(Person person) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues values = new ContentValues();

        String whereClause = KEY_NAME + " = '" + person.getName() + "'";

        values.put(KEY_AGE, person.getAge());

        sqLiteDatabase.update(TABLE_NAME, values, whereClause, null);
    }
}
