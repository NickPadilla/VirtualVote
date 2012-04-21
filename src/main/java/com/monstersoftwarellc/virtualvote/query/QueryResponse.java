/**
 * 
 */
package com.monstersoftwarellc.virtualvote.query;

import java.util.List;


/**
 * @author nicholas
 *
 */
public class QueryResponse {

	private List<QueryHit> hits;
	
	private long totalNumberOfItems;
	
	/**
	 * @param json
	 */
	public QueryResponse(List<QueryHit> hits, long totalNumberOfItems) {
		super();
		this.hits = hits;
		this.totalNumberOfItems = totalNumberOfItems;
	}
	
	/**
	 * @return the json
	 */
	public List<QueryHit> getHits() {
		return hits;
	}

	/**
	 * @param json the json to set
	 */
	public void setHits(List<QueryHit> hits) {
		this.hits = hits;
	}

	public long getTotalNumberOfItems() {
		return totalNumberOfItems;
	}

	public void setTotalNumberOfItems(long totalNumberOfItems) {
		this.totalNumberOfItems = totalNumberOfItems;
	}

}
