/**
 * 
 */
package com.monstersoftwarellc.virtualvote.index;

import java.util.List;


/**
 * @author nicholas
 *
 */
public interface IIndexingService {
	
	/**
	 * Returns all {@link IIndex}'s that have been created.
	 * @return
	 */
	public abstract List<IIndex> getIndices();
	
	/**
	 * Add a new {@link IIndex} to the list of registered indices.  A new index will be created immediately.   
	 * We will override the original {@link IIndex} if one already exists. 
	 */
	public abstract void addIndex(IIndex index);

	/**
	 * Method will index data based on the properties file.  <br/>
	 * We will always attempt to find the greatest uniqueId <br/>
	 * within the already indexed data.  We will only index <br/>
	 * data once unless the application.properties file specifies <br/>
	 * that we reindex on start up, if so we will then do a full <br/>
	 * reindex on startup.
	 */
	public abstract void indexData();
	
}
