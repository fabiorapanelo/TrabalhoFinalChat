package br.edu.ifspsaocarlos.sdm.trabalhofinalchat.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.UserInfo;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Contact;
import br.edu.ifspsaocarlos.sdm.trabalhofinalchat.model.Message;

/**
 * Created by Robson on 23/06/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "chat.db";
    public static final int DATABASE_VERSION = 1;
    public UserInfoDao userInfoDao = new UserInfoDao();
    public ContactDao contactDao = new ContactDao();
    public MessageDao messageDao = new MessageDao();

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(UserInfoDao.TABLE_CREATE);
        database.execSQL(ContactDao.TABLE_CREATE);
        database.execSQL(MessageDao.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }
}

