# HTTPServer
Multithreaded Webserver (file-based) with thread-pooling implemented in Java.

Steps to run the code:
1. Using makefile:

   1.1 cd into HTTPServer/src directory.

   1.2 To build the server, type make.
   
   1.3 To run the server, type make run.
   
   1.4 To clean the class files, type make clean.
   
   1.5 To clean and build simultaneously, type make clean build.

2. Building directly without make files:
   
   2.1 cd into HTTPServer directory.
   
   2.2 To build, type javac -d bin/ src/*.java .
   
   2.3 To run , go to bin directory and type java Server.WebServer [port-number][num_threads].


3. Run client with following different testcases :
   
   3.1 Go to browser and type http://localhost:port. This will open the default page.
   
   3.2 Go to browser and type http://localhost:port/Adobe_Page.html .This will open specified html page.
   
   3.3 Go to browser and type http://localhost:port/adobe.png. This will open the specified png file.
   
   3.4 Try running multiple clients simultaneously witn GET request with different file formats.
   
   3.5 Go to browser and type http://localhost:port/random.html. This will give "File not found" as this file is not present.
