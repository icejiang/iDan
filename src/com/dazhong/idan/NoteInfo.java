package com.dazhong.idan;

import java.io.Serializable;

public class NoteInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -113091942551031103L;
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	/**
	 * ��������
	 */
	private String CarID;
	/**
	 * ���ƺ���
	 */
	private String CarNumber;
	/**
	 * ˾���˺�
	 */
	private String DriverID;
	/**
	 * ˾������
	 */
	private String DriverName;
	/**
	 * ·�ŷ�
	 */
	private double FeeBridge;
	/**
	 * ס�޷�
	 */
	private double FeeHotel;
	/**
	 * �ͷ�
	 */
	private double FeeLunch;
	/**
	 * ��������
	 */
	private double FeeOther;
	/**
	 * ����̷�
	 */
	private double FeeOverKMs;
	/**
	 * ��ʱ���
	 */
	private double FeeOverTime;
	/**
	 * ���۱���
	 */
	private double FeePrice;
	/**
	 * �ܽ��
	 */
	private double FeeTotal;
	/**
	 * �³���ַ
	 */
	private String LeaveAddress;
	/**
	 * ·�����
	 */
	private String NoteID;
	/**
	 * �ϳ���ַ
	 */
	private String OnBoardAddress;
	/**
	 * ���ȵ���
	 */
	private String PlanID;
	/**
	 * ��ʼ·��
	 */
	private String RouteBegin;
	/**
	 * ����·��
	 */
	private String RouteEnd;
	/**
	 * ����ʼʱ��
	 */
	private String ServiceBegin;
	/**
	 * �������ʱ��
	 */
	private String ServiceEnd;
	/**
	 * �������
	 */
	private double ServiceKMs;
	/**
	 * ����ʱ��
	 */
	private double ServiceTime;

	public String getCarID() {
		return CarID;
	}

	public void setCarID(String carID) {
		CarID = carID;
	}

	public String getCarNumber() {
		return CarNumber;
	}

	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}

	public String getDriverID() {
		return DriverID;
	}

	public void setDriverID(String driverID) {
		DriverID = driverID;
	}

	public String getDriverName() {
		return DriverName;
	}

	public void setDriverName(String driverName) {
		DriverName = driverName;
	}

	public double getFeeBridge() {
		return FeeBridge;
	}

	public void setFeeBridge(double feeBridge) {
		FeeBridge = feeBridge;
	}

	public double getFeeHotel() {
		return FeeHotel;
	}

	public void setFeeHotel(double feeHotel) {
		FeeHotel = feeHotel;
	}

	public double getFeeLunch() {
		return FeeLunch;
	}

	public void setFeeLunch(double feeLunch) {
		FeeLunch = feeLunch;
	}

	public double getFeeOther() {
		return FeeOther;
	}

	public void setFeeOther(double feeOther) {
		FeeOther = feeOther;
	}

	public double getFeeOverKMs() {
		return FeeOverKMs;
	}

	public void setFeeOverKMs(double feeOverKMs) {
		FeeOverKMs = feeOverKMs;
	}

	public double getFeeOverTime() {
		return FeeOverTime;
	}

	public void setFeeOverTime(double feeOverTime) {
		FeeOverTime = feeOverTime;
	}

	public double getFeePrice() {
		return FeePrice;
	}

	public void setFeePrice(double feePrice) {
		FeePrice = feePrice;
	}

	public double getFeeTotal() {
		return FeeTotal;
	}

	public void setFeeTotal(double feeTotal) {
		FeeTotal = feeTotal;
	}

	public String getLeaveAddress() {
		return LeaveAddress;
	}

	public void setLeaveAddress(String leaveAddress) {
		LeaveAddress = leaveAddress;
	}

	public String getNoteID() {
		return NoteID;
	}

	public void setNoteID(String noteID) {
		NoteID = noteID;
	}

	public String getOnBoardAddress() {
		return OnBoardAddress;
	}

	public void setOnBoardAddress(String onBoardAddress) {
		OnBoardAddress = onBoardAddress;
	}

	public String getPlanID() {
		return PlanID;
	}

	public void setPlanID(String planID) {
		PlanID = planID;
	}

	public String getRouteBegin() {
		return RouteBegin;
	}

	public void setRouteBegin(String routeBegin) {
		RouteBegin = routeBegin;
	}

	public String getRouteEnd() {
		return RouteEnd;
	}

	public void setRouteEnd(String routeEnd) {
		RouteEnd = routeEnd;
	}

	public String getServiceBegin() {
		return ServiceBegin;
	}

	public void setServiceBegin(String serviceBegin) {
		ServiceBegin = serviceBegin;
	}

	public String getServiceEnd() {
		return ServiceEnd;
	}

	public void setServiceEnd(String serviceEnd) {
		ServiceEnd = serviceEnd;
	}

	public double getServiceKMs() {
		return ServiceKMs;
	}

	public void setServiceKMs(double serviceKMs) {
		ServiceKMs = serviceKMs;
	}

	public double getServiceTime() {
		return ServiceTime;
	}

	public void setServiceTime(double serviceTime) {
		ServiceTime = serviceTime;
	}

	public NoteInfo() {

	}

}
