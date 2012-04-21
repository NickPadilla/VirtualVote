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

import com.monstersoftwarellc.virtualvote.account.AccountLogin;
import com.monstersoftwarellc.virtualvote.validation.IUsername;
import com.monstersoftwarellc.virtualvote.validation.IUsernameValidation;
import com.monstersoftwarellc.virtualvote.validation.UsernameValidation;
import com.monstersoftwarellc.virtualvote.validation.UsernameValidator;

/**
 * Check to ensure this {@link AccountLogin} object is not using an already created 
 * username, if the {@link AccountLogin#getId()} is <code>NULL</code> we check 
 * know we already have one object so check the size to ensure there is only one.
 * @author nicholas
 *
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@ReportAsSingleViolation
@Documented
public @interface UniqueUsername {
	
    String message() default "{UniqueUsername.notUnique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    /**
     * Provide a custom {@link IUsernameValidation} that will validate for your custom settings. 
     * You should only provide this annotation on an object that implements {@link IUsername}, 
     * also the custom {@link IUsernameValidation} class needs to specify this {@link IUsername} 
     * object as the bounded type.
     * <br/><br/>
     * <b>NOTE: Default value is {@link UsernameValidation}, doesn't case sensitive.</b>
     * @return
     */
    Class<? extends IUsernameValidation<? extends IUsername>> usernameValidator() default UsernameValidation.class;
}
