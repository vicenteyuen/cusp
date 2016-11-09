package org.vsg.cusp.event;

import java.io.Serializable;

public class ReqMessageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * header attrbute 
	 */
	private byte apiCodeId;
	
	private short version;
	
	private byte[] clientMac;
	
	private long correlationId;
	
	private String address;
	
	private byte[] body;

	public byte getApiCodeId() {
		return apiCodeId;
	}

	public void setApiCodeId(byte apiCodeId) {
		this.apiCodeId = apiCodeId;
	}

	public short getVersion() {
		return version;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public byte[] getClientMac() {
		return clientMac;
	}

	public void setClientMac(byte[] clientMac) {
		this.clientMac = clientMac;
	}

	public long getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(long correlationId) {
		this.correlationId = correlationId;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
	
	public String getAddress() {
		return address;
	}

	/**
	 * max length is less than 15 bytes
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
