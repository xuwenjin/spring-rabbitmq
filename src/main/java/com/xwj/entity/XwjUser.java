package com.xwj.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *  µÃÂ¿‡
 * 
 * @author xuwenjin
 */
public class XwjUser implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String message;
	
	private Date sendTime;

	public XwjUser(int id, String message, Date sendTime) {
		super();
		this.id = id;
		this.message = message;
		this.sendTime = sendTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Override
	public String toString() {
		return "XwjUser [id=" + id + ", message=" + message + ", sendTime=" + sendTime + "]";
	}
	
}
