/*
* Date: May 5, 2021
* Names: Krishna & Elaine
* Teacher: Mr.Ho
* Description: The program allows the user to read the file containing the total sales amount
* and check the sales data for possible accounting fraud
*/

import java.util.Scanner;	//Library for the scanner
import java.io.*;
import java.io.PrintWriter;

class BenLaw {
    
    public static void main(String[] args)
    {
    	Scanner reader = new Scanner(System.in);
    
    	int[] digitOccurrences = new int[10];
    	boolean isThereFraud = false;
    	
    	do{
    		String userInput = "";
        	
        	printMenu();
            userInput = reader.nextLine();
    		
    		if (userInput.equals("1")){
    			digitOccurrences = loadFile(digitOccurrences);
                System.out.println("Sales data has been loaded.");
    		}
    		else if(userInput.equals("2")){
    			isThereFraud = checkSalesData(digitOccurrences);
                System.out.println(); // Spacing
    		}
            else if(userInput.equals("3")){
    			exportCSV(digitOccurrences);
    		}
            else{
                System.out.println("Please type in a valid option (A number from 0-3)");
            }
    		
    	} while (true);
    }
    
    public static void printMenu(){
        System.out.println("Press <1> to load the file");
        System.out.println("press <2> to check for accounting fraud");
        System.out.println("press <3> to print the results in a CSV file");
    }

    public static int[] loadFile(int[] digitOccurrences)
    {	
    	for (int i = 0; i < digitOccurrences.length; i++){
    		digitOccurrences[i] = 0;
    	}

        String fileName = "sales.csv";
    	
    	try{

            File spreadSheet = new File(fileName);
            Scanner fileReader = new Scanner(spreadSheet);

    		fileReader.useDelimiter(",");
    		
    		String fileLine = "";
    		String firstDigitString = "";
    		int firstDigitInt = 0;
    		
    		fileReader.nextLine();
    		
    		while(fileReader.hasNextLine() == true){

    			fileLine = fileReader.nextLine();
    			firstDigitString = fileLine.substring(fileLine.indexOf(",") + 1, fileLine.indexOf(",") + 2);
    			
    			firstDigitInt = Integer.parseInt(firstDigitString);
    			digitOccurrences[firstDigitInt]++;
    			digitOccurrences[0]++;
    		}
    	}

    	catch(FileNotFoundException e){
    		System.out.println(e);
    	}
    	
    	return digitOccurrences;
    }
    
    public static boolean checkSalesData(int[] digitOccurrences){
    	
        double digitPercentage = 0;
    	double frequencyOfDigitOne = 0;   
    	
    	/*
        for (int i = 1; i < digitOccurrences.length; i++){
    		System.out.println(i + " appeared " + digitOccurrences[i] + " times.");
    	}
		*/

    	
    	for (int i = 1; i < digitOccurrences.length; i++){	
    		digitPercentage = Math.round(((double)(digitOccurrences[i])/(double)(digitOccurrences[0])) * 1000) / 10.0;
    		//System.out.println(i + " occurred " + digitPercentage + "% of the time.");
    		if (i == 1){
    			frequencyOfDigitOne = digitPercentage;
    		}
    	}
		
    	
    	if (frequencyOfDigitOne >= 29 && frequencyOfDigitOne <= 32){

    		System.out.println("\nThere is no sales fraud detected.");
    		return false;
    	}

    	else{
    		System.out.println("\nSales fraud was detected.");
    		return true;
    	}
    }

    public static void exportCSV(int[] digitOccurrences){
        
        double digitPercentage = 0;
    	double frequencyOfDigitOne = 0;

        try{
            File outFile = new File("results.csv");
            PrintWriter out = new PrintWriter(outFile);

            
            if(outFile.exists()){
                
                for (int i = 1; i < digitOccurrences.length; i++){	
                    digitPercentage = Math.round(((double)(digitOccurrences[i])/(double)(digitOccurrences[0])) * 1000) / 10.0;
                    
                    out.println(i + " occurred " + digitPercentage + "% of the time."); //
                    
                    if (i == 1){
                        frequencyOfDigitOne = digitPercentage;
                    }
                }
            }
            
            System.out.println("Your information can now be found in 'results.csv'.");
            System.out.println(); // Spacing

            out.close();
        }

        catch(FileNotFoundException e){
            System.out.println(e);
        }

    }
}