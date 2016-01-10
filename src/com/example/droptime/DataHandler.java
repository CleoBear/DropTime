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
		//���U���W
		SQLiteDB helper = new SQLiteDB(context, db_name, null, 1);
		db = helper.getReadableDatabase();
	}
	
	
	/**
     * �g�J�s����
     * @param String title	���D
     * @param String type	����
     * @param String spend	��O�ɶ�
     * @return ���\:�g�J��� / ����:-1
     */
    public long insertNewRecord(String title,Integer type,String spent,Long time){
    	
    	//�g�iSQLite
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
     * ��s����
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
     * ���o�Ҧ�����
     * @return Cursor
     */
 	public Cursor getAll() {
 		return db.query(table_name,						//��ƪ�W��
 				new String[] {"_UUID", "title", "date", "spent", "type"},	//���W��
 				null, // WHERE
 				null, // WHERE ���Ѽ�
 				null, // GROUP BY
 				null, // HAVING
 				"DATE DESC"  // ORDOR BY
 				);
 	}
 	
 	/**
 	 * ���o�@�����by key
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
 	 * �R���ӵ����
 	 * @param uuid
 	 */
 	public long deleteRecord(int uuid){
 		 return db.delete(table_name, "_UUID=" + uuid, null);
 	}
 
}
