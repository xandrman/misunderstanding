package pro.xandr.misunderstanding;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import pro.xandr.database.EventBaseHelper;
import pro.xandr.database.EventDbSchema;
import pro.xandr.database.EventDbSchema.EventTable;

public class EventLab {

    private static EventLab sEventLab;

    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    private static ContentValues getContentValues(Event event) {
        ContentValues values = new ContentValues();
        values.put(EventTable.Cols.UUID, event.getUuid().toString());
        values.put(EventTable.Cols.TITLE, event.getTitle());
        values.put(EventTable.Cols.DATE, event.getDate().getTime());
        values.put(EventTable.Cols.SOLVED, event.isSolved() ? 1 : 0);
        return values;
    }

    private EventLab(Context context) {
        this.context = context.getApplicationContext();
        sqLiteDatabase = new EventBaseHelper(context).getWritableDatabase();
    }

    public static EventLab get(Context context) {
        if (sEventLab == null) {
            sEventLab = new EventLab(context);
        }
        return sEventLab;
    }

    public List<Event> getEventList() {
        List<Event> eventList = new ArrayList<>();
        EventCursorWrapper cursor = queryEvents(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                eventList.add(cursor.getEvent());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return eventList;
    }

    public Event getEvent(UUID uuid) {
        EventCursorWrapper cursor = queryEvents(
                EventTable.Cols.UUID + " = ?",
                new String[] { uuid.toString() }
                );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getEvent();
        } finally {
            cursor.close();
        }
    }

    public void addEvent(Event event) {
        ContentValues values = getContentValues(event);
        sqLiteDatabase.insert(EventTable.NAME, null, values);
    }

    public void updateEvent(Event event) {
        String uuidString = event.getUuid().toString();
        ContentValues values = getContentValues(event);
        sqLiteDatabase.update(
                EventTable.NAME,
                values,
                EventTable.Cols.UUID + " = ?",
                new String[] { uuidString }
                );
    }

    private EventCursorWrapper queryEvents(String whereClause, String[] whereArgs) {
        Cursor cursor = sqLiteDatabase.query(
                EventTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new EventCursorWrapper(cursor);
    }


}
