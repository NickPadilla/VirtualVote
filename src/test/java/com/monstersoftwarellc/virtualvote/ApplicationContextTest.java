/**
 * 
 */
package com.monstersoftwarellc.virtualvote;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Using an empty abstract class that all Application Context Aware Tests can
 * extend to always use the same context.  If you don't need access to the 
 * ApplicationContext then you don't need to worry about adding any annotations.
 * @author nicholas
 *
 */
@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-context.xml")
public abstract class ApplicationContextTest {

}
