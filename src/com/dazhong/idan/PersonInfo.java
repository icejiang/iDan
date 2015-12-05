package com.dazhong.idan;

import java.io.Serializable;

//�û���Ϣ
public class PersonInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**�û�����*/
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
/**��ȡ�û�����*/
public String getPersonID() {
	return PersonID;
}
/**�����û�����*/
public void setPersonID(String personID) {
	PersonID = personID;
}
/**��ȡ�û�����*/
public String getWorkNum() {
	return WorkNum;
}
/**�����û�����*/
public void setWorkNum(String workNum) {
	WorkNum = workNum;
}
/**��ȡ�û��ֻ�����*/
public String getPhoneNum() {
	return PhoneNum;
}
/**�����û��ֻ�����*/
public void setPhoneNum(String phoneNum) {
	PhoneNum = phoneNum;
}
/**��ȡ�û�����*/
public String getName() {
	return Name;
}
/**��ȡ�û�����*/
public void setName(String name) {
	Name = name;
}
/**��ȡ�û��Ա�*/
public String getGender() {
	return Gender;
}
/**��ȡ�û��Ա�*/
public void setGender(String gender) {
	Gender = gender;
}
/**��ȡ�û���λ*/
public String getPosition() {
	return Position;
}
/**�����û���λ*/
public void setPosition(String position) {
	Position = position;
}
/**��ȡ�û������Ŷ�*/
public String getTeam() {
	return Team;
}
/**�����û����Ŷ�*/
public void setTeam(String team) {
	Team = team;
}
/**��ȡ�û����ڵĹ�˾*/
public String getCompany() {
	return Company;
}
/**�����û��Ĺ�˾*/
public void setCompany(String company) {
	Company = company;
}

}
