/*
* Date: May 5, 2021
* Names: Krishna & Elaine
* Teacher: Mr.Ho
* Description: The program allows the user to read the file containing the total sales amount
* and check the sales data for possible accounting fraud
*/

import java.util.Scanner;	//Library for the scanner
import java.io.*;

class BenLaw {
    
    public static void main(String[] args)
    {
    	Scanner reader = new Scanner(System.in);
    
    	int[] digitOccurrences = new int[10];
    	boolean isThereFraud = false;
    	
    	do 
    	{
    		String userInput = "";
        	
        	while (!(userInput.equals("1") || userInput.equals("2")))
    		{
    			System.out.print("Press <1> to load the file, or press <2> to check for accounting fraud: ");
        		userInput = reader.nextLine();
    		}
    		
    		if (userInput.equals("1"))
    		{
    			digitOccurrences = LoadFile(digitOccurrences);
    		}
    		else
    		{
    			isThereFraud = CheckSalesData(digitOccurrences);
    		}
    		
    	} while (true);
    }
    
    private static int[] LoadFile(int[] digitOccurrences)
    {	
    	for (int i = 0; i < digitOccurrences.length; i++){
    		digitOccurrences[i] = 0;
    	}

        String fileName = "sales.csv";
    	
    	try
    	{
            File spreadSheet = new File(fileName);
            Scanner fileReader = new Scanner(spreadSheet);

    		fileReader.useDelimiter(",");
    		
    		String fileLine = "";
    		String firstDigitString = "";
    		int firstDigitInt = 0;
    		
    		fileReader.nextLine();
    		
    		while(fileReader.hasNextLine() == true)       
    		{
    			fileLine = fileReader.nextLine();
    			firstDigitString = fileLine.substring(fileLine.indexOf(",") + 1, fileLine.indexOf(",") + 2);
    			
    			firstDigitInt = Integer.parseInt(firstDigitString);
    			
    			digitOccurrences[firstDigitInt]++;
    			digitOccurrences[0]++;
    		}
    	}
    	catch(FileNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    	
    	return digitOccurrences;
    }
    
    private static boolean CheckSalesData(int[] digitOccurrences)
    {
    	double digitPercentage = 0;
    	
    	double frequencyOfDigitOne = 0;   
    	
    	
    	for (int i = 1; i < digitOccurrences.length; i++)
    	{
    		System.out.println(i + " appeared " + digitOccurrences[i] + " times.");
    	}
    	
    	
    	for (int i = 1; i < digitOccurrences.length; i++)
    	{	
    		digitPercentage = Math.round(((double)(digitOccurrences[i])/(double)(digitOccurrences[0])) * 1000) / 10.0;
    		System.out.println(i + " occurred " + digitPercentage + "% of the time.");
    		
    		if (i == 1)
    		{
    			frequencyOfDigitOne = digitPercentage;
    		}
    	}
    	
    	if (frequencyOfDigitOne >= 29 && frequencyOfDigitOne <= 32)
    	{
    		System.out.println("\nThere is no sales fraud detected.");
    		return false;
    	}
    	else
    	{
    		System.out.println("\nSales fraud was detected.");
    		return true;
    	}
    } 
}
