package gcashapp;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        UserAuthentication auth = new UserAuthentication();
        Scanner sc = new Scanner(System.in);

        System.out.println("1-Register\n2-Login");
        int choice = sc.nextInt();
        sc.nextLine();

        if(choice==1){
            System.out.print("Name: ");
            String name=sc.nextLine();
            System.out.print("Email: ");
            String email=sc.nextLine();
            System.out.print("Number: ");
            String number=sc.nextLine();
            System.out.print("4-digit PIN: ");
            String pin=sc.nextLine();
            auth.register(name,email,number,pin);
        }

        else if(choice==2){
            System.out.print("Number: ");
            String number=sc.nextLine();
            System.out.print("PIN: ");
            String pin=sc.nextLine();

            int userId = auth.login(number,pin);

            if(userId!=-1){
                System.out.println("1-Change PIN\n2-Logout");
                int c=sc.nextInt();
                if(c==1){
                    System.out.print("New PIN: ");
                    String newPin=sc.next();
                    auth.changePin(userId,newPin);
                }
                auth.logout();
            }
        }
    }
}

