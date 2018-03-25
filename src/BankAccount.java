import java.io.Serializable;
import java.util.ArrayList;

public class BankAccount implements Serializable {
    private int accountNumber;
    private double accountBalance;
    private String customerName;
    private String password;
    private double autoWithdrowal;
    private double autoDeposit;
    private int user_ID;

    //These are the getter methods--------------------------------------------------------------------------------------
    public int getAccountNumber() {return accountNumber;}
    public double getAccountBalance() {return accountBalance;}
    public String getCustomerName() {return customerName;}
    public String getPassword() {return password;}
    public double getAutoWithdrowal() {return autoWithdrowal;}
    public double getAutoDeposit() {return autoDeposit;}
    public int getUser_ID() { return user_ID; }
    //------------------------------------------------------------------------------------------------------------------

    //This is the set method for account balance------------------------------------------------------------------------
    public void setAccountBalance(double accountBalance) {this.accountBalance = accountBalance;}
    //------------------------------------------------------------------------------------------------------------------

    //create a constant to store the interest rate
    final private static double interestRate=3;

    public BankAccount(int bankAccountNumber, double bankAccountBalance,
                       String customerName,int user_ID, String password,
                       double autoWithdrowal, double autoDeposit) {
        this.accountNumber = bankAccountNumber;
        this.accountBalance = bankAccountBalance;
        this.customerName = customerName;
        this.password = password;
        this.autoWithdrowal = autoWithdrowal;
        this.autoDeposit = autoDeposit;
        this.user_ID=user_ID;
    }

    //thhis method is to display account details
    public static void displayAccount(BankAccount currentBankAccount) {
        System.out.println("---------------------------------------------------------");
        System.out.println("Yout customer account and the bank account were made."
                + "Here is your bank account's details.");
        //display the details of the bank account
        System.out.println("    Customer name        :-  " + currentBankAccount.customerName);
        System.out.println("    User ID              :-  " + currentBankAccount.user_ID);
        System.out.println("    Account number       :-  " + currentBankAccount.accountNumber);
        System.out.println("    Account balance      :-  " + currentBankAccount.accountBalance);
        System.out.println("---------------------------------------------------------");
    }

    //this method is to make a bank account
    public static BankAccount enterAccountData(String loggedCustomerName,int user_ID,String loggedPassword
            , ArrayList<BankAccount> allBankAccounts) {
        System.out.println("-----------------------------------------------------------");
        boolean isDuplicated = false;
        int accountNumber;
        double accountBalance, annualInterestRate, autoWithdrowal, autoDeposit;
        System.out.println("Please enter a new account number.");
        System.out.print("Enter : ");
        accountNumber = UserInterface.validateAccountNumber();
        /*call UserInterface.validateAccountNumber method to check whether the account number
        is between 1000 and 9999 and whether the account number is an integer.*/

        if (!(accountNumber == 0)) {

            do {
                isDuplicated = false;
                for (int i = 0; i < allBankAccounts.size(); i++) {
                    if (allBankAccounts.get(i).accountNumber == accountNumber) {
                        isDuplicated = true;
                    }
                }
                if (isDuplicated) {
                    System.out.println("This account number has been already taken."
                            + "Please enter another account number!!");
                    System.out.print("Enter : ");
                    accountNumber = UserInterface.validateAccountNumber();
                }
            } while (isDuplicated);

            System.out.println("Please enter a start account balance.");
            System.out.print("Enter : ");
            accountBalance = UserInterface.validate();

            while(accountBalance>100000||accountBalance<0){
                System.out.println("Please enter account balance less than 100000.");
                System.out.print("Enter : ");
                accountBalance=UserInterface.validate();
            }

            System.out.println("Please enter an amount for the monthly automatic withdrawal.");
            System.out.print("Enter : ");
            autoWithdrowal = UserInterface.validateDouble();
            System.out.println("Please enter an amount for the monthly automatic deposit.");
            System.out.print("Enter : ");
            autoDeposit = UserInterface.validateDouble();
            System.out.println("-----------------------------------------------------------");
            BankAccount bankAccount = new BankAccount(accountNumber, accountBalance,
                    loggedCustomerName, user_ID, loggedPassword, autoWithdrowal, autoDeposit);

            return bankAccount;

        } else {
            return null;
        }
    }

    //this method is to compute the annual interest then display future fore cast
    public static void computeInterest(BankAccount bankAccount) {
        int numberOfYears;
        double autoWithdrawal = 0;
        double autoDeposit = 0;
        double interestRate = 0;
        double currentBalance = 0;
        int previousBalance = 0;
        int futureBalance = 0;

        //get the number of years
        System.out.println("Please enter after how many years do you want to see the forecast?");
        System.out.print("Enter : ");
        numberOfYears = UserInterface.validate();

        while(numberOfYears>40){
            System.out.println("Please enter a number of years within the range of from 1 to 40.");
            System.out.print("Enter : ");
            numberOfYears=UserInterface.validate();
        }

        //get the neceassary informaion to calculate forecast from the object
        autoDeposit = bankAccount.autoDeposit;
        autoWithdrawal = bankAccount.autoWithdrowal;
        currentBalance = bankAccount.accountBalance;

        System.out.println("-----------------------------------------------------------");
        System.out.println("The current balance is " + currentBalance);
        System.out.println("Year     Begining Balance    Ending balance");
        previousBalance = (int) currentBalance;

        for (int yearCounter = 0; yearCounter < numberOfYears; yearCounter++) {
            for (int monthCounter = 1; monthCounter <= 12; monthCounter++) {
                double function = previousBalance + (currentBalance * interestRate / 100 / 12)
                        + autoDeposit - autoWithdrawal;
                if ((function <= 100000) && (function >= 0)) {
                    futureBalance = (int) function;
                } else if (function > 100000) {
                    futureBalance = 100000;
                } else if (function < 0) {
                    futureBalance = 0;
                }
            }
            System.out.println("" + (yearCounter + 1) + "        " + previousBalance
                    + "           " + futureBalance + "          ");
            previousBalance = futureBalance;
            System.out.println("____________________________________________________");
        }
        System.out.println("-----------------------------------------------------------");
    }

    //This method is to display the farecast of all the bank accounts which user have made
    public static void computeInterest2(BankAccount[] bankAccountsOfTheUser) {
        int numberOfYears;
        double autoWithdrawal = 0;
        double autoDeposit = 0;
        double interestRate = 0;
        double currentBalance = 0;
        int previousBalance = 0;
        int futureBalance = 0;

        //get the number of years
        System.out.println("Please enter after how many years do you want to see the forecast?");
        System.out.print("Enter : ");
        numberOfYears = UserInterface.validate();

        while (numberOfYears > 40) {
            System.out.println("Please enter a number of years within the range of from 1 to 40.");
            System.out.print("Enter : ");
            numberOfYears = UserInterface.validate();
        }

        //To display forecast of the all tha bank accounts of the user
        for (int i = 0; i < 10; i++) {
            //Check first that bank account is null
            if(bankAccountsOfTheUser[i]!=null) {
                System.out.println("==================================================================================");
                System.out.println("====================="+bankAccountsOfTheUser[i].getAccountNumber()+"=====================================");
                System.out.println("==================================================================================");
                //get the neceassary informaion to calculate forecast from the object
                autoDeposit = bankAccountsOfTheUser[i].autoDeposit;
                autoWithdrawal = bankAccountsOfTheUser[i].autoWithdrowal;
                currentBalance = bankAccountsOfTheUser[i].accountBalance;

                System.out.println("-----------------------------------------------------------");
                System.out.println("The current balance is " + currentBalance);
                System.out.println("Year     Begining Balance    Ending balance");
                previousBalance = (int) currentBalance;

                //Print balances at begin and end of each year
                for (int yearCounter = 0; yearCounter < numberOfYears; yearCounter++) {
                    for (int monthCounter = 1; monthCounter <= 12; monthCounter++) {
                        double function = previousBalance + (currentBalance * interestRate / 100 / 12)
                                + autoDeposit - autoWithdrawal;
                        if ((function <= 100000) && (function >= 0)) {
                            futureBalance = (int) function;
                        } else if (function > 100000) {
                            futureBalance = 100000;
                        } else if (function < 0) {
                            futureBalance = 0;
                        }
                    }
                    System.out.println("" + (yearCounter + 1) + "        " + previousBalance
                            + "           " + futureBalance + "          ");
                    previousBalance = futureBalance;
                    System.out.println("____________________________________________________");
                }

            }
        }
        System.out.println("-----------------------------------------------------------");
    }
}
