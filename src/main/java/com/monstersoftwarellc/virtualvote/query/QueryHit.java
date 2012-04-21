/**
 * 
 */
package com.monstersoftwarellc.virtualvote.query;

import java.util.List;


/**
 * @author nicholas
 *
 */
public class QueryHit {

	private List<NameValue> hitFields;
	
	private String formId;
	
	/**
	 * @param hitFields
	 */
	public QueryHit(List<NameValue> hitFields, String formId) {
		this.hitFields = hitFields;
		this.setFormId(formId);
	}

	/**
	 * @return the hitFields
	 */
	public List<NameValue> getHitFields() {
		return hitFields;
	}

	/**
	 * @param hitFields the hitFields to set
	 */
	public void setHitFields(List<NameValue> hitFields) {
		this.hitFields = hitFields;
	}

	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

}
