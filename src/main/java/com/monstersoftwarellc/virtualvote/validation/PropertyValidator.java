/**
 * 
 */
package com.monstersoftwarellc.virtualvote.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.monstersoftwarellc.virtualvote.property.PropertyMeta;
import com.monstersoftwarellc.virtualvote.property.exceptions.PropertyValueException;
import com.monstersoftwarellc.virtualvote.validation.annotation.ValidateProperty;

/**
 * @author Nick(Work)
 *
 */
public class PropertyValidator implements ConstraintValidator<ValidateProperty, PropertyMeta>  {

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(ValidateProperty constraintAnnotation) {
		
	}

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(PropertyMeta value, ConstraintValidatorContext context) {
		 boolean valid = false;		 
		 try {
			if(value.getType().isValid(value.getPropertyValue())){
				valid = true;
			 }else{
				 context.buildConstraintViolationWithTemplate(value.getType().notValidMessage())
			              /*.addNode( "propertyMeta" )
			              .addNode( "propertyValue" )*/
			              .addConstraintViolation();
			 }
		} catch (PropertyValueException e) {
			// we have an exception - add the error message to a constraint violation
			context.buildConstraintViolationWithTemplate(value.getType().notValidMessage())
			           /* .addNode( "propertyMeta" )
			            .addNode( "propertyValue" )*/
			            .addConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
	           /* .addNode( "propertyMeta" )
	            .addNode( "propertyValue" )*/
	            .addConstraintViolation();
		}
		return valid;
	}

}
