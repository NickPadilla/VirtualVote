/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account;

import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.monstersoftwarellc.virtualvote.IModel;
import com.monstersoftwarellc.virtualvote.validation.annotation.FieldMatch;
import com.monstersoftwarellc.virtualvote.validation.annotation.UniqueUsername;

/**
 * @author Nick(Work)
 *
 */
@NodeEntity
public class Account implements IModel {
	
	@GraphId
	private Long id;
	@Valid
	@Fetch
	private Person person = new Person();
	@Valid
	@Fetch	
	@UniqueUsername
	@FieldMatch(fieldToMatchOn="passwordVerify", fieldsToMatch="password", message = "{FieldMatch.account.password}")
	private AccountLogin login = new AccountLogin();
	
	private Set<Role> roles;

	/**
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person
	 */
	public void setPerson(Person user) {
		this.person = user;
	}

	/**
	 * @return the login
	 */
	public AccountLogin getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(AccountLogin login) {
		this.login = login;
	}

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// performance optimization
		if(obj == this){
			return true;
		}
		if(!(obj instanceof Account)){
			return false;
		}
		Account acc = (Account) obj;
		
		return new EqualsBuilder()
								.append(acc.getLogin().getUsername(), getLogin().getUsername())
								.append(acc.getLogin().getLastLoggedIn(), getLogin().getLastLoggedIn())
								.append(acc.getLogin().getLastLoggedInLocation(), getLogin().getLastLoggedInLocation())
								.append(acc.getLogin().getNumberOfFailedLoginAttempts(), getLogin().getNumberOfFailedLoginAttempts())
								.append(acc.getLogin().getPassword(), getLogin().getPassword())
								.append(acc.getPerson().getFirstName(), getPerson().getFirstName())
								.append(acc.getPerson().getMiddleName(), getPerson().getMiddleName())
								.append(acc.getPerson().getLastName(), getPerson().getLastName())
								.append(acc.getRoles(), getRoles())
								.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(3, 31)
								.append(getLogin().getUsername())
								.append(getLogin().getLastLoggedIn())
								.append(getLogin().getLastLoggedInLocation())
								.append(getLogin().getNumberOfFailedLoginAttempts())
								.append(getLogin().getPassword())
								.append(getPerson().getFirstName())
								.append(getPerson().getMiddleName())
								.append(getPerson().getLastName())
								.append(getRoles())
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
