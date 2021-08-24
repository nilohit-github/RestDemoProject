package org.tech.project.RestDemo.service;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tech.project.RestDemo.model.Card;
import org.tech.project.RestDemo.repo.CardRepo;

//Use @transactional to create transaction boundary at object level
//@Transactionalon a class applies to each public method on the service. 
//It is a shortcut. Typically, you can set @Transactional(readOnly = true) on a service class,if you know that all methods will access the repository layer.
//You can then override the behavior with @Transactional on methods performing changes in your model.
@Service
@Transactional(propagation =Propagation.SUPPORTS,readOnly= true)
public class CardProcessorImpl implements CardProcessor {

	@Autowired
	CardRepo cardrepo;
	
	@Override
	public Collection<Card> findAll() {
		// TODO Auto-generated method stub
		Collection<Card> cards = cardrepo.findAll();
		
		return cards;
	}

	
	@Override
	public Card findById(Long id) {
		// TODO Auto-generated method stub
		Card mycard = cardrepo.findById(id).orElse(null);
		
		return mycard;
	}
	
	@Override
	public Card findByCardNo(String cardno) {
		
		Card mycard  = cardrepo.findByCardno(cardno);
		return mycard;
	}

	@Override
	@Transactional(
	            propagation = Propagation.REQUIRED,
	            readOnly = false)
	public Card saveCard(Card card) {
		
		// Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
		if (card.getId() != null) {
           // Cannot create Greeting with specified ID value
           //Adding transaction boundary at method level provided the ability to roll back in case of exception
            throw new EntityExistsException(
                    "The id attribute must be null to persist a new entity.");
        }

		
		Card savedcard = cardrepo.saveAndFlush(card);
		return savedcard;
	}

	// Ensure the entity object to be updated exists in the repository to
    // prevent the default behavior of save() which will persist a new
    // entity if the entity matching the id does not exist
	@Override
	public Card updateCard(Card card) {
		// TODO Auto-generated method stub
		Card cardtoupdate = cardrepo.findByCardno(card.getCardno());
		if (cardtoupdate == null)
			 return null;
		cardtoupdate.setCvv(card.getCvv());
		cardtoupdate.setExpdate(card.getExpdate());
			return cardrepo.save(cardtoupdate);
	}

	@Override
	public void deleteCard(Long id) {
		// TODO Auto-generated method stub
		
		cardrepo.deleteById(id);
		
	}

}
