package br.com.waltim.api.config;

public class TestConfigs {

    public static final int SERVER_PORT = 8888;

    public static final String HEADER_PARAM_AUTHORIZATION = "Authorization";
    public static final String HEADER_PARAM_ORIGIN = "Origin";

    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_YML = "application/x-yaml";

    public static final String ORIGIN_LOCAL = "http://localhost:8081";
    public static final String ORIGIN_FRONTEND = "http://localhost:3000";
    public static final String ORIGIN_INVALID = "https://dominio-do-mal:7181";
}
