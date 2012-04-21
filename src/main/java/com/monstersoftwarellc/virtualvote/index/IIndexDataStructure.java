/**
 * 
 */
package com.monstersoftwarellc.virtualvote.index;

/**
 * @author Nick(Work)
 *
 */
public interface IIndexDataStructure {

	/**
	 * Get the message name, must use a .properties message so that it can be an international.
	 * @return
	 */
	public abstract String getStructureName();
	
	/**
	 * Gets the actual property name in the schema for the {@link IDataType}. 
	 * @return
	 */
	public abstract String getStructureProperty();
}
