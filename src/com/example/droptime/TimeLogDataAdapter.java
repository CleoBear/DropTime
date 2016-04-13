package com.example.droptime;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TimeLogDataAdapter extends BaseAdapter{

	public Context context;
	public TimeLogDataAdapter adapter;
	public Cursor cursor;
	
	public ArrayList<TimeLogData> timeLogList = new ArrayList<TimeLogData>();
	
	@Override
	public int getCount() {
		return timeLogList.size();
	}

	@Override
	public Object getItem(int position) {
		return timeLogList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup group) {

		final LinearLayout ll;
		final LayoutInflater inflater; //找layout底下的自訂layout
		final TextView name,phoneNumber;
		final ViewTag viewTag;
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
			viewTag = new ViewTag(
					(TextView) convertView.findViewById(R.id.title), 
					(TextView) convertView.findViewById(R.id.date), 
					(TextView) convertView.findViewById(R.id.spent),
					(FrameLayout)convertView.findViewById(R.id.type));
			convertView.setTag(viewTag);
		}else{
			viewTag = (ViewTag) convertView.getTag();	
		}
			
		//set the values
		viewTag.title.setText(timeLogList.get(position).title);
		viewTag.date.setText(timeLogList.get(position).date);
		viewTag.spent.setText(timeLogList.get(position).spent);
		viewTag.type.setBackgroundColor(Color.parseColor(getStyleColor(timeLogList.get(position).type)));
		//設定回收桶
		ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DataHandler dl = new  DataHandler(context);
				Long deleteRs = dl.deleteRecord(timeLogList.get(position).uuid);
				String deleteRsStr = "delete failed";
				if(deleteRs > 0){
					deleteRsStr = "delete record";
				}
				timeLogList.remove(position);
				adapter.notifyDataSetChanged();
				Toast.makeText(context,deleteRsStr,Toast.LENGTH_SHORT).show();
				
			}
		});
		
		return convertView;
	}
	
	
	private String getStyleColor(Integer type){
		String color = "#bf00ff";
		if(type == 1){
			color = "#1e90ff";
		}else if(type == 2){
			color = "#ff5555";
		}else{
			color = "#3fcd32";
		}
		return color;
	}
}
