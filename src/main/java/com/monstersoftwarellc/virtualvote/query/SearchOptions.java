/**
 * 
 */
package com.monstersoftwarellc.virtualvote.query;

import java.util.List;


/**
 * @author nicholas
 *
 */
public class SearchOptions {

	private String queryAll;
	
	private int numberPerPage = 10;
	
	private int selectedFormId = 0;
	
	private int page = 1;
	
	private List<NameValue> formFields;

	/**
	 * @return the queryAll
	 */
	public String getQueryAll() {
		return queryAll;
	}
	/**
	 * @param queryAll the queryAll to set
	 */
	public void setQueryAll(String query) {
		this.queryAll = query;
	}
	/**
	 * @return the numberPerPage
	 */
	public int getNumberPerPage() {
		return numberPerPage;
	}
	/**
	 * @param numberPerPage the numberPerPage to set
	 */
	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}
	/**
	 * @return the selectedFormId
	 */
	public int getSelectedFormId() {
		return selectedFormId;
	}
	/**
	 * @param selectedFormId the selectedFormId to set
	 */
	public void setSelectedFormId(int selectedFormId) {
		this.selectedFormId = selectedFormId;
	}
	/**
	 * @return
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return the formFields
	 */
	public List<NameValue> getFormFields() {
		return formFields;
	}
	/**
	 * @param formFields the formFields to set
	 */
	public void setFormFields(List<NameValue> formFields) {
		this.formFields = formFields;
	}
}