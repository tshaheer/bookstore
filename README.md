## Bookstore
An e-commerce application using plain old JavaEE technologies. This project uses Servlets, JSP, JSTL, JDBC, HTML5, jQuery, Bootstrap, Maven and MySQL. This serves as a project to learn the Java Web Development practices.

## Features
In this site, all roads hopefully lead to a purchase. Customers enter the site, navigate to products that they want to buy, eventually check out with their shopping cart by giving payment and shipping information, and leave as happy shoppers. The customer must log in before purchasing a product.

In addition, the site should have some functionality to allow customers to maintain lists of addresses and credit cards so that they don't have to type in this information every time they visit.

## Prerequisite	
To use this sample you must have:

MySQL installed and running, if not please check out [MySQL download page](https://dev.mysql.com/downloads/installer/) and follow [these instructions](http://dev.mysql.com/doc/refman/5.7/en/installing.html).
To check your MySQL installation, run following command in command line:

```
$ mysql --version
```
Maven build tool, if not please checkout [maven installation page](https://maven.apache.org/install.html).
To check your Maven installation, run following command in command line:

```
$ mvn --version
```
## Running
In order to test the app, do following:
* Clone the repository from github.com to your workspace folder. <br /> 
`$ git clone https://github.com/tshaheer/bookstore.git`
* Application-specific JNDI Datasource is configured in context.xml (src/main/resources/META-INF/context.xml) file with username/password as root/sa; in case, it is something else, change the username/password in context.xml file.
 <br />
* Execute the script, startup.bat in classpath (src/main/resources/db) to start the server; the server starts up by populating the database using bs_init.sql.
* Note that executing startup.bat would recreate database and insert the seed data. 

Above would start the application at the port 8080. Goto browser and access URL such as **http://localhost:8080/bookstore**

## References
* [MySQL and JSP Web Applications: Data-Driven Programming Using Tomcat and MySQL by James Turner](https://flylib.com/books/en/3.378.1/)
* [The NetBeans E-commerce Tutorial](https://netbeans.org/kb/docs/javaee/ecommerce/design.html)
* [Servlets and JSP Pages Best Practices](http://www.oracle.com/technetwork/articles/java/servlets-jsp-140445.html)
* [Database Connection Pooling in Tomcat with MySQL](https://www.ntu.edu.sg/home/ehchua/programming/java/JavaWebDBApp.html)
* [Core J2EE Design Patterns](http://www.corej2eepatterns.com/)
* [Web Based Patterns](https://stackoverflow.com/questions/3541077/design-patterns-web-based-applications)

