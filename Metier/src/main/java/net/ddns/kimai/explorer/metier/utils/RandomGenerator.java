package net.ddns.kimai.explorer.metier.utils;

import java.util.Random;

// Provide functionalities for generating random numbers
// Injected properly, will allow much much easier testing
public class RandomGenerator {

	private final Random rdm;
	
	public RandomGenerator(long seed) {
		this.rdm = new Random( seed );
	}
	
	public RandomGenerator() {
		this(0L);
	}
	// static function may be nice to force / hide singleton
	// static init()
	
	// single interface
	public int nextInt(int maxRange) {
		return rdm.nextInt(maxRange);
	}	
}
