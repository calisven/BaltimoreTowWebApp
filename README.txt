Steps to deploy



Restore Mongo Collection

1. On the terminal, navigate to 'PROJECT_ROOT/dump/test/'.
2. This location has the 'cars.bson' file needed to import the mongodb needed to pull data to the
   website.
2. On the terminal, run 'mongorestore —collection test —db NAMEOFDATABASE PATH_TO_BSON/cars.bson'

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
