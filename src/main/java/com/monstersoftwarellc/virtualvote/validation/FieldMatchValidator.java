/**
 * 
 */
package com.monstersoftwarellc.virtualvote.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanUtils;

import com.monstersoftwarellc.virtualvote.validation.annotation.FieldMatch;

/**
 * @author Nick(Work)
 *
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	private String[] fieldsToMatch;
	private String fieldToMatchOn;

    /**
	 * 
	 */
	public FieldMatchValidator() {
	}

	/* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
     */
    @Override
    public void initialize(final FieldMatch constraintAnnotation){
    	fieldsToMatch = constraintAnnotation.fieldsToMatch();
    	fieldToMatchOn = constraintAnnotation.fieldToMatchOn();
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context){
        try{
        	boolean ret = true;
    		String val = (String) BeanUtils.getPropertyDescriptor(value.getClass(), fieldToMatchOn).getReadMethod().invoke(value);
        	for(String field : fieldsToMatch){ 
        		String valN = (String) BeanUtils.getPropertyDescriptor(value.getClass(), field).getReadMethod().invoke(value);
        		if(!((val == null && valN == null) || (valN != null && valN.equals(val)))){
        			ret = false;
        			break;
        		}
        	}
            return ret;
        } catch (final Exception ignore){
            // ignore
        }
        return false;
    }
}
