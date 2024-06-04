package com.test.helper;

public class MyMessage {
	
	private String content;
	
	
	private String type;


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public MyMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public MyMessage(String content, String type) {
		super();
		this.content = content;
		this.type = type;
	}


	@Override
	public String toString() {
		return "MyMessage [content=" + content + ", type=" + type + "]";
	}
	
	

}
