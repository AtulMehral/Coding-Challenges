package com.example.demo.controller;


import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ConvertService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ConvertController {
	
	@Autowired
	private ConvertService convertService;

	@PostMapping("/convert")
	public String convert(@RequestBody String url) {
		return convertService.convertShorter(url);
	}
	
	
	@GetMapping("/fetch/{url}")
	public ResponseEntity<?> getURL(@PathVariable("url") String url) throws Exception {		
		String fetchURL = convertService.fetchURL(url);			
		System.out.println("Before extraction: "+fetchURL);
		String extractURLFromJson = convertService.extractURLFromJson(fetchURL);	
		String substring = extractURLFromJson.substring(1, extractURLFromJson.length()-1);
		System.out.println("After extraction: "+substring);
		
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(substring)).build();
	}
}

