package org.tech.project.RestDemo.service;

import java.util.Collection;

import org.tech.project.RestDemo.model.Card;

public interface CardProcessor {
	
	Collection<Card> findAll();
	
	Card  findById(Long id);
	
	Card saveCard(Card card);
	
	Card updateCard(Card card);
	
	void deleteCard(Long id);

	Card findByCardNo(String cardno);

}
