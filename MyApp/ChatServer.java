import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) {
      ServerSocket serverSocket = null;
      Socket clientSocket = null;
      PrintWriter output =null;
      BufferedReader userinput=null;
     
      try {
           //Create server socket
           serverSocket = new ServerSocket(5000);
           System.out.println("Server started. Waiting for clients...");

           //Accept Client Connections
           clientSocket = serverSocket.accept();
           System.out.println("Client connected: " +clientSocket);
           
           //Create input output streams
           BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           output = new PrintWriter(clientSocket.getOutputStream(),true);

           //create a separate thread to read messages from the client
           Thread readThread = new Thread(() -> {
              String message;
              try{
                while ((message = input.readLine()) != null) {
                    System.out.println("User:" + message);
                }
              } catch (IOException e) {
                e.printStackTrace();
              } finally {
                 try{
                   input.close();
                 }catch(IOException e){
                   throw new RuntimeException(e);
                 }
                }
            });
            readThread.start();
  
            userinput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = userinput.readLine()) != null) {
               output.println(message);
            }  
      }catch(IOException e){
        e.printStackTrace();
      }finally{
          //close connections
          output.close();
           try {
              userinput.close();
              clientSocket.close();
              serverSocket.close();
           } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } 
    }    

} 