/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.monstersoftwarellc.virtualvote.IModel;
import com.monstersoftwarellc.virtualvote.property.utility.PropertyUtility;

/** 
 * @author Nick(Work)
 *
 */
@NodeEntity
public class PropertyMeta implements IModel {
	@GraphId
	private Long id;
	
	private Object propertyValue;
	@Indexed
	private PropertyCategories propertyCatagory;
	@Indexed
	private Object propertyType;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the propertyValue
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * @param propertyValue the propertyValue to set
	 */
	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	/**
	 * This will tie in our {@link #propertyType} to a specific {@link IPropertyType}
	 * {@link Enum} implementation.  We will also page our properties by this value.
	 * @return the propertyCatagory
	 */
	public IPropertyCategories getPropertyCatagory() {
		return propertyCatagory;
	}

	/**
	 * @param propertyCatagory the propertyCatagory to set
	 */
	public void setPropertyCatagory(PropertyCategories propertyCatagory) {
		this.propertyCatagory = propertyCatagory;
	}

	/**
	 * @return the propertyType
	 */
	public Object getPropertyType() {
		return propertyType;
	}

	/**
	 * @param propertyType the propertyType to set
	 */
	public void setPropertyType(Object propertyType) {
		this.propertyType = propertyType;
	}
	
	/**
	 * Get the concrete {@link IPropertyType} that this {@link PropertyMeta} represents.
	 * Doing it this way because there isn't a good way to store different implementations of
	 * an interface AND be an {@link Enum} type.  This is because the system doesn't know 
	 * how to convert to the specific {@link Enum} and {@link IPropertyType} implementation. 
	 * We could write our own converter and register it with Spring, but this seems to be less
	 * code to achieve the same result.
	 * @return
	 */
	public <T extends IPropertyType> T getType(){
		return PropertyUtility.getPropertyTypeFromPropertyMeta(this);
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(!(obj instanceof PropertyMeta)){
			return false;
		}
		PropertyMeta propMeta = (PropertyMeta) obj;		
		return new EqualsBuilder()
								.append(propMeta.getPropertyCatagory(), getPropertyCatagory())
								.append(propMeta.getPropertyType(), getPropertyType())
								.append(propMeta.getPropertyValue(), getPropertyValue())
								.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(11, 31)
								.append(getPropertyCatagory())
								.append(getPropertyType())
								.append(getPropertyValue())
								.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
	}
}
