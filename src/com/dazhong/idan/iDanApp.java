package com.dazhong.idan;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class iDanApp {
	private String SERVICEADRRESS = "http://192.168.75.200:8084/DriverService.asmx";
	private String USERNAME = "";// �û�����
	private String WORKNUMBER = "";// ����
	private String EMPLOYEEID = "";// ϵͳ����
	private StateInfo stateInfo = null;// ״̬����
	private List<TaskInfo> tasklist = null;
	private static iDanApp instance = null;

	// public static List<Activity> activities = new ArrayList<Activity>();
	//
	// public static void addActivity(Activity activity) {
	// activities.add(activity);
	// }
	//
	// public static void removeActivity(Activity activity) {
	// activities.remove(activity);
	// }
	//
	// public static void finishAll() {
	// for (Activity activity : activities) {
	// if (!activity.isFinishing()) {
	// activity.finish();
	// }
	// }
	//
	// }

	/**
	 * ��ȡService��ַ
	 * */
	public String getSERVICEADRRESS() {
		return SERVICEADRRESS;
	}

	/**
	 * ����Service��ַ
	 * */
	public void setSERVICEADRRESS(String sERVICEADRRESS) {
		SERVICEADRRESS = sERVICEADRRESS;
	}

	/**
	 * ��ȡ�û�����
	 * */
	public String getUSERNAME() {
		return USERNAME;
	}

	/**
	 * �����û�����
	 * */
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	/**
	 * ��ȡ�û�����
	 * */
	public String getWORKNUMBER() {
		return WORKNUMBER;
	}

	/**
	 * �����û�����
	 * */
	public void setWORKNUMBER(String wORKNUMBER) {
		WORKNUMBER = wORKNUMBER;
	}

	/**
	 * ��ȡ�û����
	 * */
	public String getEMPLOYEEID() {
		return EMPLOYEEID;
	}

	/**
	 * �����û����
	 * */
	public void setEMPLOYEEID(String eMPLOYEEID) {
		EMPLOYEEID = eMPLOYEEID;
	}

	/**
	 * ��ȡ��ǰ״̬
	 * */
	public StateInfo getStateInfo() {
		return stateInfo;
	}

	/**
	 * ���õ�ǰ״̬
	 * */
	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}

	/**
	 * ��ȡ�����б�
	 * */
	public List<TaskInfo> getTasklist() {
		return tasklist;
	}

	/**
	 * ���õ����б�
	 * */
	public void setTasklist(List<TaskInfo> tasklist) {
		this.tasklist = tasklist;
	}

//	@Override
//	public void onCreate() {
//		// TODO Auto-generated method stub
//		this.setSERVICEADRRESS("http://192.168.75.200:8084/DriverService.asmx");
//		super.onCreate();
//	}

	protected iDanApp() {
		super();
//		instance=this;
		// TODO Auto-generated constructor stub
	}

	public synchronized static iDanApp getInstance() {
		if (instance == null) {
			instance = new iDanApp();
		}
		return instance;
	}

}
