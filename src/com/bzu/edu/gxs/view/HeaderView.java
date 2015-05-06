package com.bzu.edu.gxs.view;

/**
 * HeaderViewͷ��ˢ��
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
	
	//��ǰ״̬
	private int STATE=DOWN;
	//����״̬
	public static final int DOWN=0;
	//����״̬
	public static final int UP=1;
	//ˢ��״̬
	public static final int UPDATA=2;
	
	//���ֿؼ�
	private TextView t1,t2=null;
	private ImageView img=null;
	private ProgressBar probar=null;
	
	//��ת��������
	private RotateAnimation rotate1,rotate2=null;
	
	public HeaderView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//��ʼ��ˢ��ͷ��
		initView(context);
	}

	private void initView(Context context) {
		// TODO Auto-generated method stub
		/*
		 1,����header_view����
		 2����ʼ��������������
		 * */
		linear=(LinearLayout)LayoutInflater.from(context).inflate(R.layout.header_view, null);//���ز��֣��ڶ��������ʾ����ͼ��null��ʾ�˲����Ǹ���ͼ��
		LinearLayout.LayoutParams layoutparams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
		this.addView(linear, layoutparams);
		
		//��ʼ�����ֿؼ�
		t1=(TextView)linear.findViewById(R.id.text);
		t2=(TextView)linear.findViewById(R.id.text1);
		img=(ImageView)linear.findViewById(R.id.pull);
		probar=(ProgressBar)linear.findViewById(R.id.progress);
		
		//������������
		rotate1 = new RotateAnimation(0, -180, 1, 0.5f, 1, 0.5f);//�е���ʱ����ת180�ȡ�
		rotate2 = new RotateAnimation(-180, 0, 1, 0.5f, 1, 0.5f);
		rotate1.setDuration(200);
		rotate2.setDuration(200);
		rotate1.setFillAfter(true);
		rotate2.setFillAfter(true);
	}
	
	/**
	 * ����view�ĸ߶�
	 * */
	public void setHeaderViewHight(int height){
		if(height<0){
			height=0;
		}
		//���HeaderView�Ĳ�������
		LinearLayout.LayoutParams layp=(LayoutParams)linear.getLayoutParams();
		layp.height=height;
		//����HeaderView�ĸ߶�
		linear.setLayoutParams(layp);
	}

	public int getSTATE() {
		return STATE;
	}

	/**
	 * ���ݸ�����MyListView�Ĵ���λ��
	 * ����HeaderView����ʾ����״̬
	 * */
	public void setSTATE(int sTATE) {
		//��ʾ����3��״̬
		/*
		 * 1������״̬��
		 * 		��ʾimg�����ؽ�����
		 * 		�ж���һ��״̬�������������ʼ����2,�ص�ԭʼ״̬�������ƶ�����ʱ���������ƶ�����
		 * 2��ˢ��״̬
		 * 		����img����ʾ������
		 * 3������״̬
		 * 		��ʾimg�����ؽ�����
		 * 		�ж���һ��״̬�������������ʼ����1.
		 * */
		switch (sTATE) {
		case DOWN://����״̬
			t1.setText("����ˢ��");
			
			img.setVisibility(View.VISIBLE);
			probar.setVisibility(View.GONE);
			switch (STATE) {//ĳһʱ��  ��ʼ������
				case DOWN:
					break;
				case UP:
					img.startAnimation(rotate2);//�����һ��״̬���������򷵻�ԭʼ
					break;
			}
			break;
			
		case UPDATA:
			t1.setText("����ˢ��");
			img.clearAnimation();//�������
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
			t1.setText("�ɿ�ˢ��");
			img.setVisibility(View.VISIBLE);
			probar.setVisibility(View.GONE);
			switch (STATE) {
				case DOWN:
					img.startAnimation(rotate1);//�����һ��״̬����ת����������
					break;
				case UPDATA:
					break;
				case UP:
					break;
		}
		break;
		}
		STATE = sTATE;//���õ�ǰ��״̬
	}

	/**���header�ĸ߶�*/
	public float getHeaderViewHight() {
		// TODO Auto-generated method stub
		return linear.getHeight();
	}
	public void setTime(){
		Date date=new Date();
		SimpleDateFormat simple=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		String datatime=simple.format(date);
		t2.setText("ˢ��ʱ��:"+datatime);
	}
}
