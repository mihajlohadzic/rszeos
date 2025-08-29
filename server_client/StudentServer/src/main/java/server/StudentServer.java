/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package server;


import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Mihajlo
 */
public class StudentServer {

    private static List<String> studenti = new ArrayList<>();
    private static List<String> predmeti = new ArrayList<>();
    
    public static void main(String[] args) {
        
        int port = 12345;
        System.out.println("Server pokrenut na portu " + port);
        
        DataStore dataStore = new DataStore();
      //  dataStore.init();
        
        try(ServerSocket serverSocket = new ServerSocket(port)){
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Klijent je povezan");
                
                new Thread(new ClientHandler(socket, dataStore)).start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
            
        }
    
    /*static class ClientHandler implements Runnable{
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        
        public ClientHandler(Socket socket){
            this.socket = socket;
        }
        
    @Override
    public void run(){
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            
            out.writeObject("Unesite korisnicko ime: ");
            String username = (String) in.readObject();
            
            out.writeObject("Unesite lozinku: ");
            String password = (String) in.readObject();
            
            out.writeObject("Unesite ulogu (admin/student): ");
            String uloga = (String) in.readObject();
            
            if(username.equals("admin") && password.equals("admin") && uloga.equalsIgnoreCase("admin")){
                out.writeObject("Uspesno logovanje kao admin!");
                adminMeni();
            } else if(uloga.equalsIgnoreCase("student")){
                out.writeObject("Uspesno logovanje kao student!");
                studentMeni();
            } else{
                out.writeObject("Neuspesno logovanje!!!");
            }
        
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Greska: " + e.getMessage());
        } finally{
            try{
                if(socket != null) socket.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    
    */
    
    }
    

