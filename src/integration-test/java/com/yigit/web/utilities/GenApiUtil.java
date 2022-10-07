package com.yigit.web.utilities;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * This is class helper class for API testing
 */
public class GenApiUtil {

  /**
   * Global Setup Variables
   */
  public static String path; //Rest request path

  /**
   * Sets Base URI***
   * Before starting the test, we should set the RestAssured.baseURI
   */
  public static void setBaseURI() {
    RestAssured.baseURI = ConfigurationReader.get("url");
  }

  /**
   * Sets base path
   * Before starting the test, we may set the RestAssured.basePath
   *
   * @param basePath path of the url
   */
  public static void setBasePath(String basePath) {
    RestAssured.basePath = basePath;
  }

  /**
   * Reset Base URI (after test)
   * After the test, we should reset the RestAssured.baseURI
   */
  public static void resetBaseURI() {
    RestAssured.baseURI = null;
  }

  /**
   * Reset base path (after test)
   * After the test, we should reset the RestAssured.basePath
   */
  public static void resetBasePath() {
    RestAssured.basePath = null;
  }

  /**
   * Sets ContentType***
   * We should set content type as JSON or XML before starting the test
   *
   * @param Type type of the content most of the time: application/json
   */
  public static void setContentType(ContentType Type) {
    given().contentType(Type);
  }

  /**
   * search query path of first example
   * It is  equal to "barack obama/videos.json?num_of_videos=4"
   *
   * @param searchTerm   what will be searched
   * @param jsonPathTerm path
   * @param param        query parameter
   * @param paramValue   query value
   */
  public static void createSearchQueryPath(String searchTerm, String jsonPathTerm, String param, String paramValue) {
    path = searchTerm + "/" + jsonPathTerm + "?" + param + "=" + paramValue;
  }

  /**
   * Returns JsonPath object
   * First convert the API's response to String type with "asString()" method.
   * Then, send this String formatted json response to the JsonPath class and return the JsonPath
   *
   * @param response response of the request
   */
  public static JsonPath getJsonPath(Response response) {
    String json = response.asString();
    return new JsonPath(json);
  }

  /**
   * Check if data has Uppercase character
   *
   * @param data given value
   * @return true or false
   */
  public static boolean hasUpperCase(CharSequence data) {
    String a = String.valueOf(data);
    return !a.equals(a.toLowerCase());
  }

  /**
   * Check if all characters are Uppercase in data
   *
   * @param data given value
   * @return true or false
   */
  public static boolean hasAllUpperCase(CharSequence data) {
    String a = String.valueOf(data);
    return a.equals(a.toUpperCase());
  }

  /**
   * Check if data has lower case character
   *
   * @param data given value
   * @return true or false
   */
  public static boolean hasLowerCase(CharSequence data) {
    String a = String.valueOf(data);
    return !a.equals(a.toUpperCase());
  }

  /**
   * Check if data has length lower than or equal 8
   *
   * @param data given value
   * @return true or false
   */
  public static boolean hasLength(CharSequence data) {
    if (String.valueOf(data).length() >= 8) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check if data has Symbol
   *
   * @param data given value
   * @return true or false
   */
  public static boolean hasSymbol(CharSequence data) {
    String password = String.valueOf(data);
    return !password.matches("[A-Za-z0-9 ]*");
  }

  /**
   * Converts object to string json
   * @param data given value
   * @return String
   */
  public static String convertObjToString(Object data) {
    return new Gson().toJson(data, new TypeToken<Object>() {
    }.getType());
  }
}