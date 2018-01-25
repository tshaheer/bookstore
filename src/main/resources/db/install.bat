REM Script to create the BOOKSTORE database instance

echo "Recreating the whole mysql database "
mysql -u root -p -e="\. bs_init.sql"

pause;