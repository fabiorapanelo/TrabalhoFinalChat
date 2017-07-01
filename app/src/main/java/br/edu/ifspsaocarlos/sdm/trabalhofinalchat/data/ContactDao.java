package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.UserInfo;

/**
 * Created by Robson on 23/06/2017.
 */

public class ContactDao {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private UserInfoDao userInfoDao;

    public static final String TABLE_NAME = "contact";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_NICKNAME = "nickname";
    public static final String TABLE_CREATE = "CREATE TABLE "+ TABLE_NAME +" (" +
                                                KEY_ID  +  " INTEGER PRIMARY KEY, " +
                                                KEY_NAME + " TEXT NOT NULL, " +
                                                KEY_NICKNAME + " TEXT NOT NULL);";

    public ContactDao(Context context){
        this.dbHelper = new SQLiteHelper(context);
        this.userInfoDao = new UserInfoDao(context);
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

    public ArrayList<Long> saveAll(ArrayList<Contact> contacts) {
        ArrayList ids = new ArrayList();

        for (Contact contact : contacts) {
            ids.add(save(contact));
        }

        return ids;
    }

    public Contact findById(long contactId) {

        Contact currentUser = userInfoDao.find();

        if(currentUser != null && currentUser.getId() == contactId){
            return currentUser;
        }

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
            if (cursor.moveToFirst()) {
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setNickname(cursor.getString(2));
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
