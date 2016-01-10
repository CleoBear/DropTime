package com.example.droptime;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity {
	
	private ImageView timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		timer = (ImageView)findViewById(R.id.timer);
		timer.setOnClickListener(new ImageView.OnClickListener() {
			@Override
			public void onClick(View v) {
				timer.setScaleX((float) 1.2);
				timer.setScaleY((float) 1.2);
				Intent intent = new Intent(Intent.ACTION_VIEW,null);
				intent.setClass(ListActivity.this, TimerActivity.class);
				startActivity(intent);
			}
		});
		
		ListView logListView  = (ListView)findViewById(R.id.logListView);
		final ArrayList<TimeLogData> timeLogList= new ArrayList<TimeLogData>();
		//render list
		DataHandler dl = new  DataHandler(ListActivity.this);
		Cursor cursor = dl.getAll();
		for (int i = 0; i < cursor.getCount(); i++) {
            //移到指定位置
            cursor.moveToPosition(i);
            //取得第一個欄位
            TimeLogData timeLogData = new TimeLogData();
            timeLogData.uuid = cursor.getInt(0);
            timeLogData.title = cursor.getString(1);
            timeLogData.date = cursor.getString(2);
            timeLogData.spent = cursor.getString(3);
            timeLogData.type = cursor.getInt(4);
            timeLogList.add(timeLogData);
        }
		//連接adapter
		final TimeLogDataAdapter adapter = new TimeLogDataAdapter();
		adapter.context = ListActivity.this;
		adapter.adapter = adapter;
		adapter.cursor = cursor;
		adapter.timeLogList = timeLogList;
		logListView.setAdapter(adapter);
		logListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            	Intent intent = new Intent(Intent.ACTION_VIEW,null);
				intent.setClass(ListActivity.this, TimerActivity.class);
                intent.putExtra("uuid", timeLogList.get(position).uuid);
                startActivity(intent);
            }
        });
	}
	
	
	
}
