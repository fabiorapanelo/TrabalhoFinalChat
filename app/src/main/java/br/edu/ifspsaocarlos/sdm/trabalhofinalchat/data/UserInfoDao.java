package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.UserInfo;

/**
 * Created by Robson on 26-Jun-17.
 */

public class UserInfoDao {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public static final String TABLE_NAME = "user_info";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_NICKNAME = "nickname";
    public static final String TABLE_CREATE = "CREATE TABLE "+ TABLE_NAME +" (" +
            KEY_ID  +  " INTEGER PRIMARY KEY, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_NICKNAME + " TEXT NOT NULL);";

    public UserInfoDao (Context context){
        this.dbHelper = new SQLiteHelper(context);
    }

    public long save(Contact userInfo) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, userInfo.getId());
        values.put(KEY_NAME, userInfo.getName());
        values.put(KEY_NICKNAME, userInfo.getNickname());

        long id = database.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        database.close();
        return id;
    }

    public Contact find() {
        database = dbHelper.getReadableDatabase();
        Contact userInfo = null;

        Cursor cursor;

        String[] cols = new String[] {KEY_ID, KEY_NAME, KEY_NICKNAME};
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
                userInfo = new Contact();
                userInfo.setId(cursor.getInt(0));
                userInfo.setName(cursor.getString(1));
                userInfo.setNickname(cursor.getString(2));
                cursor.moveToNext();
            }
            cursor.close();
        }
        database.close();

        return userInfo;
    }
}
