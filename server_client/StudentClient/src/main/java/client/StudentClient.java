/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.List;
/**
 *
 * @author Mihajlo
 */
public class StudentClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        
        try(
            Socket socket = new Socket(host, port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
                ){
                System.out.println("Povezan na server.");
                
                String serverMsg;
                
                serverMsg = (String) in.readObject();
                System.out.println(serverMsg);
                String username = scanner.nextLine();
                out.writeObject(username);
                
                serverMsg = (String) in.readObject();
                System.out.println(serverMsg);
                String password = scanner.nextLine();
                out.writeObject(password);
                
                serverMsg = (String) in.readObject();
                System.out.println(serverMsg);
                String uloga = scanner.nextLine();
                out.writeObject(uloga);
                
                serverMsg = (String) in.readObject();
                System.out.println("Server: " + serverMsg);
                
                if(serverMsg.startsWith("LOGIN_FAIL: Neispravni podaci")){
                    System.out.println("Konekcija zatvorena.");
                    return;
                }
                
               /* System.out.println("Dobrodosli na studentski sistem!");
                System.out.println("Cekam podatke od servera!");
               */
               
                if(uloga.equalsIgnoreCase("admin")){
                    adminMeni(scanner, out, in);
                } else if(uloga.equalsIgnoreCase("student")){
                    studentMeni(scanner, out, in);
                }
                
              
            }  catch (IOException e){
                    System.out.println("Greska u komunikaciji sa serverom: " + e.getMessage());
                    } catch (ClassNotFoundException e){
                            System.out.println("Nepoznat tip objekta primljen od servera.");
                            }
    }
        private static void adminMeni(Scanner scanner, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException{
            while(true){
                System.out.println("\n ***ADMIN MENI***");
                System.out.println("1. Dodaj studenta");
                System.out.println("2. Dodaj predmet");
                System.out.println("3. Ispis studenata");
                System.out.println("4. Dodeli predmet studentu i unesi poene");
                System.out.println("5. Izvestaj o studentima");
                System.out.println("6. Izlaz");
                System.out.print("Izbor: ");
                
                String izbor = scanner.nextLine();
                out.writeObject(izbor);
                
                if(izbor.equals("6")){
                    System.out.println("Odjava u toku");
                    break;
                }
                
                /*if(izbor.equals("4")){
                    String serverMsg = (String) in.readObject();
                    System.out.print(serverMsg);
                    String username = scanner.nextLine();
                    out.writeObject(username);
                    
                    serverMsg = (String) in.readObject();
                    System.out.print(serverMsg);
                    String predmet = scanner.nextLine();
                    out.writeObject(predmet);
                    
                    serverMsg = (String) in.readObject();
                    int brojKategorija = Integer.parseInt(serverMsg);
                    
                    for(int i = 0; i < brojKategorija; i++){
                        serverMsg = (String) in.readObject();
                        System.out.print(serverMsg);
                        String poeni = scanner.nextLine();
                        out.writeObject(poeni);
                    }
                    
                    serverMsg = (String) in.readObject();
                    System.out.println("Server: " + serverMsg);
                    
                    serverMsg = (String) in.readObject();
                    if(serverMsg.equals("KRAJ")){
                        System.out.println("Unos zavrsen.");
                    }
                    continue;
                }*/
                
                handleServerResponses(scanner, out, in);
                /*String odgovor = (String) in.readObject();
                System.out.println("Server: " + odgovor);
                */
                }
        }
        
        private static void studentMeni(Scanner scanner, ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException{
            while(true){
                System.out.println("\n=== STUDENT MENI ===");
                System.out.println("1. Pogledaj ocene");
                System.out.println("2. Pogledaj predmete");
                System.out.println("3. Izlaz");
                System.out.print("Izbor: ");

                String izbor = scanner.nextLine();
                out.writeObject(izbor);
                
                if(izbor.equals("3")){
                    System.out.println("Odjava!");
                    break;
                }
                
                handleServerResponses(scanner, out, in);
                /*String odgovor = (String) in.readObject();
                System.out.println("Server: " + odgovor);
                */
            }
            
        }
        
        private static void handleServerResponses(Scanner scanner, ObjectOutputStream out, ObjectInputStream in)
                    throws IOException, ClassNotFoundException{
                        while(true){
                            String odgovor = (String) in.readObject();
                            
                            if(odgovor.startsWith("KRAJ")){
                                break;
                            }
                            
                            System.out.println("Server: " + odgovor);
                            
                            /*if(odgovor.endsWith(":")){
                                String unos = scanner.nextLine();
                                out.writeObject(unos);
                                out.flush();
                            }*/
                            
                            if (odgovor.contains("Unesite username studenta")) {
                                String username = scanner.nextLine();
                                out.writeObject(username);
                                out.flush();
                            } 
                            else if (odgovor.contains("Unesite naziv predmeta")) {
                                String predmet = scanner.nextLine();
                                out.writeObject(predmet);
                                out.flush();
                            
                             /*System.out.println("Unesite broj kategorija");
                                int brojKategorija = Integer.parseInt(scanner.nextLine());
                                out.writeObject(brojKategorija);
                                out.flush();

                                // odmah unosimo poene za svaku kategoriju
                                for (int i = 1; i <= brojKategorija; i++) {
                                    System.out.print("Poeni za kategoriju " + i + ": ");
                                    int poeni = Integer.parseInt(scanner.nextLine());
                                    //out.writeObject(poeni);
                                    out.writeObject(String.valueOf(poeni));
                                    out.flush();
                                }
                            */
                             Object obj = in.readObject();
                             if(obj instanceof List<?> kategorijeList){
                                 for(Object k : kategorijeList){
                                     String kat = (String) k;
                                     System.out.print("Poeni za kategoriju " + kat + ": ");
                                     int poeni = Integer.parseInt(scanner.nextLine());
                                     out.writeObject(String.valueOf(poeni));
                                     out.flush();
                                 }
                             }
                             while(true){
                                 String msg = (String) in.readObject();
                                 if(msg.equals("KRAJ")) break;
                             }
                            }
                            //PROMENA
                            else if(odgovor.endsWith(":")){
                                String unos = scanner.nextLine();
                                out.writeObject(unos);
                                out.flush();
                            }    
                            
                        }
        }
                
    }
