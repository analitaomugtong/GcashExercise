package gcashapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UserAuthentication auth = new UserAuthentication();
        CheckBalance cb = new CheckBalance();
        Scanner sc = new Scanner(System.in);

        System.out.println("===== GCASH USER AUTHENTICATION SYSTEM =====");
        System.out.println("1 - Register");
        System.out.println("2 - Login");
        System.out.print("Choose option: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Mobile Number: ");
            String number = sc.nextLine();

            System.out.print("4-digit PIN: ");
            String pin = sc.nextLine();

            auth.register(name, email, number, pin);
        }

        else if (choice == 2) {
            System.out.print("Mobile Number: ");
            String number = sc.nextLine();

            System.out.print("PIN: ");
            String pin = sc.nextLine();

            int userId = auth.login(number, pin);

            if (userId != -1) {
                CashTransfer ct = new CashTransfer();

                System.out.println("1 - Change PIN");
                System.out.println("2 - Check Balance");
                System.out.println("3 - Cash-In");
                System.out.println("4 - Cash Transfer");
                System.out.println("5 - Logout");

                System.out.print("Choose option: ");
                int option = sc.nextInt();
                sc.nextLine();

                if (option == 1) {
                    System.out.print("Enter new 4-digit PIN: ");
                    String newPin = sc.nextLine();
                    auth.changePin(userId, newPin);
                }
                else if (option == 2) {
                    double bal = cb.getBalance(userId);
                    System.out.println("Your current balance is: ₱" + bal);
                }
                else if(option == 3) {
                    System.out.println("Enter amount to cash-in: ");
                    double amount = sc.nextDouble();
                    Cashin ci = new Cashin();
                    ci.cashIn(userId, "Cash-In", amount);
                }
                else if(option == 4){
                System.out.print("Enter receiver Mobile Number: ");
                String recvNumber = sc.nextLine();

                    System.out.print("Enter amount to transfer: ");
                    double amt = sc.nextDouble();
                ct.cashTransferByNumber(userId, recvNumber, amt);
}
                auth.logout();
            }
        }

        else {
            System.out.println("Invalid menu choice!");
        }

        sc.close();
    }
}
