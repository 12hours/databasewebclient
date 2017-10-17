`mvn clean package -DskipTests -P prod` assembles application into `target/app` directory.

######Project structure
```
databasewebclient   
	|-src  
	|-webapp  
```
`src/` - Java sources  
`webapp/` - Front-end application sources and configuration files

######Backend
You can run api for application in IDE using `public static void main()` method from `SurveyDemoApplicattion` class, or you can simply run `mvn spring-boot:run` from command line;
by default you can access it as [localhost:8080/api](http://localhost:8080/api)

######Frontend
In `webapp` directory you can find files with project dependencies: `package.json` and `bower.json`.
Firstly, install `npm` dependencies:  
```
	npm install
```
Then install `bower` and `gulp`:
```
	npm install -g bower
	npm install -g gulp
```
Then install `bower` dependencies:  
```
	bower install
```
After that you have choices:  
	1.  To assemble web ui run `gulp build`. This will create `dist/` directory where you can look at deploy ready application.  
	2.  Run standalone web ui by `gulp serve`. This also creates `dist/` directory and starts browser-sync server with port `:9000`.  
	3.  Run `gulp clean` to remove everything from `dist/`  
