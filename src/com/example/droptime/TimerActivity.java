package com.example.droptime;

import java.text.ParseException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class TimerActivity extends Activity {
	
	
	
	private ImageButton start 	= null;
	private ImageButton refresh = null;
	private ImageButton save 	= null;
	private TextView 	time  	= null;
	private ImageView	list	= null;
	private TextView 	taskTitle = null;
	
	private Handler handler = new Handler();
	private int status = 0;
	
	static final String DEFAULT_TIME = "00:00";
	
	public 	Long startTime ;
	public	Long pauseTime = 0L;
	public	Long spentTime = 0L;
	public	String timeLog = DEFAULT_TIME;
	public	int flag = 0;
	public  String title = "";
	public  int uuid = 0;
	public  int type = 0;
	public boolean isUpdate = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
		
		time 	= (TextView)	findViewById(R.id.timer); 
		start 	= (ImageButton)	findViewById(R.id.start);
		save 	= (ImageButton)	findViewById(R.id.save);
		list 	= (ImageView)	findViewById(R.id.list);
		refresh = (ImageButton)	findViewById(R.id.refresh);
		taskTitle = (TextView)  findViewById(R.id.taskTitle);
				
		list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_MAIN,null);
				intent.setClass(TimerActivity.this, ListActivity.class);
				startActivity(intent);
			}
		});
		
		start.setOnClickListener(new ImageButton.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(status==0){
					try {
						startTimer(spentTime);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(status==1){
					pauseTimer();
				}
			}});
        
     
        refresh.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				refreshTimer();
			}
        	
        });
        
        save.setOnClickListener(new ImageButton.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				logTime();
			}
        	
        });
        
        //update 
        uuid = getIntent().getIntExtra("uuid", 0);
        if(uuid > 0){
        	isUpdate = true;
        }
        if(isUpdate){
	        DataHandler dl = new  DataHandler(TimerActivity.this);
			Cursor cursor = dl.getRow(uuid);
			for (int i = 0; i < cursor.getCount(); i++) {
				//move cursor to position
	            cursor.moveToPosition(i);
	            title 		= cursor.getString(0);
	            timeLog 	= cursor.getString(1);
				spentTime 	= cursor.getLong(2);
				type 		= cursor.getInt(3);
			}
			
        }
        
        taskTitle.setText(title);
		time.setText(timeLog);
        
	}
	
	
	
	/**
	 * 開始計時
	 * @throws ParseException 
	 */
	public void startTimer(Long spentTime) throws ParseException{
		
		this.status = 1;
		//get now time
		
		if(flag==0){
			flag = 1;
			startTime = System.currentTimeMillis() - spentTime;
		}else{
			startTime = System.currentTimeMillis();
		}
        //設定定時要執行的方法
        handler.removeCallbacks(updateTimer);
        //set up delay time
        handler.postDelayed(updateTimer, 1000);
 
        start.setImageResource(R.drawable.pause);
	}
	
	/**
	 * 暫停計時
	 */
	public void pauseTimer(){
		
		this.status = 0;
		this.pauseTime = spentTime;
		start.setImageResource(R.drawable.play);
		handler.removeCallbacks(updateTimer);
	}
	
	/**
	 * 歸零，重新開始
	 */
	public void refreshTimer(){
		Toast.makeText(TimerActivity.this,"refresh",Toast.LENGTH_SHORT).show();
		this.status = 0;
		this.pauseTime = 0L;
		this.spentTime = 0L;
		timeLog = DEFAULT_TIME;
		time.setText(timeLog);
		start.setImageResource(R.drawable.play);
		handler.removeCallbacks(updateTimer);
	}
	
	
	public void logTime(){		
		pauseTimer();
		LayoutInflater factory=LayoutInflater.from(TimerActivity.this);
		final View v1=factory.inflate(R.layout.dialog_view,null);
		final EditText titleEditText = (EditText) v1.findViewById(R.id.title);
		final RadioGroup radioGroup = (RadioGroup) v1.findViewById(R.id.radioGroup);
		titleEditText.setText(title);
		new AlertDialog.Builder(TimerActivity.this)
		.setTitle("Please enter title")
		.setIcon(R.drawable.drop_small)
		.setMessage("You spent " + timeLog + " to done this task , please enter title to save")
		.setView(v1)
		.setPositiveButton("save",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
				title = titleEditText.getText().toString();
				type = 	getTypeValue(radioGroup.getCheckedRadioButtonId());
				
				DataHandler dl = new  DataHandler(TimerActivity.this);
				if(isUpdate){
					int result = dl.updateRecord(uuid, title, type, timeLog, spentTime);
				
					if(result == 0){
						Toast.makeText(TimerActivity.this,"update failed",Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(TimerActivity.this,"Loged",Toast.LENGTH_LONG).show();
					}
					
					taskTitle.setText(title);
						
				}else{
					long result = dl.insertNewRecord(title, type, timeLog, spentTime);
				
					if(result == -1){
						Toast.makeText(TimerActivity.this,"insert failed",Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(TimerActivity.this,"Loged",Toast.LENGTH_LONG).show();
					}
				
				}
				
			}
		})
		.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		})
		.show();
	}

	public int getTypeValue(int checkedId)
    {    
		int type = 0;
	    switch(checkedId)
	    {
	    case R.id.work:
	    	type= 1;
	    break;
	    case R.id.exercise:
	    	type= 2;
	    break;
	    default:
	    	type= 0;
	    }
	    return type;
    } 
	
	/**
	 * 更新計時
	 */
    private Runnable updateTimer = new Runnable() {
        
    	//private Long spentTime;

		public void run() {
			
            spentTime = System.currentTimeMillis() - startTime;
            
            if(pauseTime!= 0L){
            	spentTime = spentTime + pauseTime;
            }
            Long caculateTime = (spentTime/1000);
            //計算目前已過小時數
            Long hours = caculateTime/(3600);
            caculateTime = caculateTime - (hours * 3600);
            //計算目前已過分鐘數
            Long minus = caculateTime/60;
            caculateTime = caculateTime - minus * 60;
            //計算目前已過秒數
            Long seconds = caculateTime;
            timeLog = Util.formatTime(hours,minus,seconds);
            time.setText(timeLog);
            handler.postDelayed(this,1000);
        }
    };
    
    @Override
	public void onConfigurationChanged(Configuration newConfig){
	    super.onConfigurationChanged(newConfig);
	    // Checks the orientation of the screen
	    if(newConfig.orientation ==Configuration.ORIENTATION_LANDSCAPE){
	    	
	    }else if(newConfig.orientation ==Configuration.ORIENTATION_PORTRAIT){
	    	
	    }
	}
    
   
}
