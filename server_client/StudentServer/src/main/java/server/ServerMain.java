/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author Mihajlo
 */
public class ServerMain {
    
    public static void main(String[] args){
        int port = 12345;
        
       // DataStore.init();
        DataStore dataStore = new DataStore();
        
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server je pokrenut na portu " + port);
            
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Klijent se povezao: " + clientSocket);
                        
                ClientHandler handler = new ClientHandler(clientSocket, dataStore);
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
