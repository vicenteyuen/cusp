package org.vsg.cusp.eventbus;

import java.util.Objects;

public class DeliveryOptions {

	 /**
	   * The default send timeout.
	   */
	  public static final long DEFAULT_TIMEOUT = 30 * 1000;

	  private long timeout = DEFAULT_TIMEOUT;
	  private String codecName;
	  private MultiMap headers;

	  /**
	   * Default constructor
	   */
	  public DeliveryOptions() {
	  }

	  /**
	   * Copy constructor
	   *
	   * @param other  the options to copy
	   */
	  public DeliveryOptions(DeliveryOptions other) {
	    this.timeout = other.getSendTimeout();
	    this.codecName = other.getCodecName();
	    this.headers = other.getHeaders();
	  }



	  /**
	   * Get the send timeout.
	   * <p>
	   * When sending a message with a response handler a send timeout can be provided. If no response is received
	   * within the timeout the handler will be called with a failure.
	   *
	   * @return  the value of send timeout
	   */
	  public long getSendTimeout() {
	    return timeout;
	  }

	  /**
	   * Set the send timeout.
	   *
	   * @param timeout  the timeout value, in ms.
	   * @return  a reference to this, so the API can be used fluently
	   */
	  public DeliveryOptions setSendTimeout(long timeout) {
	    this.timeout = timeout;
	    return this;
	  }

	  /**
	   * Get the codec name.
	   * <p>
	   * When sending or publishing a message a codec name can be provided. This must correspond with a previously registered
	   * message codec. This allows you to send arbitrary objects on the event bus (e.g. POJOs).
	   *
	   * @return  the codec name
	   */
	  public String getCodecName() {
	    return codecName;
	  }

	  /**
	   * Set the codec name.
	   *
	   * @param codecName  the codec name
	   * @return  a reference to this, so the API can be used fluently
	   */
	  public DeliveryOptions setCodecName(String codecName) {
	    this.codecName = codecName;
	    return this;
	  }

	  /**
	   * Add a message header.
	   * <p>
	   * Message headers can be sent with any message and will be accessible with {@link io.vertx.core.eventbus.Message#headers}
	   * at the recipient.
	   *
	   * @param key  the header key
	   * @param value  the header value
	   * @return  a reference to this, so the API can be used fluently
	   */
	  public DeliveryOptions addHeader(String key, String value) {
	    checkHeaders();
	    Objects.requireNonNull(key, "no null key accepted");
	    Objects.requireNonNull(value, "no null value accepted");
	    headers.add(key, value);
	    return this;
	  }

	  /**
	   * Set message headers from a multi-map.
	   *
	   * @param headers  the headers
	   * @return  a reference to this, so the API can be used fluently
	   */
	  public DeliveryOptions setHeaders(MultiMap headers) {
	    this.headers = headers;
	    return this;
	  }

	  /**
	   * Get the message headers
	   *
	   * @return  the headers
	   */
	  public MultiMap getHeaders() {
	    return headers;
	  }

	  private void checkHeaders() {
	    if (headers == null) {
	      headers = new CaseInsensitiveHeaders();
	    }
	  }	
	
}
