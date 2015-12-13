package com.dazhong.idan;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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
	private TextView tv_road;
	private TextView tv_parking;
	private TextView tv_meals;
	private TextView tv_other;
	private TextView tv_mile;
	private TextView tv_all;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.printf);

		findView();
		setData();
		print_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mBTService.getState() == mBTService.STATE_CONNECTED) {
					String message = printFile();
					if (message == null)
						return;
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
				devices = mBTService.GetBondedDevice();
				if (devices.size() > 0) {
					for (BluetoothDevice device : devices) {
						if (device.getName().equals(
								MainActivity.stateInfo.getPrinterName()))
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
	 * ·����ӡ��ʽ
	 * */
	private String printFile() {
		String messages = null;
		String mes = "\r\n\r\n";
		NoteInfo note = MainActivity.stateInfo.getCurrentNote();
		if (note == null) {
			Toast.makeText(
					PrintActivity.this,
					PrintActivity.this.getResources().getString(R.string.error),
					2000).show();
			return messages;
		}
		messages = "----------------------------------------------------" + mes;
		messages = messages + "·�����룺" + note.getNoteID() + mes;
		messages = messages + "�������ڣ�" + note.getNoteDate() + mes;
		messages = messages + "Ӫ�˳�����" + note.getCarNumber() + mes;
		messages = messages + "�ó����ˣ�" + note.getDriverName() + mes;
		messages = messages + "�ϳ�ʱ�䣺" + note.getServiceBegin() + mes;
		messages = messages + "�³�ʱ�䣺" + note.getServiceEnd() + mes;
		messages = messages + "�ϳ���ַ��" + note.getOnBoardAddress() + mes;
		messages = messages + "�³���ַ��" + note.getLeaveAddress() + mes;
		messages = messages + ";���ص㣺" + note.getServiceRoute() + mes;
		messages = messages + "������̣�" + Double.toString(note.getServiceKMs())
				+ mes;
		messages = messages + "����ʱ����" + note.getServiceTime() + mes;
		if (note.getOverHours() > 0)
			messages = messages + "��ʱ����" + note.getOverHours() + mes;
		if (note.getOverKMs() > 0)
			messages = messages + "������̣�" + note.getOverKMs() + mes;
		messages = messages + "������ã�" + note.getFeeTotal() + mes;
		messages = messages + "�ͻ�ǩ��" + mes + mes + mes;
		messages = messages + "______________________________________________"
				+ mes + mes + mes;
		return messages;
	}

	private void setData() {
		Bundle bundle = getIntent().getBundleExtra("MYKEY");
		int all = getIntent().getIntExtra("ALL", 0);
		int mile = getIntent().getIntExtra("MILE", 0);
		if (bundle != null) {
			String road = bundle.getString(AddPay.key_road);
			String meals = bundle.getString(AddPay.key_meals);
			String parking = bundle.getString(AddPay.key_parking);
			String other = bundle.getString(AddPay.key_other);
			if (!road.equals("")) {
				tv_road.setText(road + "Ԫ");
			}
			if (!meals.equals("")) {
				tv_meals.setText(meals + "Ԫ");
			}
			if (!parking.equals("")) {
				tv_parking.setText(parking + "Ԫ");
			}
			if (!other.equals("")) {
				tv_other.setText(other + "Ԫ");
			}
		}
		tv_all.setText(all + "Ԫ");
		tv_mile.setText(mile + "����");
	}

	private void findView() {
		print_confirm = (TextView) findViewById(R.id.print_confirm);
		tv_road = (TextView) findViewById(R.id.tv_road_print);
		tv_parking = (TextView) findViewById(R.id.tv_parking_print);
		tv_meals = (TextView) findViewById(R.id.tv_meals_print);
		tv_other = (TextView) findViewById(R.id.tv_other_print);
		tv_mile = (TextView) findViewById(R.id.tv_mile_print);
		tv_all = (TextView) findViewById(R.id.tv_all_print);
	}
}
