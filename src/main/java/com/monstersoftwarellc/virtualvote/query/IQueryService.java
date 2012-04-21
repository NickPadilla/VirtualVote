/**
 * 
 */
package com.monstersoftwarellc.virtualvote.query;

import java.util.List;

/**
 * @author nicholas
 *
 */
public interface IQueryService<T> {

	/**
	 * @param options
	 * @param page
	 * @return
	 */
	public abstract QueryResponse searchAllSpecificFields(SearchOptions options);

	/**
	 * @param options
	 * @param page
	 * @return
	 */
	public abstract QueryResponse searchAll(SearchOptions options);

	/**
	 * @param searchOptions
	 * @return
	 */
	public abstract List<NameValue> getExportDataFromSearchOptions(SearchOptions searchOptions);
	
	/**
	 * Method will provide the search/index client.
	 * @return
	 */
	public abstract T getClient();
}
