/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Mihajlo
 */
public class Student implements Serializable {
    private String ime;
    private String prezime;
    private String brojIndeksa;
    private String jmbg;
    private String username;
    private String password;
    
    private Map<String, Map<String, Integer>> predmeti;
    
    public Student(String ime, String prezime, String brojIndeksa, String jmbg, String username, String password){
        this.ime = ime;
        this.prezime = prezime;
        setBrojIndeksa(brojIndeksa);
        setJmbg(jmbg);
        this.username = username;
        this.password = password;
        this.predmeti = new HashMap<>();
    }
    
    public void dodajPredmet(Predmet p){
        if(!predmeti.containsKey(p.getNaziv())){
            Map<String, Integer> kategorijePoeni = new HashMap<>();
            
            for(String kat : p.getKategorije().keySet()){
                kategorijePoeni.put(kat, 0);
            }
            predmeti.put(p.getNaziv(), kategorijePoeni);
        }
    }
    
    public void dodajPoene(String nazivPredmeta, String kategorija, int poeni){
        
        if(predmeti.containsKey(nazivPredmeta)){
            Map<String, Integer> katPoeni = predmeti.get(nazivPredmeta);
            //Predmet p = DataStore.getPredmet(nazivPredmeta);
            //if(p != null && p.imaKategoriju(kategorija)){
                int stari = katPoeni.getOrDefault(kategorija.trim(), 0);
                katPoeni.put(kategorija.trim(), stari + poeni);
            //}
        }
    }
    
    public boolean jePolozio(String nazivPredmeta){
        if(!predmeti.containsKey(nazivPredmeta)) return false;
        Predmet p = DataStore.getPredmet(nazivPredmeta);
        if(p == null) return false;
        return p.jePolozen(predmeti.get(nazivPredmeta));
    }
    
    public int getOcena(String nazivPredmeta){
        if(!predmeti.containsKey(nazivPredmeta)) return 5;
        Predmet p = DataStore.getPredmet(nazivPredmeta);
        if(p == null) return 5;
        return p.getOcena(predmeti.get(nazivPredmeta));
    }
    
    public String pregledPredmeta(){
        if(predmeti.isEmpty()) return "Student nema dodeljenih predmeta.\n";

        StringBuilder sb = new StringBuilder();
        for(String nazivPredmeta : predmeti.keySet()){
            Map<String, Integer> poeniStudenta = predmeti.get(nazivPredmeta);
            
            Predmet p = DataStore.getPredmet(nazivPredmeta);
            
            if(p == null){
                sb.append("Predmet ").append(nazivPredmeta).append(" ne postoji u predmetima!\n");
                continue;
            }
            sb.append("Predmet: ").append(nazivPredmeta).append("\n");
            sb.append("Ukupno poena: ").append(getUkupnoPoena(nazivPredmeta)).append("\n");
            sb.append("Polozen: ").append(p.jePolozen(poeniStudenta) ? "Da" : "Ne").append("\n");
            sb.append("Ocena: ").append(p.getOcena(poeniStudenta)).append("\n\n");
        }
        return sb.toString();
    }
    
    public String pregledPoena(String nazivPredmeta){
        if(!predmeti.containsKey(nazivPredmeta)){
            return "Student nema ovaj predmet.\n";
        }
        Predmet p = DataStore.getPredmet(nazivPredmeta);
        if(p == null) return "Predmet ne postoji u sistemu.\n";
        
        StringBuilder sb = new StringBuilder();
        sb.append("Rezultati za ").append(p.getNaziv()).append(":\n");
        Map<String, Integer> katPoeni = predmeti.get(nazivPredmeta);
        
        int ukupno = 0;
        int max = 0;
        
        for (Map.Entry<String, Integer> e : p.getKategorije().entrySet()) {
        int osvojen = katPoeni.getOrDefault(e.getKey(), 0);
        
        sb.append("  ").append(e.getKey())
          .append(": ").append(osvojen).append("/")
          .append(e.getValue()).append("\n");
        ukupno += osvojen;
        max += e.getValue();
    }

        sb.append("Ukupno: ").append(ukupno).append("/").append(max).append("\n");
        if (ukupno >= max * 0.51) {
            sb.append("Status: POLOŽIO\n");
        } else {
            sb.append("Status: NIJE POLOŽIO\n");
        }

        return sb.toString();
    }
    
    public int getUkupnoPoena(String nazivPredmeta){
        if(!predmeti.containsKey(nazivPredmeta)) return 0;
        int sum = 0;
        for(int poeni : predmeti.get(nazivPredmeta).values()) sum += poeni;
        return sum;
    }
    
    private void setBrojIndeksa(String brojIndeksa){
        if(brojIndeksa == null || !brojIndeksa.matches("[A-Z][0-9]{1,2}-[0-9]{4}")){
            throw new IllegalArgumentException("Neispravan broj indeksa! Primer: E2-2015");
        }
        this.brojIndeksa = brojIndeksa;
    }
    
    private void setJmbg(String jmbg){
        if(jmbg == null || !jmbg.matches("\\d{13}")){
            throw new IllegalArgumentException("JMBG mora imati tano 13 cifara!");
        }
        this.jmbg = jmbg;
    }
    
    public Map<String, Map<String, Integer>> getPredmeti(){
        return predmeti;
    }
    
    public String getIme(){
        return ime;
    }
    
    public String getPrezime(){ 
        return prezime;
    }
    
    public String getBrojIndeksa(){
        return brojIndeksa;
    }
    
    public String getJmbg(){
        return jmbg;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Student: ").append(ime).append(" ").append(prezime)
          .append(" | Indeks: ").append(brojIndeksa)
          .append(" | JMBG: ").append(jmbg).append("\n");
        
        if(predmeti.isEmpty()){
            sb.append(" Student nema dodeljenih predmeta.\n");
        } else{
            sb.append("Predmeti: \n");
            for(String nazivPredmeta : predmeti.keySet()){
                Predmet p = DataStore.getPredmet(nazivPredmeta);
                sb.append("  - ").append(nazivPredmeta)
                  .append(" | Polozen: ").append(p.jePolozen(predmeti.get(nazivPredmeta)) ? "DA" : "NE")
                  .append(" | Ocena: ").append(p.getOcena(predmeti.get(nazivPredmeta)))
                  .append("\n");
            }
        }
        return sb.toString();
    }
          
}
