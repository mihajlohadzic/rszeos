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
public class DriverPoints implements Comparator<Driver>{
    int direction = 1;
        
        public DriverPoints(int direction){
            if(direction !=1 && direction !=-1){
                direction = 1;
            }
            this.direction = direction;
        }
        @Override
        public int compare(Driver d1, Driver d2){
            return direction*Integer.compare(d1.getAccumulatedPoints(), d2.getAccumulatedPoints());
        }
}
