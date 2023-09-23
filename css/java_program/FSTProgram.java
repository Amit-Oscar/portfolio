// program Name: Airline reservation simulator
// Author: Amit Oscar
// date created: 11/06/2021
// date modified: 11/18/2021

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

// Big O(n) is O(n²) at line 252

class Passenger{ // class that holds passenger information
    private String name, foodChoice, seatChoice;
    private int age;

    public Passenger(){ // Constructor Method - Passenger Class
        this.name = "";
        this.age = 1;
        this.foodChoice = "beef";
        this.seatChoice = "economy";
    }
    public Passenger(String name, int age, String foodChoice, String seatChoice){ // Overloaded constructor method - Passenger Class
        setName(name);
        setAge(age);
        setFoodChoice(foodChoice);
        setSeatChoice(seatChoice);
    }


    //set and get methods for the fields: name, age, foodChoice, seatChoice

    // parameter:  must have at least one "space"
    public void setName(String name) {
        if (name.contains(" ")){
            this.name = name;
        } else{ this.name = null;}
    }

    public String getName() {
        return name;
    }

    // parameter: must be between 0 and 100
    public void setAge(int age) {
        if (age < 1){this.age = 1;
        } else if (age > 100){this.age = 100;
        } else {this.age = age;}

    }

    public int getAge() {
        return age;
    }

    // parameter: must be either kosher, hallal, vegan, beef, egg
    public void setFoodChoice(String foodChoice) {
        switch (foodChoice.toLowerCase()){
            case "kosher":
            case "hallal":
            case "vegan":
            case "beef":
            case "egg":
                this.foodChoice = foodChoice;
                break;
            default:
                this.foodChoice = "beef";
                break;
        }

    }

    public String getFoodChoice() {
        return foodChoice;
    }

    // parameter: choose between first class, premium economy class, economy class, and business class
    public void setSeatChoice(String seatChoice) {

        switch (seatChoice.toLowerCase()){
            case "first":
            case "premium economy":
            case "economy":
            case "business":
                this.seatChoice = seatChoice;
                break;
            default:
                this.seatChoice = "economy";
                break;

        }

    }

    public String getSeatChoice() {
        return seatChoice;
    }
}



class Airline{ // Parent class that has the Airline information
    private String airlineName;


    // Constructor Method - Airline Method
    public Airline(){
        this.airlineName = "air canada";
    }

    // Overloaded Constructor Method - Airline Method
    public Airline(String airlineName){
        setAirlineName(airlineName);
    }

    // get and set AirlineName field

    // parameter: choose between elal, air canada, emirates
    public void setAirlineName(String airlineName) {
        switch (airlineName){
            case "elal":
            case "air canada":
            case "emirates":
                this.airlineName = airlineName;
                break;
            default:
                this.airlineName = "air canada";
                break;

        }
    }

    public String getAirlineName() {
        return airlineName;
    }

}



class PassengerPlane extends Airline implements Comparable<PassengerPlane>{ // sub class to the Airline class and can also use the compare method

    private int rows, columns, flightStartDate,flightEndDate;

    public PassengerPlane(){ // constructor method - PassengerPlane Class
        super();
        this.rows = 0;
        this.columns = 8;
        this.flightStartDate = 1;
        this.flightEndDate = 1;
    }

    public PassengerPlane(int flightStartDate, int flightEndDate, String airlineName){ // Overloaded constructor method - PassengerPlane Class
        super(airlineName);
        setRows(airlineName);
        setColumns();
        setFlightEndDate(flightEndDate);
        setFlightStartDate(flightStartDate);

    }

    // gets the days from today and the start flight date, parameter: must be positive
    public void setFlightStartDate(int flightStartDate) {

        this.flightStartDate = flightStartDate;
    }

    public int getFlightStartDate() { return flightStartDate;}

    // always going to be 8
    public void setColumns() {
        this.columns = 8;
    }

    public int getColumns() {
        return columns;
    }

    // gets the days from flight start date and the end flight date, parameter: must be positive
    public void setFlightEndDate(int flightEndDate) {
        this.flightEndDate = flightEndDate;
    }

    public int getFlightEndDate() {
        return flightEndDate;
    }

    //  parameter: based on the AirlineName
    public void setRows(String airplaneName) {
        switch (airplaneName){
            case "elal":
                this.rows = 22;
                break;
            case "air canada":
                this.rows = 20;
                break;
            case "emirates":
                this.rows = 24;
                break;

        }
    }

    public int getRows() { return rows; }

    // makes a list of seats that are filled that the user cannot choose
    public boolean [] fillSeats(int rows, int columns){
        // uses the rows and columns to make the size of its array
        boolean [] filled = new boolean[rows * (columns - 1)];

        int []intFilled = new int[rows * (columns - 1)];

        // uses the days in between today's date and the date of departure to make it more/less likely of seats being filled
        int max = 20;

        if (this.getFlightStartDate() < 20){
            max = 2;
        } else if(this.getFlightStartDate() < 50){
            max = 5;
        } else if (this.getFlightStartDate() < 200){
            max = 10;
        }

        for (int i = 0; i < intFilled.length; i++){
            intFilled[i] = (int)(1+Math.random()*max);
            if (intFilled[i] == 1){
                filled[i] = true;
            } else{
                filled[i] = false;
            }

        }

        return filled;
    }

    // uses rows and columns to draw the Airplane with 2D lists and makes certain seats filled using the filled list
    public void DrawAirplane(boolean [] filled){

        String [] alphebet = new String [] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        String line = "";


        int lastnum = 1;


        for (int r = 0; r < this.getRows(); r++){

            // rows 1 to 3 are only first class
            if (r < 3){
                line = " FC:  ";
            // rows 4 to 7 are only Premium Economy class
            } else if (r < 7){
                line = " PEC: ";
            // rows 8 to 11 are only Economy class
            }else if (r < 11){
                line = " EC:  ";
            // the rest are Business class
            } else{
                line = " BC:  ";
            }

            for (int c = 1; c < this.getColumns(); c++){


                if (r < 3){
                    //extra spacing
                    switch(c){
                        case 3:
                            line += "  ";
                            break;
                        case 5:
                            line += "   ";
                            break;
                    }

                     if (c  > 6){
                        line += " ";
                    // checks if seat is filled
                    } else if (filled[lastnum - 1] == true){
                        line += "XX ";
                    } else{
                        line += alphebet[r] + c + " ";
                    }
                    lastnum += 1;

                }else{
                    //extra spacing
                    switch(c){
                        case 3:
                        case 6:
                            line += " ";
                    }

                    if (filled[lastnum - 1] == true){
                        line += "XX ";
                    } else{
                        line += alphebet[r] + c + " ";
                    }
                    lastnum += 1;

                }



            }
            System.out.println(line);
        }






    }
    // based on all the information that the user inputed calculate the final price with if statements and writes all the prices in a txt file
    public double OneTicketPrice (Passenger Passenger1) throws IOException{

        FileWriter fw= new FileWriter("PriceInfo.txt");
        PrintWriter output = new PrintWriter(fw);

        double price = 0;
        double ageDiscount = 1.00;
        double airlinePrice = 0;
        double foodPrice = 0;
        double flightstartPrice = 100.00;
        double flightendPrice = 100.00;
        double seatPrice = 0;

        if (Passenger1.getAge() < 12){
            ageDiscount = 0.80;
        } else if (Passenger1.getAge() > 70){
            ageDiscount = 0.70;
        }

        output.println("Age discount:" +(100 - (ageDiscount * 100))  + "%");


        switch (this.getAirlineName()){
            case "elal":
                airlinePrice = 175.00;
                break;
            case "air canada":
                airlinePrice = 125.00;
                break;
            case "emirates":
                airlinePrice = 225.00;
                break;

        }

        output.printf("Airline Price: $" + "%.2f", airlinePrice);
        output.println("");


        switch (Passenger1.getFoodChoice()){
            case "kosher":
                foodPrice = 225.00;
                break;
            case "hallal":
                foodPrice = 200.00;
                break;
            case "vegan":
                foodPrice = 275.00;
                break;
            case "beef":
                foodPrice = 125.00;
                break;
            case "egg":
                foodPrice = 75.00;
                break;
        }

        output.printf("Food Price: $" + "%.2f", foodPrice);
        output.println("");

        if (this.getFlightStartDate() <= 7){
            flightstartPrice = 500.00;
        } else if (this.getFlightStartDate() <= 28){
            flightstartPrice = 300.00;
        }



        if (this.getFlightEndDate() >= 21){
            flightendPrice = this.getFlightEndDate() * 7.5;
        }

        output.printf("Flight Date Price: $" + "%.2f", (flightendPrice + flightstartPrice));
        output.println("");

        switch (Passenger1.getSeatChoice()){
            case "first":
                seatPrice = 325.00;
                break;
            case "premium economy":
                seatPrice = 225.00;
                break;
            case "economy":
                seatPrice = 175.00;
                break;
            case "business":
                seatPrice = 125.00;
                break;
        }

        output.printf("Seat Cabin Price: $" + "%.2f", seatPrice);
        output.println("");

        price = (foodPrice + flightstartPrice + flightendPrice + seatPrice + airlinePrice) * ageDiscount * 1.13;

        output.printf("Final Price: $" + "%.2f", price);

        output.close();

        return price;
    }

    // compares other tickets purchased by the Date they are leaving
    @Override
    public int compareTo(PassengerPlane OtherPassengerPlane) {
        if (this.getFlightStartDate() > OtherPassengerPlane.getFlightStartDate()){
            return 1;
        } else if (OtherPassengerPlane.getFlightStartDate() > this.getFlightStartDate()){
            return -1;
        } else{
            return 0;
        }
    }

}

// runs the main code here

class AirplaneReservation {

    public static int Syear, Smonth, Sday, Eyear, Emonth, Eday;

    public static Scanner scan;

    // asks user for all the inputs needed and stores them in the Passenger class and the PassengerPlane class
    public static void AllUserInput (Passenger passenger1, PassengerPlane ticket1){

        ZoneId zonedId = ZoneId.of( "America/Montreal" );
        LocalDate today = LocalDate.now( zonedId );

        while(true){
            scan = new Scanner(System.in);
            System.out.println("Please enter your full name (First name followed by the Last name):");
            String name = scan.nextLine();
            if (name.contains(" ")){
                passenger1.setName(name);
                break;
            }
            System.out.println("Improper Name input ");
        }
        while(true){
            scan = new Scanner(System.in);
            System.out.println("Please enter your age:");
            int age = tryCatchInt();
            if (age > 0 && age < 100){
                passenger1.setAge(age);
                break;
            }
            System.out.println("Improper age input ");
        }
        boolean keeplooping = true;
        while(keeplooping){
            scan = new Scanner(System.in);

            System.out.println("What food choice would you like in the airplane (Beef, Hallal, Kosher, Vegan, Egg):");
            String foodChoice = scan.nextLine();
            switch (foodChoice.toLowerCase()){
                case "kosher":
                case "hallal":
                case "vegan":
                case "beef":
                case "egg":
                    passenger1.setFoodChoice(foodChoice);
                    keeplooping = false;
                    break;
            }
            if (keeplooping){
                System.out.println("Improper input Food Choice");
            }
        }
        while(!keeplooping){
            scan = new Scanner(System.in);
            System.out.println("what Cabin choice would you like in the airplane (First, Premium Economy, Economy, Business):");

            String seatChoice = scan.nextLine();

            switch (seatChoice.toLowerCase()){
                case "first":
                case "premium economy":
                case "economy":
                case "business":
                    passenger1.setSeatChoice(seatChoice);
                    keeplooping = true;
                    break;

            }
            if (!keeplooping){
                System.out.println("Improper input Seat Choice");
            }
        }

        while(keeplooping){
            scan = new Scanner(System.in);
            System.out.println("what airline would you like to fly with (Elal, Emirates, Air Canada):");

            String airplaneName = scan.nextLine();

            switch (airplaneName.toLowerCase()){
                case "elal":
                case "air canada":
                case "emirates":
                    ticket1.setAirlineName(airplaneName);
                    ticket1.setRows(airplaneName);
                    keeplooping = false;
                    break;

            }
            if (keeplooping){
                System.out.println("Improper input Seat Choice");
            }
        }

        boolean keepLoopingStartDate = false;

        while (!keepLoopingStartDate){

            System.out.println("what year would you like to fly:");
            Syear = tryCatchInt();
            System.out.println("what month would you like to fly (1-12):");
            Smonth = tryCatchInt();
            System.out.println("what day would you like to fly (1-28/30/31):");
            Sday = tryCatchInt();

            keepLoopingStartDate  = checkStartDate(today, Syear, Smonth, Sday);
        }

        ticket1.setFlightStartDate((int)(ChronoUnit.DAYS.between( today , LocalDate.of( Syear , Smonth , Sday ))));

        boolean keepLoopingEndDate = false;

        while (!keepLoopingEndDate){

            System.out.println("what year would you like to come back:");
            Eyear = tryCatchInt();
            System.out.println("what month would you like to come back (1-12):");
            Emonth = tryCatchInt();
            System.out.println("what day would you like to come back (1-28/30/31):");
            Eday = tryCatchInt();

            keepLoopingEndDate  = CheckEndDate(Syear, Smonth, Sday, Eyear, Emonth, Eday);
        }


        ticket1.setFlightEndDate((int)(ChronoUnit.DAYS.between( LocalDate.of( Syear , Smonth , Sday ) , LocalDate.of( Eyear , Emonth , Eday ))));

    }

    //checks if end flight date is after the start flight date
    public static boolean CheckEndDate(int Syear, int Smonth, int Sday, int Eyear, int Emonth, int Eday){
        LocalDate tryStartDate = LocalDate.of( Syear , Smonth , Sday );
        try {
            LocalDate.of( Eyear , Emonth , Eday );
        }
        catch(DateTimeException d){
            System.out.println("Date Error");
            return false;
        }


        LocalDate tryEndDate = LocalDate.of( Eyear , Emonth , Eday );

        long daysBetween = ChronoUnit.DAYS.between( tryStartDate , tryEndDate);

        if (daysBetween < 1){
            System.out.println("Date has already passed, please choose another date yu would like to return");
            return false;
        } else{
            return true;
        }
    }
    //This method makes sure that the user inputs information that is acceptable
    private static int tryCatchInt() {
        int info;
        while (true) {
            Scanner scan = new Scanner(System.in);
            String userInp = scan.nextLine();
            try {
                info = Integer.parseInt(userInp);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again:");
            }
        }
        return info;
    }


    //checks if start flight date is after today's date (EST)
    public static boolean checkStartDate(LocalDate today, int Syear, int Smonth, int Sday){
        try {
            LocalDate.of( Syear , Smonth , Sday ) ;
        }
        catch(DateTimeException d){
            System.out.println("Date Error");
            return false;
        }

        LocalDate tryStartDate = LocalDate.of( Syear , Smonth , Sday ) ;

        long daysBetween = ChronoUnit.DAYS.between( today , tryStartDate);

        if (daysBetween < 1){
            System.out.println("Date has already passed, please choose another travel date");
            return false;
        } else{
            return true;
        }
    }


    //  read the information given in a txt file into the user inputs
    public static void ReadFromTxt(Passenger passenger1, PassengerPlane ticket1){
        try {
            FileReader fr = new FileReader("PassengerData.txt");
            BufferedReader input = new BufferedReader(fr);

            input.readLine(); // Name (must include first and last name):
            String line = input.readLine();
            if (line != null) passenger1.setName(line);

            input.readLine(); // age:
            line = input.readLine();
            if (line != null) passenger1.setAge(Integer.parseInt(line));

            input.readLine(); // food Choice (beef, hallal, egg, vegan, kosher):
            line = input.readLine();
            if (line != null) passenger1.setFoodChoice(line);

            input.readLine(); // food Choice (beef, hallal, egg, vegan, kosher):
            line = input.readLine();
            if (line != null) passenger1.setFoodChoice(line);

            input.readLine(); // Airline Name (elal, emirates, air canada):
            line = input.readLine();
            if (line != null) ticket1.setAirlineName(line);
            ticket1.setRows(line);

            input.readLine(); // year of deperture:
            line = input.readLine();
            if (line != null) Syear = Integer.parseInt(line);

            input.readLine(); // month of deperture (1-12):
            line = input.readLine();
            if (line != null) Smonth = Integer.parseInt(line);

            input.readLine(); // day of deperture (1-28/30/31):
            line = input.readLine();
            if (line != null) Sday = Integer.parseInt(line);

            input.readLine(); // year of return:
            line = input.readLine();
            if (line != null) Eyear = Integer.parseInt(line);

            input.readLine(); // month of return (1-12):
            line = input.readLine();
            if (line != null) Emonth = Integer.parseInt(line);

            input.readLine(); // day of return (1-12):
            line = input.readLine();
            if (line != null) Eday = Integer.parseInt(line);

            ZoneId zonedId = ZoneId.of( "America/Montreal" );
            LocalDate today = LocalDate.now( zonedId );

            ticket1.setFlightStartDate((int)ChronoUnit.DAYS.between( today , LocalDate.of( Syear , Smonth , Sday )));
            ticket1.setFlightEndDate((int)ChronoUnit.DAYS.between( LocalDate.of( Syear , Smonth , Sday ) , LocalDate.of( Eyear , Emonth , Eday )));




            input.close();
        }
        catch(IOException e){
            System.out.println("Error. file not found.");
        }

    }


    public static void main(String[] args) throws IOException{

        scan = new Scanner (System.in);

        Passenger passenger1 = new Passenger();
        PassengerPlane ticket1 = new PassengerPlane();


        // asks for user input or to read from text file: PassengerData.txt
        boolean keepLoopUser = true;

        System.out.println("Would you like use the info from PassengerData.txt? (y/n)");

        String userChoice = scan.nextLine();

        if (userChoice.equalsIgnoreCase("y")){
            ReadFromTxt(passenger1,ticket1);
            System.out.println("Name :" + passenger1.getName());
            System.out.println("Age: " + passenger1.getAge());
            System.out.println("food Choice: " + passenger1.getFoodChoice().toUpperCase(Locale.ROOT));
            System.out.println("Cabin choice: " + passenger1.getSeatChoice().toUpperCase(Locale.ROOT));
            System.out.println("Airline Name: " + ticket1.getAirlineName().toUpperCase(Locale.ROOT));
        } else{
            while(keepLoopUser){
                AllUserInput(passenger1, ticket1);
                System.out.println("Please confirm: (y/n) if you have selected no, you are must answer all questions again");
                String UserChoice = scan.nextLine();


                if (UserChoice.equals("y") || UserChoice.equals("Y") || UserChoice.equals("yes" ) || UserChoice.equals("Yes")){
                    keepLoopUser = false;
                }

            }
        }




        boolean [] filled = ticket1.fillSeats(ticket1.getRows(), ticket1.getColumns());
        // shows plane model
        ticket1.DrawAirplane(filled);


        String [] alphabet = new String [] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        //converts alphabet array to array list so that it is possible to index of
        ArrayList <String> AlphabetArray = new ArrayList<>();

        for (int i = 0; i < alphabet.length; i++){
            AlphabetArray.add(alphabet[i]);
        }

        boolean keepLooping = true;

        // seat choosing - asks user for what seat they would like to purchase
        while(keepLooping){
            System.out.println("\n what seat row would you like (A-" + alphabet[ticket1.getRows()-1]  + ") ***Must be UpperCase***:");
            String AlphebetUserRow = scan.nextLine();

            AlphabetArray.indexOf((AlphebetUserRow.toUpperCase()));

            int UserRow = AlphabetArray.indexOf(AlphebetUserRow);

            boolean checkRow = false;

            switch (passenger1.getSeatChoice()){
                case "first":
                    if (UserRow < 3){
                        checkRow = true;
                    }
                    break;
                case "premium economy":
                    if (UserRow < 7){
                        checkRow = true;
                    }
                    break;
                case "economy":
                    if (UserRow < 11){
                        checkRow = true;
                    }
                    break;
                case "business":
                    if (UserRow >= 11){
                        checkRow = true;
                    }
                    break;
            }
            if (UserRow > ticket1.getRows() - 1){
                System.out.println("Row cannot be greater than the row letter given");

            } else{

                if (UserRow < 0 || !checkRow){
                    System.out.println("Seat does not match your cabin class / **Must be Uppercase**");
                } else{
                    int UserSeatNum;
                    while (true){
                        if (UserRow < 3){
                            System.out.println("what seat number would you like (1-6):");
                            UserSeatNum = tryCatchInt();
                            if (UserSeatNum < 7){
                                break;
                            } else{
                                System.out.println("Seat number does not exist, enter a filled seat to change your row Alphabet");
                            }
                        }else{
                            System.out.println("what seat number would you like (1-7):");
                            UserSeatNum = tryCatchInt();
                            break;
                        }
                    }

                    if (!filled[((7* (UserRow)) + UserSeatNum)-1]){
                        System.out.println("Please confirm that " + AlphebetUserRow + UserSeatNum + " is the seat you would like to purchase (y/n)");
                        String UserChoiceConfirm = scan.nextLine();

                        if (UserChoiceConfirm.equalsIgnoreCase("y")){
                            keepLooping = false;
                        }
                    } else{
                        System.out.println("Seat is filled");
                    }

                }

            }


        }
        // output final price
        System.out.printf(passenger1.getName() + ", your Final price is: $" + "%.2f", (ticket1.OneTicketPrice(passenger1)));
        System.out.println(", thank you for choosing " + ticket1.getAirlineName().toUpperCase(Locale.ROOT));




    }
}