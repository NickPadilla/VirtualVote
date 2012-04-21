/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property;

import java.util.List;

import com.monstersoftwarellc.virtualvote.ISelectable;
import com.monstersoftwarellc.virtualvote.property.exceptions.PropertyValueException;

/**
 * @author Nick(Work)
 *
 */
public interface IPropertyType {
	
	/**
	 * the type of property to return.
	 * @return
	 */
	Class<?> getPropertyClassType();
	
	/**
	 * List of options that can be given for selection.
	 * @return
	 */
	List<ISelectable> getListOfSelections();
	
	/**
	 * Are we dealing with a selectable property or just a 
	 * primary type field.
	 * @return true if we have a list of selectable values.
	 */
	boolean isSelectable();
	
	/**
	 * This is the international code to get the label for this property.
	 * @return
	 */
	String getLabel();
	
	/**
	 * Returns the default value of the given property.
	 * @return
	 */
	Object getDefaultValue();
	
	/**
	 * Method should validate the value for this property.
	 * @param value true if property is in valid format
	 * @return
	 * @throws Exception - method may throw exceptions and we will try to use them to inform the user.
	 */
	boolean isValid(Object value) throws PropertyValueException;
	
	/**
	 * International message when validator fails. 
	 * @return
	 */
	String notValidMessage();
	
	/**
	 * Option to format value before saving.
	 * @param value
	 * @return the converted type
	 */
	void formatValue(Object value); 
}
