/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.*;
import java.util.*;

/**
 *
 * @author Mihajlo
 */
public class DataStore implements Serializable {
    private static final String STUDENTI_FILE = "studenti.dat";
    private static final String PREDMETI_FILE = "predmeti.dat";
    private static final String USERS_FILE = "users.txt";
    
    private static Map<String, String[]> korisnici = new HashMap<>();
    private static List<Student> studenti = new ArrayList<>();
    private static List<Predmet> predmeti = new ArrayList<>();
    
    static{
        ucitajKorisnike();
        ucitajStudente();
        ucitajPredmete();
    }
    //Studenti
    public static void addStudent(Student s) throws IOException{
        studenti.add(s);
        sacuvajStudente();
    }
    
    public static Student getStudent(String username){
        for(Student s : studenti){
            if(s.getUsername().equals(username)) return s;
        }
        return null;
    }
    
    public static List<Student> getStudenti(){
        return studenti;
    }
    
    public static void sacuvajStudente() throws IOException{
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STUDENTI_FILE))){
            oos.writeObject(studenti);
            } catch (IOException e){
                System.err.println("Greska prilikom cuvanja studenta: " + e.getMessage());
            }
    }
    
    private static void ucitajStudente(){
        File f = new File(STUDENTI_FILE);
        if(!f.exists()) return;
        
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
            studenti = (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Greska prilikom ucitavanja studenata: " + e.getMessage());
        }
    }
    
    //PREDMETI
    public static void addPredmet(Predmet p) throws IOException{
        predmeti.add(p);
        sacuvajPredmete();
    }
    
    public static Predmet getPredmet(String naziv){
        for(Predmet p : predmeti){
            if(p.getNaziv().equalsIgnoreCase(naziv)){
                return p;
            }
        }
        return null;
    }
    
    public static void pregledSvihPredmeta() {
        for (Predmet p : predmeti) {
            System.out.println(p.pregledKategorija());
        }
    }
    
    public static void pregledSvihStudenata() {
        for (Student s : studenti) {
            System.out.println(s);
        }
    }
    
    public static List<Predmet> getPredmeti(){
        return predmeti;
    }
    
    private static void sacuvajPredmete() throws IOException{
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PREDMETI_FILE))){
            oos.writeObject(predmeti);
        } catch (IOException e){
            System.err.println("Greska prilikom cuvanja predmeta: " + e.getMessage());
        }
    }
    
    private static void ucitajPredmete(){
        File f = new File(PREDMETI_FILE);
        if(!f.exists()) return;
        
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
            predmeti = (List<Predmet>) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.err.println("Greska prilikom ucitavanja predmeta: " + e.getMessage());
        }
    }
    
    public static void addKorisnik(String username, String password, String uloga){
        korisnici.put(username, new String[]{password, uloga});
      
        sacuvajKorisnike();
    }
    
    public static boolean validirajKorisnika(String username, String password, String uloga){
        if(!korisnici.containsKey(username)) return false;
        String[] data = korisnici.get(username);
        return data[0].equals(password) && data[1].equalsIgnoreCase(uloga);
    }
    
    private static void ucitajKorisnike(){
        File f = new File(USERS_FILE);
        if(!f.exists()) return;
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(":");
                if(parts.length == 3){
                    korisnici.put(parts[0], new String[]{parts[1], parts[2]});
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private static void sacuvajKorisnike(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))){
            for(Map.Entry<String, String[]> entry : korisnici.entrySet()){
                bw.write(entry.getKey() + ":" + entry.getValue()[0] + ":" + entry.getValue()[1]);
                
                bw.newLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
}
