//ISOLATA
//Java realtime application to encrypt and retrieve passwords
//Rotate text progressively
//Caesar up by sum fof keys
//Decyption : Caesar down by sum of key %26
//Rotate left progressively


import java.io.*;
import java.util.Scanner;


class isolata
{
    public static void sop(String s){System.out.print(s);}


    public static void decrypt()
    {
        String path;
        Scanner sc = new Scanner(System.in);
        sop("\nEnter path : ");
        path = sc.nextLine();
        sop("Path is "+path)
    }

    public static boolean menu()
    {
        int choice;
        sop("\n1)Extract data\n2)Add data\n3)New file\n4)Exit");
        Scanner sc = new Scanner(System.in);
        choice = sc.nextInt();
        if(choice==4)
            return false;
        if(choice == 1)
            decrypt();
        else if(choice == 2)
            add();
        else
            newfile();


        return true;

    }

    public static void main(String[] args)
    {
      
        while(menu())
        {

        }

    }
}