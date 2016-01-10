package com.example.droptime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDB extends SQLiteOpenHelper{
	
	private static final int VERSION = 3;//��Ʈw����
	
	public SQLiteDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	/**
	 *���ƪ��s�b���ɭ�Ĳ�o
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		 String DATABASE_CREATE_TABLE =
	     "create table TimeLog("
	       + "_UUID INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL,"
	             + "date  VARCHAR,"
	             + "title VARCHAR,"
	             + "spent VARCHAR,"
	             + "time BIGINT,"
	             + "type  	 INT"
	            
	         + ")";
	            db.execSQL(DATABASE_CREATE_TABLE);
	}
	
	/**
	 *���ƪ����ʪ��ɭ�Ĳ�o
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//oldVersion=�ª���Ʈw�����FnewVersion=�s����Ʈw����
	    Log.e("cleo", "onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS TimeLog "); //�R���¦�����ƪ�
	    onCreate(db);
	}
	
	@Override   
	public void onOpen(SQLiteDatabase db) {     
	    super.onOpen(db);       
       // TODO �C�����\���}�ƾڮw�᭺���Q����     
	} 
	 
	@Override
    public synchronized void close() {
        super.close();
    }

}
