/*
 * Written by Craig Poirier and Eric Luong
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

class ServerThread extends Thread {
	private Socket socket;
	 
    public ServerThread(Socket socket) {
        this.socket = socket;
        
        try (        		
        		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);//to client
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//from client        		
        	)
        {     	      	
        	String fromClient, toClient;
        	StringBuilder stdOutput = null;
        		
        	while ((fromClient = in.readLine()) != null)
        	{   				
        		switch (fromClient.toLowerCase())
        		{
        		case "date and time":
        			Date date = new Date();
        			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        			out.println("From Server: " + formatter.format(date) + "\n");
        			out.println("Command Completed");
        			break;
        			
        		case "uptime":
        			Process p = Runtime.getRuntime().exec("uptime");		
        			stdOutput = new StringBuilder();
        			BufferedReader stdIn = new BufferedReader(new InputStreamReader(p.getInputStream()));
        			toClient = null;
        			while ((toClient = stdIn.readLine()) != null)
        			{
        				stdOutput.append("From Server: " + toClient + "\n");
        			}
        			out.println(stdOutput);
        			out.println("Command Completed");
        			break;
        			
        		case "memory use":
        			Process r = Runtime.getRuntime().exec("free");		
        			stdOutput = new StringBuilder();
        			BufferedReader rIn = new BufferedReader(new InputStreamReader(r.getInputStream()));
        			toClient = null;
        			while ((toClient = rIn.readLine()) != null)
        			{
        				stdOutput.append("From Server: \n" + toClient + "\n");
        			}
        			out.println(stdOutput);
        			out.println("Command Completed");
        			break;
        			
        		case "netstat":
        			Process s = Runtime.getRuntime().exec("netstat");		
        			stdOutput = new StringBuilder();
        			BufferedReader sIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        			toClient = null;
        			while ((toClient = sIn.readLine()) != null)
        			{
        				stdOutput.append("From Server: \n" + toClient + "\n");
        			}
        			out.println(stdOutput);
        			out.println("Command Completed");
        			break;
        			
        		case "current users":
        			Process t = Runtime.getRuntime().exec("who");		
        			stdOutput = new StringBuilder();
        			BufferedReader tIn = new BufferedReader(new InputStreamReader(t.getInputStream()));
        			toClient = null;
        			while ((toClient = tIn.readLine()) != null)
        			{
        				stdOutput.append("From Server: \n" + toClient + "\n");
        			}
        			out.println(stdOutput);
        			out.println("Command Completed");
        			break;
        			
        		case "running processes":
        			Process q = Runtime.getRuntime().exec("ps -ef");		
        			stdOutput = new StringBuilder();
        			BufferedReader qIn = new BufferedReader(new InputStreamReader(q.getInputStream()));
        			toClient = null;
        			while ((toClient = qIn.readLine()) != null)
        			{
        				stdOutput.append("From Server: \n" + toClient + "\n");
        			}
        			out.println(stdOutput);
        			out.println("Command Completed");
        			break;
        			 		
        		case "quit":
        			socket.close();
        			break;
        			
        		default:
        			out.println("Enter a valid entry!");
        			out.println("Command Completed");
        			break;
        		     		
        		}
        		
        		
        		   		   		
        			}
        	System.out.println("Client Disconnected.  Session Terminated");
        	               
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    }
    
public class MultiThreadServer {

	public static void main(String[] args) throws Exception {
		if (args.length < 1) return;
		 
        int port = Integer.parseInt(args[0]);
 
        try (ServerSocket serverSocket = new ServerSocket(port))
        {      	
            System.out.println("Server is listening on port " + port);
            
            	while (true)
            	{
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");        
                new ServerThread(socket).start();
            	}
           
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }

	}
}

