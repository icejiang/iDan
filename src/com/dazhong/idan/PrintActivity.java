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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PrintActivity extends Activity {
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	public Handler mhandler ;
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
	private TextView route_id;
	private TextView record;
	private ImageView iv_return;
	private ImageView iv_home;
	private boolean blueinit;
	private NoteInfo noteInfo;
	private TaskInfo taskInfo;
	private getStateInfo myGetStateInfo;
	private StateInfo myStateInfo;
	private int position;
	private TextView tv_alterPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.printf);
		ActivityControler.addActivity(this);
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			myStateInfo.setCurrentState(18);
			noteInfo = myStateInfo.getCurrentNote();
			taskInfo = myStateInfo.getCurrentTask();
			position = myStateInfo.getPosition();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

//		if (mBTService != null) {
//			mBTService.DisConnected();
//			mBTService = null;
//		}
		blueinit = true;
		findView();
		setData();

		iv_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBTService != null) {
					mBTService.DisConnected();
					mBTService = null;
				}
				PrintActivity.this.finish();
			}
		});
		iv_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBTService != null) {
					mBTService.DisConnected();
					mBTService = null;
				}
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);

			}
		});
		print_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (blueinit) {
					Log.i("jxb", "����״̬��"+mBTService.getState());
					if (mBTService.getState() == mBTService.STATE_CONNECTED) {
						String message = printFile();
						if (message == null) {
							Toast.makeText(
									PrintActivity.this,
									PrintActivity.this.getResources()
											.getString(R.string.str_printfail),
									2000).show();
							return;
						}
						mBTService.PrintCharacters(message);
						int k = getInfoValue.InsertNote(noteInfo.toUploadNote());
						TaskInfo myTask = iDanApp.getInstance().getTasklist().get(position);
						myTask.setDone(true);
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
					iv_return.setVisibility(View.GONE);
					iv_home.setVisibility(View.VISIBLE);
				}
				else
					Toast.makeText(getApplicationContext(), "��ӡ�����Ӵ�������������������", 2000)
					.show();
			}
		});

		// ******************************************************

		try {
			mhandler = new Handler() {
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					switch (msg.what) {
					case MESSAGE_STATE_CHANGE:// ��������״̬
						switch (msg.arg1) {
						case BlueToothService.STATE_CONNECTED:// �Ѿ�����
							break;
						case BlueToothService.STATE_CONNECTING:// ��������
							break;
						case BlueToothService.STATE_LISTEN:
						case BlueToothService.STATE_NONE:
							break;
						case BlueToothService.SUCCESS_CONNECT:
							Toast.makeText(
									PrintActivity.this,
									PrintActivity.this.getResources()
											.getString(R.string.str_succonnect),
									2000).show();
							break;
						case BlueToothService.FAILED_CONNECT:
							Toast.makeText(
									PrintActivity.this,
									PrintActivity.this.getResources()
											.getString(
													R.string.str_faileconnect),
									2000).show();
							break;
						case BlueToothService.LOSE_CONNECT:
							switch (msg.arg2) {
							case -1:
								Toast.makeText(
										PrintActivity.this,
										PrintActivity.this.getResources()
												.getString(R.string.str_lose),
										2000).show();
								break;
							case 0:
								break;
							}
						}
						break;
					case MESSAGE_READ:
						// sendFlag = false;//����������
						break;
					case MESSAGE_WRITE:// ������δ��
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
				if (!mBTService.IsOpen()) {// �ж������Ƿ��
					Toast.makeText(
							PrintActivity.this,
							PrintActivity.this.getResources().getString(
									R.string.str_closed), 2000).show();

				} else {
					String connAddress = null;
					// mBTService.ScanDevice();
					// Thread.sleep(200);
					devices = mBTService.GetBondedDevice();
					if (devices.size() > 0) {
						for (BluetoothDevice device : devices) {
							if (device.getName().equals(
									getStateInfo.getInstance(getApplicationContext()).getStateinfo()
											.getPrinterName()))
								connAddress = device.getAddress();
						}
						mBTService.DisConnected();
						Thread.sleep(200);
						mBTService.ConnectToDevice(connAddress);
					}

				}
			} else {
				Toast.makeText(
						PrintActivity.this,
						PrintActivity.this.getResources().getString(
								R.string.str_hasnodevice), 2000).show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (mBTService != null) {
				mBTService.DisConnected();
				mBTService = null;
			}
			blueinit=false;
			myStateInfo.setCurrentState(1);
			myGetStateInfo.setStateinfo(myStateInfo);
			Toast.makeText(getApplicationContext(), "��ӡ�����Ӵ�������������������", 2000)
					.show();
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
	 * ·����ӡ��ʽ
	 * */
	private String printFile() {

		String messages = null;
		try {
			String mes = "\r\n\r\n";
			NoteInfo note = myStateInfo.getCurrentNote();
			if (note == null) {
				Toast.makeText(
						PrintActivity.this,
						PrintActivity.this.getResources().getString(
								R.string.error), 2000).show();
				return messages;
			}
			double taxRate = 1 + note.getInvoiceTaxRate();
			int bridgeFeeType = noteInfo.getBridgefeetype();
			int outFeeType = noteInfo.getOutfeetype();
			messages = "";// "--------------------------" + mes;
			messages = messages + "·�����룺" + note.getNoteID() + mes;
			messages = messages + "�������ڣ�" + note.getNoteDate() + mes;
			messages = messages + "Ӫ�˳�����" + note.getCarNumber() + mes;
			messages = messages + "�ó����ˣ�" + note.getCustomerName() + mes;
			messages = messages + "�ϳ�ʱ�䣺" + note.getServiceBegin() + mes;
			messages = messages + "�³�ʱ�䣺" + note.getServiceEnd() + mes;
			messages = messages + "�ϳ���ַ��" + note.getOnBoardAddress() + mes;
			messages = messages + "�³���ַ��" + note.getLeaveAddress() + mes;
			messages = messages + ";���ص㣺" + note.getServiceRoute() + mes;
			messages = messages + "������̣�"
					+ Double.toString(note.getDoServiceKms()) + mes;
			messages = messages + "����ʱ����"
					+ Double.toString(note.getDoServiceTime()) + mes;
			if (note.getOverHours() > 0)
				messages = messages + "��ʱ����"
						+ Integer.toString(note.getOverHours()) + mes;
			if (note.getOverKMs() > 0)
				messages = messages + "������̣�"
						+ Integer.toString(note.getOverKMs()) + mes;
			if (note.getFeeOverTime() > 0)
				messages = messages + "��Сʱ�ѣ�"
						+ note.getFeeOverTime() + mes;
			if (note.getFeeOverKMs() > 0)
				messages = messages + "������ѣ�"
						+ note.getFeeOverKMs() + mes;
			if (note.getFeeBridge() > 0)
				if (bridgeFeeType == 0) {
					messages = messages + "·�ŷѣ�"
						+ reserve2(note.getFeeBridge()*taxRate) + mes;
				} else {
					messages = messages + "·�ŷѣ�"
							+ note.getFeeBridge() + mes;
				}
			if (note.getFeePark() > 0)
				if (bridgeFeeType == 0) {
					messages = messages + "ͣ���ѣ�"
						+ reserve2(note.getFeePark()*taxRate) + mes;
				} else {
					messages = messages + "ͣ���ѣ�"
							+ note.getFeePark() + mes;
				}
			if (note.getFeeHotel() > 0)
				if (outFeeType == 0) {
					messages = messages + "ס�޷ѣ�"
						+ reserve2(note.getFeeHotel()*taxRate) + mes;
				} else {
					messages = messages + "ס�޷ѣ�"
							+ note.getFeeHotel() + mes;
				}
			if (note.getFeeLunch() > 0)
				if (outFeeType == 0) {
					messages = messages + "�ͷѣ�"
						+ reserve2(note.getFeeLunch()*taxRate) + mes;
				} else {
					messages = messages + "�ͷѣ�"
							+ note.getFeeLunch() + mes;
				}
			if (note.getFeeOther() > 0)
				messages = messages + "�����ѣ�"
						+ reserve2(note.getFeeOther()*taxRate) + mes;
			if (note.getFeeBack() > 0)
				messages = messages + "�����ѣ�"
						+ -note.getFeeBack() + mes;
			if(noteInfo.getInvoiceType().equals("SD")){
				int int_all = (int)noteInfo.getFeeTotal();
				noteInfo.setFeeTotal(int_all);
				messages = messages + "�ܷ��ã�" + Double.toString(int_all)
						+ mes;
			} else {
				double all = reserve2(noteInfo.getFeeTotal());
				noteInfo.setFeeTotal(all);
				messages = messages + "�ܷ��ã�" + Double.toString(all)
						+ mes;
			}
			messages = messages + "�ͻ�ǩ��" + mes + mes + mes;
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
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sDateFormat
				.format(new Date(System.currentTimeMillis()));
		date.setText(curDate);
		time.setText(noteInfo.getServiceBegin() + "-"
				+ noteInfo.getServiceEnd());
		type.setText(taskInfo.ServiceTypeName());
		name.setText(noteInfo.getCustomerName());
		location.setText(noteInfo.getOnBoardAddress());
		destination.setText(noteInfo.getLeaveAddress());
		// int totalMile =
		// Integer.parseInt(noteInfo.getRouteEnd())-Integer.parseInt(noteInfo.getRouteBegin());
		int totalMile = noteInfo.getDoServiceKms();
		int hours = noteInfo.getDoServiceTime();
		serviceMile.setText(noteInfo.getDoServiceKms() + "����");
		serviceTime.setText(noteInfo.getDoServiceTime() + "Сʱ");
		int serviceMile = noteInfo.getServiceKMs();
		int serviceHour = noteInfo.getServiceTime();
		Log.i("jxb", "serviceMile = " + serviceMile);
		if (serviceMile < totalMile) {
			extraMile.setText((totalMile - serviceMile) + "����");
			double price_extraMile = noteInfo.getFeeOverKMs();
			tv_beyondMile.setText(price_extraMile + "Ԫ");
		}
		if (hours > serviceHour) {
			extraTime.setText((hours - serviceHour) + "Сʱ");
			double price_extraTime = noteInfo.getFeeOverTime();
			tv_beyondTime.setText(price_extraTime + "Ԫ");
		}
		double taxRate = 1 + noteInfo.getInvoiceTaxRate();
		tv_base.setText(noteInfo.getFeePrice() + "Ԫ");
		if(noteInfo.getBridgefeetype() == 0){
			tv_road.setText(reserve2(noteInfo.getFeeBridge()*taxRate) + "Ԫ");
			tv_parking.setText(reserve2(noteInfo.getFeePark()*taxRate) + "Ԫ");
		} else {
			tv_road.setText(noteInfo.getFeeBridge() + "Ԫ");
			tv_parking.setText(noteInfo.getFeePark() + "Ԫ");
		}
		if(noteInfo.getOutfeetype() == 0 ){
			tv_meals.setText(reserve2(noteInfo.getFeeLunch()*taxRate) + "Ԫ");
			tv_hotel.setText(reserve2(noteInfo.getFeeHotel()*taxRate) + "Ԫ");
		} else {
			tv_meals.setText(noteInfo.getFeeLunch() + "Ԫ");
			tv_hotel.setText(noteInfo.getFeeHotel() + "Ԫ");
		}
		tv_other.setText(reserve2(noteInfo.getFeeOther()*taxRate) + "Ԫ");
		tv_alterPrice.setText(-noteInfo.getFeeBack()+"Ԫ");
		tv_all.setText(noteInfo.getFeeTotal() + "Ԫ");
		if(noteInfo.getInvoiceType().equals("SD")){
			int int_all = (int)noteInfo.getFeeTotal();
			tv_all.setText(int_all+"Ԫ");
		} else {
			double all = reserve2(noteInfo.getFeeTotal());
			tv_all.setText(all+"Ԫ");
		}
		tv_dateLast.setText(curDate);
		route_id.setText(noteInfo.getNoteID());
		record.setText(noteInfo.getServiceRoute());
	}

	private void findView() {
		print_confirm = (TextView) findViewById(R.id.print_confirm);
		tv_base = (TextView) findViewById(R.id.tv_base_print);
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
		tv_beyondMile = (TextView) findViewById(R.id.tv_beyond_mile);
		tv_beyondTime = (TextView) findViewById(R.id.tv_beyond_time);
		route_id = (TextView) findViewById(R.id.print_routeID);
		iv_return = (ImageView) findViewById(R.id.return_print);
		iv_home = (ImageView) findViewById(R.id.home_print);
		record = (TextView) findViewById(R.id.print_record);
		tv_alterPrice = (TextView) findViewById(R.id.tv_alter_price);
	}
	
	private Double reserve2(Double x){
		Double result = (double)(Math.round(x*100)/100.0);
		return result;
	}
}
