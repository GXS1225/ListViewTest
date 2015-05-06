package com.bzu.gxs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.bzu.edu.gxs.adapter.ArticleAdapter;
import com.bzu.edu.gxs.adapter.Messages;
import com.bzu.edu.gxs.view.MyListView;
import com.bzu.gxs.*;
import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import android.app.Activity;
import android.content.res.TypedArray;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
	private MyListView mylistview=null;
	private List<Messages> list_msg=null;
	private BaseAdapter adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		init();
		if(list_msg==null){
			getData();
		}
		
		
		adapter=new ArticleAdapter(this,list_msg);
		
		mylistview.setOnRershListener(new OnRershListener());//设置监听器
		mylistview.setAdapter(adapter);
	}
		


	private void getData() {
		// TODO Auto-generated method stub
		//获得xml的资源
		String names[];
		String article[];
		TypedArray img;
		int i;
		
		names=getResources().getStringArray(R.array.name);
		article=getResources().getStringArray(R.array.article);
		img=getResources().obtainTypedArray(R.array.head_photo);
		
		list_msg=new ArrayList<Messages>();
		for(i=0;i<names.length;i++){
			Messages message=new Messages();
			message.setImg(img.getDrawable(i));
			message.setName(names[i]);
			message.setArticel(article[i]);
			message.setRq(String.valueOf(new Random().nextInt(120)+"分钟前"));
			Date date=new Date();
			SimpleDateFormat simple=new SimpleDateFormat("MM-dd");
			message.setTime(simple.format(date));
			list_msg.add(message);
		}
	}
	private void init() {
		// TODO Auto-generated method stub
		mylistview=(MyListView)findViewById(R.id.myListView1);
//		mylistview=new MyListView(this);
//		LinearLayout linear=(LinearLayout)MainActivity.this.findViewById(R.id.liner);
//		linear.addView(mylistview);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	Handler handler=new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message arg0) {
			// TODO Auto-generated method stub
			if(arg0.what==0){				
				//结束刷新
				mylistview.endOnRersh();
			}
			return false;
		}
	});
	
	//刷新接口实现类
	class OnRershListener implements OnRersh{
	    //实现刷新接口的 方法，，在MyList中会回调该方法。
		//模拟
		@Override
		public void OnRershListener() {
			// TODO Auto-generated method stub
			new Thread(new Run()).start();//启动一个线程
		}
	}

	//线程接口实现类
	class Run implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			handler.sendEmptyMessage(0);
		}
	}

}






