/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.monstersoftwarellc.virtualvote.ApplicationContextTest;
import com.monstersoftwarellc.virtualvote.property.repository.PropertyRepository;
import com.monstersoftwarellc.virtualvote.property.service.IPropertyService;
import com.monstersoftwarellc.virtualvote.property.utility.PropertyUtility;

/**
 * @author nicholas
 *
 */
public class PropertyTest extends ApplicationContextTest {

	private static final Logger LOG = Logger.getLogger(PropertyTest.class);
	
	@Autowired
	private IPropertyService propertiesService;
	
	/**
	 * Test using {@link PropertyRepository} - uses an Integer type property
	 */
	@Test
	@Transactional
	public void saveAndFindAll(){
		Property property = PropertyUtility.createProperty(PropertyTypeIndex.INDEX_NUMBER_TO_FETCH);
		propertiesService.getPropertyRepository().save(property);
		// check we have an id - save happened
		Long id = property.getId();
		assertNotNull(id);
		// check we can get the same object from db
		property = null;
		Property prop = propertiesService.getPropertyRepository().findOne(id);
		if(LOG.isDebugEnabled()){
			LOG.debug(PropertyTypeIndex.INDEX_NUMBER_TO_FETCH.name() + "  : " + prop);
		}
		assertEquals(prop.getPropertyValue(), PropertyTypeIndex.INDEX_NUMBER_TO_FETCH.getDefaultValue());
	}

	
	/**
	 * Test using {@link PropertyRepository}, this is a Grails type method in the {@link PropertyRepository} interface.
	 * Uses a String type property.
	 */
	@Test
	@Transactional
	public void saveAndFindByPropertyMetaPropertyType(){
		Property property = PropertyUtility.createProperty(PropertyTypeIndex.INDEX_FEDERAL_DATA_DIR);
		propertiesService.getPropertyRepository().save(property);
		// check we have an id - save happened
		assertNotNull(property.getId());
		// check we can get the same object from db
		Property prop = propertiesService.getPropertyRepository().findByPropertyMetaPropertyType(PropertyTypeIndex.INDEX_FEDERAL_DATA_DIR);
		if(LOG.isDebugEnabled()){
			LOG.debug(PropertyTypeIndex.INDEX_FEDERAL_DATA_DIR.name() + "  : " + prop);
		}
		assertEquals(prop.getPropertyValue(), PropertyTypeIndex.INDEX_FEDERAL_DATA_DIR.getDefaultValue());
	}
	
	/**
	 * Test using {@link IPropertyService} - will get the {@link Property} from the database,
	 * if it exists.  If it doesn't exists we will create one and save it with the defaults.
	 */
	@Test
	@Transactional
	public void getPropertyByPropertyType(){
		Property property = propertiesService.getPropertyByPropertyType(PropertyTypeIndex.INDEX_FEDERAL_DATA_DIR);
		// we saved the property successfully
		assertNotNull(property.getId());		
	}
	
	/**
	 * Test using {@link IPropertyService} - Save then try and update the property and save again.
	 */
	@Test
	@Transactional
	public void saveAndMerge(){
		// check save works property
		Property property = propertiesService.getPropertyByPropertyType(PropertyTypeIndex.INDEX_SCHEDULE);
		// we saved the property
		assertNotNull(property.getId());
		if(LOG.isDebugEnabled()){
			LOG.debug(PropertyTypeIndex.INDEX_SCHEDULE.name() + " Before : " + property);
		}
		property = null;
		property = propertiesService.getPropertyRepository().findByPropertyMetaPropertyType(PropertyTypeIndex.INDEX_SCHEDULE);
		// currently is correct
		assertEquals(property.getPropertyValue(), PropertyTypeIndex.INDEX_SCHEDULE.getDefaultValue());
		property.getPropertyMeta().setPropertyValue(" 1 2 3 4 5 6 ");
		propertiesService.save(property);
		property = null;
		property = propertiesService.getPropertyRepository().findByPropertyMetaPropertyType(PropertyTypeIndex.INDEX_SCHEDULE);
		if(LOG.isDebugEnabled()){
			LOG.debug(PropertyTypeIndex.INDEX_SCHEDULE.name() + " After : " + property);
		}
		// is it still correct
		assertEquals(property.getPropertyValue(), " 1 2 3 4 5 6 ");
	}


}
