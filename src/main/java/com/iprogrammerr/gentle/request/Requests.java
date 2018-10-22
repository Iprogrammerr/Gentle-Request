package com.iprogrammerr.gentle.request;

public interface Requests {

    Response getResponse(String url, Header... headers) throws Exception;

    Response postResponse(String url, byte[] body, Header... headers) throws Exception;

    Response postResponse(String url, Header... headers) throws Exception;

    Response putResponse(String url, byte[] body, Header... headers) throws Exception;

    Response deleteResponse(String url, Header... headers) throws Exception;

    Response methodResponse(String method, String url, byte[] body, Header... headers) throws Exception;

    Response methodResponse(String method, String url, Header... headers) throws Exception;
}
