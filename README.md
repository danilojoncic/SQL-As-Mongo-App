# SQL-As-Mongo-App

SQL-As-Mongo-App is a simple Java Swing application that allows users to input SQL queries into a text field. The app translates these SQL queries into MongoDB-compatible queries using SQL object creation, validation, and adaptation. The resulting MongoDB queries are then executed, and the output is presented in a table in the lower part of the GUI.

## Features

- Input SQL queries and translate them into MongoDB-compatible queries.
- Validate the SQL queries before converting them into MongoDB format.
- Utilize Singleton, Adapter and Composite design patterns for efficient query conversion.
- Display the query results in a table for easy visualization.

## Query Keywords & Operators

- SELECT
- FROM
- WHERE
- JOIN (works for joining 2 tables/collections, joining more than 2 will not work)
- USING
- ORDER BY
- ASC (by default)
- DESC
- AND
- OR (logic works for infinite 'AND' or 'OR' operators, but they must not be mixed in the same query, priority and boolean logic is not present)
- LIKE with '$' and '_'
- > , < , = , >=, <=

EXAMPLE QUERY:
 SELECT first_name 
        salary 
        department_name
 FROM employees 
 JOIN departments 
 USING department_id 
 WHERE salary >= 10000 
 AND department_name LIKE %S 
 ORDERBY salary DESC

## Prerequisites

- Java 8 or higher installed on your system.
- MongoDB server installed and running on localhost (or the desired server address).

## How to Use

1. Clone the repository:

```bash
git clone https://github.com/your-username/SQL-As-Mongo-App.git
```

2. Open the project in your favorite Java IDE (e.g., IntelliJ IDEA, Eclipse).

3. Ensure that you have MongoDB installed and running on the local machine or specify the MongoDB server address in the app settings. Make sure to import the .json files from the mongoDocs folder into your MongoDB

4. Configure MongoDB connection settings in `Credentials` class if necessary. Replace the placeholders with your MongoDB server credentials:

```java
public class Credentials {
    ...
    public static final String DATABASE = "your_database_name";
    public static final String USERNAME = "your_mongodb_username";
    public static final String PASSWORD = "your_mongodb_password";
    // Update other settings as needed.
    ...
}
```

5. Build the project and resolve any dependencies if required.

6. Run the application and a GUI window will open.

7. Enter your SQL query in the provided text field.

8. Click the "Excecute" button to convert the SQL query into a MongoDB-compatible query.

9. The app will validate and adapt the SQL query using `.jsonify` methods within each clause of a query and transform them into json like documents which can be sent in the mongo acceptable BSON format and then executed on the MongoDB server.

10. The results will be displayed in a table below the input field.

## Design Patterns Used

- **Singleton**: Singleton pattern is used to ensure that there is only one instance of the MongoDB database connection throughout the application's lifecycle. The `CustomMongoDatabase` class employs this pattern to provide a single point of access for connecting to the MongoDB server.

- **Adapter**: The Adapter pattern is utilized to adapt and transform the SQL queries into MongoDB-compatible queries. The `AdapterMDB` class acts as the adapter, converting SQL objects into MongoDB objects through `.jsonify`.

- **Composite**: Composite is used for easier and logical representation of a query as an object made up of clauses where each clause has its own elements, this approach makes it possible for subqueries to be implemented and used.
