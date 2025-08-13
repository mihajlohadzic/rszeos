/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package formulagp;
import java.util.Comparator;
/**
 *
 * @author Mihajlo
 */
public class DriverRanking implements Comparator<Driver>{
    @Override
    public int compare(Driver d1, Driver d2){
        return Integer.compare(d1.getRanking(), d2.getRanking());
    }
    
}
