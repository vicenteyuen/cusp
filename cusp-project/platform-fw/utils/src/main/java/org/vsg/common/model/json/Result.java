/**
 * 
 */
package org.vsg.common.model.json;

/**
 * @author Bill Vison
 *
 */
public class Result implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2412239418317851634L;

	private boolean success;
	
	/**
	 * return the data mapping when response is successful.
	 */
	private Object data;
	
	/**
	 * return message when response is successful
	 */
	private String msg;
	
	/**
	 * return the data mapping when response is fail.
	 */
	private Object errors;

	/**
	 * return message when response is fail
	 */	
	private String errorMessage;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object result) {
		this.data = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String message) {
		this.msg = message;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}

	public String getErrorMessage() {
		if (errorCode != null) {
			return "["+ errorCode + "]: " + errorMessage;
		}
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * add the error code and message
	 * @param code
	 * @param errorMessage
	 */
	public void setErrorMessage(String code , String errorMessage) {
		this.errorMessage = errorMessage;
		setErrorCode(code);
	}
	
	private String errorCode;
	
	public void setErrorCode(String code) {
		errorCode  = code;
	}
	
	

}
