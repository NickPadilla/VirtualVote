/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account;

import java.util.Date;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.format.annotation.DateTimeFormat;

import com.monstersoftwarellc.virtualvote.IModel;
import com.monstersoftwarellc.virtualvote.validation.IUsername;


/**
 * @author Nick(Work)
 *
 */
@NodeEntity
public class AccountLogin implements IModel, IUsername {

	@GraphId
	private Long id;
	@Indexed(indexName="search")
	@NotBlank(message="{NotEmpty.account.username}")
	private String username;
    @Size(min=8, message="{Size.account.password}")
	@NotBlank(message="{NotEmpty.account.password}")
	private String password;
    @Size(min=8, message="{Size.account.password}")
	@NotBlank(message="{NotEmpty.account.password}")
	private String passwordVerify;
	@DateTimeFormat(iso=DateTimeFormat.ISO.NONE)
	private Date lastLoggedIn = new Date();
	private String lastLoggedInLocation;
	private int numberOfFailedLoginAttempts;
	
	
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the passwordVerify
	 */
	public String getPasswordVerify() {
		return passwordVerify;
	}

	/**
	 * @param passwordVerify the passwordVerify to set
	 */
	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

	/**
	 * @return the lastLoggedIn
	 */
	public Date getLastLoggedIn() {
		return lastLoggedIn;
	}

	/**
	 * @param lastLoggedIn the lastLoggedIn to set
	 */
	public void setLastLoggedIn(Date lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	/**
	 * @return the lastLoggedInLocation
	 */
	public String getLastLoggedInLocation() {
		return lastLoggedInLocation;
	}

	/**
	 * @param lastLoggedInLocation the lastLoggedInLocation to set
	 */
	public void setLastLoggedInLocation(String lastLoggedInLocation) {
		this.lastLoggedInLocation = lastLoggedInLocation;
	}

	/**
	 * @return the numberOfFailedLoginAttempts
	 */
	public int getNumberOfFailedLoginAttempts() {
		return numberOfFailedLoginAttempts;
	}

	/**
	 * @param numberOfFailedLoginAttempts the numberOfFailedLoginAttempts to set
	 */
	public void setNumberOfFailedLoginAttempts(int numberOfFailedLoginAttempts) {
		this.numberOfFailedLoginAttempts = numberOfFailedLoginAttempts;
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
		if(!(obj instanceof AccountLogin)){
			return false;
		}
		AccountLogin accLog = (AccountLogin) obj;
		
		return new EqualsBuilder()
								.append(accLog.getUsername(), getUsername())
								.append(accLog.getLastLoggedIn(), getLastLoggedIn())
								.append(accLog.getLastLoggedInLocation(), getLastLoggedInLocation())
								.append(accLog.getNumberOfFailedLoginAttempts(), getNumberOfFailedLoginAttempts())
								.append(accLog.getPassword(), getPassword())
								.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(7, 31)
								.append(getUsername())
								.append(getLastLoggedIn())
								.append(getLastLoggedInLocation())
								.append(getNumberOfFailedLoginAttempts())
								.append(getPassword())
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
