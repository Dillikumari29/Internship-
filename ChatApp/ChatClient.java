import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) {
        Socket clientSocket = null;
        PrintWriter output = null;
        BufferedReader userinput = null;

        try{
            //create a client,ClientSocket
            clientSocket = new Socket("192.168.31.115",5000);
            System.out.println("Connected to server: "+ clientSocket);

            //create input output streams
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
            output = new PrintWriter(clientSocket.getOutputStream(),true);

           //Create a separate thread to read messages from the Server
           Thread readThread = new Thread (() -> {
           String message;
           try {
              while((message = input.readLine()) != null){
                System.out.println("CustomerCare: "+ message);
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

           //start communication
           userinput = new BufferedReader(new InputStreamReader(System.in));
           String message;
           while((message = userinput.readLine()) != null){
            output.println(message);
           }

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            output.close();
            try {
                userinput.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }  
    }    
}