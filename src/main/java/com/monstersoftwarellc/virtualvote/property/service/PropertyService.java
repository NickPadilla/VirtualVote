/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monstersoftwarellc.virtualvote.property.IPropertyType;
import com.monstersoftwarellc.virtualvote.property.Property;
import com.monstersoftwarellc.virtualvote.property.PropertyCategories;
import com.monstersoftwarellc.virtualvote.property.PropertyMeta;
import com.monstersoftwarellc.virtualvote.property.repository.PropertyMetaRepository;
import com.monstersoftwarellc.virtualvote.property.repository.PropertyRepository;
import com.monstersoftwarellc.virtualvote.property.utility.PropertyUtility;

/**
 * @author nicholas
 *
 */
@Service
public class PropertyService implements IPropertyService {
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired 
	private PropertyMetaRepository propertyMetaRepository;
	
	@PostConstruct
	@SuppressWarnings("unused")
	private void init(){
		for(PropertyCategories category : PropertyCategories.values()){
			for(IPropertyType type : category.getPropertyTypeForCategory().getEnumConstants()){
				// just call this method and it will create the default if none exist.
				getPropertyByPropertyType(type);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.virtualvote.property.service.IPropertyService#getPropertyRepository()
	 */
	@Override
	public PropertyRepository getPropertyRepository() {
		return propertyRepository;
	}


	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.virtualvote.property.service.IPropertyService#getPropertyByPropertyType(com.monstersoftwarellc.virtualvote.property.IPropertyType)
	 */
	@Override
	public Property getPropertyByPropertyType(IPropertyType type) {
		Property property = propertyRepository.findByPropertyMetaPropertyType(type);
		if(property == null){
			property = propertyRepository.save(PropertyUtility.createProperty(type));
		}
		return property;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.virtualvote.property.service.IPropertyService#save(com.monstersoftwarellc.virtualvote.property.Property)
	 */
	@Override
	public Property save(Property property) {
		if(property.getId() == null){
			property = propertyRepository.save(property);
		}else{
			// save property meta first then save property
			PropertyMeta meta = propertyMetaRepository.save(property.getPropertyMeta());
			property.setPropertyMeta(meta);
			property = propertyRepository.save(property);
		}
		return property;
	}

}
