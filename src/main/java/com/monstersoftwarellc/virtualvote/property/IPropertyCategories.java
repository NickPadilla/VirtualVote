/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property;

/**
 * @author nicholas
 *
 */
public interface IPropertyCategories {
	
	Class<? extends IPropertyType> getPropertyTypeForCategory();
	
	String getCategoryLabel();

}
