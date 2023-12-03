package com.example.demo.strategies;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomGenerationStrategy implements UniqueShorterURLGeneration {

	@Override
	public String convert() {
		String random = RandomStringUtils.random(6, true, true);
		return random;
	}

}
