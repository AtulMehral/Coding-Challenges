package com.example.demo.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ConvertRepo {
	
	public Map<String, String> repo=new HashMap<>();
	
	public void add(String oldUrl, String newUrl) {
		repo.put(oldUrl, newUrl);
	}
	
	public Map<String, String> getRepo() {
		return repo;
	}

}
