/**
 * 
 */
package com.monstersoftwarellc.virtualvote.index;

import java.util.List;

/**
 * @author Nick(Work)
 *
 */
public interface IIndexDataType {
	
	/**
	 * The name to use as the Type Name in the parent {@link IIndex}. 
	 * @return
	 */
	public abstract String getDataTypeName();

	/**
	 * The type of data we are looking for, could be xml or csv or txt. The IDataType will specify the ITransformer to use.
	 * @return
	 */
	public abstract IDataType getDataType();
	
	/**
	 * How is this data structured?  This object will ensure we will always be able to query our data correctly. It will bind 
	 * somehow to the existing schema for a given type 
	 * @return
	 */
	public abstract List<IIndexDataStructure> getIndexDataStructures();
	
	/**
	 * Add an {@link IIndexDataStructure} that can be added to a {@link IDataType}.
	 * @return
	 */
	public abstract IIndexDataType addIndexDataStructures(IIndexDataStructure structureDefinition);
}
