/**
 * 
 */
package com.monstersoftwarellc.virtualvote.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;

import com.monstersoftwarellc.virtualvote.validation.annotation.UniqueUsername;

/**
 * @author nicholas
 *
 */
public class UsernameValidator implements ConstraintValidator<UniqueUsername, IUsername>, BeanFactoryAware {
	
	private BeanFactory beanFactory = null;
	private IUsernameValidation<IUsername> usernameValidator;

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(UniqueUsername constraintAnnotation) {
		// we can safely cast here due to the annotation requirements - added suppress warnings 
		this.usernameValidator = (IUsernameValidation<IUsername>) BeanFactoryUtils.beanOfTypeIncludingAncestors
																													((ListableBeanFactory)beanFactory, constraintAnnotation.usernameValidator());
	}

	/* (non-Javadoc)
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(IUsername value, ConstraintValidatorContext context) {
		boolean ret = true;
		// check already persisted 
		if(value.getId() != null && !usernameValidator.isAlreadySavedUsernameUnique(value)){
			ret = false;
		}
		// check no persisted
		if(value.getId() == null){
			if(!usernameValidator.isUsernameUnique(value)){
				ret = false;
			}
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
