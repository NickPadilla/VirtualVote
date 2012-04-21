/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property;

/**
 * If you have a new Property Catagory you need to add it here or else no one will ever see your additions.
 * @author nicholas
 *
 */
public enum PropertyCategories implements IPropertyCategories {
	
	INDEX{
		public Class<? extends IPropertyType> getPropertyTypeForCategory() {
			return PropertyTypeIndex.class;
		}
		public String getCategoryLabel() {
			return "index.category.label";
		}
	};

}
