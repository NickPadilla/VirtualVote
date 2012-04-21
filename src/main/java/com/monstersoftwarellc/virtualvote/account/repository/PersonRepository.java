/**
 * 
 */
package com.monstersoftwarellc.virtualvote.account.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.monstersoftwarellc.virtualvote.account.Person;


/**
 * @author Nick(Work)
 *
 */
public interface PersonRepository extends GraphRepository<Person> {

}
