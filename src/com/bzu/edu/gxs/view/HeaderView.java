package com.bzu.edu.gxs.view;

/**
 * HeaderView头部刷新
 * hes
 * */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.bzu.gxs.*;
import android.content.Context;
import android.opengl.Visibility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HeaderView extends LinearLayout {
	private LinearLayout linear=null;
	
	//当前状态
	private int STATE=DOWN;
	//下拉状态
	public static final int DOWN=0;
	//上拉状态
	public static final int UP=1;
	//刷新状态
	public static final int UPDATA=2;
	
	//布局控件
	private TextView t1,t2=null;
	private ImageView img=null;
	private ProgressBar probar=null;
	
	//旋转动画对象
	private RotateAnimation rotate1,rotate2=null;
	
	public HeaderView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//初始化刷新头部
		initView(context);
	}

	private void initView(Context context) {
		// TODO Auto-generated method stub
		/*
		 1,加载header_view布局
		 2，初始化创建动画对象
		 * */
		linear=(LinearLayout)LayoutInflater.from(context).inflate(R.layout.header_view, null);//加载布局，第二个对象表示根视图，null表示此布局是根视图。
		LinearLayout.LayoutParams layoutparams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
		this.addView(linear, layoutparams);
		
		//初始化布局控件
		t1=(TextView)linear.findViewById(R.id.text);
		t2=(TextView)linear.findViewById(R.id.text1);
		img=(ImageView)linear.findViewById(R.id.pull);
		probar=(ProgressBar)linear.findViewById(R.id.progress);
		
		//创建动画对象
		rotate1 = new RotateAnimation(0, -180, 1, 0.5f, 1, 0.5f);//中点逆时针旋转180度。
		rotate2 = new RotateAnimation(-180, 0, 1, 0.5f, 1, 0.5f);
		rotate1.setDuration(200);
		rotate2.setDuration(200);
		rotate1.setFillAfter(true);
		rotate2.setFillAfter(true);
	}
	
	/**
	 * 设置view的高度
	 * */
	public void setHeaderViewHight(int height){
		if(height<0){
			height=0;
		}
		//获得HeaderView的布局设置
		LinearLayout.LayoutParams layp=(LayoutParams)linear.getLayoutParams();
		layp.height=height;
		//设置HeaderView的高度
		linear.setLayoutParams(layp);
	}

	public int getSTATE() {
		return STATE;
	}

	/**
	 * 根据父容器MyListView的触摸位移
	 * 设置HeaderView的显示条的状态
	 * */
	public void setSTATE(int sTATE) {
		//显示条的3种状态
		/*
		 * 1，下拉状态：
		 * 		显示img，隐藏进度条
		 * 		判断上一个状态，如果是上拉则开始动画2,回到原始状态（触摸移动发生时，有向上移动）。
		 * 2，刷新状态
		 * 		隐藏img，显示进度条
		 * 3，上拉状态
		 * 		显示img，隐藏进度条
		 * 		判断上一个状态，如果是下拉则开始动画1.
		 * */
		switch (sTATE) {
		case DOWN://下拉状态
			t1.setText("下拉刷新");
			
			img.setVisibility(View.VISIBLE);
			probar.setVisibility(View.GONE);
			switch (STATE) {//某一时刻  开始动画。
				case DOWN:
					break;
				case UP:
					img.startAnimation(rotate2);//如果上一个状态是下拉，则返回原始
					break;
			}
			break;
			
		case UPDATA:
			t1.setText("正在刷新");
			img.clearAnimation();//清除动画
			img.setVisibility(View.GONE);
			probar.setVisibility(View.VISIBLE);
			switch (STATE) {
				case DOWN:
					break;
				case UPDATA:
					break;
				case UP:
					break;
			}
			break;
		case UP:
			t1.setText("松开刷新");
			img.setVisibility(View.VISIBLE);
			probar.setVisibility(View.GONE);
			switch (STATE) {
				case DOWN:
					img.startAnimation(rotate1);//如果上一个状态是旋转上拉动画，
					break;
				case UPDATA:
					break;
				case UP:
					break;
		}
		break;
		}
		STATE = sTATE;//设置当前的状态
	}

	/**获得header的高度*/
	public float getHeaderViewHight() {
		// TODO Auto-generated method stub
		return linear.getHeight();
	}
	public void setTime(){
		Date date=new Date();
		SimpleDateFormat simple=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		String datatime=simple.format(date);
		t2.setText("刷新时间:"+datatime);
	}
}
