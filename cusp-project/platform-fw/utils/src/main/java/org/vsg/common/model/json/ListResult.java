/**
 * 
 */
package org.vsg.common.model.json;

import java.util.Collection;

/**
 * 此 Json对象实现对 extjs 的对象转换
 * @author Bill vison
 *
 */
public class ListResult implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Collection rows;
	
	private int results = 0;

	public int getResults() {
		return results;
	}
	
	public void setResults(int results) {
		this.results = results;
	}

	public Collection getRows() {
		return rows;
	}

	public void setRows(Collection rows) {
		this.rows = rows;
	}
	
	
	

}
