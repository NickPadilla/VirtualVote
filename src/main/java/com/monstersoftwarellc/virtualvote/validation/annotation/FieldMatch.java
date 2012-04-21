/**
 * 
 */
package com.monstersoftwarellc.virtualvote.validation.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import com.monstersoftwarellc.virtualvote.validation.FieldMatchValidator;


/**
 * Validation annotation to validate that 2 or more fields have the same value.
 * An array of fields and their matching confirmation fields can be supplied.
 * 
 * <br/><br/>
 * Currently this annotation cannot be used at the class level, but <b>HAS</b>
 * to be applied to an {@link Object} with the given fields. 
 * <br/><br/>
 *
 * Example, compare 1 pair of fields:
 * @FieldMatch(fieldToMatchOn="passwordVerify", fieldsToMatch="password", message = "{FieldMatch.account.password}")
 * 
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
@ReportAsSingleViolation
@Documented
public @interface FieldMatch {
	
    String message() default "{FieldMatch.field}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * The field we will use as the primary field to test all the other fields.
     * @return
     */
    String fieldToMatchOn();
    
    /**
     * All fields that must match primary field.
     * @return fields to match
     */
    String[] fieldsToMatch();
}
