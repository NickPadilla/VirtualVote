/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property.service;

import com.monstersoftwarellc.virtualvote.property.IPropertyType;
import com.monstersoftwarellc.virtualvote.property.Property;
import com.monstersoftwarellc.virtualvote.property.repository.PropertyRepository;

/**
 * @author nicholas
 *
 */
public interface IPropertyService {
	
	/**
	 * Get the {@link PropertyRepository} as a convenience for repository access. 
	 * @return
	 */
	public abstract PropertyRepository getPropertyRepository();

	/**
	 * Method will retrieve the value from the database, if no value exists in the database
	 * we will save one with the default value;
	 * @param type
	 * @return
	 */
	public abstract Property getPropertyByPropertyType(IPropertyType type);
	
	/**
	 * Save delegation, so that we can ensure we have the nested classes saved and up to date.
	 * @param property
	 * @return
	 */
	public abstract Property save(Property property);
	
}
