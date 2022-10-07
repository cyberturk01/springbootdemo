package com.yigit.web.utilities;


import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public final class DBConnection implements AutoCloseable {

 private Connection connection;

 /**
  * Create a connection to Database
  *
  * @param dbUrl      url which database belongs to
  * @param dbUsername username of the db
  * @param dbPassword password of the db
  * @throws SQLException throws when connection is not successful
  * @throws ClassNotFoundException driver not found
  */
 public DBConnection(String dbUrl, String dbUsername, String dbPassword) throws SQLException, ClassNotFoundException {
   Class.forName("org.postgresql.Driver");
   log.info("Connecting to Database: " + dbUrl);
   connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
 }

 /**
  * Closes existing connections to the target database
  */
 @Override
 public void close() {
   try {
     if (connection != null) {
       connection.close();
     }
   } catch (SQLException e) {
     log.error("Failed to close the connection", e);
   }
 }

 /**
  * Executes the given SQL statement, which returns a single ResultSet object.
  *
  * @param query requested data from db
  * @throws SQLException throws when statement is not correct
  */
 public ResultSet executeQuery(String query) throws SQLException {
   Statement statement;
   statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
   return statement.executeQuery(query);
 }

 /**
  * Result list of lists where outer list represents
  * collection of rows and inner lists represent a single row
  *
  * @param query requested data from db
  * @return returns query result list
  * @throws SQLException throws when statement is not correct
  */
 public List<List<Object>> getQueryResultList(String query) throws SQLException {
   ResultSet result = executeQuery(query);
   List<List<Object>> rowList = new ArrayList<>();
   ResultSetMetaData rsmd;
   try {
     rsmd = result.getMetaData();
     while (result.next()) {
       List<Object> row = new ArrayList<>();
       for (int i = 1; i <= rsmd.getColumnCount(); i++) {
         row.add(result.getObject(i));
       }
       rowList.add(row);
     }
   } catch (SQLException e) {
     log.error("err", e);
   }
   return rowList;
 }

 /**
  * Result List of maps where the list represents
  * collection of rows and a map represents represent a single row with
  * key being the column name
  *
  * @param query requested data from db
  * @return returns query result in a list of maps
  * @throws SQLException throws when statement is not correct
  */
 public List<Map<String, Object>> getQueryResultMap(String query) throws SQLException {
   ResultSet result = executeQuery(query);
   List<Map<String, Object>> rowList = new ArrayList<>();
   ResultSetMetaData rsmd;
   try {
     rsmd = result.getMetaData();
     while (result.next()) {
       Map<String, Object> colNameValueMap = new HashMap<>();
       for (int i = 1; i <= rsmd.getColumnCount(); i++) {
         colNameValueMap.put(rsmd.getColumnName(i), result.getObject(i));
       }
       rowList.add(colNameValueMap);
     }
   } catch (SQLException e) {
     log.error("err", e);
   }
   return rowList;
 }

 /**
  * Gets list of values of a single column from the result set
  *
  * @param query  requested data from db
  * @param column name of the column
  * @return list of values
  * @throws SQLException throws when statement is not correct
  */
 public List<Object> getColumnData(String query, String column) throws SQLException {
   ResultSet result = executeQuery(query);
   List<Object> rowList = new ArrayList<>();
   ResultSetMetaData rsmd;
   try {
     rsmd = result.getMetaData();
     rsmd.getColumnCount();
     while (result.next()) {
       rowList.add(result.getObject(column));
     }
   } catch (SQLException e) {
     log.error("err", e);
   }
   return rowList;
 }

 /**
  * Get Single cell value. If the results in multiple rows and/or
  * columns of data, only column number of the first row will be returned.
  * The rest of the data will be ignored
  *
  * @param query        requested data from db
  * @param columnNumber specific column
  * @return returns cell value.
  * @throws SQLException throws when statement is not correct
  */
 public Object getCellValue(String query, int columnNumber) throws SQLException {
   return getQueryResultList(query).get(0).get(columnNumber);
 }

 /**
  * Represents a row of data. If the query
  * results in multiple rows and/or columns of data, only first row will
  * be returned. The rest of the data will be ignored
  *
  * @param query
  * @return returns a list
  * @throws SQLException throws when statement is not correct
  */
 public List<Object> getRowList(String query) throws SQLException {
   return getQueryResultList(query).get(0);
 }

 /**
  * Represents a row of data where key is the column
  * name. If the query results in multiple rows and/or columns of data,
  * only first row will be returned. The rest of the data will be ignored
  *
  * @param query
  * @return returns a map
  * @throws SQLException throws when statement is not correct
  */
 public Map<String, Object> getRowMap(String query) throws SQLException {
   return getQueryResultMap(query).get(0);
 }
}
