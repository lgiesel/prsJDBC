package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;

public class Console {//Like a utility class to use tools to do things
    
    private static Scanner sc = new Scanner(System.in);

    public static void printToConsole(String str) {//Create another method for data validation on input
    	System.out.println(str);
    }
    
    public static String getString(String prompt) {
        System.out.print(prompt);
        String s = sc.next();  // read user entry
        sc.nextLine();  // discard any other data entered on the line
        return s;
    }

    public static String getString(String prompt, int maxLength) {
        boolean isValid = false;
        String s = "";
        while (!isValid){
            System.out.print(prompt);
            s = sc.next();  // read user entry
            if (s.toString().length() > maxLength) { 
                System.out.println("Error! Entry must be < " + maxLength + ". Try again.");
            } else {
                isValid = true;
            }
        }       
        sc.nextLine();  // discard any other data entered on the line
        return s;
    }

    public static String getString(String prompt, String s1, String s2) {
        String s = "";
        boolean isValid = false;
        while (!isValid) {
            s = getString(prompt);
            if (!s.equalsIgnoreCase(s1) && !s.equalsIgnoreCase(s2)) {
                System.out.println("Error! Entry must be '" + s1 + "' or '" +
                        s2 + "'. Try again.");
            } else {
                isValid = true;
            }
        }
        return s;
    }

    public static String getString(String prompt, int maxLength, boolean isLine) {
        boolean isValid = false;
        String s = "";
        while (!isValid){
            System.out.print(prompt);
            s = sc.nextLine();  // read user entry
            if (s.toString().length() > maxLength) { 
                System.out.println("Error! Entry must be < " + maxLength + ". Try again.");
            } else {
                isValid = true;
            }
        }       
        sc.nextLine();  // discard any other data entered on the line
        return s;
    }
        
    public static boolean getBoolean(String prompt) {
    	boolean b = false;
    	boolean isValid = false;
    	
    	while(!isValid) {
        	String s = getString(prompt);
        	if (s.equalsIgnoreCase("t") || s.equalsIgnoreCase("true")) {
        		b=true;
        		isValid=true;
        	} else if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("false")){
        		b = false;
        		isValid=true;
        	} else {
        		System.out.println("Invalid input. T, t, F, f expected.");    		
        	}
    	}
    	return b;
    }

    public static int getInt(String prompt) {
        int i = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                i = sc.nextInt();
                isValid = true;
            } else {
                System.out.println("Error! Invalid integer. Try again.");
            }
            sc.nextLine();  // discard any other data entered on the line
        }
        return i;
    }

    public static int getInt(String prompt, int min, int max) {
        int i = 0;
        boolean isValid = false;
        while (!isValid) {
            i = getInt(prompt);
            if (i < min || i > max) {
            	 printToConsole("Error! Number must be  >= " + min + " and <= " + max + ".");
            } else {
                isValid = true;
            }
        }
        return i;
    }
           
    public static double getDouble(String prompt) {
        double d = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                d = sc.nextDouble();
                isValid = true;
            } else {
                System.out.println("Error! Invalid number. Try again.");
            }
            sc.nextLine();  // discard any other data entered on the line
        }
        return d;
    }

    public static double getDouble(String prompt, double min, double max) {
        double d = 0;
        boolean isValid = false;
        while (!isValid) {
            d = getDouble(prompt);
            if (d <= min) {
                System.out.println(
                        "Error! Number must be greater than " + min + ".");
            } else if (d >= max) {
                System.out.println(
                        "Error! Number must be less than " + max + ".");
            } else {
                isValid = true;
            }
        }
        return d;
    }
 
    public static LocalDate getDateFromString(String dateStr) {       
          return LocalDate.parse(dateStr);
    }
    
    //Extra comment
}