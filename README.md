<a><h1><span class="octicon octicon-link"></span></a>Baltimore Vehicle Tow Web App</h1>

<p>An API and Visualization Site.</p>

<h1><a id="user-content-purpose" class="anchor" href="#purpose" aria-hidden="true"><span class="octicon octicon-link"></span></a>Purpose</h1>

<p>This site allows you to view every vehicle tow that's taken place in 
	   Baltimore City from 2014 through December 2015. The data is visualized
	   in a variety of ways, including total vehicle tows, vehicles stolen, and so on.</p>

<h1><a id="user-content-pre-installation" class="anchor" href="#pre-installation" aria-hidden="true"><span class="octicon octicon-link"></span></a>Pre-Installation</h1>

<h2> Install MongoDB</h2>
<p>Make sure brew is up to date by running brew update and make sure  MongoDB is installed globally.</p>

<pre lang="unix"><code>brew update
brew install mongodb
brew upgrade mongodb
</code></pre>

<p>Try to access the /data/db dir. If it does not exist, create one.</p>

<pre lang="unix"><code> ls /data/db
 mkdir /data 
 mkdir /data/db
 whoami
 'CURRENT_USER'
 sudo chown 'CURRENT_USER' /data/db
</code></pre>

<h2> Run MongoDB Server</h2>

<p>Open a new terminal window/tab and run mongoDB in the background.</p>

<pre lang="unix"><code>mongod
</code></pre>

<p>If you see output similar to the following, it was successful and you should leave this terminal open.</p>

<pre lang="unix"><code>2015-10-01T11:09:54.839-0400 I NETWORK  [initandlisten] connection accepted ...
2015-10-01T11:09:54.839-0400 I NETWORK  [initandlisten] connection accepted ...
2015-10-01T11:09:54.839-0400 I NETWORK  [initandlisten] connection accepted ...
2015-10-01T11:09:54.839-0400 I NETWORK  [initandlisten] connection accepted ...
2015-10-01T11:09:54.841-0400 I NETWORK  [initandlisten] connection accepted ...
</code></pre>

<h2> Restore Tow Database</h2>
<p> In order to successfully restore the tow database, the MongoDB server must be running (see previous step).</p>
<pre lang="unix"><code>cd PROJECT_ROOT/BaltimoreTowWebApp/dump 
mongorestore --db test --drop dump/test/
</pre></code>

<p> After a few moments, you should see output similar to what's seen below:</p>

<pre lang="unix"><code>...
2015-12-21T17:41:24.224-0500	finished restoring test.cars (62158 documents)
2015-12-21T17:41:24.225-0500	done
</pre></code>

<h2> Install Tomcat 7</h2>
<ul>
<li>Navigate to <a href="http://tomcat.apache.org/download-70.cgi"> http://tomcat.apache.org/download-70.cgi.</a></li>
<li>Under 'Binary Distributions', select the 'tag.gz' download link.</li>
<li>Go the file's download location and un-zip the file.</li>
<li>Rename the file to 'Tomcat' for ease of use.</li>
</ul>
<p> You may move the location of the Tomcat folder now if you wish.</p>

<h2> Install Java JRE </h2>
<p>If you do not have Java installed already, go to <a href="http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html">the Java 8 download page.</a> This will install the Java JRE. If you are a developer, it is recommended to download the <a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html" >Java JDK instead.</a> After unzipping the download, follow the installation instructions.</p>

<h1>Installation</h1>

<h2>Clone the repository </h2>

<pre lang="unix"><code>git clone https://github.com/calisven/BalitmoreTowWebApp.git
cd PROJECT_ROOT/BaltimoreTowWebApp/deployment
cp CSWebProject.war path/to/tomcat/Tomcat/webapps
</code></pre>

<p> When the Tomcat server is started, the web application will automatically be deployed.</p>

<h2> Start the Tomcat 7 Server</h2>

<pre lang="unix"><code>cd /path/to/tomcat
cd bin
chmod +x *
./startup.sh 
>> Using CATALINA_BASE:   /path/to/tomcat/Tomcat
>> Using CATALINA_HOME:   /path/to/tomcat/Tomcat
>> Using CATALINA_TMPDIR: /path/to/tomcat/Tomcat/temp
>> Using JRE_HOME: /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home
>> Using CLASSPATH:       /path/to/tomcat/Tomcat/bin/bootstrap.jar:/Users/Sven/Downloads/Tomcat/bin/tomcat-juli.jar
>>Tomcat started.
</code></pre>

<a href="http://localhost:8080/CSWebProject/#/"> <p>Go to http://localhost:8080/CSWebProject/#/.</p></a>

<h1> Miscellaneous </h1>

<p>The steps below assume the user has Eclipse installed along with the Maven Eclipse plugin. Though these steps can be done manually, using Eclipse makes this process significantly easier.  You may download Eclipse at <a href="http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/mars1">this location.</a> This will include everything needed to run a web project out of the box, along with the Maven integration.</p>

<h3>Importing the Project into Eclipse</h3>
<ol>
<li>In Eclipse, select File --> Import...</li>
<li>A dialog menu will appear. Select General --> Existing Projects into Workspace</li>
<li>Click 'Browse..' under 'select root directory'</li>
<li>In the newly opened file explorer, locate the location of the project, then find the folder containing the hidden '.project' file. In this case, it will be located in the 'BaltimoreTowWebApp' folder. Click 'Open' after locating it.
<li>Click 'Finish'. The project will now be imported.</li>
</ol>

<h3>Manually Generating a '.war' File</h3>

<p> A '.war' file can be manually generated, which can be dropped in the 'webapps' folder in Tomcat. This is required if you have made any local changes to the project that you wish to deploy.</p>
<ol>
<li>Import the project in Eclipse (see above)</li>
<li>Ensure that the project is selected in Eclipse</li>
<li>Go to File --> Export --> Web --> WAR file </li>
<li>In the next menu, select the destination folder. The destination folder should be the location of your Tomcat installation, in the 'webapps' folder. Select 'Finish'.</li>
<li>The '.war' file is now ready to deploy. Open the Terminal and go to the Tomcat installation location, then the 'bin' location. Run './startup.sh'.</li>
<li>Go to<a href="http://localhost:8080/CSWebProject/#/"> http://localhost:8080/CSWebProject/#/.</a></li>
</ol>

<h3> Running the Project's Test Suite </h3>

<p>If you wish to run the project's Test Suite manually, open Eclipse and import the project. After importing the project, right click the project and select 
Run As --> JUnit Test.</p>
