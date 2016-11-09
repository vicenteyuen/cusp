package org.vsg.cusp.eventbus;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface MultiMap extends Iterable<Map.Entry<String, String>> {

	  /**
	   * Create a multi-map implementation with case insensitive keys, for instance it can be used to hold some HTTP headers.
	   *
	   * @return the multi-map
	   */
	 static MultiMap caseInsensitiveMultiMap() {
	    return new CaseInsensitiveHeaders();
	 }
	
	
	 String get(CharSequence name);
	 
	 String get(String name);
	 
	 /**
	   * Returns the values with the specified name
	   *
	   * @param name The name to search
	   * @return A immutable {@link java.util.List} of values which will be empty if no values
	   *         are found
	   */
	 List<String> getAll(String name);	 
	  
	  /**
	   * Like {@link #getAll(String)} but accepting a {@code CharSequence} as a parameter
	   */	 
	 List<String> getAll(CharSequence name);
	 
	  /**
	   * Returns all entries in the multi-map.
	   *
	   * @return A immutable {@link java.util.List} of the name-value entries, which will be
	   *         empty if no pairs are found
	   */	 
	 List<Map.Entry<String, String>> entries();
	 
	 /**
	   * Checks to see if there is a value with the specified name
	   *
	   * @param name The name to search for
	   * @return true if at least one entry is found
	   */
	  boolean contains(String name);

	  /**
	   * Like {@link #contains(String)} but accepting a {@code CharSequence} as a parameter
	   */
	  boolean contains(CharSequence name);

	  /**
	   * Return true if empty
	   */
	  boolean isEmpty();

	  /**
	   * Gets a immutable {@link java.util.Set} of all names
	   *
	   * @return A {@link java.util.Set} of all names
	   */
	  Set<String> names();

	  /**
	   * Adds a new value with the specified name and value.
	   *
	   * @param name The name
	   * @param value The value being added
	   * @return a reference to this, so the API can be used fluently
	   */
	  MultiMap add(String name, String value);

	  /**
	   * Like {@link #add(String, String)} but accepting {@code CharSequence} as parameters
	   */
	  MultiMap add(CharSequence name, CharSequence value);

	  /**
	   * Adds a new values under the specified name
	   *
	   * @param name The name being set
	   * @param values The values
	   * @return a reference to this, so the API can be used fluently
	   */
	  MultiMap add(String name, Iterable<String> values);

	  /**
	   * Like {@link #add(String, Iterable)} but accepting {@code CharSequence} as parameters
	   */
	  MultiMap add(CharSequence name, Iterable<CharSequence> values);

	  /**
	   * Adds all the entries from another MultiMap to this one
	   *
	   * @return a reference to this, so the API can be used fluently
	   */
	  MultiMap addAll(MultiMap map);

	  /**
	   * Adds all the entries from a Map to this
	   *
	   * @return a reference to this, so the API can be used fluently
	   */
	  MultiMap addAll(Map<String, String> headers);

	  /**
	   * Sets a value under the specified name.
	   * <p>
	   * If there is an existing header with the same name, it is removed.
	   *
	   * @param name The name
	   * @param value The value
	   * @return a reference to this, so the API can be used fluently
	   */
	  MultiMap set(String name, String value);

	  /**
	   * Like {@link #set(String, String)} but accepting {@code CharSequence} as parameters
	   */
	  MultiMap set(CharSequence name, CharSequence value);

	  /**
	   * Sets values for the specified name.
	   *
	   * @param name The name of the headers being set
	   * @param values The values of the headers being set
	   * @return a reference to this, so the API can be used fluently
	   */
	  MultiMap set(String name, Iterable<String> values);

	  /**
	   * Like {@link #set(String, Iterable)} but accepting {@code CharSequence} as parameters
	   */
	  MultiMap set(CharSequence name, Iterable<CharSequence> values);

	  /**
	   * Cleans this instance.
	   *
	   * @return a reference to this, so the API can be used fluently
	   */
	  MultiMap setAll(MultiMap map);

	  /**
	   * Cleans and set all values of the given instance
	   *
	   * @return a reference to this, so the API can be used fluently
	   */

	  MultiMap setAll(Map<String, String> headers);

	 /**
	  * Removes the value with the given name
	  *
	  * @param name The name  of the value to remove
	  * @return a reference to this, so the API can be used fluently
	  */
	  MultiMap remove(String name);

	  /**
	   * Like {@link #remove(String)} but accepting {@code CharSequence} as parameters
	   */
	  MultiMap remove(CharSequence name);

	  /**
	   * Removes all
	   *
	   * @return a reference to this, so the API can be used fluently
	   */
	  MultiMap clear();

	  /**
	   * Return the number of keys.
	   */
	  int size();	 
}
