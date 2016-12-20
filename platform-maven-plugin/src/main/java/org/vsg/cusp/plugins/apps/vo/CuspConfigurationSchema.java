/**
 * 
 */
package org.vsg.cusp.plugins.apps.vo;

import java.io.IOException;
import java.util.Set;

import io.protostuff.Input;
import io.protostuff.Output;
import io.protostuff.Schema;

/**
 * @author ruanweibiao
 *
 */
public class CuspConfigurationSchema implements Schema<CuspConfigration> {

	/* (non-Javadoc)
	 * @see io.protostuff.Schema#getFieldName(int)
	 */
	@Override
	public String getFieldName(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see io.protostuff.Schema#getFieldNumber(java.lang.String)
	 */
	@Override
	public int getFieldNumber(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see io.protostuff.Schema#isInitialized(java.lang.Object)
	 */
	@Override
	public boolean isInitialized(CuspConfigration message) {
		Set<MicroComp>  microComps  = message.getMicroComps();
		return !microComps.isEmpty();
	}

	/* (non-Javadoc)
	 * @see io.protostuff.Schema#newMessage()
	 */
	@Override
	public CuspConfigration newMessage() {
		return new CuspConfigration();
	}

	/* (non-Javadoc)
	 * @see io.protostuff.Schema#messageName()
	 */
	@Override
	public String messageName() {
		return CuspConfigration.class.getSimpleName();
	}

	/* (non-Javadoc)
	 * @see io.protostuff.Schema#messageFullName()
	 */
	@Override
	public String messageFullName() {
		return CuspConfigration.class.getName();
	}

	/* (non-Javadoc)
	 * @see io.protostuff.Schema#typeClass()
	 */
	@Override
	public Class<CuspConfigration> typeClass() {
		// TODO Auto-generated method stub
		return CuspConfigration.class;
	}

	/* (non-Javadoc)
	 * @see io.protostuff.Schema#mergeFrom(io.protostuff.Input, java.lang.Object)
	 */
	@Override
	public void mergeFrom(Input input, CuspConfigration message) throws IOException {
		
		

	}

	/* (non-Javadoc)
	 * @see io.protostuff.Schema#writeTo(io.protostuff.Output, java.lang.Object)
	 */
	@Override
	public void writeTo(Output output, CuspConfigration message) throws IOException {
		
		
		String configrationPath = message.getPlatform().getConfigrationPath();
		
		output.writeString(0, configrationPath, false);
		
		//output.write

	}

}
