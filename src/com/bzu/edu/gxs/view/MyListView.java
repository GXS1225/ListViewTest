package com.bzu.edu.gxs.view;

import com.bzu.gxs.OnRersh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {
	
	private HeaderView head_view=null;
	//起始的y坐标
	private float pageY = 0f;
	//阻尼器
	private float DAMPER=1.25f;
	private OnRersh onRershListener=null;

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	private void initView(Context context) {
		// TODO Auto-generated method stub
		head_view=new HeaderView(context);
		this.addHeaderView(head_view);
	}

	//触摸事件方法，发生屏幕按下、抬起、滑动，程序都回调该方法
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		/**
		 一,判断用户触发事件的三种状态
		 	1，屏幕按下事件：
		 		屏幕按下时，获得用户在屏幕的起始y坐标
		 	2，屏幕移动事件：
		 		屏幕移动时，动态的变化HeaderVeiw的状态
		 		（1）获得移动的距离
		 		（2）获得最终的y坐标
		 		（3）首项item为HeaderView时，并且状态不是刷新，
		 			则调用setHeaderViewHight（）方法来设置高度，---设置状态
		 	3，屏幕抬起事件：
		 		屏幕抬起时：根据HeaderView当前显示条的状态判断是否刷新
		 		（1）获得HeaderView的状态,
		 			下拉时：不刷新
		 			上拉时：刷新
		 			刷新时：
		 * */
		switch (ev.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			pageY=ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float move=0;//移动的距离
			move=ev.getY()-pageY;
			pageY=ev.getY();
			
			if(head_view.getSTATE()!=HeaderView.UPDATA && getFirstVisiblePosition()==0 &&
						(move/DAMPER+head_view.getHeaderViewHight())>0){
				setHeaderViewHight(move/DAMPER);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			switch (head_view.getSTATE()) {
			case HeaderView.DOWN:
				//下拉显示状态，不刷新,高度0
				setHeaderViewHight(-head_view.getHeaderViewHight());
				break;
			case HeaderView.UPDATA:
				//刷新状态
				break;
			case HeaderView.UP:
				//开始刷新
				setHeaderViewHight(75-head_view.getHeaderViewHight());//设置HeaderView的高为75
				head_view.setSTATE(HeaderView.UPDATA);
				head_view.setTime();
				//调用刷新接口
				if(onRershListener!=null){
					onRershListener.OnRershListener();
				}
				break;
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**设置HeaderView的高度，并根据滑动位移，设置其状态*/
	private void setHeaderViewHight(float f) {
		// TODO Auto-generated method stub
		//设置HeaderView的高度  并给HeaderView设置不同的状态。（不同的状态显示不同的header样式）
		/*
		 * 
		 1, 调用Headeview的方法设置其高度
		 2，判断Headeview的状态不是UPDATA
		 	判断HeaderView的高度，来设置状态
		 	(1)距离大于60---设置UP
		 	(2)否则大于0---设置Down
		 */
		head_view.setHeaderViewHight((int)(f+head_view.getHeaderViewHight()));
		
		if(head_view.getSTATE()!=HeaderView.UPDATA){
			
			if(head_view.getHeaderViewHight()>75){
				head_view.setSTATE(HeaderView.UP);//上拉状态
			}else if(head_view.getHeaderViewHight()>0) {
				head_view.setSTATE(HeaderView.DOWN);
			}
		}
	}
	public void setOnRershListener(OnRersh onRershListener) {
		this.onRershListener = onRershListener;
	}
	/**结束刷新*/
	public void endOnRersh() {
		// TODO Auto-generated method stub
		//状态设置为Down（默认的初始状态），高度0
		head_view.setSTATE(HeaderView.DOWN);
		head_view.setHeaderViewHight(-(int)head_view.getHeaderViewHight());
	}
}
