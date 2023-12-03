package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UrlModel;
import com.example.demo.repository.ConvertRepo;
import com.example.demo.strategies.RandomGenerationStrategy;
import com.example.demo.strategies.UniqueShorterURLGeneration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConvertService {

	@Autowired
	private ConvertRepo convertRepo;
	
	public String convertShorter(String url) {
		String newUrl=makeURLShorter(url);
		
		UrlModel model=new UrlModel();
		model.setIdempotency_key(newUrl);
		model.setLongURL(url);
		model.setShortURL(newUrl);
		convertRepo.save(model);
		return "Successfully Stored newURL "+newUrl;
	}

	private String makeURLShorter(String url) {		
		UniqueShorterURLGeneration strategy=new RandomGenerationStrategy();
		 		 
		 String convert = checkDuplicateURL(strategy);
		 
		 System.out.println(convert);
		 return convert;
	}

	private String checkDuplicateURL(UniqueShorterURLGeneration strategy) {
		List<UrlModel> all=(List<UrlModel>) convertRepo.findAll();		 
		 Set<String> set = all.stream().map(s->s.getShortURL()).collect(Collectors.toSet());
		 
		 String convert =null;
		 while(convert==null || set.contains(convert)) {
			 convert = strategy.convert();
		 }				
		return convert;
	}
	
	public String extractURLFromJson(String jsonResponse) throws JsonMappingException, JsonProcessingException {		
		 ObjectMapper map=new ObjectMapper();
		 JsonNode jsonNode = map.readTree(jsonResponse);
		 JsonNode node = jsonNode.get("url");
		 System.out.println("node: "+node);		 
		 return node.toString();
		
	    }
	
	public String fetchURL(String url) {
		System.out.println("In service method "+url);
		UrlModel originalUrl = convertRepo.findByShortURL(url);
		System.out.println(originalUrl.getLongURL());	
		return originalUrl.getLongURL();
	}
}
