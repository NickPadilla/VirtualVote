/**
 * 
 */
package com.monstersoftwarellc.virtualvote.property.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.monstersoftwarellc.virtualvote.property.Property;
import com.monstersoftwarellc.virtualvote.property.PropertyCategories;
import com.monstersoftwarellc.virtualvote.property.service.IPropertyService;

/**
 * @author nicholas
 *
 */
@Controller
@RequestMapping(value="/property/*")
public class PropertyController {
	
	@Autowired
	private IPropertyService propertyService;
	
	@RequestMapping(value="list")
	public String getProperties(Model model){
		model.addAttribute("properties", propertyService.getPropertyRepository()
											.findByPropertyMetaPropertyCatagory(PropertyCategories.INDEX));
		model.addAttribute("currentCategory", PropertyCategories.INDEX);
		return "property/list";
	}
	
	@RequestMapping(value="save", method=RequestMethod.PUT, consumes="application/json")
	public String saveProperty(@Valid Property property, BindingResult result){
		// if errors stay, redisplay list to show errors.
		// may have to change this to be non json based.  
		// i don't know if returning to "list" will actually 
		// cause a page reload. Maybe with JSF we can do this
		if(result.hasErrors()) {
			 return "property/list";
		}
		propertyService.save(property);
		return null;
	}

}
