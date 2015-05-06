package com.bzu.edu.gxs.adapter;

import java.util.List;
import com.bzu.gxs.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ArticleAdapter extends BaseAdapter {
	private List<Messages> list_mes=null;
	private Context context;
	
	public ArticleAdapter(Context context,List<Messages> list_mes) {
		// TODO Auto-generated constructor stub
		this.list_mes=list_mes;
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_mes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_mes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ListItem listitem;
		if(convertView==null)
		{
			convertView=(LinearLayout)LayoutInflater.from(context).inflate(R.layout.list_layout, null);
			
			listitem=new ListItem();
			listitem.img=(ImageView)convertView.findViewById(R.id.head_img);
			listitem.name=(TextView)convertView.findViewById(R.id.name);
			//显示当前日期
			listitem.time=(TextView)convertView.findViewById(R.id.time);
			listitem.rq=(TextView)convertView.findViewById(R.id.rq);
			listitem.article=(TextView)convertView.findViewById(R.id.article);
			convertView.setTag(listitem);
		}else {
			listitem=(ListItem)convertView.getTag();
		}
		
		listitem.img.setImageDrawable(list_mes.get(position).getImg());
		listitem.name.setText(list_mes.get(position).getName());
		listitem.time.setText(list_mes.get(position).getTime());
		listitem.rq.setText(list_mes.get(position).getRq());
		listitem.article.setText(list_mes.get(position).getArticel());
		return convertView;
	}
	
	public class ListItem{
		ImageView img;
		TextView name;
		TextView time;
		TextView rq;
		TextView article;
	}
}
