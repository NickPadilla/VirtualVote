/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property.utility;

import java.util.Arrays;
import java.util.List;

import com.monstersoftwarellc.virtualvote.property.IPropertyType;
import com.monstersoftwarellc.virtualvote.property.Property;
import com.monstersoftwarellc.virtualvote.property.PropertyCategories;
import com.monstersoftwarellc.virtualvote.property.PropertyMeta;


/**
 * @author nicholas
 *
 */
public class PropertyUtility {

	/**
	 * @param meta
	 * @return
	 */
	public static <T> T getPropertyTypeFromPropertyMeta(PropertyMeta meta){
		@SuppressWarnings("unchecked")
		List<IPropertyType> list = (List<IPropertyType>) Arrays.asList(meta.getPropertyCatagory().getPropertyTypeForCategory().getEnumConstants());		
		T instance = findMatchingPropertyType(meta.getPropertyType(), list);
		return instance;
	}
	
	/**
	 * @param meta
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getPropertyValueFromPropertyMeta(PropertyMeta meta){
		IPropertyType type = getPropertyTypeFromPropertyMeta(meta);
		return (T) performCasting(type.getPropertyClassType(), meta.getPropertyValue());
	}
	
	/**
	 * @param type
	 * @param value
	 * @return
	 */
	public static <T> T performCasting(Class<T> type, Object value){
		return type.cast(value);
	}
	
	/**
	 * @param type
	 * @return
	 */
	public static PropertyCategories getCategoryFromPropertyType(IPropertyType type){
		List<PropertyCategories> list = Arrays.asList(PropertyCategories.values());
		PropertyCategories category = null;
		for(PropertyCategories cat : list){
			@SuppressWarnings("unchecked")
			List<IPropertyType> subList = (List<IPropertyType>) Arrays.asList(cat.getPropertyTypeForCategory().getEnumConstants());
			// if we find the matching type we have the right catagory
			if(findMatchingPropertyType(type, subList) != null){
				category = cat;
				break;
			}
		}
		return category;
	}
	
	/**
	 * @param meta
	 * @param list
	 * @param instance
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T findMatchingPropertyType(Object propertyType, List<IPropertyType> list) {
		T instance = null;
		for(IPropertyType type : list){
			// try to ensure we have identical types -- if not we test the to string - which for Enums should match if 
			// you have two enums that are the same
			if(propertyType.equals(type) || propertyType.toString().equals(type.toString())){
				instance = (T) type;
				break;
			}
		}
		return instance;
	}
	
	/**
	 * Create test {@link Property} using {@link IPropertyType}
	 * @param propertyType
	 * @return
	 */
	public static Property createProperty(IPropertyType propertyType) {
		Property property = new Property();
		PropertyMeta meta = new PropertyMeta();
		meta.setPropertyCatagory(getCategoryFromPropertyType(propertyType));
		meta.setPropertyType(propertyType);
		meta.setPropertyValue(propertyType.getDefaultValue());
		property.setPropertyMeta(meta);
		return property;
	}
}
