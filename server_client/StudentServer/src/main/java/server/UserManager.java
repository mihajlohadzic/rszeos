/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.*;
import java.util.*;
import java.util.Map;
/**
 *
 * @author Mihajlo
 */
public class UserManager {
    private static final String USERS_FILE = "users.txt";
    private static Map<String, User> korisnici = new HashMap<>();
    //metoda za login
    
    static{
        ucitajKorisnike();
    }
    
    private static void ucitajKorisnike() {
        korisnici.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    korisnici.put(parts[0], new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fajl users.txt ne postoji, kreira se novi.");
            // fajl će biti kreiran prilikom dodavanja prvog korisnika
        } catch (IOException e) {
            System.err.println("Greska prilikom citanja users.txt");
        }
    }
    
    
    public static boolean login(String username, String password, String role){
        return getUser(username, password, role) != null;
    }
    
    public static void addUser(String username, String password, String role){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))){
            writer.write(username + ":" + password + ":" + role);
            writer.newLine();
        } catch (IOException e){
            System.err.println("Greska prilikom pisanja u users.txt");
        }
    }
    
    private static void upisiUFajl(User u) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(u.getUsername() + ":" + u.getPassword() + ":" + u.getRole());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Greska prilikom pisanja u users.txt");
        }
    }
    
    public static User getUser(String username, String password, String role){
        try(BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(":");
                if(parts.length == 3){
                    String u = parts[0];
                    String p = parts[1];
                    String r = parts[2];
                    
                    if(u.equals(username) && p.equals(password) && r.equalsIgnoreCase(role)){
                        return new User(u, p, r);
                    }
                   
                }
            }
        } catch (IOException e){
            System.err.println("Greska prilikom citanja fajla users.txt");
        }
        return null;
    }
    
    public static Collection<User> getAllUsers() {
        return korisnici.values();
    }
   
    
}
