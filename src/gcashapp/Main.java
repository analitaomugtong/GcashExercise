package gcashapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UserAuthentication auth = new UserAuthentication();
        CheckBalance cb = new CheckBalance();
        Cashin cashin = new Cashin();
        CashTransfer ct = new CashTransfer();
        Transactions trans = new Transactions();

        Scanner sc = new Scanner(System.in);

        int userId = -1;
        String userNumber = "";

        while (true) {
            System.out.println("\n===== GCASH APP =====");
            System.out.println("1 - Register");
            System.out.println("2 - Login");
            System.out.println("3 - Exit");
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
                userNumber = sc.nextLine();
                System.out.print("PIN: ");
                String pin = sc.nextLine();

                userId = auth.login(userNumber, pin);

                if (userId == -1) continue;

                boolean loggedIn = true;

                while (loggedIn) {
                    System.out.println("\n===== MAIN MENU =====");
                    System.out.println("1 - Check Balance");
                    System.out.println("2 - Cash-In");
                    System.out.println("3 - Cash Transfer");
                    System.out.println("4 - View My Transactions");
                    System.out.println("5 - Logout");
                    System.out.print("Choose option: ");
                    int opt = sc.nextInt();
                    sc.nextLine();

                    switch (opt) {
                        case 1:
                            System.out.println("Balance: ₱" + cb.getBalance(userId));
                            break;

                        case 2:
                            System.out.print("Enter cash-in amount: ");
                            double inAmt = sc.nextDouble();
                            sc.nextLine();
                            cashin.cashIn(userId, "Cash-In", inAmt);
                            break;

                        case 3:
                            System.out.print("Enter receiver mobile number: ");
                            String recvNum = sc.nextLine();
                            System.out.print("Enter amount to transfer: ");
                            double tAmt = sc.nextDouble();
                            sc.nextLine();
                            ct.cashTransferByNumber(userId, recvNum, tAmt);
                            break;

                        case 4:
                            trans.viewUserAllByNumber(userNumber);
                            break;

                        case 5:
                            auth.logout();
                            loggedIn = false;
                            break;

                        default:
                            System.out.println("Invalid choice!");
                    }

                    if (loggedIn) {
                        System.out.print("\nAnother transaction? (Y/N): ");
                        String again = sc.nextLine();
                        if (!again.equalsIgnoreCase("Y")) {
                            auth.logout();
                            loggedIn = false;
                        }
                    }
                }
            }

            else if (choice == 3) {
                System.out.println("Thank you for using GCash!");
                sc.close();
                break;
            }

            else {
                System.out.println("Invalid choice!");
            }
        }
    }
}
