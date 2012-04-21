/**
 * 
 */
package com.monstersoftwarellc.virtualvote.index;

/**
 * @author Nick(Work)
 *
 */
public interface IIndexModule {

	/**
	 * Gives a chance to an {@link IIndexModule} to add an {@link IIndex} or {@link IFilter} to an existing index to the system.  
	 * @param indexingService
	 */
	public abstract void addIndexOrFilter(IIndexingService indexingService);
	
	/**
	 * Add a new {@link IIndexDataStructure} to the list for the given indexName and 
	 * @param indexName
	 * @param structure
	 */
	public abstract void addIndexDataStructureToIndexType(String indexName, String indexDataTypeName, IIndexDataStructure structure);
	
}
