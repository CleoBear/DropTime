package com.example.droptime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDB extends SQLiteOpenHelper{
	
	private static final int VERSION = 3;//資料庫版本
	
	public SQLiteDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	/**
	 *當資料表不存在的時候觸發
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
	 *當資料表有異動的時候觸發
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//oldVersion=舊的資料庫版本；newVersion=新的資料庫版本
	    Log.e("cleo", "onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS TimeLog "); //刪除舊有的資料表
	    onCreate(db);
	}
	
	@Override   
	public void onOpen(SQLiteDatabase db) {     
	    super.onOpen(db);       
       // TODO 每次成功打開數據庫後首先被執行     
	} 
	 
	@Override
    public synchronized void close() {
        super.close();
    }

}
