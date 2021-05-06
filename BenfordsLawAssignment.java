/*
* Date: May 5, 2021
* Names: Krishna & Elaine
* Teacher: Mr.Ho
* Description: The program allows the user to read the file containing the total sales amount
* and check the sales data for possible accounting fraud
*/
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

class BenfordsLawAssignment{
    public static void main(String[]args){

        Scanner reader = new Scanner(System.in);

        String exitCondition, readFile, checkFraud, userInput;
        exitCondition = "0";
        readFile = "1";
        checkFraud = "2";

        do{
            menu();
            userInput = reader.nextLine(); 

            if (userInput.equals(readFile)){       
                System.out.println("readfile");
                readFile();
            }
            else if (userInput.equals(checkFraud)){
                System.out.println("checkfraud");
            }

            else if ((!userInput.equals(readFile)) && (!userInput.equals(checkFraud)) && (!userInput.equals(exitCondition))){
                System.out.println("Please type in a valid option (number 0-2)");
            }
            
            
        } while (!userInput.equals(exitCondition));
    }

    public static void menu(){
        System.out.println("Sales analysis system");
        System.out.println("1 - read the file containing sales");
        System.out.println("2 - check for possible accounting fraud");
        System.out.println("0 - exit");
    }

    public static void readFile(){

        String fileName = "sales.csv";
        int len;
        int totalLen =0;
        int count1 =0, count2 =0, count3=0, count4=0, count5=0, count6=0, count7=0, count8=0, count9=0;

        try{
            File spreadSheet = new File(fileName);
            Scanner scnr = new Scanner(spreadSheet);

            scnr.nextLine();

            while(scnr.hasNextLine()){
                String line = scnr.nextLine();
                len = line.length();
                totalLen ++;

                for(int i = 0; i< len; i++){
                    
                    //if statement to check for blank space
                    if(line.charAt(i) == ','){
                        
                        char first = line.charAt(i+1);
                        int num = Character.getNumericValue(first);
                        //System.out.println(num + ", ");
                    
                        if(num==1){
                            count1 ++;
                        }
                        else if(num==2){
                            count2 ++;
                        }
                        else if(num==3){
                            count3 ++;
                        }
                        else if(num==4){
                            count4 ++;
                        }
                        else if(num==5){
                            count5 ++;
                        }
                        else if(num==6){
                            count6 ++;
                        }
                        else if(num==7){
                            count7 ++;
                        }
                        else if(num==8){
                            count8 ++;
                        }
                        else if(num==9){
                            count9 ++;
                        }
                    }
                }
            }
            System.out.println("%1: " + 1.0* count1*100/totalLen);
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
    }
 
}