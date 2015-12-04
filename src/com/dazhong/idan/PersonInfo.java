package com.dazhong.idan;

import java.io.Serializable;

//�û���Ϣ
public class PersonInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/*�û�����*/
private String PersonID;//�û�����
private String WorkNum;//����
private String PhoneNum;//�ֻ�����
private String Name;//����
private String Gender;//�Ա�
private String Position;//��λ
private String Team;//����
private String Company;//��˾

public PersonInfo() {
	super();
}
public PersonInfo(String personID, String workNum, String phoneNum,
		String name, String gender, String position, String team, String company) {
	super();
	PersonID = personID;
	WorkNum = workNum;
	PhoneNum = phoneNum;
	Name = name;
	Gender = gender;
	Position = position;
	Team = team;
	Company = company;
}
public PersonInfo(String personID,String workNum,String name){
	PersonID=personID;
	WorkNum=workNum;
	Name=name;
}
@Override
public String toString() {
	return  PersonID + "," + WorkNum
			+ "," + PhoneNum + "," + Name + ","
			+ Gender + "," + Position + "," + Team
			+ "," + Company + ";";
}
public String getPersonID() {
	return PersonID;
}
public void setPersonID(String personID) {
	PersonID = personID;
}
public String getWorkNum() {
	return WorkNum;
}
public void setWorkNum(String workNum) {
	WorkNum = workNum;
}
public String getPhoneNum() {
	return PhoneNum;
}
public void setPhoneNum(String phoneNum) {
	PhoneNum = phoneNum;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public String getGender() {
	return Gender;
}
public void setGender(String gender) {
	Gender = gender;
}
public String getPosition() {
	return Position;
}
public void setPosition(String position) {
	Position = position;
}
public String getTeam() {
	return Team;
}
public void setTeam(String team) {
	Team = team;
}
public String getCompany() {
	return Company;
}
public void setCompany(String company) {
	Company = company;
}

}
