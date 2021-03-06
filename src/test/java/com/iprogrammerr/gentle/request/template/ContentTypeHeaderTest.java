package com.iprogrammerr.gentle.request.template;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.HeaderThatContainsValues;

public final class ContentTypeHeaderTest {

	private static final String KEY = "Content-type";

	@Test
	public void canHaveProperValues() {
		String type = "application/json";
		assertThat(new ContentTypeHeader(type),
				new HeaderThatContainsValues(KEY.toLowerCase(), type));
	}

	@Test
	public void canHaveProperAdditonalValues() {
		String type = "text/html";
		String additional = "charset=ISO-8859-2";
		Header header = new ContentTypeHeader(type, additional);
		assertThat(header, new HeaderThatContainsValues(KEY.toLowerCase(), header.value()));
	}

	@Test
	public void shouldNotBeEqual() {
		String type = "text/plain";
		assertThat(new ContentTypeHeader(type),
				Matchers.not(new HeaderThatContainsValues(KEY.toUpperCase(), type.substring(2))));
	}
}
