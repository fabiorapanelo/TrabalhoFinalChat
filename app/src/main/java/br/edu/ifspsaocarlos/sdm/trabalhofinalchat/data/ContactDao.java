package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;

/**
 * Created by Robson on 23/06/2017.
 */

public class ContactDao {
    private static ContactDao instance = new ContactDao();
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public static final String TABLE_NAME = "contact";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_NICKNAME = "nickname";
    public static final String TABLE_CREATE = "CREATE TABLE "+ TABLE_NAME +" (" +
                                                KEY_ID  +  " INTEGER PRIMARY KEY, " +
                                                KEY_NAME + " TEXT NOT NULL, " +
                                                KEY_NICKNAME + " TEXT NOT NULL);";

    public static ContactDao getInstance(){
        return instance;
    }

    public long save(Contact contact) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, contact.getId());
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_NICKNAME, contact.getNickname());

        long id = database.insert(TABLE_NAME, null, values);
        database.close();
        return id;
    }

    public Contact findById(long contactId) {
        database = dbHelper.getReadableDatabase();

        Cursor cursor;
        Contact contact = new Contact();

        String[] cols = new String[] {KEY_ID, KEY_NAME, KEY_NICKNAME};
        String where = null;
        String[] argWhere = null;
        String groupBy = null;
        String having = null;
        String orderBy = KEY_NAME;

        where = KEY_ID + " = ? ";
        argWhere = new String[]{"" + contactId};

        cursor = database.query(TABLE_NAME, cols, where , argWhere, groupBy, having, orderBy, "1");

        if (cursor!=null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setNickname(cursor.getString(2));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        return contact;
    }

    public List<Contact> findAll() {
        database = dbHelper.getReadableDatabase();
        List<Contact> contacts = new ArrayList<>();

        Cursor cursor;

        String[] cols = new String[] {KEY_ID, KEY_NAME, KEY_NICKNAME};
        String where = null;
        String[] argWhere = null;
        String groupBy = null;
        String having = null;
        String orderBy = KEY_NAME;

        cursor = database.query(TABLE_NAME, cols, where , argWhere, groupBy, having, orderBy);

        if (cursor!=null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setNickname(cursor.getString(2));
                contacts.add(contact);
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        return contacts;
    }
}
