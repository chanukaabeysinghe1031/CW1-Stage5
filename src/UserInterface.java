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


    //this araylist is to store details of bank accounts of the logged unauthorized customer
    private static ArrayList<BankAccount> loggedBankAccounts = new ArrayList<BankAccount>();

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
            password = userInput.nextLine();

            boolean isUserExisting = false;
            System.out.println("The number of user accounts is "+ users.size());
            for (int i = 0; i < users.size(); i++) {
                if (userName.equals(users.get(i).getUserName()) && password.equals(users.get(i).getPassword())) {
                    isLoggedUserAuthorized = users.get(i).isAuthorized();
                    isUserExisting = true;
                }
            }
            if(userName.equals("admin")&&password.equals("123")){
                isUserExisting=true;
            }

            if (isUserExisting) {

                if (isLoggedUserAuthorized||userName.equals("admin")) {
                    displayAuthorizedMenu();

                } else {
                    loggedUserName=userName;
                    loggedPassword=password;
                    for(int i=0;i<allBankAccounts.size();i++){
                        if(userName.equals(allBankAccounts.get(i).getCustomerName())){
                            loggedBankAccounts.add(allBankAccounts.get(i));
                        }
                    }
                    System.out.println("The number Of bank accounts of the user is "+loggedBankAccounts.size());
                    displayCustomerMenu();
                }

                System.out.println("Do you want to exit from the application. If you want to exit please enter ,1");
                exitNumber = validate();
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

    //This menu will be displayed only for a authorization user after login
    public static void displayAuthorizedMenu(){
        boolean wantToContinue=true;
        BankAccount[] bankAccountsOfTheUser;
        do {
            int number;
            System.out.println("---------------------------------------------------------");
            System.out.println("Please enter");
            System.out.println("    1 :- To make a customer account");
            System.out.println("    2 :- To make a bank account");
            System.out.println("    3 :- To log out from the current user account");
            System.out.print("Enter :- ");
            number = validate();
            System.out.println("---------------------------------------------------------");

            switch (number) {

                //to make customer account then make bank account for it
                case 1:
                    User user=null;
                    bankAccountsOfTheUser = new BankAccount[10];
                    boolean moreBankAccounts=true;
                    user = User.makeUserAccount(users);
                    //add the user to the array list of users
                    users.add(user);
                    System.out.println("the user account was made successfully. ");
                    //display the details of the user account
                    User.displayUserAccount(user);

                    //to make bank accounts
                    //Only unauthorized persons will  be able to have bank accounts.
                    //But their bank accounts must be made only by authorized persons.
                    if(!user.isAuthorized()) {
                        BankAccount firstBankAccount = BankAccount.enterAccountData(user.getUserName(), user.getPassword());
                        //add the new bank account to the array list of all bank accounts
                        allBankAccounts.add(firstBankAccount);
                        //add the new bank account to the array  of logged bank accounts
                        bankAccountsOfTheUser[0] = firstBankAccount;
                        //display second account details
                        BankAccount.displayAccount(firstBankAccount);

                        do {
                            int indexOfBankAccount = 1;
                            if (bankAccountsOfTheUser.length > 10) {
                                System.out.println("Sorry you can't add bank accounts more than 10");
                                break;
                            }

                            System.out.println("If you don't want to make bank accounts"
                                    + "please enter 0 as the bank account number.");
                            BankAccount bankAccount = BankAccount.enterAccountData(user.getUserName(), user.getPassword());

                            if (bankAccount == null) {
                                moreBankAccounts = false;

                            } else {
                                //add the new bank account to the array list of all bank accounts
                                allBankAccounts.add(bankAccount);
                                //add the new bank account to the array  of logged bank accounts
                                bankAccountsOfTheUser[indexOfBankAccount] = bankAccount;
                                //display second account details
                                BankAccount.displayAccount(bankAccount);
                                indexOfBankAccount++;
                            }
                        } while (moreBankAccounts);
                    }
                    break;

                //to make bank accounts for existing customer account
                case 2:

                    String userName;
                    String password;
                    boolean isUserExisting=false;
                    bankAccountsOfTheUser = new BankAccount[10];

                    System.out.println("Please enter the customer name you want to add bank accounts");
                    System.out.print("Enter");
                    userName=userInput.nextLine();
                    System.out.println("Please enter the password");
                    System.out.print("Enter");
                    password=userInput.nextLine();

                    for(int i=0;i<users.size();i++){
                        if(userName.equals(users.get(i).getUserName())&&password.equals(users.get(i).getPassword())){
                            isUserExisting=true;
                        }
                    }

                    if(isUserExisting){

                        int indexOfBankAccount=0;
                        //get the all existing bank accounts of the user to the array
                        for(;indexOfBankAccount<10;indexOfBankAccount++){
                            for(int j=0;j<allBankAccounts.size();j++) {
                                if (allBankAccounts.get(indexOfBankAccount).getCustomerName().equals(userName)) {
                                    bankAccountsOfTheUser[indexOfBankAccount]=allBankAccounts.get(j);
                                }
                            }
                        }

                        //if there're no 10 bank accounts,user can make bank accounts
                        if(bankAccountsOfTheUser.length<10){
                            boolean makeMoreBankAccounts=true;
                            do{
                                if (bankAccountsOfTheUser.length > 10) {
                                    System.out.println("Sorry you can't add bank accounts more than 10");
                                    break;
                                }

                                System.out.println("If you don't want to make bank accounts"
                                        + "please enter 0 as the bank account number.");
                                BankAccount bankAccount = BankAccount.enterAccountData(userName, password);

                                if (bankAccount == null) {
                                    makeMoreBankAccounts = false;
                                } else {
                                    //add the new bank account to the array list of all bank accounts
                                    allBankAccounts.add(bankAccount);
                                    //add the new bank account to the array  of logged bank accounts
                                    bankAccountsOfTheUser[indexOfBankAccount+1] = bankAccount;
                                    //display second account details
                                    BankAccount.displayAccount(bankAccount);
                                    indexOfBankAccount++;
                                }
                            }while(makeMoreBankAccounts);
                        }
                    }
                    break;

                case 3:
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Thank You");
                    System.out.println("---------------------------------------------------------");
                    wantToContinue=false;
                    break;
            }
        }while(wantToContinue);
    }

    //This method will be displayed only for a customer after login
    public static void displayCustomerMenu() {
        int number;
        boolean wantToContinue=true;
        do {
            System.out.println("---------------------------------------------------------");
            System.out.println("Please enter");
            System.out.println("    1 :- To check the account balance");
            System.out.println("    2 :- To do a transfer");
            System.out.println("    3 :- To display the forecast");
            System.out.println("    3 :- To log out");
            System.out.print("Enter :- ");
            number = validate();
            System.out.println("---------------------------------------------------------");

            switch (number) {

                case 1:
                    BankAccount bankAccount = null;
                    int bankAccountNumber;
                    boolean hasAccount = false;
                    System.out.println("Please enter your bank account number");
                    System.out.print("Enter : ");
                    bankAccountNumber = validate();
                    for (int i = 0; i < loggedBankAccounts.size(); i++) {
                        if (bankAccountNumber == loggedBankAccounts.get(i).getAccountNumber()) {
                            hasAccount = true;
                            bankAccount = loggedBankAccounts.get(i);
                        }
                    }
                    if (hasAccount) {
                        System.out.println("Your bank account balance is " + bankAccount.getAccountBalance());
                    } else {
                        System.out.println("Sorry, this is not a valid bank account number.");
                    }
                    System.out.println("---------------------------------------------------------");
                    break;

                case 2:
                    transferCurrency();
                    System.out.println("---------------------------------------------------------");
                    break;

                case 3:
                    int accountNumber;
                    boolean hasAccounts = false;
                    BankAccount forecastBankAccount = null;
                    System.out.println("Please enter the bank account number you want to see forecast");
                    System.out.print("Enter : ");
                    accountNumber = validateAccountNumber();
                    for (int i = 0; i < loggedBankAccounts.size(); i++) {
                        if (loggedBankAccounts.get(i).getAccountNumber() == accountNumber) {
                            forecastBankAccount = loggedBankAccounts.get(i);
                            hasAccounts = true;
                        }
                    }
                    if (hasAccounts) {
                        BankAccount.computeInterest(forecastBankAccount);
                    } else {
                        System.out.println("Sorry ! invalid bank account number.");
                    }
                    break;

                case 4:
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Thank You");
                    System.out.println("---------------------------------------------------------");
                    wantToContinue=false;
                    break;
            }

        }while(wantToContinue);
    }

    public static void transferCurrency() {
        BankAccount sendBankAccount = null;
        BankAccount receiveBankAccount = null;
        int transferFromAccountNumber,
                transferToAccountNumber,
                transferCurrency;

        System.out.println("Please enter the bank account number you want to send from");
        System.out.print("Enter : ");
        transferFromAccountNumber = validateAccountNumber();
        System.out.println("Please enter the bank account number you want to send to");
        System.out.print("Enter : ");
        transferToAccountNumber = validateAccountNumber();

        //check the array list and get the bank account which want to send money
        for (int i = 0; i < loggedBankAccounts.size(); i++) {
            if (transferFromAccountNumber == loggedBankAccounts.get(i).getAccountNumber()) {
                sendBankAccount = loggedBankAccounts.get(i);
            }
        }
        //check the array list and get the bank account which is wanted to be received money
        for (int i = 0; i < loggedBankAccounts.size(); i++) {
            if (transferToAccountNumber == loggedBankAccounts.get(i).getAccountNumber()) {
                receiveBankAccount = loggedBankAccounts.get(i);
            }
        }

        if (!(sendBankAccount == null && receiveBankAccount == null)) {
            System.out.println("Please enter the amount of currency you want to transfer ");
            transferCurrency = validate();

            if (sendBankAccount.getAccountBalance() - transferCurrency < 10
                    && sendBankAccount.getAccountBalance() >= 0) {

                System.out.println("Please notice that tour balance "
                        + "will be reduce than 10. ");
                sendBankAccount.setAccountBalance(sendBankAccount.getAccountBalance()- transferCurrency);
                receiveBankAccount.setAccountBalance(receiveBankAccount.getAccountBalance() + transferCurrency);
                System.out.println("Your new balance of the bank account "
                        + transferFromAccountNumber + " is " + sendBankAccount.getAccountBalance());
                System.out.println("Your new balance of the bank account "
                        + transferToAccountNumber + " is " + receiveBankAccount.getAccountBalance());

            } else if (!(sendBankAccount.getAccountBalance() - transferCurrency < 10
                    || receiveBankAccount.getAccountBalance() + transferCurrency > 100000)) {

                sendBankAccount.setAccountBalance(sendBankAccount.getAccountBalance()- transferCurrency);
                receiveBankAccount.setAccountBalance(receiveBankAccount.getAccountBalance() + transferCurrency);
                System.out.println("Your new balance of the bank account "
                        + transferFromAccountNumber + " is " + sendBankAccount.getAccountBalance());
                System.out.println("Your new balance of the bank account "
                        + transferToAccountNumber + " is " + receiveBankAccount.getAccountBalance());

            } else {
                System.out.println("Sorry!! You can't proceed the transaction");
            }
        }
    }

    //this method is to write and read into files
    public static void dataPersistency(String whatToDo) {

        if (whatToDo.equals("read")) {
            //Read  bankAccounts objects from the files
            try {
                FileInputStream fis1 = new FileInputStream("BankAccounts.dat");
                ObjectInputStream input = new ObjectInputStream(fis1);
                allBankAccounts = (ArrayList<BankAccount>) input.readObject();
                System.out.println("Successfully transferred all bank accounts from the file.");
                input.close();
            } catch (FileNotFoundException e2) {
                System.out.println("Couldn't find the file.");
            } catch (IOException ex) {
                System.out.println("Sorry error was occurred while reading the file");
            } catch (ClassNotFoundException ex) {
                System.out.println("Sorry error was occurred while reading the file");
            }

            //read users objects from the file
            try {
                FileInputStream fis1 = new FileInputStream("CustomerAccounts.dat");
                ObjectInputStream input = new ObjectInputStream(fis1);
                allBankAccounts = (ArrayList<BankAccount>) input.readObject();
                System.out.println("Successfully transferred all customer accounts  from the file.");
                input.close();
            } catch (FileNotFoundException e2) {
                System.out.println("Couldn't find the file.");
            } catch (IOException ex) {
                System.out.println("Sorry error was occurred while reading the file");
            } catch (ClassNotFoundException ex) {
                System.out.println("Sorry error was occurred while reading the file");
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

            //write user accounts details to a text file
            try{
                FileOutputStream fos=new FileOutputStream("userAccountDetails.txt");
                PrintWriter pw=new PrintWriter(fos);
                for(int i=0;i<users.size();i++){
                    pw.println("_______________________________________________________________________");
                    pw.println("User Name       :- " + users.get(i).getUserName());
                    pw.println("Password        :- " + users.get(i).getPassword());
                    pw.println("Authorized      :- " + users.get(i).isAuthorized());
                    pw.println("_______________________________________________________________________");
                }
            }catch(FileNotFoundException e){
                System.out.println("Error occurred while writing the file.");
            }
            try {
                FileOutputStream s = new FileOutputStream("BankAccounts.dat");
                ObjectOutputStream output = new ObjectOutputStream(s);
                output.writeObject(allBankAccounts);
                output.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Sorry File couldn't be found.");
            } catch (IOException ex) {
                System.out.println("Sorry saving is unsuccessful.");
            }

            //write all the user accounts to the files
            try{
                FileOutputStream s2=new FileOutputStream("CustomerAccounts.dat");
                ObjectOutputStream oos=new ObjectOutputStream(s2);
                oos.writeObject(users);
                oos.close();
            }catch (FileNotFoundException e){
                System.out.println("Sorry the file couldn't be find.");
            }catch (IOException ex){
                System.out.println("Sorry, error");
            }
        }
    }
}
