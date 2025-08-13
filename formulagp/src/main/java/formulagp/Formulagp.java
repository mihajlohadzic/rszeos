/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package formulagp;
import java.util.Scanner;
/**
 *
 * @author Mihajlo
 */
public class Formulagp {

    public static void main(String[] args) {
        Championship c = new Championship();
        
        Scanner scanner = new Scanner(System.in);
        int numberOfRaces = 0;
        
        while(numberOfRaces < 3 || numberOfRaces > 5){
            System.out.print("Unesite broj trka (3-5): ");
            if(scanner.hasNextInt()){
                numberOfRaces = scanner.nextInt();
                if(numberOfRaces < 3 || numberOfRaces > 5){
                    System.out.println("Greska: broj trka mora biti izmedju 3 i 5.");
                    
                }
            }
            else{
                System.out.println("Greska: unesite ceo broj.");
                scanner.next();
            }
        }
        System.out.println("Broj trka koje ce se odrzati: " + numberOfRaces);
                
        c.readDrivers("vozaci.txt");
        c.readVenues("staze.txt");
		
		Collections.shuffle(c.getVenues());
        List<Venue> selectedVenues = c.getVenues().subList(0, numberOfRaces);
		
        c.prepareForTheRace();
		for(int i = 0; i < numberOfRaces; i++){
            
            
            System.out.println("\n======================================");
            System.out.println("\n TRKA   " +(i+1));
            Venue currentVenue = selectedVenues.get(i);
            System.out.println("\nSTAZA " +currentVenue.getVenueName());
            
            c.assignStartingPositions(i);
            c.assignTires();
            
            c.checkMechanicalProblem();
            c.RainLap(currentVenue);
            c.driveAverageLapTime(currentVenue);
            c.applySpecialSkills(numberOfRaces);
            c.assignPointsAfterVenue();
            
            c.printLeader(i+1);
            c.printWinnersAfterRace(currentVenue.getVenueName());
            
            
            System.out.println("=========================================");
        }
            System.out.println("\n****Konacan sampion ***");
            c.printChampion(numberOfRaces);
            System.out.println("*******************************************");
        
    }
}
