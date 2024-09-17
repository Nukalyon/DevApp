package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner;
        System.out.print("Entrer votre username: ");
        scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.print("Entrer votre password: ");
        scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        if(VerifInfoDTB.authentification(username, password))
        {
            System.out.println("/!\\Vous êtes connecté /!\\");
        }
        else {
            System.out.println("/!\\Vérifiez vous informations /!\\");
        }
    }
}