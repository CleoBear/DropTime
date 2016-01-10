package com.example.droptime;

import android.widget.FrameLayout;
import android.widget.TextView;

public class ViewTag {
	
	public TextView title,date,spent;
	public FrameLayout type;
	ViewTag(TextView title,TextView date,TextView spent,FrameLayout type){
		this.title = title;
		this.date = date;
		this.spent = spent;
		this.type = type;
	}
	
}
