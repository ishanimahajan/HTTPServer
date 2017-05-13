package Server;

import java.util.*;
import java.io.*;
import java.net.*;

public class Worker implements Runnable{

    protected Socket clientSocket = null;
    final static String CRLF = "\r\n";

    public Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /*To determine the MIME type*/
    private static String contentType(String filename){

        String mimeType="text/plain";

        if (filename.endsWith(".html") || filename.endsWith(".htm"))
            mimeType="text/html";
        else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg"))
            mimeType="image/jpeg";
        else if (filename.endsWith(".gif"))
            mimeType="image/gif";
        else if (filename.endsWith(".class"))
            mimeType="application/octet-stream";


    return mimeType;

    }

    /*Send file contents to client*/
    private static void sendBytes(FileInputStream fis, OutputStream os)
    throws Exception
    {
        // Construct a 1K buffer to hold bytes.
        byte[] buffer = new byte[1024];
        int bytes = 0;

        // Copy the file into the socket's output stream.
        while((bytes = fis.read(buffer)) != -1 ) {
            os.write(buffer, 0, bytes);
        }
    }


    public void run() {
        String fileName = "index.html";
    	try {

    		//Open Input Stream
      		InputStream is = clientSocket.getInputStream();
      		InputStreamReader isr = new InputStreamReader(is);
      		BufferedReader in = new BufferedReader(isr);

      		//Open Output Stream
      		OutputStream os1 = clientSocket.getOutputStream();
          DataOutputStream os = new DataOutputStream(os1);

      		//Read the request line of http request message
      		String req = in.readLine();
      		System.out.println(req);  // Log the request

      		try {
          		// Extract the filename from the request line.
          		StringTokenizer tokens = new StringTokenizer(req);
          		// skip over the method, which should be "GET"
          		tokens.nextToken();
          		fileName = tokens.nextToken();
          		if(fileName.equals(new String("/"))){
          			os.writeBytes("HTTP/1.0 404 Not Found\r\n"+
              				"Content-type: text/html\r\n\r\n"+
              				"<html><head></head><body>Welcome to Multithreaded Server.</body></html>\n");
          			os.close();
          			return;
          		}

          		fileName = fileName.substring(1);

        		// Check for illegal characters to prevent access to superdirectories
        		if (fileName.indexOf("..")>=0 || fileName.indexOf(':')>=0 || fileName.indexOf('|')>=0)
          		throw new FileNotFoundException();

          		FileInputStream fis = null;
              String filePaths = "./fileLib/";
          		fis = new FileInputStream(filePaths + fileName);

          		// Construct the response message.
          		String statusLine = null;
          		String contentTypeLine = null;
              	statusLine = "HTTP/1.0 200 OK\r\n";
              	contentTypeLine = "Content-type: " + contentType( fileName ) + CRLF;
          		// Send the status line.
          		os.writeBytes(statusLine);
          		// Send the content type line.
          		os.writeBytes(contentTypeLine);
          		// Send a blank line to indicate the end of the header lines.
          		os.writeBytes(CRLF);
          		sendBytes(fis, os);
          		fis.close();
          		os.close();

      		}catch (FileNotFoundException x) {
      			/*return an HTTP error "404 Not Found"*/
        		os.writeBytes("HTTP/1.0 404 Not Found\r\n"+
          				"Content-type: text/html\r\n\r\n"+
          				"<html><head></head><body>"+fileName+" not found</body></html>\n");
        		os.close();

      		}catch(Exception e){
      			/*return an HTTP error "404 Not Found"*/
          		os.writeBytes("HTTP/1.0 404 Not Found\r\n"+
                        "Content-type: text/html\r\n\r\n"+
                        "<html><head></head><body>"+fileName+" not found</body></html>\n");
          		os.close();
      		}
    	}catch (IOException x) {
      		System.out.println(x);
    	}
    }
}
