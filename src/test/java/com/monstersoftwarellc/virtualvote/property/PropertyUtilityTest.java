/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property;

import static org.junit.Assert.*;

import org.junit.Test;

import com.monstersoftwarellc.virtualvote.property.utility.PropertyUtility;
/**
 * @author nicholas
 *
 */
public class PropertyUtilityTest {
	
	@Test
	public void testConvertPropertyTypeFromPropertyMeta(){
		PropertyMeta meta = new PropertyMeta();
		meta.setPropertyCatagory(PropertyCategories.INDEX);
		meta.setPropertyType(PropertyTypeIndex.INDEX_NUMBER_TO_FETCH);
		meta.setPropertyValue(PropertyTypeIndex.INDEX_NUMBER_TO_FETCH.getDefaultValue());

		PropertyTypeIndex numbToFetch = PropertyUtility.getPropertyTypeFromPropertyMeta(meta);
		assertEquals(numbToFetch, PropertyTypeIndex.INDEX_NUMBER_TO_FETCH);
	}
	
	@Test
	public void testGetPropertyValueFromPropertyMeta(){
		PropertyMeta meta = new PropertyMeta();
		meta.setPropertyCatagory(PropertyCategories.INDEX);
		meta.setPropertyType(PropertyTypeIndex.INDEX_NUMBER_TO_FETCH);
		meta.setPropertyValue(PropertyTypeIndex.INDEX_NUMBER_TO_FETCH.getDefaultValue());
		
		Integer numbToFetch = PropertyUtility.getPropertyValueFromPropertyMeta(meta);
		assertEquals(numbToFetch, PropertyTypeIndex.INDEX_NUMBER_TO_FETCH.getDefaultValue());
	}

}
