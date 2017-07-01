package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Message;

/**
 * Created by Robson on 26-Jun-17.
 */

public class MessageDao {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private ContactDao contactDao;

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

    public MessageDao(Context context){
        this.dbHelper = new SQLiteHelper(context);
        contactDao = new ContactDao(context);
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

    public ArrayList<Long> saveAll(List<Message> messages) {
        ArrayList ids = new ArrayList();

        for (Message message : messages) {
            ids.add(save(message));
        }

        return ids;
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
                message.setOrigin(contactDao.findById(cursor.getInt(1)));
                message.setDestination(contactDao.findById(cursor.getInt(2)));
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
                message.setOrigin(contactDao.findById(cursor.getInt(1)));
                message.setDestination(contactDao.findById(cursor.getInt(2)));
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

    public List<Message> findByContact(Contact contact) {
        database = dbHelper.getReadableDatabase();
        List<Message> messages = new ArrayList<>();

        Cursor cursor;

        String[] cols = new String[] {KEY_ID, KEY_ORIGIN_ID, KEY_DESTINATION_ID, KEY_SUBJECT, KEY_BODY};
        String where = null;
        String[] argWhere = null;
        String groupBy = null;
        String having = null;
        String orderBy = KEY_ID;

        where = KEY_ORIGIN_ID + " = ? OR " + KEY_DESTINATION_ID + " = ? ";
        argWhere = new String[]{String.valueOf(contact.getId()), String.valueOf(contact.getId())};

        cursor = database.query(TABLE_NAME, cols, where , argWhere, groupBy, having, orderBy);

        if (cursor!=null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Message message = new Message();
                message.setId(cursor.getInt(0));
                message.setOrigin(contactDao.findById(cursor.getInt(1)));
                message.setDestination(contactDao.findById(cursor.getInt(2)));
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

    public Message findLastMessage(Contact contact) {
        database = dbHelper.getReadableDatabase();
        Message message = null;

        Cursor cursor;

        String[] cols = new String[] {KEY_ID, KEY_ORIGIN_ID, KEY_DESTINATION_ID, KEY_SUBJECT, KEY_BODY};
        String where = null;
        String[] argWhere = null;
        String groupBy = null;
        String having = null;
        String orderBy = KEY_ID + " DESC";

        where = KEY_ORIGIN_ID + " = ? OR " + KEY_DESTINATION_ID + " = ? ";
        argWhere = new String[]{String.valueOf(contact.getId()), String.valueOf(contact.getId())};

        cursor = database.query(TABLE_NAME, cols, where , argWhere, groupBy, having, orderBy);

        if (cursor!=null)
        {
            if (cursor.moveToFirst()) {

                message = new Message();
                message.setId(cursor.getInt(0));
                message.setOrigin(contactDao.findById(cursor.getInt(1)));
                message.setDestination(contactDao.findById(cursor.getInt(2)));
                message.setSubject(cursor.getString(3));
                message.setBody(cursor.getString(4));

            }
            cursor.close();
        }
        database.close();

        return message;
    }
}
