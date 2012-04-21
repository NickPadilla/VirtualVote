/**
 * 
 */
package com.monstersoftwarellc.virtualvote.spel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.monstersoftwarellc.virtualvote.ApplicationContextTest;
import com.monstersoftwarellc.virtualvote.property.PropertyTypeIndex;

/**
 * @author nicholas
 *
 */
public class SpringExpressionLanguageTest extends ApplicationContextTest {

	@Value(value="#{propertyService.getPropertyByPropertyType(T(com.monstersoftwarellc.virtualvote.property.PropertyTypeIndex).INDEX_SCHEDULE).getPropertyValue()}")
	private String dataDirectory;
	
	@Test
	public void test(){
		assertEquals(dataDirectory, PropertyTypeIndex.INDEX_SCHEDULE.getDefaultValue());
	}
}
