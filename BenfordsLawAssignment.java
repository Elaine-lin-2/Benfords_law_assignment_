/*
* Date: May 5, 2021
* Names: Krishna Basani & Elaine Lin
* Teacher: Mr.Ho
*
* Description: 
* 
* The program allows the user to read the file containing the total sales amount
* and check the sales data for possible accounting fraud.
*
* It does this by finding the percentage of first digit occurences for each number from 1 to 9. 
*
* It then outputs the numbers and percentages, generates a bar graph and creates a CSV file with the results.
* 
*/

//import jFreeChart applictions
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

//Import other neccessary APIs
import java.util.Scanner;	
import java.io.*;
import java.io.PrintWriter;


public class BenfordsLawAssignment extends ApplicationFrame {
    public static void main(String[] args){
    	Scanner reader = new Scanner(System.in);
    
		//Generate new int-type array
    	int[] digitOccurrences = new int[10];
		
    	boolean isThereFraud = false;
    	
    	do{
    		String userInput = "";
        	
			//Prints the menu
        	printMenu();

			//prompts an option from user
            userInput = reader.nextLine();
			
    		//Executes the loadFile method
    		if (userInput.equals("1")){
    			digitOccurrences = loadFile(digitOccurrences);
                System.out.println("Sales data has been loaded.");
				
    		}

			//Executes the checkSalesData method
    		else if(userInput.equals("2")){
    			isThereFraud = checkSalesData(digitOccurrences);
                System.out.println(); // Spacing
    		}

			//Executes the exportCSV method
            else if(userInput.equals("3")){
    			exportCSV(digitOccurrences);

				//Open and run the jFreeChart application
				BenfordsLawAssignment chart = new BenfordsLawAssignment("Digit Occurrences Percentages Bar Graph", "Digit Occurrence Percentages Bar Graph", digitOccurrences);
				chart.pack();
				RefineryUtilities.centerFrameOnScreen(chart);
				chart.setVisible(true);
    		}
			
			//Detects invalid input; prompts re-input from the user
            else{
                System.out.println("Please type in a valid option (A number from 1-3)");
            }
			 
		//Exit conditions
    	} while (true);	
    }
    
	/*
	* Prints the menu
	* 
	* @param - no parameters
	* @return - is void type method; does not return a value
	* @Author - Krishna & Elaine
	*/
    public static void printMenu(){

		//Prints user options
        System.out.println("Press <1> to load the file");
        System.out.println("press <2> to check for accounting fraud");
        System.out.println("press <3> to print the results in a CSV file and generate visual representation");
    }

	/*
	* Loads the sales.csv file
	* 
	* @param - int type array
	* @return - the digit occurrences of each number (1-9)
	* @Author - Krishna & Elaine
	*/
    public static int[] loadFile(int[] digitOccurrences){	

		//Saves digit occurrences
    	for (int i = 0; i < digitOccurrences.length; i++){
    		digitOccurrences[i] = 0;
    	}

		//Define the variables
		int len;
        String fileName = "sales.csv";
		
    	try{

			//Opens and scans the CSV
            File spreadSheet = new File(fileName);
            Scanner fileReader = new Scanner(spreadSheet);
    		int firstDigitInt = 0;
    		fileReader.nextLine();

			//If there is a line after
			while(fileReader.hasNextLine()){
                String line = fileReader.nextLine();

				//Finds the length of each line
                len = line.length();
                for(int i = 0; i< len; i++){
                    
                    //if statement to check for comma
                    if(line.charAt(i) == ','){
                        char firstDigitString = line.charAt(i+1);
                        firstDigitInt = Character.getNumericValue(firstDigitString);
                    }
                }

				//keeps track of digit occurrences
				digitOccurrences[firstDigitInt]++;
    			digitOccurrences[0]++;
            }

    	}
    	catch(FileNotFoundException e){
    		System.out.println(e);
    	}
    	return digitOccurrences;
    }
	
	/*
	* Checks and varifies the Sales Data
	* 
	* @param - int type array
	* @return - the result of the validation (true /false statement)
	* @Author - Krishna
	*/
    public static boolean checkSalesData(int[] digitOccurrences){
    	
		//Define variables
        double digitPercentage = 0;
    	double frequencyOfDigitOne = 0;   
    	
    	/*
        for (int i = 1; i < digitOccurrences.length; i++){
    		System.out.println(i + " appeared " + digitOccurrences[i] + " times.");
    	}
		*/

		//Iterates the array
    	for (int i = 1; i < digitOccurrences.length; i++){	

			//Calculates the percentage
    		digitPercentage = Math.round(((double)(digitOccurrences[i])/(double)(digitOccurrences[0])) * 1000) / 10.0;
    		System.out.println(i + " occurred " + digitPercentage + "% of the time.");
    		if (i == 1){

				//Saves digit percentage for digit 1 in variable frequencyOfDigitOne
    			frequencyOfDigitOne = digitPercentage;
    		}
    	}
		
    	//Detects and checks for fraud 
    	if (frequencyOfDigitOne >= 29 && frequencyOfDigitOne <= 32){

    		System.out.println("There is no sales fraud detected.");
    		return false;
    	}
    	else{
    		System.out.println("Sales fraud was detected.");
    		return true;
    	}
    }

	/*
	* Exports the digit occurrences as a CSV
	* 
	* @param - int type array
	* @return - void type method; does not return a value
	* @Author - Elaine
	*/
    public static void exportCSV(int[] digitOccurrences){
        
		//Defines variables
        double digitPercentage = 0;

        try{
            File outFile = new File("results.csv");
            PrintWriter out = new PrintWriter(outFile);

            //Generates the CSV
            if(outFile.exists()){
                
				//Prints the reuslts
                for (int i = 1; i < digitOccurrences.length; i++){
					
                    digitPercentage = Math.round(((double)(digitOccurrences[i])/(double)(digitOccurrences[0])) * 1000) / 10.0;         
					out.println(i + " occurred " + digitPercentage + "% of the time.");        
                }
				System.out.println("Your information can now be found in 'results.csv'.");
            }
            System.out.println(); // Spacing
            out.close();
        }

        catch(FileNotFoundException e){
            System.out.println(e);
        }
    }

	/*
	* Generate the dataset for the bar graph
	* 
	* @param - int type array
	* @return - method does not return a value
	* @Author - Elaine
	*/
	public CategoryDataset createDataset(int[] digitOccurrences){
	
		double digitPercentage =0;
		//Create a new dataset
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
  
		//Call array and import data into the graph
		for(int i=1; i<digitOccurrences.length;i++){
			digitPercentage = Math.round(((double)(digitOccurrences[i])/(double)(digitOccurrences[0])) * 1000) / 10.0;
			dataset.addValue(digitPercentage, "digitOccurences" , "digit " + i);
		}
		
		return dataset; 
	}

	/*
	* Generate visual representation
	* 
	* @param - application title, visual title, int type array
	* @return - method does not return a value
	* @Author - Elaine
	*/
	public BenfordsLawAssignment(String applicationTitle,String chartTitle, int[] digitOccurrences) {
		super(applicationTitle);        
		JFreeChart barChart = ChartFactory.createBarChart(

		//Set chart title
		chartTitle,
		"Digit Occurrences Percentage",
		"Digits (1-9)",
		createDataset(digitOccurrences),
		PlotOrientation.VERTICAL,           
		true, true, false);
		
		//Generate bar graph
		ChartPanel chartPanel = new ChartPanel(barChart);        

		//Set the dimensions
		chartPanel.setPreferredSize(new java.awt.Dimension(560,367));        
		setContentPane(chartPanel); 
	}
}