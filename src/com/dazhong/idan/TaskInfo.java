package com.dazhong.idan;

public class TaskInfo {
	private String bookman;
	private String booktel;
	private String carid;
	private String carnumber;
	private String cartype;
	private String customer;
	private String customertel;
	private String driverid;
	private String drivername;
	private String driverphone;
	private String frightin;
	private String frightnum;
	private String frightout;
	private int invoicetype;
	private String invoicetypename;
	private String leaveaddress;
	private int maxload;
	private String onboardtime;
	private String pickupaddress;
	private String planner;
	private String plannerremark;
	private String plannertel;
	private double salehotelfee;
	private double salekms;
	private double salelunchfee;
	private double saleotherfee;
	private double saleprice;
	private double salepriceperkm;
	private double salepriceperhour;
	private String salesman;
	private String salestel;
	private String salesremark;
	private double saletime;
	private String servicebegin;
	private String serviceend;
	private int servicetype;
	private String servicetypename;
	private String taskcode;
	private String taskid;
	private String taskcontract;
	
	/**
	 * ������
	 */
	public String Bookman()
	{
		return bookman;
	}
	/**
	 * �����˵绰
	 */
	public String BookTel()
	{
		return booktel;
	}
	/**
	 * ��������
	 */
	public String CarID()
	{
		return carid;
	}
	/**
	 * ���ƺ���
	 */
	public String CarNumber()
	{
		return carnumber;
	}
	/**
	 * ����
	 */
	public String CarType()
	{
	return cartype;
	}
	/**
	 * ��������
	 */
	public String Customer()
	{
		return customer;
	}
	/**
	 * ���˵绰
	 */
	public String CustomerTel()
	{
		return customertel;
	}
	/**
	 * ˾������
	 */
	public String DriverID()
	{
		return driverid;
	}
	/**
	 * ˾������
	 */
	public String DriverName()
	{
		return drivername;
	}
	/**
	 * ˾���ֻ�����
	 */
	public String DriverPhone()
	{
		return driverphone;
	}
	// �������ʱ��
	public String FrightIn()
	{
		return frightin;
	}
	/**
	 * �����
	 */
	public String FrightNum()
	{
		return frightnum;
	}
	//�������ʱ��
	public String FrightOut()
	{
		return frightout;
	}
	/**
	 * ��Ʊ����
	 */
	public int InvoiceType()
	{
		return invoicetype;
	}
	/**
	 * ��Ʊ��������
	 */
	public String InvoiceTypeName()
	{
		return invoicetypename;
	}
	/**
	 * �³���ַ
	 */
	public String LeaveAddress()
	{
		return leaveaddress;
	}
	/**
	 * ����ؿ���
	 */
	public int MaxLoad()
	{
		return maxload;
	}
	/**
	 * �ϳ�ʱ��
	 */
	public String OnboardTime()
	{
		return onboardtime;
	}
	/**
	 * �ϳ���ַ
	 */
	public String PickupAddress()
	{
		return pickupaddress;
	}
	/**
	 * ������
	 */
	public String Planner()
	{
		return planner;
	}
	/**
	 * ����Ա��ע
	 */
	public String PlannerRemark()
	{
	return plannerremark;
	}
	/**
	 * ���ȵ绰
	 */
	public String PlannerTel()
	{
		return plannertel;
	}
	/**
	 * ס�޷�
	 */
	public double SaleHotelFee()
	{
		return salehotelfee;
	}
	/**
	 * �����ķ������
	 */
	public double SaleKMs()
	{
		return salekms;
	}
	/**
	 * �ͷ�
	 */
	public double SaleLunchFee()
	{
		return salelunchfee;
	}
	/**
	 * ��������
	 */
	public double SaleOtherFee()
	{
		return saleotherfee;
	}
	/**
	 * ����
	 */
	public double SalePrice()
	{
		return saleprice;
	}
	/**
	 * ������ĵ���
	 */
	public double SalePricePerKM()
	{
		return salepriceperkm;
	}
	/**
	 * ��Сʱ�ĵ���
	 */
	public double SalePricePerHour()
	{
		return salepriceperhour;
	}
	/**
	 * ����
	 */
	public String Salesman()
	{
		return salesman;
	}
	/**
	 * ���۵绰
	 */
	public String SalesmanTel()
	{
		return salestel;
	}
	/**
	 * ҵ��Ա��ע
	 */
	public String SalesRemark()
	{
		return salesremark;
	}
	/**
	 * �����ķ���ʱ��
	 */
	public double SaleTime()
	{
		return saletime;
	}
	/**
	 * ����ʼ����
	 */
	public String ServiceBegin()
	{
		return servicebegin;
	}
	/**
	 * �����������
	 */
	public String ServiceEnd()
	{
		return serviceend;
	}
	/**
	 * ҵ������
	 */
	public int ServiceType()
	{
		return servicetype;
	}
	/**
	 * ������������
	 */
	public String ServiceTypeName()
	{
		return servicetypename;
	}
	/**
	 * ���ȵ����
	 */
	public String TaskCode()
	{
		return taskcode;
	}
	/**
	 * �����Ӧ�ĺ�ͬ���
	 */
	public String TaskContract()
	{
		return taskcontract;
	}
	
	/**
	 * ���ȵ����
	 */
	public String TaskID()
	{
		return taskid;
	}
	
	public void setBookman(String bookman) {
		this.bookman = bookman;
	}
	public void setBooktel(String booktel) {
		this.booktel = booktel;
	}
	public void setCarid(String carid) {
		this.carid = carid;
	}
	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}
	public void setCartype(String cartype) {
		this.cartype = cartype;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public void setCustomertel(String customertel) {
		this.customertel = customertel;
	}
	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}
	public void setDrivername(String drivername) {
		this.drivername = drivername;
	}
	public void setDriverphone(String driverphone) {
		this.driverphone = driverphone;
	}
	public void setFrightin(String frightin) {
		this.frightin = frightin;
	}
	public void setFrightnum(String frightnum) {
		this.frightnum = frightnum;
	}
	public void setFrightout(String frightout) {
		this.frightout = frightout;
	}
	public void setInvoicetype(int invoicetype) {
		this.invoicetype = invoicetype;
	}
	public void setInvoicetypename(String invoicetypename) {
		this.invoicetypename = invoicetypename;
	}
	public void setLeaveaddress(String leaveaddress) {
		this.leaveaddress = leaveaddress;
	}
	public void setMaxload(int maxload) {
		this.maxload = maxload;
	}
	public void setOnboardtime(String onboardtime) {
		this.onboardtime = onboardtime;
	}
	public void setPickupaddress(String pickupaddress) {
		this.pickupaddress = pickupaddress;
	}
	public void setPlanner(String planner) {
		this.planner = planner;
	}
	public void setPlannerremark(String plannerremark) {
		this.plannerremark = plannerremark;
	}
	public void setPlannertel(String plannertel) {
		this.plannertel = plannertel;
	}
	public void setSalehotelfee(double salehotelfee) {
		this.salehotelfee = salehotelfee;
	}
	public void setSalekms(double salekms) {
		this.salekms = salekms;
	}
	public void setSalelunchfee(double salelunchfee) {
		this.salelunchfee = salelunchfee;
	}
	public void setSaleotherfee(double saleotherfee) {
		this.saleotherfee = saleotherfee;
	}
	public void setSaleprice(double saleprice) {
		this.saleprice = saleprice;
	}
	public void setSalepriceperkm(double salepriceperkm) {
		this.salepriceperkm = salepriceperkm;
	}
	public void setSalepriceperhour(double salepriceperhour) {
		this.salepriceperhour = salepriceperhour;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public void setSalestel(String salestel) {
		this.salestel = salestel;
	}
	public void setSalesremark(String salesremark) {
		this.salesremark = salesremark;
	}
	public void setSaletime(double saletime) {
		this.saletime = saletime;
	}
	public void setServicebegin(String servicebegin) {
		this.servicebegin = servicebegin;
	}
	public void setServiceend(String serviceend) {
		this.serviceend = serviceend;
	}
	public void setServicetype(int servicetype) {
		this.servicetype = servicetype;
	}
	public void setServicetypename(String servicetypename) {
		this.servicetypename = servicetypename;
	}
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public void setTaskcontract(String taskcontract) {
		this.taskcontract = taskcontract;
	}


	public TaskInfo(){

	}

}
