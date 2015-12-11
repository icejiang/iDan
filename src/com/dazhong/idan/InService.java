package com.dazhong.idan;

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
import android.widget.Toast;

public class InService extends Activity {
	
	private Button btn_end;
	private int input_end;
	private NoteInfo noteInfo;
	private int input_start;
	public static String INPUT_TOTAL_KEY = "INPUT_TOTAL";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.in_service);
		
		Intent intent = getIntent();
		noteInfo = (NoteInfo) intent.getSerializableExtra(OrderDetail.INPUT_KEY);
		input_start = Integer.parseInt(noteInfo.getRouteBegin());
		btn_end = (Button) findViewById(R.id.btn_end);
		
		
		btn_end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final EditText editText = new EditText(InService.this);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				new AlertDialog.Builder(InService.this).setTitle("����д����·��").
				setView(editText).setPositiveButton("��д��һ��", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						String input = editText.getText().toString();
						if(input.equals("")){
							Toast.makeText(getApplicationContext(), "·�벻��Ϊ��", Toast.LENGTH_SHORT).show();
						} else {
							input_end = Integer.parseInt(input);
							Log.i("jxb", "����·�� = "+input_end);
							if (input_end < input_start){
								Toast.makeText(getApplicationContext(), "����·��С����ʼ·�룬��ȷ������", Toast.LENGTH_SHORT).show();
							} else {
								Intent intent = new Intent();
								intent.putExtra(INPUT_TOTAL_KEY, input_end-input_start);
								intent.setClass(InService.this, OrderDetailEnd.class);
								startActivity(intent);
							}
						}
					}
				}).show();
			}
		});
	}
}
