/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formulagp;

/**
 *
 * @author Mihajlo
 */
public class Driver {
        private String name;
        private int ranking;
        private String specialSkill;
        private boolean eligibleToRace = true;
        private int accumulatedTime;
        private int accumulatedPoints;
        private boolean pneumatic = false;
        
public Driver(String name, int ranking, String specialSkill /*boolean eligibleToRace*/){
        this.name = name;
        this.ranking = ranking;
        this.specialSkill = specialSkill;
       // this.eligibleToRace = true;
    
}
@Override
public String toString(){
    return "Ime: " + getName() + ", Ranking: " + getRanking() + ", Special skill: " + getSpecialSkill();
}
public void setName(String name){
    this.name = name;
}        

public void setRanking(int ranking){
    this.ranking = ranking;
}

public void setSpecialSkill(String specialSkill){
    this.specialSkill = specialSkill;
}

public void setEligibleToRace(boolean eligibleToRace){
    this.eligibleToRace = eligibleToRace;
}

public void setAccumulatedTime(int accumulatedTime){
    this.accumulatedTime = accumulatedTime;
}

public void setAccumulatedPoints(int accumulatedPoints){
    this.accumulatedPoints = accumulatedPoints;
}

public void useSpecialSkill(Rng rng){
    int previousTime = accumulatedTime;
    previousTime -= rng.getRandomValue();
    accumulatedTime = previousTime;
}

public void pneumatic(){
    Rng rng = new Rng(0,2);
    int possibility = rng.getRandomValue();
    if(possibility == 0){
        pneumatic = false;
    }
    else{
        System.out.println("Vozac" + getName() + " je stavio pneumatike za kisu.");
        pneumatic = true;
        accumulatedTime += 10;
    }
}

public boolean isPneumatic(){
    return pneumatic;
}

public void setPneumatic(boolean pneumatic){
    this.pneumatic = pneumatic;
}
        
public String getName(){
    return name;
}
public int getRanking(){
    return ranking;
}

public String getSpecialSkill(){
    return specialSkill;
}
public boolean isEligibleToRace(){
    return eligibleToRace;
}
public int getAccumulatedTime(){
    return accumulatedTime;
}
public int getAccumulatedPoints(){
    return accumulatedPoints;
}
}
