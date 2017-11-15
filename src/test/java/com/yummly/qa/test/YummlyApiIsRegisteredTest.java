package com.yummly.qa.test;

import static org.hamcrest.Matchers.equalTo;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
 
public class YummlyApiIsRegisteredTest {
	private RequestSpecification httpRequest;
	
	@Before
	public void setUp()
	{
		RestAssured.baseURI = "https://mapi.yummly.com/mapi";
		// It does not appear this API endpoint support specifying Content-Type
		// so based on observation on the response data being returned I have
		// defaulted the parser to JSON
		RestAssured.defaultParser = Parser.JSON;
		this.httpRequest = RestAssured.given();
	}
	
	@After
	public void tearDown()
	{
		this.httpRequest = null;
	}
	
	/**
	 * POST method is not allowed on this endpoint
	 * @result 405 error method not allowed 
	 * and server passes back the correct allowable header
	 */
	@Test
	public void IsRegisteredApiPOSTNotAllowedTest()
	{
		this.httpRequest.
			given().
			when().
				post("/v16/user/is-registered").
			then().
				header("allow", equalTo("GET")).
				statusCode(405);
	}

	/**
	 * PUT method is not allowed on this endpoint
	 * @result 405 error method not allowed 
	 * and server passes back the correct allowable header
	 */
	@Test
	public void IsRegisteredApiPUTNotAllowedTest()
	{
		this.httpRequest.
			given().
			when().
				put("/v16/user/is-registered").
			then().
				header("allow", equalTo("GET")).
				statusCode(405);
	}

	/**
	 * DELETE method is not allowed on this endpoint
	 * @result 405 error method not allowed 
	 * and server passes back the correct allowable header
	 */
	@Test
	public void IsRegisteredApiDELETENotAllowedTest()
	{
		this.httpRequest.
			given().
			when().
				delete("/v16/user/is-registered").
			then().
				header("allow", equalTo("GET")).
				statusCode(405);
	}
	
	/**
	 * Searching for unregistered user
	 * @result 404 not found
	 * JSON response will indicate no entity found as well
	 */
	@Test
	public void IsRegisteredApiGETNotRegisteredUserTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "en-US").
				queryParam("email", "testuser@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(404).
				body("code", equalTo("E_NO_SUCH_ENTITY")).
				body("description", equalTo("Entity does not exist")).
				body("entity", equalTo("User"));
	}

	/**
	 * Searching for unregistered user with no locale header
	 * @result 404 not found
	 * JSON response will indicate no entity found as well
	 */
	@Test
	public void IsRegisteredApiGETNotRegisteredUserNoLocaleTest()
	{
		this.httpRequest.
			given().
				queryParam("email", "testuser@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(404).
				body("code", equalTo("E_NO_SUCH_ENTITY")).
				body("description", equalTo("Entity does not exist")).
				body("entity", equalTo("User"));
	}

	/**
	 * Searching for unregistered user with fr-CA locale header
	 * @result 404 not found
	 * JSON response will indicate no entity found as well
	 */
	@Test
	public void IsRegisteredApiGETNotRegisteredUserFrCALocaleTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "fr-CA").
				queryParam("email", "testuser@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(404).
				body("code", equalTo("E_NO_SUCH_ENTITY")).
				body("description", equalTo("Entity does not exist")).
				body("entity", equalTo("User"));
	}
	
	/**
	 * Searching for unregistered user with empty locale header
	 * @result 404 not found
	 * JSON response will indicate no entity found as well
	 */
	@Test
	public void IsRegisteredApiGETNotRegisteredUserEmptyLocaleTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "").
				queryParam("email", "testuser@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(404).
				body("code", equalTo("E_NO_SUCH_ENTITY")).
				body("description", equalTo("Entity does not exist")).
				body("entity", equalTo("User"));
	}

	/**
	 * Searching for unregistered user with invalid locale header
	 * @result 404 not found
	 * JSON response will indicate no entity found as well
	 */
	@Test
	public void IsRegisteredApiGETNotRegisteredUserInvalidLocaleTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "what-is-this").
				queryParam("email", "testuser@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(404).
				body("code", equalTo("E_NO_SUCH_ENTITY")).
				body("description", equalTo("Entity does not exist")).
				body("entity", equalTo("User"));
	}
	
	/**
	 * Searching for registered user
	 * @result 200 ok response
	 * JSON response should indicate the id-index
	 */
	@Test
	public void IsRegisteredApiGETRegisteredUserTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "en-US").
				queryParam("email", "john.m.lee@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(200).
				body("id-index", equalTo(1));
	}

	/**
	 * Searching for registered user with no locale header
	 * @result 200 ok response
	 * JSON response should indicate the id-index
	 */
	@Test
	public void IsRegisteredApiGETRegisteredUserNoLocaleTest()
	{
		this.httpRequest.
			given().
				queryParam("email", "john.m.lee@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(200).
				body("id-index", equalTo(1));
	}
	
	/**
	 * Searching for registered user with fr-CA locale header
	 * @result 200 ok response
	 * JSON response should indicate the id-index
	 */
	@Test
	public void IsRegisteredApiGETRegisteredUserFrCALocaleTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "fr-CA").
				queryParam("email", "john.m.lee@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(200).
				body("id-index", equalTo(1));
	}
	
	/**
	 * Searching for registered user with empty locale header
	 * @result 200 ok response
	 * JSON response should indicate the id-index
	 */
	@Test
	public void IsRegisteredApiGETRegisteredUseEmptyLocaleTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "").
				queryParam("email", "john.m.lee@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(200).
				body("id-index", equalTo(1));
	}
	
	/**
	 * Searching for registered user with invalid locale header
	 * @result 200 ok response
	 * JSON response should indicate the id-index
	 */
	@Test
	public void IsRegisteredApiGETRegisteredUseInvalidLocaleTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "what-is-this").
				queryParam("email", "john.m.lee@gmail.com").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(200).
				body("id-index", equalTo(1));
	}
	
	/**
	 * Searching for user with no email param
	 * @result 400 bad request
	 */
	@Test
	public void IsRegisteredApiGETNoEmailParamTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "en-US").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(400);
	}

	/**
	 * Searching for user with empty email param
	 * @result 404 not found
	 * JSON response will indicate no entity found as well
	 */
	@Test
	public void IsRegisteredApiGETEmptyEmailParamTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "en-US").
				queryParam("email", "").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(404).
				body("code", equalTo("E_NO_SUCH_ENTITY")).
				body("description", equalTo("Entity does not exist")).
				body("entity", equalTo("User"));
	}

	/**
	 * Searching for user with badly formed email param
	 * @result 404 not found
	 * JSON response will indicate no entity found as well
	 */
	@Test
	public void IsRegisteredApiGETBadlyFormedEmailTest()
	{
		this.httpRequest.
			given().
				header("x-yummly-locale", "en-US").
				queryParam("email", "john").
			when().
				get("/v16/user/is-registered").
			then().
				statusCode(404).
				body("code", equalTo("E_NO_SUCH_ENTITY")).
				body("description", equalTo("Entity does not exist")).
				body("entity", equalTo("User"));
	}
}