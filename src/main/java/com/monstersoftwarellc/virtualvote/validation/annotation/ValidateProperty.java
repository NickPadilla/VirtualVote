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

import com.monstersoftwarellc.virtualvote.property.Property;
import com.monstersoftwarellc.virtualvote.validation.PropertyValidator;

/**
 * {@link Property} validator.  
 * @author Nick(Work)
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = PropertyValidator.class)
@ReportAsSingleViolation
@Documented
public @interface ValidateProperty {
    String message() default "{ValidateProperty.field}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
