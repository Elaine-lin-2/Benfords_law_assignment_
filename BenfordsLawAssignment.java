/*
* Date: May 5, 2021
* Names: Krishna & Elaine
* Teacher: Mr.Ho
* Description: The program allows the user to read the file containing the total sales amount
* and check the sales data for possible accounting fraud
*/

//import Java APIs
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

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
				BenfordsLawAssignment chart = new BenfordsLawAssignment("Digit Occurrences", "Digit Occurrences", digitOccurrences);
				chart.pack();
				RefineryUtilities.centerFrameOnScreen(chart);
				chart.setVisible(true);
				//System.exit(0);
    		}
			
			//Detects invalid input; prompts re-input from the user
            else{
                System.out.println("Please type in a valid option (A number from 0-3)");
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

		//Define variables
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
				digitOccurrences[firstDigitInt]++;
    			digitOccurrences[0]++;
            }
			

			/*
    		while(fileReader.hasNextLine() == true){

    			fileLine = fileReader.nextLine();
    			firstDigitString = fileLine.substring(fileLine.indexOf(",") + 1, fileLine.indexOf(",") + 2);
    			
    			firstDigitInt = Integer.parseInt(firstDigitString);
    			digitOccurrences[firstDigitInt]++;
    			digitOccurrences[0]++;
    		}
			*/

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

				//
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
    	double frequencyOfDigitOne = 0;

        try{
            File outFile = new File("results.csv");
            PrintWriter out = new PrintWriter(outFile);

            //Generates the CSV
            if(outFile.exists()){
                
				//Prints the reuslts
                for (int i = 1; i < digitOccurrences.length; i++){	
                    digitPercentage = Math.round(((double)(digitOccurrences[i])/(double)(digitOccurrences[0])) * 1000) / 10.0;         
                    
					out.println(i + " occurred " + digitPercentage + "% of the time.");        
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

	/*
	@Override public void start(Stage stage){

        stage.setTitle("Digit Occurrences Bar Graph");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
        new BarChart<String,Number>(xAxis,yAxis);

        bc.setTitle("Digit Occurrences Bar Graph");
        xAxis.setLabel("Digits (1-9)");
        yAxis.setLabel("Digit occurences (%)");

        XYChart.Series series1 = new XYChart.Series();  
		series1.setName("Digit Occurences");

        series1.getData().add(new XYChart.Data("digit 1", 31.5));
        series1.getData().add(new XYChart.Data("digit 2", 13.8));
        series1.getData().add(new XYChart.Data("digit 3", 12.7));
        series1.getData().add(new XYChart.Data("digit 4", 11.0));
        series1.getData().add(new XYChart.Data("digit 5", 9.0));      
		series1.getData().add(new XYChart.Data("digit 6", 6.8));
		series1.getData().add(new XYChart.Data("digit 7", 5.7));
		series1.getData().add(new XYChart.Data("digit 8", 4.3));
		series1.getData().add(new XYChart.Data("digit 9", 5.2));
		
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
		
    }
	*/
	public BenfordsLawAssignment(String applicationTitle,String chartTitle, int[] digitOccurrences) {
		super(applicationTitle);        
		JFreeChart barChart = ChartFactory.createBarChart(
		   chartTitle,
		   "Digit occurences",            
		   "Digits (1-9)",            
		   createDataset(digitOccurrences),
		   PlotOrientation.VERTICAL,           
		   true, true, false);
		   
		ChartPanel chartPanel = new ChartPanel(barChart);        
		chartPanel.setPreferredSize(new java.awt.Dimension(560,367));        
		setContentPane(chartPanel); 
	 }

	 private CategoryDataset createDataset(int[] digitOccurrences){
	
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
  
		for(int i=1; i<digitOccurrences.length;i++){
			dataset.addValue(digitOccurrences[i], "digitOccurences" , "digit " + i);
		}
		  
		/*
		dataset.addValue( 13.8 , "digitOccurences" , "digit 2" );
		dataset.addValue( 12.7, "digitOccurences" , "digit 3" );
		dataset.addValue( 11.0 , "digitOccurences" , "digit 4" );
		dataset.addValue( 9.0 , "digitOccurences" , "digit 5" );
		dataset.addValue( 6.8 , "digitOccurences" , "digit 6" );
		dataset.addValue( 5.7 , "digitOccurences" , "digit 7" );
		dataset.addValue( 4.3 , "digitOccurences" , "digit 8" );
		dataset.addValue( 5.2 , "digitOccurences" , "digit 9" );
		*/

		return dataset; 
	 }

	
}