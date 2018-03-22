import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

class User implements Serializable {
    private String userName;
    private String password;
    private boolean authorized;//whether the authorized or unauthorized
    private int user_ID;

    static Scanner userInput=new Scanner(System.in);
    public User(String userName,String password,boolean authorized,int user_ID){
        this.userName=userName;
        this.password=password;
        this.authorized=authorized;
        this.user_ID=user_ID;
    }

    //These are the getter methods of User class------------------------------------------------------------------------
    public String getUserName() {return userName;}
    public String getPassword() {return password;}
    public boolean isAuthorized() {return authorized; }
    public int getUser_ID() { return user_ID;}
    //------------------------------------------------------------------------------------------------------------------

    //This method is to make a user account
    public static User makeUserAccount(ArrayList<User> users) {
        String newCustomerName;
        String newPassword;
        boolean isAuthorized;
        int inputNumber;
        int user_ID;

        System.out.println("Please press the 'Enter' key");
        userInput.nextLine();
        System.out.println("Please enter new user if");
        System.out.print("Enter : ");
        user_ID=UserInterface.validate();

        //Check whether the user id is aleady existing
        boolean isIDExisting;
        do {
            isIDExisting = false;
            for (int i = 0; i < users.size(); i++) {
                if (user_ID==users.get(i).getUser_ID()) {
                    isIDExisting = true;
                }
            }
            if (isIDExisting) {
                System.out.println("This user id is existing.Please enter a new user ID!!!");
                System.out.print("Enter :");
                user_ID=UserInterface.validate();
            }

        } while (isIDExisting);


        System.out.println("Please enter a new user name");
        System.out.print("Enter : ");
        newCustomerName = userInput.nextLine();
        System.out.println("Please enter new password");
        System.out.print("Enter : ");
        newPassword = userInput.next();


        System.out.println("Please enter 1 if new user is an authorized person.Or else enter any other number.");
        System.out.print("Enter : ");
        inputNumber = UserInterface.validate();

        if (inputNumber == 1) {
            isAuthorized = true;
        } else {
            isAuthorized = false;
        }
        //make a object of the user
        User user = new User(newCustomerName, newPassword, isAuthorized,user_ID);
        return user;
    }

    public static void displayUserAccount(User user){
        System.out.println("User Name   :- " + user.getUserName());
        System.out.println("Password    :- " + user.getPassword());
        System.out.println("User ID     :- " + user.getUser_ID());
        System.out.println("Authorized  :- " + user.isAuthorized());
    }
}
