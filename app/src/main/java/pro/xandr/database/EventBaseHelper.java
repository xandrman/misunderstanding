package pro.xandr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import pro.xandr.database.EventDbSchema.EventTable;

public class EventBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "eventBase.db";

    public EventBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EventTable.NAME + "(" +
                "_id integer PRIMARY KEY AUTOINCREMENT, " +
                EventTable.Cols.UUID + ", " +
                EventTable.Cols.TITLE + ", " +
                EventTable.Cols.DATE + ", " +
                EventTable.Cols.SOLVED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
