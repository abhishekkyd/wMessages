package org.wcontacts.commons;

public class Csv {

	private String chatName;
	private String phoneNumber;
	private String message;
	private String time;
	
	public Csv(String chatName, String phoneNumber, String message, String time) {
		super();
		this.chatName = chatName;
		this.phoneNumber = phoneNumber;
		this.message = message;
		this.time = time;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Csv [chatName=" + chatName + ", phoneNumber=" + phoneNumber
				+ ", message=" + message + ", time=" + time + "]";
	}
	
	

}
