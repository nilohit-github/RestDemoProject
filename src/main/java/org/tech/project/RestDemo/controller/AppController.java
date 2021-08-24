package org.tech.project.RestDemo.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tech.project.RestDemo.model.Card;
import org.tech.project.RestDemo.service.CardProcessor;

@RestController
public class AppController {
	
	@Autowired
	CardProcessor cardprocessor;
	

	
	@GetMapping(value="dtc/find/all")
	public ResponseEntity<Collection<Card>> findAll()
	{
		List<Card> cards= (List<Card>) cardprocessor.findAll();
		
		cards.sort((l1,l2)-> l1.getExpdate().compareTo(l2.getExpdate()));
		
		if(cards.isEmpty())
			return new ResponseEntity<Collection<Card>>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<Collection<Card>>(cards , HttpStatus.OK);
	}
	
	@GetMapping(value="dtc/find/cardbyid/{id}")
	public ResponseEntity<Card> findCardbyid(@PathVariable("id") Long id)
	{
		Card card= cardprocessor.findById(id);
		
		
		
		if(card == null)
			return new ResponseEntity<Card>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Card>(card, HttpStatus.OK);
	
	}
	
	@GetMapping(value="dtc/find/cardbyno/{cardno}")
	public ResponseEntity<Card> findCardbyno(@PathVariable("cardno") String cardno)
	{
		Card card= cardprocessor.findByCardNo(cardno);
		
		
		if(card == null)
			return new ResponseEntity<Card>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Card>(card, HttpStatus.OK);
	
	}
	
	
	
	@PostMapping(value="dtc/add/card")
	public ResponseEntity<Card> addCard(@RequestBody Card card)
	{
		if(cardprocessor.findByCardNo(card.getCardno()) != null)
			return new ResponseEntity<Card>(HttpStatus.CONFLICT);
		Card savedcard = cardprocessor.saveCard(card);
		return new ResponseEntity<Card>(savedcard, HttpStatus.OK);
			
		
		
	}
	
	@PutMapping(value="dtc/update/card")
	public ResponseEntity<Card> updateCard(@RequestBody Card card)
	{
		Card updatedcard = cardprocessor.updateCard(card);
		if(updatedcard == null)
			return new ResponseEntity<Card>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Card>(updatedcard, HttpStatus.OK);
			
		
		
	}
	
	@DeleteMapping(value="dtc/delete/{id}")
	public ResponseEntity<Card> updateCard(@PathVariable("id") Long id)
	{
		cardprocessor.deleteCard(id);
		
		return new ResponseEntity<Card>(HttpStatus.OK);
	
		
	}

}
