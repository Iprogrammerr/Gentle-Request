package com.iprogrammerr.gentle.request.multipart;

public interface Part {

	String contentType() throws Exception;

	byte[] content() throws Exception;

	byte[] source() throws Exception;
}
