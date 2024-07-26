package Services;

import Entities.Building;
import Entities.Elevator;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceElevator {
    
    Scanner scan = new Scanner(System.in).useDelimiter("\n");
    String choose;//Saves the user's choice of which elevator he wants to use.
    String option;//Saves the user's choice when asked if they want to re-enter the password for the public elevator.
    Integer password;//Saves the password entered by the user, and then compares it with the registered passwords.
    Boolean door = true;//Save the state of the elevator doors.
    Integer person_floor;//It is the floor where the person who is going to use the elevators is currently located.
    Integer elevator_weight;//It is the current weight of the public elevator. It is set to random
    Integer freight_weight;//It is the current weight of the freight elevator. It is set to random
    String use_again;//Saves the user's choice of using the elevator again once it takes some load off so as not to exceed the limit
    Integer choose_floor;//It is the floor where the user is going
    Integer password_counter = 0;//Save the three password attempts that the user has
    Integer c_pass = 0;//Counter used when iterating the list, if there is a match with the password entered by the user, add 1
    Integer aux_for1 = 0;//I used it to reuse the up_down function
    Integer aux_for2 = 0;//I used it to reuse the up_down function
    String use;//Saves the user's choice of using an elevator again once they have reached the chosen floor
    String yes_no;//Saves the user's choice of using the same elevator again once they have reached the chosen floor
    List<Integer> passwords = new ArrayList<Integer>() { //This list has valid passwords to access floor 50 and -1
        {
            add(123); 
            add(234);
            add(345);
        }
    };
    //I instantiated the objects of my program
    Building building = new Building();
    Elevator public_elevator = new Elevator();
    Elevator freight_elevator = new Elevator();

    //Initialize my program
    public void start() {
        System.out.println("Welcome!");
        max_weight();
        person_floor();
        elevator_floor();
        choose_elevator();
    }
    //I value the current weight of each elevator
    public void current_weight() {
        elevator_weight = (int) (Math.random() * 1300) + 1;
        freight_weight = (int) (Math.random() * 6300) + 1;
    }
    //I value the maximum weight of each elevator
    public void max_weight() {  
        public_elevator.setMax_weight(1000);
        freight_elevator.setMax_weight(3000);
    }
    //Determine from which floor the person will call the elevator at the beginning of the program
    public void person_floor() {
        person_floor = (int) (Math.random() * (50 - (-1) + 1) + (-1));
        current_person_floor();
    }
    //Show me the floor where the person is
    public void current_person_floor() {
        System.out.println("You are at the floor number " + person_floor);
    }
    //Determine which floor the elevators are on at the beginning of the program
    public void elevator_floor() {
        public_elevator.setFloors((int) (Math.random() * (50 - (-1) + 1) + (-1)));
        freight_elevator.setFloors((int) (Math.random() * (50 - (-1) + 1) + (-1)));
        current_elevator_floor();
    }
    //Show me the floor where the elevators are
    public void current_elevator_floor() {
        System.out.println("Public elevator: Floor " + public_elevator.getFloors());
        System.out.println("Freight elevator: Floor " + freight_elevator.getFloors());
    }
    //I choose which elevator I am going to use or if I don't want to use any, exit the program
    public void choose_elevator() {
        do {
            current_weight();
            System.out.println("Which elevator do you use?");
            System.out.println("Public elevator: Press (P) // Freight elevator: Press (F) // Neither: Press (X");
            choose = scan.next();
            choose = choose.toLowerCase();
            switch (choose) {
                case "p":
                    public_elevator();
                    break;
                case "f":
                    freight_elevator();
                    break;
                case "x":
                    System.out.println("You do not want to use any elevator");
                    current_elevator_floor();
                    exit(0);
                    break;
            }
        } while (!choose.equals("p") && !choose.equals("f") && !choose.equals("x"));
    }
    //Show the status of a door 
    public void doors() {
        if (door) {
            System.out.println("((the door is opening))");
            time();
        } else {
            time();
            System.out.println("((The door is closing))");
        }
        door = true;
    }
    /*
    Calls the public elevator, contains the call to the function up_down, which shows the route of the elevator until it reaches its destination
    and contains the call to the check_weight, function that evaluates that the load does not exceed the maximum weight of the elevator
     */
    public void public_elevator() {
        aux_for1 = public_elevator.getFloors();
        aux_for2 = person_floor;
        up_down_p();
        public_elevator.setFloors(person_floor);
        System.out.println("The public elevator is now at the floor number " + public_elevator.getFloors());
        doors();
        System.out.println("Please, enter the elevator");
        check_weight_p(); 
    }
    /*
    Calls the freight elevator, contains the call to the function up_down, which shows the route of the elevator until it reaches its destination
    and contains the call to the check_weight, function that evaluates that the load does not exceed the maximum weight of the elevator
     */
    public void freight_elevator() {
        aux_for1 = freight_elevator.getFloors();
        aux_for2 = person_floor;
        up_down_p();
        freight_elevator.setFloors(person_floor);
        System.out.println("The freight elevator is now at the floor number " + freight_elevator.getFloors());
        doors();
        System.out.println("Please, enter the elevator");
        check_weight_f();
    }
    //Function that evaluates if the weight loaded in the freight elevator exceeds the maximum allowed limit
    public void check_weight_f(){
        do { 
            if (freight_weight > freight_elevator.getMax_weight()) {
                time();
                System.out.println("Warning! the weight limit has been exceeded");
                System.out.println("Please, offload the elevator");
                current_weight();
                time();
                System.out.println("Do you want to use this elevator again? YES: Press (Y) NO: Press (N)");
                use_again = scan.next();
                use_again = use_again.toLowerCase();
                if (use_again.equals("n")) {
                    door = true;
                    doors();
                    current_elevator_floor();
                    choose_elevator();
                }
            } else {
                time();
                System.out.println("The weight is right");
                door = false;
                doors();
                choose_floor();
                use_again = "n";
            }
        } while (use_again.equals("y"));
    }
    //Function that evaluates if the weight loaded in the public elevator exceeds the maximum allowed limit
    public void check_weight_p() {
        time();
        do { 
            if (elevator_weight > public_elevator.getMax_weight()) {
                time();
                System.out.println("Warning!! the weight limit has been exceeded");
                System.out.println("Please, offload the elevator");
                current_weight();
                time();
                System.out.println("Do you want to use this elevator again? YES: Press (Y) NO: Press (N)");
                use_again = scan.next();
                use_again = use_again.toLowerCase();
                if (use_again.equals("n")) {
                    door = true;
                    doors();
                    current_elevator_floor();
                    choose_elevator();
                }
            } else {
                time();
                System.out.println("The weight is right");
                door = false;
                doors();
                choose_floor();
                use_again = "n";
            }
        } while (use_again.equals("y"));
    }
    //Shows the route of the elevator to reach the destination
    public void up_down_p() {
        if (aux_for1 < aux_for2) {
            System.out.println("The elevator is going up");
            for (int i = aux_for1; i < aux_for2 + 1; i++) {
                System.out.println("Floor " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServiceElevator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (aux_for1 > aux_for2) { 
            System.out.println("The elevator is going down");
            for (int i = aux_for1; i > aux_for2 - 1; --i) {
                System.out.println("Floor " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServiceElevator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        aux_for1 = 0;
        aux_for2 = 0;
    }
    /*
    Function to choose the floor to which I want to go and in the case of using the public elevator,
    call the function to validate the password
     */
    public void choose_floor() {
        time();
        System.out.println("Please, enter the floor number you want to go to");
        current_person_floor();
        System.out.println("The building has 50 floors and a basement. If you want to go at basement press (-1)");
        choose_floor = scan.nextInt();
        if (choose_floor < - 1 || choose_floor > 50){ 
            System.out.println("Incorrect number");
            choose_floor();  
        }
        else if (choose_floor >= 0 && choose_floor < 50 && choose.equals("p")) {
            aux_for1 = person_floor;
            aux_for2 = choose_floor;
            if (aux_for1 == aux_for2) {
                System.out.println("You are on the chosen floor");
                choose_floor();
            }
            up_down_p();
            public_elevator.setFloors(choose_floor);
            System.out.println("The elevator reached the floor number " + public_elevator.getFloors());
            person_floor = public_elevator.getFloors();
        } else if (choose_floor >= -1 && choose_floor <= 50 && choose.equals("f")) {
            aux_for1 = person_floor;
            aux_for2 = choose_floor;
            if (aux_for1 == aux_for2) {
                System.out.println("You are on the chosen floor");
                choose_floor();
            }
            up_down_p();
            freight_elevator.setFloors(choose_floor);
            System.out.println("The elevator reached the floor number " + freight_elevator.getFloors());
            person_floor = freight_elevator.getFloors();
        } else if (choose_floor == -1 || choose_floor == 50 && choose.equals("p")) {
            if (choose_floor == person_floor) {
                System.out.println("You are on the chosen floor");
                choose_floor();
            }
            System.out.println("Enter your password to access this floor");
            password = scan.nextInt();
            validate_password();
        }
        door = true;
        doors();
        exit_elevator();
    }
    //Compares the password entered by the user with the passwords registered in the program and validates them
    public void validate_password() {
        do {
            for (Integer pass : passwords) {
                if (pass.equals(password)) {
                    c_pass++;
                }
            }
            if (c_pass == 1) {
                aux_for1 = public_elevator.getFloors();
                aux_for2 = choose_floor;
                if (aux_for1 == aux_for2) {
                    System.out.println("You are on the chosen floor");
                    validate_password();
                }
                up_down_p();
                c_pass = 0;
                password_counter = 0;
                public_elevator.setFloors(choose_floor);
                System.out.println("The elevator reached the floor number " + public_elevator.getFloors());
                person_floor = public_elevator.getFloors();
                door = true;
                doors();
                exit_elevator();
            } else if (c_pass == 0) {
                do {
                    System.out.println("Incorrect password");
                    System.out.println("Do you want to re-enter it? YES: Press (Y) NO: Press (N)");
                    option = scan.next();
                    option = option.toLowerCase();
                } while (!option.equals("y") && !option.equals("n"));
            }
            if (option.equals("n")) {
                choose_floor();
                password_counter = 0;
            } else {
                option.equals("y");
                System.out.println("Plese, re-enter it");
                password = scan.nextInt();
                password_counter++;
                if (password_counter == 2) {
                    System.out.println("Last failed attempt. Choose another floor number");
                }
            }
        } while (password_counter < 2);
        if (password_counter > 1) {
            choose_floor();
            password_counter = 0;
        }
    }
    //Function to exit the elevator and stop using it or call it again
    public void exit_elevator() {
        System.out.println("Do you want to use this elevator again? YES: Press(Y) NO: Press(N)");
        yes_no = scan.next();
        yes_no = yes_no.toLowerCase();
        if (yes_no.equals("y")) {
            door = false;
            doors();
            choose_floor();
        } else {
            time();
            System.out.println("Please, exit the elevator");
            door = false;
            doors();
        }
        time();
        System.out.println("Do you want to use any of the elevators again? YES: Press(Y) NO: Press(N)");
        use = scan.next();
        use = use.toLowerCase();
        if (use.equals("y")) {
            current_person_floor();
            current_elevator_floor();
            choose_elevator();
        } else {
            current_elevator_floor();
            exit(0);
        }
    }
    //Function that determines a time between executions
    public static void time() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServiceElevator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Function to terminate the program if the user does not want to use the elevators
    public static void exit(int i) {
        System.out.println("Thank you for your visit");
        System.exit(0);
    } 
}
