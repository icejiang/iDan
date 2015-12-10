package com.dazhong.idan;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity{
	//ϵͳ�ӿڵ�ַ
	public static String SERVICEADRRESS="http://www.dzzcgs.com:8099/DriverAppwebServiceRelease/DriverService.asmx";
	public static String USERNAME="";//�û�����
	public static String WORKNUMBER="";//����
	public static String EMPLOYEEID="";//ϵͳ����
	//************************************************
	public static List<TaskInfo> tasklist=null;
	private ListView mListView;
	private TextView tv_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_list);
		findView();
		tv_name.setText(USERNAME);
		MyAdapter mAdapter = new MyAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), OrderDetail.class);
				startActivity(intent);

			}
		});
		// configure the SlidingMenu
		MenuLeftFragment menuLayout = new MenuLeftFragment(
				getApplicationContext());
		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		/**
		 * SLIDING_WINDOW will include the Title/ActionBar in the content
		 * section of the SlidingMenu, while SLIDING_CONTENT does not.
		 */
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.left_menu);
	}
	
	
	
	private void findView(){
		mListView = (ListView) findViewById(R.id.listView_main);
		tv_name = (TextView) findViewById(R.id.tv_titleName);
	}
	
	class MyAdapter extends BaseAdapter{
		
		private LayoutInflater mInflater = null;
		private Context mContext;
		
		public MyAdapter(Context context) {
			
			this.mContext = context;
			this.mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			
			if(tasklist!=null){
				return tasklist.size();
			} else {
				return 0;
			}
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.order_item, null);
				holder.id = (TextView) convertView.findViewById(R.id.item_id);
				holder.location = (TextView) convertView.findViewById(R.id.item_location);
				holder.name = (TextView) convertView.findViewById(R.id.item_name);
				holder.nubmer = (TextView) convertView.findViewById(R.id.item_number);
				holder.time = (TextView) convertView.findViewById(R.id.item_time);
				holder.type = (TextView) convertView.findViewById(R.id.item_type);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			TaskInfo taskInfo = tasklist.get(position);
			holder.time.setText(taskInfo.OnboardTime());
			holder.id.setText(taskInfo.TaskCode());
			holder.location.setText(taskInfo.PickupAddress());
			holder.name.setText(taskInfo.Customer());
			holder.nubmer.setText(taskInfo.CustomerTel());
			holder.type.setText(taskInfo.ServiceTypeName()); 
			
			
			
			return convertView;
		}
		
		
	}
	static class ViewHolder  
	{  
		public TextView time;  
		public TextView id;  
		public TextView type;  
		public TextView name;  
		public TextView nubmer;  
		public TextView location;  
	}
}
