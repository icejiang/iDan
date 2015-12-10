package com.dazhong.idan;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public abstract class getInfoValue {
	/**
	 * �����û�״̬���ȴ��ӵ�
	 * */
	public static boolean ServiceStandby(String userid,String taskid){
		try {
			String sInfo=getDZService.getServiceConnect(userid, taskid,"0", "SetLockDispatch");
			System.out.println(sInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * �����û�״̬��������
	 * */
	public static boolean ServiceDoing(String userid,String taskid){
		try {
			String sInfo=getDZService.getServiceConnect(userid, taskid,"1", "SetLockDispatch");
			System.out.println(sInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * ���õ��ȵ��Ѷ����ϴ���������
	 * �ɹ� true��ʧ�� false
	 * */
	public static boolean setTaskRead(String userid,String taskid){
		try {
			String sInfo=getDZService.getServiceConnect(userid, taskid, "SetReadDispatch");
			System.out.println(sInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * ��ȡ������Ϣ�б�
	 * */
	public static List<TaskInfo> getTasks(String employeeid) throws Exception {
		List<TaskInfo> listTasks = null;
		List<String> listInfo = null;
		TaskInfo taskinfo = null;
		String sInfo = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String sf = sdf.format(now);
		System.out.println(sf);
		try {
			sInfo = getDZService.getServiceConnect(employeeid, sf, "1",
					"GetDispatchInfo");
			System.out.println(sInfo);
			if (sInfo == null)
				return null;
			listInfo = getDZService.getInfoValue(sInfo);
			if (listInfo == null)
				return null;
			listTasks = new ArrayList<TaskInfo>();
			System.out.println("info count:" + listInfo.size());
			for (String sinf : listInfo) {
				taskinfo = new TaskInfo();
				taskinfo.setBookman(getDZService.getInfoValue(sinf, "Order"));
				taskinfo.setBooktel(getDZService.getInfoValue(sinf,
						"OrderPhone"));
				taskinfo.setCarid(getDZService.getInfoValue(sinf, "CarID"));
				taskinfo.setCarnumber(getDZService.getInfoValue(sinf,
						"LicenseTag"));
				taskinfo.setCartype(getDZService
						.getInfoValue(sinf, "CarSRName"));
				taskinfo.setCustomer(getDZService.getInfoValue(sinf, "CarUser"));
				taskinfo.setCustomertel(getDZService.getInfoValue(sinf,
						"CarUserPhone"));
				taskinfo.setDriverid(getDZService
						.getInfoValue(sinf, "DriverID"));
				taskinfo.setDrivername(getDZService.getInfoValue(sinf,
						"DriverName"));
				taskinfo.setDriverphone(getDZService.getInfoValue(sinf,
						"DriverPhone"));
				taskinfo.setFrightin(getDZService.getInfoValue(sinf,
						"FlightTime"));
				taskinfo.setFrightout(getDZService.getInfoValue(sinf,
						"FlightTime"));
				taskinfo.setFrightnum(getDZService.getInfoValue(sinf,
						"FlightNo"));
				taskinfo.setInvoicetype(getDZService
						.getInfoValue(sinf, "InvoiceTypeCode"));
				taskinfo.setInvoicetypename(getDZService.getInfoValue(sinf,
						"InvoiceTypeName"));
				taskinfo.setLeaveaddress(getDZService.getInfoValue(sinf,
						"Destination"));
				taskinfo.setMaxload(Integer.parseInt(getDZService.getInfoValue(
						sinf, "SeatNumber")));
				taskinfo.setOnboardtime(getDZService.getInfoValue(sinf,
						"UpLoadTime"));
				taskinfo.setPickupaddress(getDZService.getInfoValue(sinf,
						"UpLoadAddress"));
				taskinfo.setPlanner(getDZService.getInfoValue(sinf,
						"DispatchManName"));
				taskinfo.setPlannerremark(getDZService.getInfoValue(sinf,
						"DispatchManRemarks"));
				taskinfo.setPlannertel(getDZService.getInfoValue(sinf,
						"DispatchManPhone"));
				taskinfo.setSalehotelfee(Double.parseDouble(getDZService
						.getInfoValue(sinf, "OutFee")));
				taskinfo.setSalekms(Double.parseDouble(getDZService
						.getInfoValue(sinf, "AvailableMile")));
				taskinfo.setSaleprice(Double.parseDouble(getDZService
						.getInfoValue(sinf, "ActualRent")));
				taskinfo.setSalepriceperkm(Double.parseDouble(getDZService
						.getInfoValue(sinf, "ExceedMileFee")));
				taskinfo.setSalepriceperhour(Double.parseDouble(getDZService
						.getInfoValue(sinf, "ExceedTimeFee")));
				taskinfo.setSalesman(getDZService.getInfoValue(sinf,
						"EmployeeName"));
				taskinfo.setSalestel(getDZService.getInfoValue(sinf,
						"EmployeePhone"));
				taskinfo.setSalesremark(getDZService.getInfoValue(sinf,
						"BargainRemarks"));
				taskinfo.setSaletime(Double.parseDouble(getDZService
						.getInfoValue(sinf, "AvailableTime")));
				taskinfo.setServicebegin(getDZService.getInfoValue(sinf,
						"StartDate"));
				taskinfo.setServiceend(getDZService.getInfoValue(sinf,
						"EndDate"));
				taskinfo.setServicetype(Integer.parseInt(getDZService
						.getInfoValue(sinf, "JobTypeCode")));
				taskinfo.setServicetypename(getDZService.getInfoValue(sinf,
						"JobTypeName"));
				taskinfo.setTaskcode(getDZService.getInfoValue(sinf,
						"DispatchNo"));
				taskinfo.setTaskcontract(getDZService.getInfoValue(sinf,
						"BargainNo"));
				taskinfo.setTaskid(getDZService
						.getInfoValue(sinf, "DispatchID"));
				taskinfo.setReadmark(Integer.parseInt(getDZService
						.getInfoValue(sinf, "ReadMark")));

				System.out.println(taskinfo.toString());
				listTasks.add(taskinfo);
			}
			System.out.println("task count:" + listTasks.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listTasks;
	}

	/**
	 * ��ȡ�û���Ϣ �������û���ϵͳ���
	 **/
	public static PersonInfo getPersonInfo(String employeeid) throws Exception {
		String sInfo = getDZService.getServiceConnect(employeeid,
				"GetEmployeeInfo");
		if (sInfo == null)
			return null;
		PersonInfo personinfo = new PersonInfo();
		personinfo.setName(getDZService.getInfoValue(sInfo, "DriverName"));
		personinfo.setGender(getDZService.getInfoValue(sInfo, "Gender"));
		personinfo.setWorkNum(getDZService.getInfoValue(sInfo, "WorkCardNo"));
		personinfo.setPosition(getDZService.getInfoValue(sInfo, "Post"));
		personinfo.setTeam(getDZService.getInfoValue(sInfo, "Team"));
		personinfo.setCompany(getDZService.getInfoValue(sInfo, "Company"));
		return personinfo;
	}

	/**
	 * �û���½ �������˺ţ����� ���أ�True �ɹ���false ʧ��
	 **/
	public static boolean getLogin(String sAccount, String sPassword) {
		if (sAccount.length() < 3 || sPassword.length() < 3 || sAccount == null
				|| sPassword == null)
			return false;
		try {
			String sInfo = getDZService.getServiceConnect(sAccount, sPassword,
					"Signin");
			if (sInfo == null)
				return false;
			int i = Integer
					.parseInt(getDZService.getInfoValue(sInfo, "Result"));
			if (i < 0) {
				return false;
			}
			MainActivity.USERNAME = getDZService.getInfoValue(sInfo,
					"DriverName");
			MainActivity.EMPLOYEEID = getDZService.getInfoValue(sInfo,
					"EmployeeID");
			MainActivity.WORKNUMBER = getDZService.getInfoValue(sInfo,
					"WorkCardNo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
