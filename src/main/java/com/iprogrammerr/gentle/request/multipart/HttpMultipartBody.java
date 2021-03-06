package com.iprogrammerr.gentle.request.multipart;

import java.io.ByteArrayOutputStream;
import java.util.List;

public final class HttpMultipartBody implements MultipartBody {

	private static final String TWO_HYPHENS = "--";
	private static final String CRLF = "\r\n";
	private final String boundary;
	private final List<? extends Part> parts;

	public HttpMultipartBody(String boundary, List<? extends Part> parts) {
		this.boundary = boundary;
		this.parts = parts;
	}

	@Override
	public byte[] content() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bufferSize());
		String boundary = TWO_HYPHENS + this.boundary;
		baos.write(boundary.getBytes());
		baos.write(CRLF.getBytes());
		for (int i = 0; i < this.parts.size(); i++) {
			baos.write(this.parts.get(i).source());
			baos.write(CRLF.getBytes());
			baos.write(boundary.getBytes());
			if (i == (this.parts.size() - 1)) {
				baos.write(TWO_HYPHENS.getBytes());
			} else {
				baos.write(CRLF.getBytes());
			}
		}
		return baos.toByteArray();
	}

	private int bufferSize() throws Exception {
		int size = 0;
		for (Part part : this.parts) {
			size += part.source().length;
		}
		return size;
	}
}
