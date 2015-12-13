package com.dazhong.idan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.dazhong.idan.R.id;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class PrintActivity extends Activity {
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	public Handler mhandler;
	private BlueToothService mBTService = null;
	private Set<BluetoothDevice> devices;

	private TextView print_confirm;
	private TextView tv_base;
	private TextView tv_road;
	private TextView tv_parking;
	private TextView tv_meals;
	private TextView tv_hotel;
	private TextView tv_other;
	private TextView tv_all;
	private TextView tv_beyondMile;
	private TextView tv_beyondTime;
	private TextView tv_dateLast;
	private TextView date;
	private TextView time;
	private TextView type;
	private TextView name;
	private TextView destination;
	private TextView location;
	private TextView serviceTime;
	private TextView serviceMile;
	private TextView extraTime;
	private TextView extraMile;
	
	private NoteInfo noteInfo;
	private TaskInfo taskInfo;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.printf);

		findView();
		setData();
		
		Intent intent = getIntent();
		noteInfo = (NoteInfo) intent.getSerializableExtra(InService.INPUT_TOTAL_KEY);
		position = intent.getIntExtra("TYPE",0);
		taskInfo = MainActivity.tasklist.get(position);
		
		print_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBTService.getState() == mBTService.STATE_CONNECTED) {
					String message = printFile();
					if (message == null) {
						Toast.makeText(
								PrintActivity.this,
								PrintActivity.this.getResources().getString(
										R.string.str_printfail), 2000).show();
						return;
					}
					mBTService.PrintCharacters(message);
					Toast.makeText(
							PrintActivity.this,
							PrintActivity.this.getResources().getString(
									R.string.str_printok), 2000).show();
				} else {
					Toast.makeText(
							PrintActivity.this,
							PrintActivity.this.getResources().getString(
									R.string.str_printfail), 2000).show();
				}

				// Intent intent = new Intent();
				// intent.setClass(getApplicationContext(), MainActivity.class);
				// startActivity(intent);

			}
		});

		// ******************************************************
		mhandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case MESSAGE_STATE_CHANGE:// 蓝牙连接状态
					switch (msg.arg1) {
					case BlueToothService.STATE_CONNECTED:// 已经连接
						break;
					case BlueToothService.STATE_CONNECTING:// 正在连接
						break;
					case BlueToothService.STATE_LISTEN:
					case BlueToothService.STATE_NONE:
						break;
					case BlueToothService.SUCCESS_CONNECT:
						Toast.makeText(
								PrintActivity.this,
								PrintActivity.this.getResources().getString(
										R.string.str_succonnect), 2000).show();
						break;
					case BlueToothService.FAILED_CONNECT:
						Toast.makeText(
								PrintActivity.this,
								PrintActivity.this.getResources().getString(
										R.string.str_faileconnect), 2000)
								.show();
						break;
					case BlueToothService.LOSE_CONNECT:
						switch (msg.arg2) {
						case -1:
							Toast.makeText(
									PrintActivity.this,
									PrintActivity.this.getResources()
											.getString(R.string.str_lose), 2000)
									.show();
							break;
						case 0:
							break;
						}
					}
					break;
				case MESSAGE_READ:
					// sendFlag = false;//缓冲区已满
					break;
				case MESSAGE_WRITE:// 缓冲区未满
					// sendFlag = true;
					break;
				}
			}
		};
		mBTService = new BlueToothService(this, mhandler);
		if (mBTService.HasDevice()) {
			Toast.makeText(
					PrintActivity.this,
					PrintActivity.this.getResources().getString(
							R.string.str_devecehasblue), 2000).show();
			if (!mBTService.IsOpen()) {// 判断蓝牙是否打开
				Toast.makeText(
						PrintActivity.this,
						PrintActivity.this.getResources().getString(
								R.string.str_closed), 2000).show();

			} else {
				String connAddress = null;
				devices = mBTService.GetBondedDevice();
				if (devices.size() > 0) {
					for (BluetoothDevice device : devices) {
						if (device.getName().equals(MainActivity.stateInfo.getPrinterName()))
							connAddress = device.getAddress();
					}
					mBTService.DisConnected();
					mBTService.ConnectToDevice(connAddress);
				}

			}
		} else {
			Toast.makeText(
					PrintActivity.this,
					PrintActivity.this.getResources().getString(
							R.string.str_hasnodevice), 2000).show();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	public void onBackPressed() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mBTService != null) {
			mBTService.DisConnected();
			mBTService = null;
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintActivity.this.finish();
	}

	// ******************************************
	// define print string
	/**
	 * 路单打印格式
	 * */
	private String printFile() {

		String messages = null;
		try {
			String mes = "\r\n\r\n";
			 NoteInfo note = MainActivity.stateInfo.getCurrentNote();
			//在此调整数据格式，将实际的路单信息传入
//			NoteInfo note = new NoteInfo();
//			note.setCarNumber("沪BZ8911");
//			note.setNoteDate("20151213");
//			note.setNoteID("dz16820320151213001");
//			note.setCustomerCompany("大众物流");
//			note.setCustomerName("Ms lee");
//			note.setOnBoardAddress("renmin square");
//			note.setLeaveAddress("如果团队很小，把每个人的公钥收集起来放到服务器的/home/git/.ssh/authorized_keys这里我们不介绍怎么玩Gitosis了，几百号人的团队基本都在500强了，相信找个高水平的Linux管理员问题不大。");
//			note.setServiceBegin("09:35");
			if (note == null) {
				Toast.makeText(
						PrintActivity.this,
						PrintActivity.this.getResources().getString(
								R.string.error), 2000).show();
				return messages;
			}
			messages ="";// "--------------------------" + mes;
			messages = messages + "路单号码：" + note.getNoteID() + mes;
			messages = messages + "服务日期：" + note.getNoteDate() + mes;
			messages = messages + "营运车辆：" + note.getCarNumber() + mes;
			messages = messages + "用车客人：" + note.getCustomerName() + mes;
			messages = messages + "上车时间：" + note.getServiceBegin() + mes;
			messages = messages + "下车时间：" + note.getServiceEnd() + mes;
			messages = messages + "上车地址：" + note.getOnBoardAddress() + mes;
			messages = messages + "下车地址：" + note.getLeaveAddress() + mes;
			messages = messages + "途径地点：" + note.getServiceRoute() + mes;
			messages = messages + "服务里程："
					+ Double.toString(note.getServiceKMs()) + mes;
			messages = messages + "服务时长："
					+ Double.toString(note.getServiceTime()) + mes;
			if (note.getOverHours() > 0)
				messages = messages + "超时服务"
						+ Integer.toString(note.getOverHours()) + mes;
			if (note.getOverKMs() > 0)
				messages = messages + "超出里程："
						+ Integer.toString(note.getOverKMs()) + mes;
			messages = messages + "服务费用：" + Double.toString(note.getFeeTotal())
					+ mes;
			messages = messages + "客户签名" + mes + mes + mes;
			messages = messages + "___________________________" + mes + mes
					+ mes;
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return messages;
		}
		return messages;
	}

	private void setData() {
//		Bundle bundle = getIntent().getBundleExtra("MYKEY");
//		int all = getIntent().getIntExtra("ALL", 0);
//		int mile = getIntent().getIntExtra("MILE", 0);
//		if (bundle != null) {
//			String road = bundle.getString(AddPay.key_road);
//			String meals = bundle.getString(AddPay.key_meals);
//			String parking = bundle.getString(AddPay.key_parking);
//			String other = bundle.getString(AddPay.key_other);
//			if (!road.equals("")) {
//				tv_road.setText(road + "元");
//			}
//			if (!meals.equals("")) {
//				tv_meals.setText(meals + "元");
//			}
//			if (!parking.equals("")) {
//				tv_parking.setText(parking + "元");
//			}
//			if (!other.equals("")) {
//				tv_other.setText(other + "元");
//			}
//		}
//		tv_all.setText(all + "元");
//		serviceMile.setText(mile + "公里");
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		String curDate = sDateFormat.format(new Date(System.currentTimeMillis()));
		date.setText(curDate);
		time.setText(noteInfo.getServiceBegin()+"-"+noteInfo.getServiceEnd());
		type.setText(taskInfo.ServiceTypeName());
		int totalMile = Integer.parseInt(noteInfo.getRouteEnd())-Integer.parseInt(noteInfo.getRouteBegin());
		serviceMile.setText(totalMile+"公里");
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		int hours = 0;
		try {
			Date d1 = df.parse(noteInfo.getServiceBegin());
			Date d2 = df.parse(noteInfo.getServiceEnd());
			long diff = d1.getTime() - d2.getTime();
			long hour = diff/(1000* 60 * 60);
			hours = Integer.parseInt(Long.toString(hour));
			serviceTime.setText(Long.toString(hour)+"小时");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int serviceMile = (int) noteInfo.getServiceKMs();
		int serviceHour = (int) noteInfo.getServiceTime();
		if(serviceMile<totalMile){
			extraMile.setText((totalMile - serviceMile)+"公里" );
		}
		if(hours > serviceHour){
			extraTime.setText((hours - serviceHour)+"小时");
		}
		tv_base.setText(noteInfo.getFeeChoice()+"元");
		tv_road.setText(noteInfo.getFeeBridge()+"元");
//		tv_parking.setText(text);
		tv_meals.setText(noteInfo.getFeeLunch()+"元");
		tv_hotel.setText(noteInfo.getFeeHotel()+"元");
		tv_other.setText(noteInfo.getFeeOther()+"元");
//		extraMile
		tv_all.setText(noteInfo.getFeeTotal()+"元");
		tv_dateLast.setText(curDate);
	}

	private void findView() {
		print_confirm = (TextView) findViewById(R.id.print_confirm);
		tv_base = (TextView) findViewById(R.id.tv_base);
		tv_road = (TextView) findViewById(R.id.tv_road_print);
		tv_parking = (TextView) findViewById(R.id.tv_parking_print);
		tv_meals = (TextView) findViewById(R.id.tv_meals_print);
		tv_hotel = (TextView) findViewById(R.id.tv_hotel_print);
		tv_other = (TextView) findViewById(R.id.tv_other_print);
		tv_all = (TextView) findViewById(R.id.tv_all_print);
		serviceMile = (TextView) findViewById(R.id.print_serviceMile);
		serviceTime = (TextView) findViewById(R.id.print_serviceTime);
		date = (TextView) findViewById(R.id.print_date);
		time = (TextView) findViewById(R.id.print_time);
		type = (TextView) findViewById(R.id.print_type);
		name = (TextView) findViewById(R.id.print_name);
		location = (TextView) findViewById(R.id.print_location);
		destination = (TextView) findViewById(R.id.print_destination);
		extraMile = (TextView) findViewById(R.id.print_extraMile);
		extraTime = (TextView) findViewById(R.id.print_extraTime);
		tv_dateLast = (TextView) findViewById(R.id.print_date_last);
	}
}
