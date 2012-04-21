/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property;

import java.io.File;
import java.util.List;

import org.springframework.scheduling.support.CronSequenceGenerator;

import com.monstersoftwarellc.virtualvote.ISelectable;
import com.monstersoftwarellc.virtualvote.property.exceptions.EmptyValueException;
import com.monstersoftwarellc.virtualvote.property.exceptions.FileSecurityException;
import com.monstersoftwarellc.virtualvote.property.exceptions.NullValueException;
import com.monstersoftwarellc.virtualvote.property.exceptions.PropertyValueException;

/**
 * Specifies application properties. 
 * <br/><br/>
 * <ul>
 * 	<li>{@link #INDEX_SCHEDULE}</li>
 *  <li>{@link #INDEX_FEDERAL_DATA_DIR}</li>
 *  <li>{@link #INDEX_NUMBER_TO_FETCH}</li>
 * </ul>
 * @author nicholas
 *
 */
public enum PropertyTypeIndex implements IPropertyType {
	/**
	 * This field will specify when our index schedule runs.<br/>
	 * <br/>
	 * Cron expression, here is the syntax:<br/>
	 * <br/>
	 * <pre>
 	 * Position        Field			Range
 	 *     1           Sec.			0-59
 	 *     2           Min.			0-59
 	 *     3           Hour			0-23
 	 *     4           DayOfMonth		1-31
 	 *     5           Month			1-12 or JAN-DEC
 	 *     6           DayOfWeek		1-7 or SUN-SAT
 	 *     7           Year(opt.)		1970-2099
 	 * </pre>
 	 * <br/><br/>
 	 * Default is : <b> 0 * * * * * </b>
 	 * <br/><br/>
 	 * @see CronSequenceGenerator
	 */
	INDEX_SCHEDULE{
		public Class<?> getPropertyClassType() {
			return String.class;
		}
		public List<ISelectable> getListOfSelections() {
			return null;
		}
		public boolean isSelectable() {
			return false;
		}
		public String getLabel() {
			return "index.schedule";
		}
		public String getDefaultValue() {
			return " 0 * * * * * ";
		}
		public boolean isValid(Object value) throws PropertyValueException {
			if(value == null){
				throw new NullValueException();
			}
			if(((String)value).isEmpty()){
				throw new EmptyValueException();
			}
			// will thow an exception if it is not able to be
			// formatted
			new CronSequenceGenerator((String)value, null);
			return true;
		}
		public String notValidMessage() {
			return "cronSchedule.notValid";
		}
		public void formatValue(Object value) {
		}
	},
	/**
	 * The directory that the federal data dump resides.
	 * <br/><br/>
 	 * Default is : <b>/media/Data/GovTrackData</b>
	 */
	INDEX_FEDERAL_DATA_DIR{
		public Class<?> getPropertyClassType() {
			return String.class;
		}
		public List<ISelectable> getListOfSelections() {
			return null;
		}
		public boolean isSelectable() {
			return false;
		}
		public String getLabel() {
			return "index.federalDataDir";
		}
		public String getDefaultValue() {
			return "/media/Data/GovTrackData";
		}
		public boolean isValid(Object value) throws PropertyValueException {
			boolean ret = false;
			try{
				if(new File((String)value).exists()){
					ret = true;
				}
			}catch(SecurityException ex){
				throw new FileSecurityException();
			}catch(NullPointerException e){
				throw new NullValueException();
			}
			return ret;
		}
		public String notValidMessage() {
			return "federalDataDir.doesNotExist";
		}
		public void formatValue(Object value) {
		}
	},
	/**
	 * The number of items to fetch at once.  This will only apply to the indexing<br/>
	 * process.  Provide a 0 and we will not limit the result set.
	 * <br/><br/>
 	 * Default is : <b>100</b>
	 */
	INDEX_NUMBER_TO_FETCH{
		public Class<?> getPropertyClassType() {
			return Integer.class;
		}
		public List<ISelectable> getListOfSelections() {
			return null;
		}
		public boolean isSelectable() {
			return false;
		}
		public String getLabel() {
			return "index.numberToFetch";
		}
		public Integer getDefaultValue() {
			return 100;
		}
		public boolean isValid(Object value) throws PropertyValueException {
			boolean ret = false;
			try{
				if(value instanceof Integer && (Integer)value > 0){
					ret = true;
				}
			}catch(Exception ex){
				throw new NullValueException(ex.getMessage());
			}
			return ret;
		}
		public String notValidMessage() {
			return null;
		}
		public void formatValue(Object value) {
		}
	};
}
