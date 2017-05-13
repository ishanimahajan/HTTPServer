package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class WebServer{

	protected int portNumber;
	protected int numThreads;
  	protected ServerSocket serverSocket = null;
  	protected ExecutorService threadPool = null;

	public WebServer(){
    	
    	this.portNumber = 8080;
		this.numThreads = 10;
		try{
			this.threadPool = Executors.newFixedThreadPool(this.numThreads);
		}catch (Exception e){
			System.out.println("Error during creation of thread pool"+ e);
			System.exit(1);
		}

		try {
			this.serverSocket = new ServerSocket(this.portNumber);
		}catch (IOException e) {
			System.out.println(this.portNumber+" Port already in use");
			System.exit(1);
		}catch (Exception e){
			System.out.println("Exception during creation of Server socket"+e);
			System.exit(1);
		}

  	}

	/*constructor with arguments (port number, number of threads)*/
	public WebServer(int port, int nThreads){
   
    	this.portNumber = port;
    	this.numThreads = nThreads;
		try{
			this.threadPool = Executors.newFixedThreadPool(this.numThreads);
		}catch (Exception e){
			System.out.println("Error during creation of thread pool"+e);
			System.exit(1);
		}

		try {
			this.serverSocket = new ServerSocket(this.portNumber);
		}catch (IOException e) {
			System.out.println(this.portNumber+" Port already in use");
			System.exit(1);
		}catch (Exception e){
			System.out.println("Exception during creation of Server socket");
			System.exit(1);
		}

  	}

    public static void main(String[] args) {
	
		WebServer server = null;
		final int privilegedPortRange = 1024;
		
		/*call webserver with default constructor if (num of arguments)<2 or the port number is within the privileged port range*/
		if(args.length < 2 || Integer.parseInt(args[0]) <= privilegedPortRange || Integer.parseInt(args[1]) <= 0){
			server = new WebServer();
		}
		else{
			int port =  Integer.parseInt(args[0]);
			int numThreads = Integer.parseInt(args[1]);
    			server = new WebServer(port, numThreads);
		}
		
		System.out.println("Server is listening on port: "+ server.portNumber+" !!!!!!!!!!!");
		System.out.println("Number of threads available: "+ server.numThreads);
  		Socket clientSocket = null;
 
  		/*Process HTTP service requests in an infinite loop*/
    	while(true){
    		try{
    			clientSocket = server.serverSocket.accept();
				server.threadPool.execute(new Worker(clientSocket));
    		}catch (IOException e){
    			System.out.println("Error accepting client connection ");
			System.exit(1);
    		}catch(Exception e){
			System.out.println("Error accepting client connection or executing task");
			System.exit(1);
		}
    	}
    }

}
