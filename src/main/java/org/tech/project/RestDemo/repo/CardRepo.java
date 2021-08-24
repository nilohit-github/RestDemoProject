package org.tech.project.RestDemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tech.project.RestDemo.model.Card;


@Repository
public interface CardRepo extends JpaRepository<Card,Long> {

	//One of the powerfull tool with spring JPA is the ability to create custom methods like below.
	//Jpa will try to match the parameter provided to the method and provide methods  similar to default methods like findById
	Card findByCardno (String cardno);
	
	

}
