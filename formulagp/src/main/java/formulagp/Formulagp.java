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
        c.prepareForTheRace();
       // for(int i = 0; i < 10; i++){
        c.checkMechanicalProblem();
       // }
       Venue venue = c.getVenues().get(4);
        c.driveAverageLapTime(venue);
        for(int i = 1; i <=numberOfRaces;i++){
        c.printLeader(i);
        c.printChampion(i);
        }
        c.displayDrivers();
        c.displayVenues();
        
    }
}
