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
public class DriverTime implements Comparator<Driver>{
    @Override
        public int compare(Driver a, Driver b){
            return a.getAccumulatedTime() - b.getAccumulatedTime();
        }
}
