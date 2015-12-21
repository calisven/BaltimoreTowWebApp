Steps to deploy

MongoDB Installation (OSX):

1. Follow the instructions found here exactly: http://www.mkyong.com/mongodb/how-to-install-mongodb-on-mac-os-x/
   The version shown in those instructions is old; replace the mongo version you download in the command line steps from
   the instructions to have it work properly.

Restore Mongo Collection

1. On the terminal, navigate to 'PROJECT_ROOT/dump'.
2. This location has the 'cars.bson' file needed to import the mongodb needed to pull data to the
   website.
3. On a separate terminal tab, run 'mongod' to start the MongoDB server
4. NOTE: Replace the '--db test' with any database name you wish to use. 
   On the terminal, run 'mongorestore --db test --drop dump/test/'
5. After a few moments, you should see:
   "...
   2015-12-21T17:41:24.224-0500	finished restoring test.cars (62158 documents)
   2015-12-21T17:41:24.225-0500	done"
6. The database is not imported. 


Validate Collection Was Imported

1. On the terminal, run 'mongo —-shell'
2. Run ‘show collections’, ensure that the ‘cars’ collection is there

Start Mongo DB server

1. On the terminal, run ‘mongod’ to start the server

Deploy The Website

The '.war' file was deployed on a Tomcat 7.0 server, by dropping the '.war' file
in the Tomcat 'webapps' folder (should auto-deploy). However, you may use any deployment server
of your choosing. After deploying the website, open a browser and navigate to
‘http://localhost:8080/CSWebProject/#/'. 

You may also simply import the .war file in Eclipse (with the Tomcat 7.0 plugin installed) then 
right-click the imported projected --> Run As --> Run On Server
