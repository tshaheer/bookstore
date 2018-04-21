REM Script to create the BOOKSTORE database instance

echo "Recreating the whole mysql database "
mysql -u root -p -e="\. bs_init.sql"

rem navigate back to maven root folder
cd ..
cd ..
cd ..
cd ..
mvn clean tomcat7:run

PAUSE;