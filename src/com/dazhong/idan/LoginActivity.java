package com.dazhong.idan;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private Button btn_login;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		textView = (TextView) findViewById(R.id.textView1);
		btn_login = (Button) findViewById(R.id.login);
		btn_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// show service add
	public void showTest(View v) {
		try {
//			if (getInfoValue.getLogin("15821151093", "1234abcd"))
//			// textView.setText(MainActivity.USERNAME);
//			{
//				PersonInfo personinfo = new PersonInfo(MainActivity.EMPLOYEEID,
//						MainActivity.WORKNUMBER, MainActivity.USERNAME);
//				textView.setText(personinfo.toString());
//			} else
//				textView.setText("no info");
			 PersonInfo personinfo= new PersonInfo();
			 personinfo=getInfoValue.getPersonInfo("168203");
			 Map<String,Object> map=new HashMap<String,Object>();
			 Object obj=personinfo;
			 map.put("person", obj);
			 //getDZService.OutputObject(map);
			 getStateInfo gs=new getStateInfo();
			 gs.OutputObject(map);
//			 gs.inputObject();
			 String sKp=personinfo.toString();
//			textView.setText(sKp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), R.string.error, 1).show();
		}
	}

}
