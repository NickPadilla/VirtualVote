/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.monstersoftwarellc.virtualvote.property.IPropertyType;
import com.monstersoftwarellc.virtualvote.property.Property;
import com.monstersoftwarellc.virtualvote.property.PropertyCategories;
import com.monstersoftwarellc.virtualvote.property.service.IPropertyService;


/**
 * @author Nick(Work)
 *
 */
public interface PropertyRepository extends GraphRepository<Property> {
	/**
	 * Pass in the {@link IPropertyType} value you would like returned.  If we have no value you will
	 * get null returned.  Application usage is recommended to use {@link IPropertyService} to 
	 * get {@link Property}'s. 
	 * @param property
	 * @return
	 */
	Property findByPropertyMetaPropertyType(IPropertyType property);
	
	/**
	 * Will return a {@link List} of {@link Property}'s that are part of the given {@link PropertyCategories};
	 * @param category
	 * @return
	 */
	List<Property> findByPropertyMetaPropertyCatagory(PropertyCategories category);
}
