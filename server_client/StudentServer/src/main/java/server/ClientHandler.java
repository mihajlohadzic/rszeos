/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Mihajlo
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    private DataStore dataStore;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    public ClientHandler(Socket socket, DataStore dataStore){
        this.socket = socket;
        this.dataStore = dataStore;
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        try{
            //User loggedUser = null;
            
            //while(loggedUser == null){
            out.writeObject("Unesite username: ");
            String username = (String) in.readObject();
            
            out.writeObject("Unesite password: ");
            String password = (String) in.readObject();
            
            out.writeObject("Unesite ulogu (admin/student): ");
            String uloga = (String) in.readObject();
            
            //User loggedUser = UserManager.getUser(username, password, uloga);
            User loggedUser = UserManager.getUser(username, password, uloga);
            
            if(loggedUser == null){
                out.writeObject("LOGIN_FAIL: Neispravni podaci");
                socket.close();
                return;
            }
            
            out.writeObject("LOGIN_OK: Uspesno logovanje kao " + uloga);
            
            if(loggedUser.getRole().equalsIgnoreCase("admin")){
                    handleAdmin();
                } else if(loggedUser.getRole().equalsIgnoreCase("student")){
                    handleStudent(loggedUser.getUsername());
                  }
                
            
                /*out.writeObject("LOGIN_FAIL: Neispravni podaci");
                out.flush();
                out.writeObject("Zelite li da pokusate ponovo? (da/ne)");
                out.flush();
                String choice = ((String) in.readObject()).trim().toLowerCase();
                if(!choice.equals("da")){
                    out.writeObject("Konekcija se zatvara...");
                    socket.close();
                    return;
                }      
            } else{
                out.writeObject("LOGIN_OK: Uspesno logovanje kao " + loggedUser.getRole()); //+uloga
                if(loggedUser.getRole().equalsIgnoreCase("admin")){
                    handleAdmin();
                } else if(loggedUser.getRole().equalsIgnoreCase("student")){
                    handleStudent(loggedUser.getUsername());
                  }
                }*/
            
            //}    
        }   catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                    } finally{
                            try{
                                socket.close();
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                            }
        }
    
        private void handleAdmin() throws IOException, ClassNotFoundException{
            boolean running = true;
            
                while(running){
                    
                    String command = (String) in.readObject();
                    
                    switch(command){
                        case "1":
                            out.writeObject("Ime:");
                            out.flush();
                            String ime = (String) in.readObject();
                            
                            out.writeObject("Prezime:");
                            out.flush();
                            String prezime = (String) in.readObject();
                            
                            out.writeObject("Broj indeksa (primer: E1-2024):");
                            out.flush();
                            String brojIndeksa = (String) in.readObject();
                            
                            out.writeObject("JMBG:");
                            out.flush();
                            String jmbg = (String) in.readObject();
                            
                            out.writeObject("Username:");
                            out.flush();
                            String newUsername = (String) in.readObject();
                            
                            out.writeObject("Password:");
                            out.flush();
                            String newPassword = (String) in.readObject();
                            
                            try{
                                Student s = new Student(ime, prezime, brojIndeksa, jmbg, newUsername, newPassword);
                                DataStore.addStudent(s);
                                UserManager.addUser(newUsername, newPassword, "student");
                                out.writeObject("Student dodat!");
                                
                            } catch(IllegalArgumentException e){
                                out.writeObject("Greska: " + e.getMessage());
                                
                            }
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                            
                        case "2":
                            
                            out.writeObject("Naziv predmeta:");
                            out.flush();
                            String naziv = (String) in.readObject();

                            out.writeObject("Unesite kategorije i poene u formatu K1:poeni,K2:poeni,...:");
                            out.flush();
                            String kategorijeStr = (String) in.readObject();

                            Predmet p = new Predmet(naziv);
                            boolean greska = false;
                            int ukupnoPoena = 0;
                            
                            String[] parts = kategorijeStr.split(",");
                            for (String part : parts) {
                                String[] k = part.split(":");
                                if (k.length != 2) {
                                    greska = true;
                                    break;
                                }
                                try {
                                    int poeni = Integer.parseInt(k[1].trim());
                                    p.dodajKategoriju(k[0].trim(), poeni);
                                    ukupnoPoena += poeni;
                                } catch (NumberFormatException e) {
                                    greska = true;
                                    break;
                                }
                            }

                            if (greska) {
                                out.writeObject("Neispravan format unosa kategorija!");
                                out.writeObject("KRAJ");
                                out.flush();
                                break;
                            }
                            
                            if(ukupnoPoena != 100){
                                out.writeObject("Ukupno poena mora biti 100!");
                                out.writeObject("KRAJ");
                                out.flush();
                                break;
                            }

                            DataStore.addPredmet(p);
                            out.writeObject("Predmet dodat!");
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                           
                            
                        case "3":
                            StringBuilder sb = new StringBuilder("Lista studenata:\n");
                            for (Student s : DataStore.getStudenti()) {
                                sb.append(s).append("\n");
                            }
                            out.writeObject(sb.toString());
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                            
                        case "4":
                            
                            out.writeObject("Unesite username studenta: ");
                            out.flush();
                            String studentUsername = (String) in.readObject();
                            Student student = DataStore.getStudent(studentUsername);

                            if(student == null){
                                out.writeObject("Ne postoji student sa tim username-om");
                                out.writeObject("KRAJ");
                                out.flush();
                                break;
                            }

                            out.writeObject("Unesite naziv predmeta: ");
                            out.flush();
                            String predmetNaziv = (String) in.readObject();
                            Predmet predmet = DataStore.getPredmet(predmetNaziv);

                            if(predmet == null){
                                out.writeObject("Predmet ne postoji!");
                                out.writeObject("KRAJ");
                                out.flush();
                                break;
                            }

                            student.dodajPredmet(predmet); // ovo je ok, metoda prima Predmet
                            //student.dodajPoene(predmetNaziv, kat, poeni);
                            
                            // Koristimo listu da zadržimo redosled kategorija
                            List<String> kategorije = new ArrayList<>(predmet.getKategorije().keySet());
                            out.writeObject(kategorije); // šaljemo celu listu kategorija
                            out.writeObject("KRAJ");
                            out.flush();
                            
                            for(String kat : kategorije){
                                out.writeObject("Poeni za kategoriju " + kat + ": ");
                                out.flush();
                                String unos = (String) in.readObject();
                                int poeni = 0;
                                try {
                                    poeni = Integer.parseInt(unos.trim());
                                } catch(NumberFormatException e){
                                    poeni = 0; // ako korisnik ne unese broj
                                }
                                student.dodajPoene(predmetNaziv, kat, poeni); // metoda prima String naziv predmeta
                            }
                            
                            DataStore.sacuvajStudente();
                            
                            String pregled = student.pregledPoena(predmetNaziv);
                            out.writeObject("Poeni uneti i predmet azuriran.\n" + pregled);
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                            
                            

                        case "5":
                            StringBuilder report = new StringBuilder("Izvestaj o studentima:\n");
                            for (Student st : DataStore.getStudenti()) {
                                report.append(st.getIme()).append(" ").append(st.getPrezime()).append(" - ");
                                for (String nazivPredmeta : st.getPredmeti().keySet()) {
                                    report.append(nazivPredmeta).append(": ")
                                    .append(st.getUkupnoPoena(nazivPredmeta)).append(" poena; ");
                                }
                                report.append("\n");
                            }
                            out.writeObject(report.toString());
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                        
                        case "6": // Izlaz
                            running = false;
                            out.writeObject("Izlaz iz admin menija");
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                           // return true;
                            
                        default:
                            
                            out.writeObject("Nepoznata komanda");
                            out.writeObject("KRAJ");
                            out.flush();
                    }
                }
                
                
            } 
            
            private void handleStudent(String username) throws IOException, ClassNotFoundException{
                boolean running = true;
                while(running){
                    String command = (String) in.readObject();
                    Student s = DataStore.getStudent(username);
                    
                    switch(command){
                        case "1":
                            //Student s = DataStore.getStudent(username);
                            /*if(s != null && !s.getPredmeti().isEmpty()){
                               StringBuilder sbOcene = new StringBuilder("Ocene i poeni po predmetima:\n");
                                for(Predmet p : s.getPredmeti().keySet()){
                                    Map<String, Integer> poeni = s.getPredmeti().get(p);
                                    sbOcene.append("Predmet: ").append(p.getNaziv())
                                    .append(" | Poeni po kategorijama: ").append(poeni)
                                    .append(" | Polozen: ").append(p.jePolozen(poeni) ? "Da" : "Ne")
                                    .append(" | Ocena: ").append(p.getOcena(poeni)).append("\n");
                                }
                               
                                
                                out.writeObject(sbOcene.toString());
                                
                            } else{
                                out.writeObject("Ne postoji student ili nema dodeljene predmete!");
                                
                            }
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                            */
                            
                            if(s != null && !s.getPredmeti().isEmpty()){
                                out.writeObject(s.pregledPredmeta());
                            } else{
                                out.writeObject("Ne postoji student ili nema dodeljena predmete.");
                            }
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                            
                        case "2":
                            
                            
                            if(s != null && !s.getPredmeti().isEmpty()){
                            StringBuilder sbPredmeti = new StringBuilder("Lista predmeta: \n");
                            for(String nazivPredmeta : s.getPredmeti().keySet()){
                                sbPredmeti.append(nazivPredmeta).append("\n");
                                
                            }
                            out.writeObject(sbPredmeti.toString());
                            } else{
                                out.writeObject("Ne postoji student!");
                            }
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                            
                        case "3":    
                            running = false;
                            out.writeObject("Izlaz iz student menija");
                            out.writeObject("KRAJ");
                            out.flush();
                            break;
                        
                        default:
                            out.writeObject("Nepoznata komanda");
                            out.writeObject("KRAJ");
                            out.flush();
                    }
                }
            }
        }
           
    
    

