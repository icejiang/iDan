package com.dazhong.idan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailEnd extends Activity implements OnClickListener {
	
	private TextView tv_addPay;
	private Button btn_confirmEnd;
	private TextView tv_road;
	private TextView tv_meals;
	private TextView tv_parking;
	private TextView tv_other;
	private TextView tv_base;
	private TextView tv_hotel;
	private TextView tv_all;
	private TextView tv_print;
	private TextView tv_addRecord;
	private TextView tv_record;
	private TextView date;
	private TextView startTime;
	private TextView endTime;
	private TextView type;
	private TextView mile;
	private TextView time;
	private TextView extraMile;
	private TextView extraTime;
	
	private Bundle mBundle;
	private int mileInt;
	private int all;
	private NoteInfo noteInfo;
	private TaskInfo taskInfo;
	private int position;
	private StateInfo myStateInfo;
	private getStateInfo myGetStateInfo;
	
	public static String NOTEKEY = "NOTEKEY";
	public static String TASKKEY = "TASKKEY";
	
	public final static int REQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.route_note);
		ActivityControler.addActivity(this);
		try {
			myGetStateInfo = getStateInfo.getInstance(getApplicationContext());
			myStateInfo = myGetStateInfo.getStateinfo();
			myStateInfo.setCurrentState(16);
			noteInfo = myStateInfo.getCurrentNote();
			taskInfo = myStateInfo.getCurrentTask();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		findView();
		setView();
		myStateInfo.setCurrentNote(noteInfo);
		myGetStateInfo.setStateinfo(myStateInfo);
		tv_addPay.setOnClickListener(this);
		tv_print.setOnClickListener(this);
		tv_addRecord.setOnClickListener(this);

		
	}
	
	private void findView(){
		tv_addPay = (TextView) findViewById(R.id.add_pay);
		tv_meals = (TextView) findViewById(R.id.tv_meals);
		tv_other = (TextView) findViewById(R.id.tv_other);
		tv_road = (TextView) findViewById(R.id.tv_road);
		tv_parking = (TextView) findViewById(R.id.tv_parking);
		tv_base = (TextView) findViewById(R.id.tv_base);
		tv_hotel = (TextView) findViewById(R.id.tv_hotel1);
		tv_all = (TextView) findViewById(R.id.tv_all);
		tv_print = (TextView) findViewById(R.id.tv_print);
		tv_addRecord = (TextView) findViewById(R.id.tv_add_record);
		tv_record = (TextView) findViewById(R.id.tv_record);
//		btn_confirmEnd = (Button) findViewById(R.id.confirm_end);
		date = (TextView) findViewById(R.id.route_date);
		startTime = (TextView) findViewById(R.id.route_startTime);
		endTime = (TextView) findViewById(R.id.route_endTime);
		type = (TextView) findViewById(R.id.route_type);
		mile = (TextView) findViewById(R.id.route_mile);
		time = (TextView) findViewById(R.id.route_time);
		extraMile = (TextView) findViewById(R.id.route_extraMile);
		extraTime = (TextView) findViewById(R.id.route_extraTime);
		
	}
	
	private void setView(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sDateFormat.format(new Date(System.currentTimeMillis()));
		date.setText(curDate);
		startTime.setText(noteInfo.getServiceBegin());
		endTime.setText(noteInfo.getServiceEnd());
		type.setText(taskInfo.ServiceTypeName());
		int totalMile = Integer.parseInt(noteInfo.getRouteEnd())-Integer.parseInt(noteInfo.getRouteBegin());
		mile.setText(totalMile+"����");
		noteInfo.setDoServiceKms(totalMile);
		DateFormat df = new SimpleDateFormat("HH:mm");
		int hours = 0;
		try {
			Date d1 = df.parse(noteInfo.getServiceBegin());
			Date d2 = df.parse(noteInfo.getServiceEnd());
			long diff = d1.getTime() - d2.getTime();
			long hour = diff/(1000* 60 * 60);
			hours = Integer.parseInt(Long.toString(hour));
			time.setText(Long.toString(hour)+"Сʱ");
			noteInfo.setDoServiceTime(hours);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int serviceMile = noteInfo.getServiceKMs();
		int serviceHour = noteInfo.getServiceTime();
		if(serviceMile<totalMile){
			extraMile.setText((totalMile - serviceMile)+"����" );
			double price_extraMile = (totalMile - serviceMile)*(taskInfo.SalePricePerKM());
//			noteInfo.set
		}
		if(hours > serviceHour){
			extraTime.setText((hours - serviceHour)+"Сʱ");
			double price_extraTime = (hours - serviceHour)*(taskInfo.SalePricePerHour());
		}
		tv_base.setText(noteInfo.getFeePrice()+"Ԫ");
		tv_all.setText(noteInfo.getFeePrice()+"Ԫ");
	}
	
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.add_pay:
			myStateInfo.setCurrentState(17);
			myGetStateInfo.setStateinfo(myStateInfo);
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), AddPay.class);
			startActivityForResult(intent1, REQUEST_CODE);
			break;
		case R.id.tv_print:
			Intent intent3 = new Intent();
//			intent3.putExtra(NOTEKEY, noteInfo);
//			intent3.putExtra(TASKKEY, position);
			intent3.setClass(getApplicationContext(), PrintActivity.class);
			startActivity(intent3);
			break;
		case R.id.tv_add_record:
			final EditText editText = new EditText(OrderDetailEnd.this);
//			editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			new AlertDialog.Builder(OrderDetailEnd.this)
					.setTitle("����дӪ�˼�¼")
					.setView(editText)
					.setPositiveButton(
							"ȷ��",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									String input = editText.getText()
											.toString();
									tv_record.setText(input);
									noteInfo.setServiceRoute(input);
									myStateInfo.setCurrentNote(noteInfo);
									myGetStateInfo.setStateinfo(myStateInfo);
								}

							}).show();
			break;
		default:
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE) {
			if (requestCode == AddPay.RESULT_CODE) {
				if (null!=data) {
					Bundle bundle = data.getExtras();
					this.mBundle = bundle;
					String road = bundle.getString(AddPay.key_road);
					String meals = bundle.getString(AddPay.key_meals);
					String parking = bundle.getString(AddPay.key_parking);
					String other = bundle.getString(AddPay.key_other);
					String hotel = bundle.getString(AddPay.key_hotel);
					int all = 0;
					if (!road.equals("")) {
						all = Integer.parseInt(road);
						tv_road.setText(road + "Ԫ");
						noteInfo.setFeeBridge(Double.valueOf(road));
					}
					if (!meals.equals("")) {
						all += Integer.parseInt(meals);
						tv_meals.setText(meals + "Ԫ");
						noteInfo.setFeeLunch(Double.valueOf(meals));
					}
					if (!parking.equals("")) {
						all += Integer.parseInt(parking);
						tv_parking.setText(parking + "Ԫ");
						noteInfo.setFeePark(Double.valueOf(parking));
					}
					if (!other.equals("")) {
						all += Integer.parseInt(other);
						tv_other.setText(other + "Ԫ");
						noteInfo.setFeeOther(Double.valueOf(other));
					}
					if (!hotel.equals("")) {
						all += Integer.parseInt(hotel);
						tv_hotel.setText(hotel + "Ԫ");
						noteInfo.setFeeHotel(Double.valueOf(hotel));
					}
					all = (int) (all+noteInfo.getFeePrice());
					tv_all.setText(all + "Ԫ");
					noteInfo.setFeeTotal(all);
					myStateInfo.setCurrentNote(noteInfo);
					myGetStateInfo.setStateinfo(myStateInfo);
					this.all = all;
				}
			}
		}
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myStateInfo.setCurrentState(16);
		myGetStateInfo.setStateinfo(myStateInfo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
