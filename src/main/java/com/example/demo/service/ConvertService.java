package com.example.demo.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ConvertRepo;
import com.example.demo.strategies.RandomGenerationStrategy;
import com.example.demo.strategies.UniqueShorterURLGeneration;

@Service
public class ConvertService {

	@Autowired
	private ConvertRepo convertRepo;
	
	public String convertShorter(String url) {
		String newUrl=makeURLShorter(url);
		convertRepo.add(url, newUrl);
		return "Successfully Stored newURL "+newUrl;
	}

	private String makeURLShorter(String url) {		
		UniqueShorterURLGeneration strategy=new RandomGenerationStrategy();
		 String convert = strategy.convert();
		 System.out.println(convert);
		 return convert;
	}
	
	public String fetchURL(String url) {
		Map<String,String> repo = convertRepo.getRepo();
		System.out.println(repo);
		
		String originalUrl = repo.entrySet().stream()
				.filter(p->p.getValue().equals(url))
				.map(Map.Entry::getKey)
				.findFirst().get();
		
		
		
		System.out.println(originalUrl);
		
		return originalUrl;
	}
}
