package com.iprogrammerr.gentle.request.initialization;

import java.nio.charset.StandardCharsets;

public final class HttpBoundary implements Initialization<String> {

	private static final int ASCII_0 = 48;
	private static final int ASCII_UPPER_CASE_A = 65;
	private static final int ASCII_LOWER_CASE_A = 97;
	private final int min;
	private final int max;

	public HttpBoundary(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public HttpBoundary() {
		this(25, 70);
	}

	@Override
	public String value() {
		int length = this.min + (int) (Math.random() * (this.max - this.min));
		byte[] boundary = new byte[length];
		for (int i = 0; i < boundary.length; ++i) {
			boundary[i] = randomCharacter();
		}
		return new String(boundary, StandardCharsets.US_ASCII);
	}

	private byte randomCharacter() {
		double type = Math.random();
		byte random;
		if (type <= 0.33) {
			random = (byte) (ASCII_0 + (int) (Math.random() * 10));
		} else if (type <= 0.66) {
			random = (byte) (ASCII_UPPER_CASE_A + (int) (Math.random() * 26));
		} else {
			random = (byte) (ASCII_LOWER_CASE_A + (int) (Math.random() * 26));
		}
		return random;
	}
}
