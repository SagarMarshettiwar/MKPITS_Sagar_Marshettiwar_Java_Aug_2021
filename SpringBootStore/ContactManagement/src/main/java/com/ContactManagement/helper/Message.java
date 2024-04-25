package com.ContactManagement.helper;

public class Message {
	private String type;
	private String Content;
	public Message(String content, String type) {
		super();
		this.type = type;
		Content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
}
