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
public class Predmet implements Serializable {
    
    private String naziv;
    private Map<String, Integer> kategorije;
    private Map<String, Integer> osvojeniPoeni;
    
    public Predmet(String naziv){
        this.naziv = naziv;
        this.kategorije = new HashMap<>();
        this.osvojeniPoeni = new HashMap<>();
    }
    
    public String getNaziv(){
        return naziv;
    }
    
    public Map<String, Integer> getKategorije(){
        return kategorije;
    }
    
    public void dodajKategoriju(String naziv, int poeni){
        kategorije.put(naziv, poeni);
        
    }
    
    public boolean imaKategoriju(String kategorija){
        return kategorije.containsKey(kategorija);
    }
    
    public int getMaxPoeni(){
        int sum = 0;
        for(int p : kategorije.values()) sum += p;
        return sum;
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Predmet predmet = (Predmet) obj;
        return naziv.equalsIgnoreCase(predmet.naziv);
    }
    
    @Override
    public int hashCode(){
        return naziv.toLowerCase().hashCode();
    }
    /*public void dodajPoene(String kategorija, int poeni){
        if(!kategorije.containsKey(kategorija)) return;
        int trenutni = osvojeniPoeni.getOrDefault(kategorija, 0);
            osvojeniPoeni.put(kategorija, trenutni + poeni);
    }*/
    
    public boolean jePolozen(Map<String, Integer> poeniStudenta){
        /*int ukupno = getUkupnoPoena();
        if(ukupno < 51) return false;
        for(String k : kategorije.keySet()){
            int minPoeni = (int) (kategorije.get(k) * 0.5); //50% po kategoriji
            if(osvojeniPoeni.getOrDefault(k, 0) < minPoeni) return false;
        }
        return true;*/
        if(poeniStudenta == null) return false;
        
        int ukupno = 0;
        for(String k : kategorije.keySet()){
            int max = kategorije.get(k);
            int osvojen = poeniStudenta.getOrDefault(k,0);
            int min = max/2;
            if(osvojen < min) return false;
            ukupno += osvojen;
        }
        return ukupno >= 51;
    }
    
    public int getOcena(Map<String, Integer> poeniStudenta){
        
        if(poeniStudenta == null) return 5;
        
        int ukupno = 0;
        for(int p : poeniStudenta.values()) ukupno += p;
        if(ukupno < 51) return 5;
        else if(ukupno <= 60) return 6;
        else if(ukupno <= 70) return 7;
        else if(ukupno <= 80) return 8;
        else if(ukupno <= 90) return 9;
        else return 10;
    }
    
    public String pregledKategorija(){
        StringBuilder sb = new StringBuilder();
        sb.append("Predmet: ").append(naziv).append("\n");
        for (Map.Entry<String, Integer> e : kategorije.entrySet()){
            sb.append("  ").append(e.getKey())
            .append(" (max ").append(e.getValue()).append(" poena)\n");
        }
        return sb.toString();
    }
    
    
  /*  public int getUkupnoPoena(){
        int sum = 0;
        for(int poeni : osvojeniPoeni.values()) sum += poeni;
        return sum;
    }*/
    
    
    
 /*  public Map<String, Integer> getOsvojeniPoeni(){
        return osvojeniPoeni;
   }*/
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Predmet: " + naziv + "\n");
        
        sb.append("Kategorije i poeni: ").append(kategorije).append("\n");
       /* sb.append("Osvojeni poeni: ").append(osvojeniPoeni).append("\n");
        sb.append("Polozen: ").append(jePolozen() ? "Da" : "Ne").append(", Ocena: ").append(getOcena()).append("\n");
      */  return sb.toString();
    }
        
}
