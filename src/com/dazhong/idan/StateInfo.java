package com.dazhong.idan;

import java.io.Serializable;

/**
 * @author davis
 * 
 */

public class StateInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7161724473272133612L;
	/*
	 * ��ǰ״̬ 0:��½ʧ�� 1:��¼�ɹ� 2:�ϳ�·�� 3:�³�·�� 11:׼������ 12:���뿪ʼ·�� 13:������ 14:פ�� 15:�������·��
	 * 16:������ 17:������� 18:��ӡԤ�� 51:��ʷ·�� 52:������ϸ 53:·����ϸ 54:������ϸ 55:������Ϣ 56:�޸�����
	 * 101:�˳�ϵͳ
	 */
	private int CurrentState;
	// ��ǰ�û�
	private PersonInfo CurrentPerson;
	// ��ǰ���ȵ�
	private TaskInfo CurrentTask;
	/* ��ǰ·�� * */
	private NoteInfo CurrentNote;
	/* ����ҳ�� */
	private int PageOfTask;
	/* ��ʷ·��ҳ�� */
	private int PageOfNoteHistory;
	/* ��������·�� */
	private String CurrentKMS;
	/* ����·������ */
	private int TimeOfTaskOneDay;
	/* �����ϳ�ʱ�� */
	private String TimeInCar;
	/* �����³�ʱ�� */
	private String TimeOffCar;
	/* ��¼���� */
	private String Today;
	// �����ϳ�·��
	private String BeginKMsOfToday;
	// �����³�·��
	private String EndKMsOfToday;
	// ��ǰ��¼��״̬��true ��¼״̬��false �ǳ�״̬
	private boolean CurrentLogin;

	/**��ȡ��ǰ��¼״̬
	 * */
	public boolean isCurrentLogin() {
		return CurrentLogin;
	}

	/**
	 * ���õ�ǰ��¼״̬
	 * */
	public void setCurrentLogin(boolean login) {
		CurrentLogin = login;
	}

	/**
	 * ��ȡ��ǰ״̬ �������£�
	 *  0:��½ʧ�� 
	 *  1:��¼�ɹ� 
	 *  2:�ϳ�·�� 
	 *  3:�³�·��
	 *  11:׼������
	 *  12:���뿪ʼ·�� 
	 *  13:������
	 *  14:פ��
	 *  15:�������·�� 
	 *  16:������ 
	 *  17:������� 
	 *  18:��ӡԤ�� 
	 *  51:��ʷ·�� 
	 *  52:������ϸ 
	 *  53:·����ϸ 
	 *  54:������ϸ 
	 *  55:������Ϣ
	 *  56:�޸����� 
	 *  101:�˳�ϵͳ
	 */
	public int getCurrentState() {
		return CurrentState;
	}

	/**
	 * ���õ�ǰ״̬ �������£�
	 *  0:��½ʧ�� 
	 *  1:��¼�ɹ� 
	 *  2:�ϳ�·�� 
	 *  3:�³�·�� 
	 *  11:׼������ 
	 *  12:���뿪ʼ·�� 
	 *  13:������ 
	 *  14:פ��
	 *  15:�������·�� 
	 *  16:������ 
	 *  17:������� 
	 *  18:��ӡԤ�� 
	 *  51:��ʷ·�� 
	 *  52:������ϸ 
	 *  53:·����ϸ 
	 *  54:������ϸ 
	 *  55:������Ϣ
	 *  56:�޸����� 
	 *  101:�˳�ϵͳ
	 */
	public void setCurrentState(int currentState) {
		CurrentState = currentState;
	}

	public StateInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StateInfo(int currentState, PersonInfo currentPerson,
			TaskInfo currentTask, NoteInfo currentNote, int pageOfTask,
			int pageOfNoteHistory, String currentKMS, int timeOfTaskOneDay,
			String timeInCar, String timeOffCar, String today,
			String beginKMsOfToday, String endKMsOfToday) {
		super();
		CurrentState = currentState;
		CurrentPerson = currentPerson;
		CurrentTask = currentTask;
		CurrentNote = currentNote;
		PageOfTask = pageOfTask;
		PageOfNoteHistory = pageOfNoteHistory;
		CurrentKMS = currentKMS;
		TimeOfTaskOneDay = timeOfTaskOneDay;
		TimeInCar = timeInCar;
		TimeOffCar = timeOffCar;
		Today = today;
		BeginKMsOfToday = beginKMsOfToday;
		EndKMsOfToday = endKMsOfToday;
	}

	@Override
	public String toString() {
		return "StateInfo [CurrentState=" + CurrentState + ", CurrentPerson="
				+ CurrentPerson + ", CurrentTask=" + CurrentTask
				+ ", CurrentNote=" + CurrentNote + ", PageOfTask=" + PageOfTask
				+ ", PageOfNoteHistory=" + PageOfNoteHistory + ", CurrentKMS="
				+ CurrentKMS + ", TimeOfTaskOneDay=" + TimeOfTaskOneDay
				+ ", TimeInCar=" + TimeInCar + ", TimeOffCar=" + TimeOffCar
				+ ", Today=" + Today + ", BeginKMsOfToday=" + BeginKMsOfToday
				+ ", EndKMsOfToday=" + EndKMsOfToday + "]";
	}

	/** ��ȡ��ǰ�û� */
	public PersonInfo getCurrentPerson() {
		return CurrentPerson;
	}

	/** ���õ�ǰ�û� */
	public void setCurrentPerson(PersonInfo currentPerson) {
		CurrentPerson = currentPerson;
	}

	/** ��ȡ��ǰ���ȵ� */
	public TaskInfo getCurrentTask() {
		return CurrentTask;
	}

	/** ���õ�ǰ���ȵ� */
	public void setCurrentTask(TaskInfo currentTask) {
		CurrentTask = currentTask;
	}

	/** ��ȡ��ǰ·�� */
	public NoteInfo getCurrentNote() {
		return CurrentNote;
	}

	/** ���õ�ǰ·�� */
	public void setCurrentNote(NoteInfo currentNote) {
		CurrentNote = currentNote;
	}

	/**
	 * ��ȡ ��������б�ĵ�ǰҳ�룡 ÿ���һ�ο���ʱ��Ϊ��һҳ ÿҳ����50����¼
	 * */
	public int getPageOfTask() {
		return PageOfTask;
	}

	/**
	 * ���� ��������б�ĵ�ǰҳ�룡 ÿ���һ�ο���ʱ��Ϊ��һҳ ÿҳ����50����¼
	 * */
	public void setPageOfTask(int pageOfTask) {
		PageOfTask = pageOfTask;
	}

	/**
	 * ��� �����ʷ·���б�ĵ�ǰҳ�룡 ÿ���һ�ο���ʱ��Ϊ��һҳ ÿҳ����50����¼
	 * */
	public int getPageOfNoteHistory() {
		return PageOfNoteHistory;
	}

	/**
	 * ���� �����ʷ·���б�ĵ�ǰҳ�룡 ÿ���һ�ο���ʱ��Ϊ��һҳ ÿҳ����50����¼
	 * */
	public void setPageOfNoteHistory(int pageOfNoteHistory) {
		PageOfNoteHistory = pageOfNoteHistory;
	}

	/** ��ȡ��������·���¼ */
	public String getCurrentKMS() {
		return CurrentKMS;
	}

	/** ������������·���¼ */
	public void setCurrentKMS(String currentKMS) {
		CurrentKMS = currentKMS;
	}

	/** ��ȡ����ִ��·���Ĵ���������ܳ���999�� */
	public int getTimeOfTaskOneDay() {
		return TimeOfTaskOneDay;
	}

	/** ���õ���ִ��·���Ĵ���������ܳ���999�� */
	public void setTimeOfTaskOneDay(int timeOfTaskOneDay) {
		TimeOfTaskOneDay = timeOfTaskOneDay;
	}

	/** ��ȡ�����һ���ϳ���ʱ�䣬��HH��MM��ʽ���� */
	public String getTimeInCar() {
		return TimeInCar;
	}

	/** ���õ����һ���ϳ���ʱ�䣬��HH��MM��ʽ���� */
	public void setTimeInCar(String timeInCar) {
		TimeInCar = timeInCar;
	}

	/** ��ȡ�������һ���³���ʱ�䣬��HH��MM��ʽ���� */
	public String getTimeOffCar() {
		return TimeOffCar;
	}

	/** ���õ������һ���³���ʱ�䣬��HH��MM��ʽ���� */
	public void setTimeOffCar(String timeOffCar) {
		TimeOffCar = timeOffCar;
	}

	/** ��ȡ�����������Ϣ����yyyy-mm-dd��ʽ���� */
	public String getToday() {
		return Today;
	}

	/** ���õ����������Ϣ����yyyy-mm-dd��ʽ���� */
	public void setToday(String today) {
		Today = today;
	}

	/** ��ȡ������ϳ�·�� */
	public String getBeginKMsOfToday() {
		return BeginKMsOfToday;
	}

	/** ���õ�����ϳ�·�� */
	public void setBeginKMsOfToday(String beginKMsOfToday) {
		BeginKMsOfToday = beginKMsOfToday;
	}

	/** ��ȡ������³�·�� */
	public String getEndKMsOfToday() {
		return EndKMsOfToday;
	}

	/** ���õ�����³�·�� */
	public void setEndKMsOfToday(String endKMsOfToday) {
		EndKMsOfToday = endKMsOfToday;
	}

}
