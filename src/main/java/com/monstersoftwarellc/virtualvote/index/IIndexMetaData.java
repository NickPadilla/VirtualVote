/**
 * 
 */
package com.monstersoftwarellc.virtualvote.index;

import java.util.List;

/**
 * @author Nick(Work)
 *
 */
public interface IIndexMetaData {
	
	/**
	 * We return a valid list of IIndexDataType's that will contain the data types that this index may contain.  For instance this will 
	 * specify the kind of data this Index will use, XML and/or CSV.   These objects will be a seperate index_type in this index.
	 * @return
	 */
	public abstract List<IIndexDataType> getIndexDataType();
}
