/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formulagp;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
/**
 *
 * @author Mihajlo
 */
public class Championship {
    private ArrayList<Driver> drivers = new ArrayList<>();
    private ArrayList<Venue> venues = new ArrayList<>();
    
    final int MINOR_MECHANICAL_FAULT = 5;
    final int MAJOR_MECHANICAL_FAULT = 3;
    final int UNRECOVERABLE_MECHANICAL_FAULT = 1;
    
    
    public void readDrivers(String file){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                String name = parts[0].trim();
                int ranking = Integer.parseInt(parts[1].trim());
                String specialSkill = parts[2].trim();
                drivers.add(new Driver(name, ranking, specialSkill));
            }
        } catch(IOException e){
            System.out.println("Greska pri ucitavanju vozaca: " +e.getMessage());
        }
    }
    
    public void displayDrivers(){
        System.out.println("DRIVERS: ");
        for (Driver d: drivers){
            System.out.println(d);
        }
    }
    
    public void readVenues(String file){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                String venueName = parts[0].trim();
                int numberOfLaps = Integer.parseInt(parts[1].trim());
                int averageLapTime = Integer.parseInt(parts[2].trim());
                double chanceOfRain = Double.parseDouble(parts[3].trim()); ;
                
                
                venues.add(new Venue(venueName, numberOfLaps, averageLapTime, chanceOfRain));
            }
        } catch(IOException e){
            System.out.println("Greska pri ucitavanju staze: " +e.getMessage());
        }
    }
    
    public void displayVenues(){
        System.out.println("VENUES: ");
        for (Venue v: venues){
            System.out.println(v);
        }
    }
    
    public void setDrivers(ArrayList<Driver> drivers){
        this.drivers = drivers;
    }
    
    public void setVenues(ArrayList<Venue> venues){
        this.venues = venues;
    }
    
    public ArrayList<Driver> getDrivers(){
        return drivers;
    }
    
    public ArrayList<Venue> getVenues(){
        return venues;
    }
    
    public void prepareForTheRace(){
        for(Driver driver : drivers){
            driver.setAccumulatedTime(0);
            driver.setEligibleToRace(true);
            System.out.println("Vozac: " +driver.getName()+ " je pripremljen.");
        }
    }
    
    public void driveAverageLapTime(Venue venue){
        for(Driver driver: drivers){
            if(driver.isEligibleToRace()){
                int newTime = driver.getAccumulatedTime() + venue.getAverageLapTime();
                driver.setAccumulatedTime(newTime);
                System.out.println("Svakom vozacu se povecava vreme za: "+newTime+"s\n");
            }
        }
    }
    
    public void applySpecialSkills(int numberOfRaces){
        for(Driver driver : drivers){
            if(driver.isEligibleToRace()){
                String skill = driver.getSpecialSkill().toLowerCase();
                
                if(skill.equals("braking") || (skill.equals("cornering"))){
                Rng rng = new Rng(1,8);
                int timeReduction = rng.getRandomValue();
                driver.setAccumulatedTime(driver.getAccumulatedTime()- timeReduction);
                }
                
                else if(skill.equals("overtaking") && numberOfRaces % 3 == 0){
                Rng rng = new Rng (10,20); 
                int timeReduction = rng.getRandomValue();
                driver.setAccumulatedTime(driver.getAccumulatedTime() - timeReduction);
                }
            }
        }
    }
    
    public void checkMechanicalProblem(){
        System.out.println("checkMechanicalProblem() je pozvana");
        Rng rng = new Rng(1,100);
        System.out.println("Broj vozaca: " +drivers.size());
        for(Driver driver : drivers){
            System.out.println("Provera vozaca: " + driver.getName());
            if(!driver.isEligibleToRace()){
                System.out.println(driver.getName() + " nije eligible za trku.");
                continue;
            }
            
            int rand_num = rng.getRandomValue();
            
            if(rand_num == UNRECOVERABLE_MECHANICAL_FAULT){
                driver.setEligibleToRace(false);
                System.out.println("Vozac " +driver.getName()+ " nije u mogucnosti da nastavi trku zbog kvara.");
            }
            else if(rand_num >= UNRECOVERABLE_MECHANICAL_FAULT + 1 && 
                    rand_num <= UNRECOVERABLE_MECHANICAL_FAULT + MAJOR_MECHANICAL_FAULT ){
                driver.setAccumulatedTime(driver.getAccumulatedTime() + 120);
                System.out.println("Vozac " +driver.getName()+ " je imao veci kvar. +120s");
            }
            
            else if(rand_num > UNRECOVERABLE_MECHANICAL_FAULT + MAJOR_MECHANICAL_FAULT &&
                    rand_num <= UNRECOVERABLE_MECHANICAL_FAULT + MAJOR_MECHANICAL_FAULT + MINOR_MECHANICAL_FAULT){
                driver.setAccumulatedTime(driver.getAccumulatedTime() + 20);
                System.out.println("Vozac " +driver.getName()+ " je imao manji kvar. +20s");
            }
            else {
                System.out.println("Vozac " + driver.getName() + " nije imao kvar.");
            }
        }
        
    }
    
    public void printLeader(int lap){
        Driver leader = Collections.min(drivers, new DriverTime());
        
        if(leader != null && leader.isEligibleToRace()){
            System.out.println("Nakon kruga: " +lap+ " lider je: " +leader.getName()+
                                " sa vremenom: " +leader.getAccumulatedTime()+ " s");
        }else{
            System.out.println("Nema lidera " +lap+ ".");
        }
    }
    
    public void printWinnersAfterRace(String venueName){
        
    }
    
    public void printChampion(int numberOfRaces){
        Driver champion = Collections.max(drivers, new DriverPoints(-1));
        
        if(champion != null){
            System.out.println("Sampion nakon " +numberOfRaces+ " trke je: " + champion.getName()+ " sa ukupno " + champion.getAccumulatedPoints() + " poena.");
        } else{
            System.out.println("Nema sampiona. ");
        }
    }
    
    public int getMINOR_MECHANICAL_FAULT(){
        return MINOR_MECHANICAL_FAULT;
    }
    
     public int getMAJOR_MECHANICAL_FAULT(){
        return MAJOR_MECHANICAL_FAULT;
    }
     
      public int getUNRECOVERABLE_MECHANICAL_FAULT(){
        return UNRECOVERABLE_MECHANICAL_FAULT;
    }    
}
