/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formulagp;
import java.util.Random;
/**
 *
 * @author Mihajlo
 */
public class Rng {
    private int minimumValue;
    private int maximumValue;
    private Random rnd;

public Rng(int minimumValue, int maximumValue){
    this.minimumValue = minimumValue;
    this.maximumValue = maximumValue;
    
}  
public int getRandomValue(){
    
    rnd = new Random();
    return rnd.nextInt((maximumValue - minimumValue + 1)) + minimumValue;
}                                                                /* int minimumValue = 10;
                                                                     int maximumValue = 20;

                                                                     Random rnd = new Random();

                                                                     int range = maximumValue - minimumValue +1;
                                                                     int randomNumber = rnd.nextInt(range);

                                                                     int finalNumber = randomNumber + minimumValue;

                                                                     return finalNumber;*/
   
public void setMinimumValue(int minimumValue){
    this.minimumValue = minimumValue;
}

public void setMaximumValue(int maximumValue){
    this.maximumValue = maximumValue;
}

public void setRnd (Random rnd){
    this.rnd = rnd;
}

public int getMinimumValue(){
    return minimumValue;
}

public int getMaximumValue(){
    return maximumValue;
}

public Random getRnd(){
    return rnd;
}
}
