package com.example.droptime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataHandler {
	
	SQLiteDatabase db;
	
	/**
	 * table name
	 */
	public String table_name = "TimeLog";
	 
	/**
	 * data base name 
	 */
	public String db_name = "SQLiteDB";
	
	public DataHandler(Context context){
		//輔助類名
		SQLiteDB helper = new SQLiteDB(context, db_name, null, 1);
		db = helper.getReadableDatabase();
	}
	
	
	/**
     * 寫入新紀錄
     * @param String title	標題
     * @param String type	類型
     * @param String spend	花費時間
     * @return 成功:寫入行數 / 失敗:-1
     */
    public long insertNewRecord(String title,Integer type,String spent,Long time){
    	
    	//寫進SQLite
		 ContentValues cv = new ContentValues();
		 String now = Util.getDateTime();
		 cv.put("date",now);
		 cv.put("title",title);
		 cv.put("type",type);
		 cv.put("spent",spent);
		 cv.put("time",time);
		 long result = db.insert(table_name, "", cv);
		 return result;
    }
    
    
    /**
     * 更新紀錄
     * @param item
     * @return
     */
    public int updateRecord(int uuid, String title,Integer type,String spent,Long time) {
       
        ContentValues cv = new ContentValues();
        String now = Util.getDateTime();
        cv.put("date", now);
        cv.put("title",title);
		cv.put("type",type);
		cv.put("spent",spent);
		cv.put("time",time);

        String where = "_UUID =" + uuid;
 
        return db.update(table_name, cv, where, null);         
    }
    
    /**
     * 取得所有紀錄
     * @return Cursor
     */
 	public Cursor getAll() {
 		return db.query(table_name,						//資料表名稱
 				new String[] {"_UUID", "title", "date", "spent", "type"},	//欄位名稱
 				null, // WHERE
 				null, // WHERE 的參數
 				null, // GROUP BY
 				null, // HAVING
 				"DATE DESC"  // ORDOR BY
 				);
 	}
 	
 	/**
 	 * 取得一筆資料by key
 	 * @param uuid
 	 * @return
 	 */
 	public Cursor getRow(int uuid){
 		
 		String whereStr = "_UUID = " + uuid;
 		
 		return db.query(table_name,
 						new String[] {"title","spent","time","type"},
 						whereStr,
 						null,
 						null,
 						null,
 						null,
 						null);
 	}
 	/**
 	 * 刪除該筆資料
 	 * @param uuid
 	 */
 	public long deleteRecord(int uuid){
 		 return db.delete(table_name, "_UUID=" + uuid, null);
 	}
 
}
