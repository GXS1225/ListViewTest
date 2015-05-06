package com.bzu.edu.gxs.view;

import com.bzu.gxs.OnRersh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {
	
	private HeaderView head_view=null;
	//��ʼ��y����
	private float pageY = 0f;
	//������
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

	//�����¼�������������Ļ���¡�̧�𡢻��������򶼻ص��÷���
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		/**
		 һ,�ж��û������¼�������״̬
		 	1����Ļ�����¼���
		 		��Ļ����ʱ������û�����Ļ����ʼy����
		 	2����Ļ�ƶ��¼���
		 		��Ļ�ƶ�ʱ����̬�ı仯HeaderVeiw��״̬
		 		��1������ƶ��ľ���
		 		��2��������յ�y����
		 		��3������itemΪHeaderViewʱ������״̬����ˢ�£�
		 			�����setHeaderViewHight�������������ø߶ȣ�---����״̬
		 	3����Ļ̧���¼���
		 		��Ļ̧��ʱ������HeaderView��ǰ��ʾ����״̬�ж��Ƿ�ˢ��
		 		��1�����HeaderView��״̬,
		 			����ʱ����ˢ��
		 			����ʱ��ˢ��
		 			ˢ��ʱ��
		 * */
		switch (ev.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			pageY=ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float move=0;//�ƶ��ľ���
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
				//������ʾ״̬����ˢ��,�߶�0
				setHeaderViewHight(-head_view.getHeaderViewHight());
				break;
			case HeaderView.UPDATA:
				//ˢ��״̬
				break;
			case HeaderView.UP:
				//��ʼˢ��
				setHeaderViewHight(75-head_view.getHeaderViewHight());//����HeaderView�ĸ�Ϊ75
				head_view.setSTATE(HeaderView.UPDATA);
				head_view.setTime();
				//����ˢ�½ӿ�
				if(onRershListener!=null){
					onRershListener.OnRershListener();
				}
				break;
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**����HeaderView�ĸ߶ȣ������ݻ���λ�ƣ�������״̬*/
	private void setHeaderViewHight(float f) {
		// TODO Auto-generated method stub
		//����HeaderView�ĸ߶�  ����HeaderView���ò�ͬ��״̬������ͬ��״̬��ʾ��ͬ��header��ʽ��
		/*
		 * 
		 1, ����Headeview�ķ���������߶�
		 2���ж�Headeview��״̬����UPDATA
		 	�ж�HeaderView�ĸ߶ȣ�������״̬
		 	(1)�������60---����UP
		 	(2)�������0---����Down
		 */
		head_view.setHeaderViewHight((int)(f+head_view.getHeaderViewHight()));
		
		if(head_view.getSTATE()!=HeaderView.UPDATA){
			
			if(head_view.getHeaderViewHight()>75){
				head_view.setSTATE(HeaderView.UP);//����״̬
			}else if(head_view.getHeaderViewHight()>0) {
				head_view.setSTATE(HeaderView.DOWN);
			}
		}
	}
	public void setOnRershListener(OnRersh onRershListener) {
		this.onRershListener = onRershListener;
	}
	/**����ˢ��*/
	public void endOnRersh() {
		// TODO Auto-generated method stub
		//״̬����ΪDown��Ĭ�ϵĳ�ʼ״̬�����߶�0
		head_view.setSTATE(HeaderView.DOWN);
		head_view.setHeaderViewHight(-(int)head_view.getHeaderViewHight());
	}
}
