package com.example.droptime;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

public class Util {
	
	@SuppressLint("SimpleDateFormat")
	static String getDateTime() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);		
		return strDate;
    }
	
	
	 /**
     * 格式化計時時間
     * @param Long hours	時
     * @param Long minus	分
     * @param Long seconds	秒
     * @return timeText		花費時間 00:00:00
     */
	static String formatTime(Long hours,Long minus,Long seconds){
    	DecimalFormat df = new DecimalFormat("00");
    	String timeText = TimerActivity.DEFAULT_TIME;
    	timeText = df.format(minus) + ":" + df.format(seconds);
    	if(hours!=0L){
    		timeText = df.format(hours) + ":" + timeText;
    	}
    	return timeText;
    }
}
