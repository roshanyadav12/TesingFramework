package com.evs.qacodes.utils;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

@SuppressWarnings("unused")
public class ApiUtils {
	private RequestSpecBuilder requestSpecBuilder;
	private static RequestSpecification requestSpec = null;
	private Response responseSpec;
	private int expectedStatusCode;
	private String expectedResponseContentType;
	



	public ApiUtils setupBasicAuthentication(String baseURI, String username, String password) {
		RestAssured.baseURI = baseURI;
		RestAssured.useRelaxedHTTPSValidation();
		PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
		authScheme.setUserName(username);
		authScheme.setPassword(password);
		RestAssured.authentication = authScheme;
		requestSpecBuilder = new RequestSpecBuilder();
		return this;
	}

	public ApiUtils makeRequest(String httpMethod, String endpoint) {
		Response response = null;

		switch (httpMethod.toUpperCase()) {
		case "GET":
			requestSpec = requestSpecBuilder.build();
			responseSpec = given().log().all()
	                        .filter(new APIResponseFilter())
					.spec(requestSpec).when().get(endpoint).then().assertThat().statusCode(expectedStatusCode)
					.and().extract().response();

			return this;
		case "POST":
			requestSpec = requestSpecBuilder.build();
			responseSpec = given().log().all()
					 .filter(new APIResponseFilter())
					.spec(requestSpec).when().post(endpoint).then().assertThat().statusCode(201)
					.and().extract().response();

			return this;
		case "PUT":
			requestSpec = requestSpecBuilder.build();
			response = given().log().all()
					 .filter(new APIResponseFilter())
					.spec(requestSpec).when().put(endpoint).then()
					.and().extract().response();

			return this;
		case "PATCH":
			requestSpec = requestSpecBuilder.build();
			response = given().log().all()
					 .filter(new APIResponseFilter())
					.spec(requestSpec).when().patch(endpoint).then()
					.and().extract().response();

			return this;
		case "DELETE":
			requestSpec = requestSpecBuilder.build();
			response = given().log().all()
					 .filter(new APIResponseFilter())
					.spec(requestSpec).when().delete(endpoint).then().assertThat().statusCode(200)
					.and().extract().response();

			return this;
		default:
			 throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
		}

	}

	public ApiUtils path(String path) {
		requestSpecBuilder.setBasePath(path);
		return this;
	}

	/**
	 * Defines Path Parameters to Request Specification
	 *
	 * @param key
	 * @param value
	 * @return this
	 */
	public ApiUtils pathParam(String key, String value) {
		requestSpecBuilder.addPathParam(key, value);
		return this;
	}
	/**
	 * Defines Path Parameters to Request Specification
	 *
	 * @param Map<String, String> data
	*/
	public ApiUtils pathParam(Map<String, String> data) {
		requestSpecBuilder.addPathParams(data);
		return this;
	}
	/**
	 * Defines Query Parameters to Request Specification
	 *
	 * @param key
	 * @param value
	 * @return this
	 */
	public ApiUtils queryParam(String key, String value) {
		requestSpecBuilder.addQueryParam(key, value);
		return this;
	}
	/**
	 * Defines Query Parameters to Request Specification
	 *
	 * @param key
	 * @param value
	 * @return this
	 */
	public ApiUtils queryParam(Map<String, String> data) {
		requestSpecBuilder.addQueryParams(data);
		return this;
	}

	/**
	 * Defines Content Type Header to Request Specification
	 *
	 * @param contentType
	 * @return this
	 */
	public ApiUtils contentType(ContentType contentType) {
		requestSpecBuilder.setContentType(contentType);
		return this;
	}
	/**
	 * Defines Headers to Request Specification
	 *
	 * @param headers
	 * @return this
	 */
	public ApiUtils header(String key , String value) {
		requestSpecBuilder.addHeader(key,value);
		return this;
	}
	/**
	 * Defines Headers to Request Specification
	 *
	 * @param headers
	 * @return this
	 */
	public ApiUtils headers(Map<String, String> headers) {
		requestSpecBuilder.addHeaders(headers);
		return this;
	}

	/**
	 * Defines Cookies to Request Specification
	 *
	 * @param cookies
	 * @return this
	 */
	public ApiUtils cookies(Map<String, String> cookies) {
		requestSpecBuilder.addCookies(cookies);
		return this;
	}



	/**
	 * Defines Cookie to Request Specification
	 *
	 * @param cookie
	 * @return this
	 */
	public ApiUtils cookie(Cookie cookie) {
		requestSpecBuilder.addCookie(cookie);
		return this;
	}

	/**
	 * Defines Body to Request Specification
	 *
	 * @param body
	 * @return this
	 */
	public ApiUtils body(Object body) {
		requestSpecBuilder.setBody(body);
		return this;
	}

	/**
	 * Defines the Expected Status Code following successful api execution for
	 * validation
	 *
	 * @param expectedStatusCode
	 * @return this
	 */
	public ApiUtils expectedStatusCode(int expectedStatusCode) {
		this.expectedStatusCode = expectedStatusCode;
		return this;
	}

	/**
	 * Defines the Expected Response Content Type following successful api execution
	 * for validation
	 *
	 * @param contentType
	 * @return this
	 */
	public ApiUtils expectedResponseContentType(ContentType contentType) {
		this.expectedResponseContentType = contentType.toString();
		return this;
	}

	

    public ResponseBody name() {
		return responseSpec.getBody();
	}



	

	

}
