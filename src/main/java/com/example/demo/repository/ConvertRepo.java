package com.example.demo.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UrlModel;

@Repository
public interface ConvertRepo extends CrudRepository<UrlModel, Integer> {

	public UrlModel findByShortURL(String url);

}
