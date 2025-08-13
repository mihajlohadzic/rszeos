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
    
	public void RainLap(Venue venue){
        Rng rng = new Rng(1,99);
        int chance = rng.getRandomValue();
        if(chance < (venue.getChanceOfRain()*100)){
           System.out.println("PADA KISA\n");
           for(Driver d : drivers){
               if(!d.isPneumatic()){
                   d.setAccumulatedTime(d.getAccumulatedTime() + 5);
                   System.out.println("Vozac " +d.getName()+ " nema gume za kisu.");
               } else{
                   System.out.println("Vozac " +d.getName()+ " ima gume za kisu.");
               }
           }
        
    } else{
            System.out.println("Ne pada kisa.\n");
        }
    }
    
    public void assignTires(){
        for(Driver d : drivers){
            if(d.isEligibleToRace()){
                d.pneumatic();
                if(!d.isPneumatic()){
                    System.out.println("Vozac " +d.getName()+ " nije stavio gume za kisu.");
                }
            }
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
    
	public void assignStartingPositions(int numberOfRaces){
        List<Driver> eligibleDrivers = new ArrayList<>();
        for (Driver d : drivers){
            if(d.isEligibleToRace()){
                eligibleDrivers.add(d);
            }
        }
        if(numberOfRaces == 1){
            Collections.sort(eligibleDrivers, new DriverRanking());    
        }
        else{
            Collections.sort(eligibleDrivers, new DriverPoints(-1));
        }
        System.out.println("\n>>Rang lista za trku #" + (numberOfRaces + 1));
        for(int i = 0; i < eligibleDrivers.size(); i++){
            Driver d = eligibleDrivers.get(i);
            int penalty = 0;
            
            switch(i){
                case 0: penalty = 0;
                    break;
                case 1: penalty = 3;
                    break;
                case 2: penalty = 5;
                    break;
                case 3: penalty = 7;
                    break;
                default: penalty = 10;
                    break;
            }
            d.setAccumulatedTime(d.getAccumulatedTime() + penalty);
        
          /*  for (Driver dr : eligibleDrivers) {
                
          //  System.out.println(dr.getName() + " | ranking: " + dr.getRanking() + " | poeni: " + dr.getAccumulatedPoints());
}       */        
            System.out.println(d.getName()+ " startuje sa pozicije " +(i+1)+ ". Vremenska kazna " +penalty+ "s. Ukupno vreme: " + d.getAccumulatedTime());
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
        //System.out.println("checkMechanicalProblem() je pozvana");
        Rng rng = new Rng(1,100);
        //System.out.println("Broj vozaca: " +drivers.size());
        for(Driver driver : drivers){
            //System.out.println("Provera vozaca: " + driver.getName());
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
            System.out.println("Nakon kruga: " +lap+ " lider je: " +leader.getName());
                               // " sa vremenom: " +leader.getAccumulatedTime()+ " s");
        }else{
            System.out.println("Nema lidera " +lap+ ".");
        }
    }
    
    public void printWinnersAfterRace(String venueName){
		int bestTime = Integer.MAX_VALUE;

    
        for (Driver d : drivers) {
            if (d.isEligibleToRace()) {
                if (d.getAccumulatedTime() < bestTime) {
                bestTime = d.getAccumulatedTime();
                }
            }
        }

    
            System.out.println("\n>> Pobednik trke na stazi " + venueName + ":");
            for (Driver d : drivers) {
                if (d.isEligibleToRace() && d.getAccumulatedTime() == bestTime) {
                System.out.println(" - " + d.getName() + " sa poenima: " + d.getAccumulatedPoints());
                }       
            }	
    }
    
    public void printChampion(int numberOfRaces){
        
		Collections.sort(drivers, new DriverPoints(-1));
        Driver champion = drivers.get(0);
		
        if(champion != null){
            System.out.println("Sampion nakon " +numberOfRaces+ " trke je: " + champion.getName()+ " sa ukupno " + champion.getAccumulatedPoints() + " poena.");
        } else{
            System.out.println("Nema sampiona. ");
        }
    }
	
	void assignPointsAfterVenue(){
        Collections.sort(drivers, new DriverTime());
        
        int points[] = {8, 5, 3, 1};
        int awarded = 0;
        System.out.println("\n>> Rezultati trke: ");
        for(Driver d : drivers){
            if(!d.isEligibleToRace()){
                System.out.println(" - " +d.getName()+" nije zavrsio trku(kvar).");
                continue;
            }
            
            if(awarded < 4){
                //d.setAccumulatedPoints(d.getAccumulatedPoints() + points[awarded]);
                d.addPoints(points[awarded]);
                System.out.println(" - " +d.getName()+ " je osvojio " +points[awarded]+ " poena.");
                awarded++;
            }
            else {
                System.out.println(" - " +d.getName()+ " je zavrsio trku bez poena.");
            }
        }
        
        //reset vremena
        for(Driver d : drivers){
            d.setAccumulatedTime(0);
            d.setEligibleToRace(true);
        }
        
        //rangiranje po poenima
        Collections.sort(drivers, new DriverPoints(-1));
        for(int i = 0; i < drivers.size();i++){
            drivers.get(i).setRanking(i+1);
        }
        
        System.out.println("\n>> Trenutni plasman (po poenima): ");
        for(Driver d : drivers){
            System.out.println(" - " +d.getRanking()+ ". " +d.getName()+ " (" +d.getAccumulatedPoints()+ " poena)");
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
