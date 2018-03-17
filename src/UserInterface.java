import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class UserInterface {

    static Scanner userInput=new Scanner(System.in);
    static ArrayList<BankAccount> allBankAccounts=new ArrayList<>();//this array list is to store all thee bank accounts
    static ArrayList<User> users=new ArrayList<>();//this array list is to store all the customer accounts
    static String loggedUserName;
    static String loggedPassword;
    static boolean isLoggedUserAuthorized;

    public static void main(String[]args){

        //read from the file
        dataPersistency("read");
        int switchNumber;
        int exitNumber;
        boolean continueToMainMenu = true;
        /*if this is true the main menu will be
        displayed. The user will be able to create a new customer account or to
        logging in to an existing customer account
         */

        dataPersistency("read");//to read from the file

        System.out.println("---------------------------------------------------------");
        System.out.println("Welcome to the Customer and Account Management System");
        System.out.println("---------------------------------------------------------");

        do {
            String userName;
            String password;

            //logging into the appilication
            System.out.println("Please enter your user name");
            userName = userInput.nextLine();
            System.out.println("Please enter your password");
            password = userInput.next();

            boolean isUserExisting = false;
            for (int i = 0; i < users.size(); i++) {
                if ((userName.equals(users.get(i)) && password.equals(users.get(i).getPassword())) ||
                        (userName.equals("admin") && password.equals("123"))) {
                    isLoggedUserAuthorized = users.get(i).isAuthorized();
                    isUserExisting = true;
                }
            }

            if (isUserExisting) {

                if (isLoggedUserAuthorized) {
                    displayAuthorizedMenu();

                } else {
                    displayCustomerMenu();
                }

                System.out.println("Do you want to exit from the application. If you want to exit please enter ,1");
                exitNumber = validateAccountNumber();
                if (exitNumber == 1) {
                    continueToMainMenu=false;
                }

            } else {
                System.out.println("Sorry ,invalid user name or password!!");
            }

        }while(continueToMainMenu);

        dataPersistency("write");
    }

    /*------------------------------------------------------------------------------------------------------------------
    THESE ARE THE USER INPUTS VALIDATION METHODS
    this method is to check whether a number entered by user is an integer*/
    public static int validate() {
        int validatedInput = 0;

        do {
            while (!userInput.hasNextInt()) {
                System.out.println("Invalid input,Please re-enter!");
                System.out.print("Enter : ");
                userInput.next();
            }
            validatedInput = userInput.nextInt();
            if (validatedInput <= 0) {
                System.out.println("Invalid input,Please re-enter!");
                System.out.print("Enter : ");
            }
            userInput.nextLine();
        } while (validatedInput <= 0);
        return validatedInput;
    }

    //this method is to validate double inputs
    public static double validateDouble() {

        double validatedInput = 0;

        do {
            while (!userInput.hasNextDouble()) {
                System.out.println("Invalid input,Please re-enter!");
                System.out.print("Enter : ");
                userInput.next();
            }
            validatedInput = userInput.nextDouble();
            if (validatedInput <= 0) {
                System.out.println("Invalid input,Please re-enter!");
                System.out.print("Enter : ");
            }
            userInput.nextLine();
        } while (validatedInput <= 0);

        return validatedInput;
    }

    //this method to check whether the bank account number is between 1000 and 9999
    public static int validateAccountNumber() {

        int validatedAccountNumber;

        do {
            while (!userInput.hasNextInt()) {
                System.out.println("Invalid input,Please re-enter!");
                System.out.print("Enter : ");
                userInput.next();
            }
            validatedAccountNumber = userInput.nextInt();
            if (validatedAccountNumber < 0) {
                System.out.println("Invalid input,Please re-enter!");
                System.out.print("Enter : ");
            }
            userInput.nextLine();
        } while (validatedAccountNumber < 0);

        while (!(validatedAccountNumber >= 1000 && validatedAccountNumber <= 9999) && validatedAccountNumber != 0) {
            System.out.println("Please enter a number between 1000 and 9999 as the "
                    + "bank account number.");
            System.out.print("Enter : ");
            validatedAccountNumber = userInput.nextInt();
        }

        return validatedAccountNumber;

    }
    //End of validate methods-------------------------------------------------------------------------------------------

    //this method is to write and read into files
    public static void dataPersistency(String whatToDo) {

        if (whatToDo.equals("read")) {
            //Read from the files
            try {
                FileInputStream fis1 = new FileInputStream("BankAccounts.dat");
                ObjectInputStream input = new ObjectInputStream(fis1);
                allBankAccounts = (ArrayList<BankAccount>) input.readObject();
                System.out.println("Successfully transfered from the file.");
                input.close();
            } catch (FileNotFoundException e2) {
                System.out.println("Couldn't find the file.");
            } catch (IOException ex) {
                System.out.println("Sorry error");
            } catch (ClassNotFoundException ex) {
                System.out.println("CNF error");
            }
        } else if (whatToDo.equals("write")) {

            //write bank account details into text file
            FileOutputStream s3 = null;
            PrintWriter outputStream3 = null;

            try {
                s3 = new FileOutputStream("details.txt");
                outputStream3 = new PrintWriter(s3);
                for (int i = 0; i < allBankAccounts.size(); i++) {
                    outputStream3.println("_________________________________________________________");
                    outputStream3.println("Account Number      :- " + allBankAccounts.get(i).getAccountNumber());
                    outputStream3.println("Customer Name       :- " + allBankAccounts.get(i).getCustomerName());
                    outputStream3.println("Password            :- " + allBankAccounts.get(i).getPassword());
                    outputStream3.println("Account Balance     :- " + allBankAccounts.get(i).getAccountBalance());
                    outputStream3.println("Automatic Withdrowal:- " + allBankAccounts.get(i).getAutoWithdrowal());
                    outputStream3.println("Automstic Deposit    :- " + allBankAccounts.get(i).getAutoDeposit());
                    outputStream3.println("_________________________________________________________");

                }
            } catch (FileNotFoundException e) {
                System.out.println("Sorry the file couldn't be found.");
            }
            outputStream3.close();
            System.out.println("Successfully saved to the file.");
            try {
                FileOutputStream s = new FileOutputStream("BankAccounts.dat");
                ObjectOutputStream output = new ObjectOutputStream(s);
                output.writeObject(allBankAccounts);
                output.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Sorry File couldn't be found.");
            } catch (IOException ex) {
                System.out.println("Sorry saving is unsuccessfull.");
            }
        }
    }

    //This menu will be displayed only for a authorization user after login
    public static void displayAuthorizedMenu(){
        int number;
        System.out.println("---------------------------------------------------------");
        System.out.println("1 :- To make a customer account");
        System.out.println("2 :- To make a bank account");
        System.out.println("3 :- To log out from the current customer account");
        System.out.print("Enter :- ");
        number=validate();
        System.out.println("---------------------------------------------------------");

        switch (number){
            case 1:
                User user =User.makeUserAccount(users);
                users.add(user);
                break;
            case 2:
                //The user will be able to make only 10 bank accounts
                BankAccount[] bankAccountsOfTheUser=new BankAccount[10];
                BankAccount bankAccount=BankAccount.enterAccountData(user.getUserName(),user.getPassword());
                break;
            case 3:
                break;
        }
    }

    //This method will be displayed only for a customer after login
    public static void displayCustomerMenu() {
        int number;
        boolean wantToContinue=false;
        do {
            System.out.println("---------------------------------------------------------");
            System.out.println("Please enter");
            System.out.println("    1 :- To check the account balance");
            System.out.println("    2 :- To do a transfer");
            System.out.println("    3 :- To log out");
            System.out.print("Enter :- ");
            number = validate();
            System.out.println("---------------------------------------------------------");

            switch (number) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    wantToContinue=false;
                    break;
            }

        }while(wantToContinue);
    }
}
