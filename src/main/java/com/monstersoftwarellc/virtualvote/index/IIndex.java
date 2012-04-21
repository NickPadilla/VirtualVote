/**
 * 
 */
package com.monstersoftwarellc.virtualvote.index;

/**
 * @author Nick(Work)
 *
 */
public interface IIndex {
	
	/**
	 * This will be the name for the index, such as the congressional year - which can be derived.  This way we can have
	 * {@link IIndex}'s that get dynamically created and maintained. 
	 * @return
	 */
	public abstract String getIndexName();
	
	/**
	 * Here we need to resolve the index based on something, not sure yet how we will do this.  The thought goes like this:
	 * 
	 * We resolve our index based on, maybe, an IIndexResolver that has a chance to create an index if one doesn't already
	 * exist.  An IIndexResolver will have a list of IIndexMetaData objects that will specify the IndexDataType the index 
	 * represents. Should be able to add new IIndexModules by adding new jars to the classpath.  The IIndexModules will register
	 * IIndex's and any helper classes, probably need to add new spring context xmls.  Not sure but it would be best to be able to
	 * just add functionality like new index - which means IndexDataType's will need to be dynamic and reloadable.  Maybe there is a 
	 * list of them which are also registered so we know how to relate what data a specific index holds.  So when the new jar is registered,
	 * by a job that scans specified locations for any non registered classes, the jar will will be scanned for any IIndexModule impls.  
	 * If any are found they will be able to execute any registrations needed, doesn't have just be new indices but also new IFilters to
	 * existing indices.
	 * 
	 * @return
	 */
	public abstract IIndexMetaData getIndexMetaData();
	
	/**
	 * Add IFilter to the list of registered filters.  This will enable a clean way to filter out unwanted
	 * data.  Please see the contract of IFilter's to ensure correct usage.
	 * @param filter
	 * @return the current IIndex implementation - used for chaining IFilters
	 */
	public abstract IIndex addFilter(IFilter filter);		

}
