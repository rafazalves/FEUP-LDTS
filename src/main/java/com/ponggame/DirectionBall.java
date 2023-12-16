package com.ponggame;

import java.util.Random;

public enum DirectionBall {
	LEFT, UL, DL, ULL, DLL, RIGHT, UR, DR, URR, DRR;
	private static final DirectionBall[] VALUES = values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();
	public static DirectionBall getRandomDirection()  {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
	public static DirectionBall getRandomDirectionLEFT()  {
		return VALUES[RANDOM.nextInt(SIZE - 5)];
	}
	public static DirectionBall getRandomDirectionRIGHT()  {
		int s = RANDOM.nextInt(SIZE);
		if(s + 5 < SIZE){
			s = s + 5;
		}
		return VALUES[s];
	}
}
