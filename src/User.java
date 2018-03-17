import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String userName;
    private String password;
    private boolean authorized;//whether the authorized or unauthorized

    static Scanner userInput=new Scanner(System.in);
    public User(String userName,String password,boolean authorized){
        this.userName=userName;
        this.password=password;
        this.authorized=authorized;
    }

    //These are the getter methods of User class------------------------------------------------------------------------
    public String getUserName() {return userName;}
    public String getPassword() {return password;}
    public boolean isAuthorized() {return authorized; }
    //------------------------------------------------------------------------------------------------------------------

    //These are the setter methods of User class------------------------------------------------------------------------
    public void setUserName(String userName) {this.userName = userName;}
    public void setPassword(String password) {this.password = password;}
    public void setAuthorized(boolean authorized) { this.authorized = authorized; }
    //------------------------------------------------------------------------------------------------------------------

    //This method is to make a user account
    public static User makeUserAccount(ArrayList<User> users) {
        String newCustomerName;
        String newPassword;
        boolean isAuthorized;
        int inputNumber;
        System.out.println("Please enter a new user name");
        newCustomerName = userInput.nextLine();

        boolean isUserExisting;
        do {
            isUserExisting = false;
            for (int i = 0; i < users.size(); i++) {
                if (newCustomerName.equals(users.get(i).getUserName())) {
                    isUserExisting = true;
                }
            }
            if (isUserExisting) {
                System.out.println("This user name is existing.Please enter a new user name!!!");
                System.out.print("Enter :");
                newCustomerName = userInput.nextLine();
            }

        } while (isUserExisting);

        System.out.println("Please enter new password");
        newPassword = userInput.next();
        System.out.println("Please enter 1 if new user is an authorized person.Or else enter any other number.");
        inputNumber = UserInterface.validate();
        if (inputNumber == 1) {
            isAuthorized = true;
        } else {
            isAuthorized = false;
        }
        //make a object of the user
        User user = new User(newCustomerName, newPassword, isAuthorized);
        return user;
    }
}
