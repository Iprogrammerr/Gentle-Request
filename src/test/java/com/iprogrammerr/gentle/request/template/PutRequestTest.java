package com.iprogrammerr.gentle.request.template;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import com.iprogrammerr.gentle.request.FilledRequestThatHaveProperValues;
import com.iprogrammerr.gentle.request.Header;
import com.iprogrammerr.gentle.request.RequestThatCanHaveAdditionalHeaders;
import com.iprogrammerr.gentle.request.initialization.FileContent;
import com.iprogrammerr.gentle.request.matching.FunctionThatThrowsException;
import com.iprogrammerr.gentle.request.mock.MockedBinary;
import com.iprogrammerr.gentle.request.mock.MockedHeaders;
import com.iprogrammerr.gentle.request.multipart.HttpFormPart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipart;
import com.iprogrammerr.gentle.request.multipart.HttpMultipartForm;
import com.iprogrammerr.gentle.request.multipart.HttpPart;
import com.iprogrammerr.gentle.request.multipart.Multipart;
import com.iprogrammerr.gentle.request.multipart.MultipartForm;

public final class PutRequestTest {

	private static final String URL = "www.mock.com";
	private static final String PUT = "PUT";

	@Test
	public void canContainBytes() throws Exception {
		byte[] content = new MockedBinary().content();
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(new PutRequest(URL, headers, content),
				new FilledRequestThatHaveProperValues(PUT, URL, headers, content));
	}

	@Test
	public void canContainText() throws Exception {
		String content = "Text";
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(new PutRequest(URL, headers, content), new FilledRequestThatHaveProperValues(PUT,
				URL, headers, new ContentTypeHeader("text/plain"), content.getBytes()));
	}

	@Test
	public void canContainJson() throws Exception {
		JSONObject json = new JSONObject();
		json.put("id", 22);
		json.put("name", "Igor");
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(new PutRequest(URL, json, headers.toArray(new Header[headers.size()])),
				new FilledRequestThatHaveProperValues(PUT, URL, headers,
						new ContentTypeHeader("application/json"), json.toString().getBytes()));
	}

	@Test
	public void canContainFile() throws Exception {
		File file = new File(String.format("src%stest%sresources%sjava.png", File.separator,
				File.separator, File.separator));
		String type = "image/jpg";
		List<Header> headers = new MockedHeaders().mocked();
		byte[] content = new FileContent(file).value();
		assertThat(new PutRequest(URL, headers, type, file), new FilledRequestThatHaveProperValues(
				PUT, URL, headers, new ContentTypeHeader(type), content));
	}

	@Test
	public void canContainMultipart() throws Exception {
		Multipart multipart = new HttpMultipart("alternative",
				new HttpPart("application/json", "{\"secret\":true}".getBytes()),
				new HttpPart("image/png", new MockedBinary().content()));
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(new PutRequest(URL, headers, multipart), new FilledRequestThatHaveProperValues(
				PUT, URL, headers, multipart.header(), multipart.body()));
	}

	@Test
	public void canContainMultipartForm() throws Exception {
		MultipartForm multipart = new HttpMultipartForm(
				new HttpFormPart("json", "json.json", "application/json",
						"{\"secret\": false}".getBytes()),
				new HttpFormPart("image", "java.png", "image/png", new MockedBinary().content()));
		List<Header> headers = new MockedHeaders().mocked();
		assertThat(new PutRequest(URL, multipart, headers.toArray(new Header[headers.size()])),
				new FilledRequestThatHaveProperValues(PUT, URL, headers, multipart.header(),
						multipart.body()));
	}

	@Test
	public void shouldFailIfBodyIsNotValid() {
		PutRequest request = new PutRequest(URL, "unknown", new File("??"));
		assertThat(() -> request.body(), new FunctionThatThrowsException());
	}

	@Test
	public void canHaveAdditionalHeaders() {
		assertThat(new PutRequest(URL, "mock".getBytes()),
				new RequestThatCanHaveAdditionalHeaders(new MockedHeaders().mocked()));
	}
}
