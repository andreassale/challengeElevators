
package ChallengeMain;

import Services.ServiceElevator;
 
public class ChallengeMain {
    /*
    - The basement is floor number -1
    - Valid passwords for floors 50 and -1: 123/234/345
    - The weight of the elevators, it is set to random
    - The initial floor of the person, it is set to random
     */

    public static void main(String[] args) {
        ServiceElevator serv = new ServiceElevator();
        
        serv.start();
    }
}
