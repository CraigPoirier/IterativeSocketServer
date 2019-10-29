/*
 * Written by Craig Poirier and Eric Luong
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiThreadClient {

	public static void main(String[] args) throws IOException {
		if (args.length < 2) return;
		 
    	String hostname = "139.62.210.153";
        int port = Integer.parseInt(args[0]);
        int users = Integer.parseInt(args[1]);
         
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));//client input
        
        System.out.println("Commands:  Date and Time | Uptime | Memory Use | Netstat | Current Users | Running Process | Quit");
		
		System.out.print("Choose command: ");
		String command = stdIn.readLine();    		
	      
        Socket[] userAry = new Socket[users];
        long[][] times = new long[users][3];
        
        for (int i = 0; i < users; i++)
        {
        	
        	try {
				userAry[i] = new Socket(hostname,port);
					
				PrintWriter out = new PrintWriter(userAry[i].getOutputStream(), true);//to server
		    	BufferedReader in = new BufferedReader(new InputStreamReader(userAry[i].getInputStream()));//from server
		    	        		
		    		times[i][0] = System.currentTimeMillis();
	        		out.println(command);
	        		
	        		String line = "";
	        		
	        		while(!line.equals("Command Completed") ) 
	        		{
	        	    	line = in.readLine();
	        	    	System.out.println(line);
	        		}
	        	   
	        		times[i][1] = System.currentTimeMillis();
	        	
	            userAry[i].close();
	            System.out.println("Session Terminated");
		    	
				
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        for (int rows = users - 1; rows >= 0; rows--)
        {
        	times[rows][2] = times[rows][1] - times[rows][0];   	
        }
        
        for (int rows = users - 1; rows >= 0; rows--)
        {
        	System.out.println("TurnAroundTime of Client #" + rows + ": " + times[rows][2] + " ms");   	
        }
        
        long total = 0;
        for (int rows = users - 1; rows >= 0; rows--)
        {
        	total = total + times[rows][2];    	
        }
        
        System.out.println("Total Turnaround Time: " + total);
        
        long avg = total/users;
        
        System.out.println("AVG Turnaround Time: " + avg);     

	}

}
