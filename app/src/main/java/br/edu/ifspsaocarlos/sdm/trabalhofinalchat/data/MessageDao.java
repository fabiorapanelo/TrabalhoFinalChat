package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Message;

/**
 * Created by Robson on 26-Jun-17.
 */

public class MessageDao {
    private static MessageDao instance = new MessageDao();
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public static final String TABLE_NAME = "message";
    public static final String KEY_ID = "id";
    public static final String KEY_ORIGIN_ID = "origin_id";
    public static final String KEY_DESTINATION_ID = "destination_id";
    public static final String KEY_SUBJECT= "subject";
    public static final String KEY_BODY = "body";
    public static final String TABLE_CREATE = "CREATE TABLE "+ TABLE_NAME +" (" +
            KEY_ID  +  " INTEGER PRIMARY KEY, " +
            KEY_ORIGIN_ID + " INTEGER NOT NULL, " +
            KEY_DESTINATION_ID + " INTEGER NOT NULL, " +
            KEY_SUBJECT + " TEXT NOT NULL, " +
            KEY_BODY + " TEXT NOT NULL);";

    public static MessageDao getInstance(){
        return instance;
    }

    public long save(Message message) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, message.getId());
        values.put(KEY_ORIGIN_ID, message.getOrigin().getId());
        values.put(KEY_DESTINATION_ID, message.getDestination().getId());
        values.put(KEY_SUBJECT, message.getSubject());
        values.put(KEY_BODY, message.getBody());

        long id = database.insert(TABLE_NAME, null, values);
        database.close();
        return id;
    }

    public Message findById(long messageId) {
        database = dbHelper.getReadableDatabase();
        Message message = new Message();

        Cursor cursor;

        String[] cols = new String[] {KEY_ID, KEY_ORIGIN_ID, KEY_DESTINATION_ID, KEY_SUBJECT, KEY_BODY};
        String where = null;
        String[] argWhere = null;
        String groupBy = null;
        String having = null;
        String orderBy = KEY_ID;

        cursor = database.query(TABLE_NAME, cols, where , argWhere, groupBy, having, orderBy, "1");

        if (cursor!=null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                message.setId(cursor.getInt(0));
                message.setOrigin(ContactDao.getInstance().findById(cursor.getInt(1)));
                message.setDestination(ContactDao.getInstance().findById(cursor.getInt(2)));
                message.setSubject(cursor.getString(3));
                message.setBody(cursor.getString(4));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        return message;
    }

    public List<Message> findAll() {
        database = dbHelper.getReadableDatabase();
        List<Message> messages = new ArrayList<>();

        Cursor cursor;

        String[] cols = new String[] {KEY_ID, KEY_ORIGIN_ID, KEY_DESTINATION_ID, KEY_SUBJECT, KEY_BODY};
        String where = null;
        String[] argWhere = null;
        String groupBy = null;
        String having = null;
        String orderBy = KEY_ID;

        cursor = database.query(TABLE_NAME, cols, where , argWhere, groupBy, having, orderBy);

        if (cursor!=null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Message message = new Message();
                message.setId(cursor.getInt(0));
                message.setOrigin(ContactDao.getInstance().findById(cursor.getInt(1)));
                message.setDestination(ContactDao.getInstance().findById(cursor.getInt(2)));
                message.setSubject(cursor.getString(3));
                message.setBody(cursor.getString(4));
                messages.add(message);
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        return messages;
    }

    public List<Message> findByContactId(long contactId) {
        database = dbHelper.getReadableDatabase();
        List<Message> messages = new ArrayList<>();

        Cursor cursor;

        String[] cols = new String[] {KEY_ID, KEY_ORIGIN_ID, KEY_DESTINATION_ID, KEY_SUBJECT, KEY_BODY};
        String where = null;
        String[] argWhere = null;
        String groupBy = null;
        String having = null;
        String orderBy = KEY_ID;

        if (contactId > 0) {
            where = KEY_ORIGIN_ID + " = ? OR " + KEY_DESTINATION_ID + " = ? ";
            argWhere = new String[]{"" + contactId, "" + contactId};
        }

        cursor = database.query(TABLE_NAME, cols, where , argWhere, groupBy, having, orderBy);

        if (cursor!=null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Message message = new Message();
                message.setId(cursor.getInt(0));
                message.setOrigin(ContactDao.getInstance().findById(cursor.getInt(1)));
                message.setDestination(ContactDao.getInstance().findById(cursor.getInt(2)));
                message.setSubject(cursor.getString(3));
                message.setBody(cursor.getString(4));
                messages.add(message);
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        return messages;
    }
}
