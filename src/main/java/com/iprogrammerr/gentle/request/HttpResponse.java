package com.iprogrammerr.gentle.request;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.gentle.request.binary.HttpBinary;
import com.iprogrammerr.gentle.request.binary.SmartBinary;

public final class HttpResponse implements Response {

	private final int code;
	private final List<Header> headers;
	private final SmartBinary body;

	public HttpResponse(int code, List<Header> headers, SmartBinary body) {
		this.code = code;
		this.headers = headers;
		this.body = body;
	}

	public HttpResponse(int code, List<Header> headers, byte[] body) {
		this(code, headers, new HttpBinary(body));
	}

	public HttpResponse(int code, byte[] body) {
		this(code, new ArrayList<>(), body);
	}

	public HttpResponse(int code, List<Header> headers) {
		this(code, headers, new byte[0]);
	}

	public HttpResponse(int code) {
		this(code, new byte[0]);
	}

	@Override
	public int code() {
		return this.code;
	}

	@Override
	public boolean hasSuccessCode() {
		return this.code >= 200 && this.code < 300;
	}

	@Override
	public List<Header> headers() {
		return this.headers;
	}

	@Override
	public boolean hasHeader(String key) {
		for (Header h : this.headers) {
			if (h.is(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Header header(String key) throws Exception {
		for (Header h : this.headers) {
			if (h.is(key)) {
				return h;
			}
		}
		throw new Exception(String.format("There is no header associated with %s key", key));
	}

	@Override
	public SmartBinary body() {
		return this.body;
	}
}
