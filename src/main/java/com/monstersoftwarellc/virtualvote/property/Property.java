/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.monstersoftwarellc.virtualvote.IModel;
import com.monstersoftwarellc.virtualvote.property.utility.PropertyUtility;
import com.monstersoftwarellc.virtualvote.validation.annotation.ValidateProperty;

/**
 * @author Nick(Work)
 *
 */
@NodeEntity
public class Property implements IModel {
	@GraphId
	private Long id;
	
	@ValidateProperty
	@Valid
	@Fetch
	private PropertyMeta propertyMeta;

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
	 * @return the propertyMeta
	 */
	public PropertyMeta getPropertyMeta() {
		return propertyMeta;
	}

	/**
	 * @param propertyMeta the propertyMeta to set
	 */
	public void setPropertyMeta(PropertyMeta propertyMeta) {
		this.propertyMeta = propertyMeta;
	}
	
	/**
	 * This method is a convenience method to easily get at the value for this {@link Property}.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getPropertyValue(){
		return (T) PropertyUtility.getPropertyValueFromPropertyMeta(this.propertyMeta);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == this){
			return true;
		}
		if(!(obj instanceof Property)){
			return false;
		}
		Property prop = (Property) obj;		
		return new EqualsBuilder()
								.append(prop.getPropertyMeta().getPropertyCatagory(), getPropertyMeta().getPropertyCatagory())
								.append(prop.getPropertyMeta().getPropertyType(), getPropertyMeta().getPropertyType())
								.append(prop.getPropertyMeta().getPropertyValue(), getPropertyMeta().getPropertyValue())
								.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(9, 31)
								.append(getPropertyMeta().getPropertyCatagory())
								.append(getPropertyMeta().getPropertyType())
								.append(getPropertyMeta().getPropertyValue())
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
