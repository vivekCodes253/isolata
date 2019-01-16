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

    public static int getValue(String key)
    {
        int i,return_value=0;
        for(i=0;i<key.length();i++)
            return_value += key.charAt(i);

        return return_value;
    }

    public static String rotate(int direction, String word, int rotate_value)   //-1 = left 1 = right
    {
        String return_value ="";
        int i;
        rotate_value = rotate_value % word.length();
        if(rotate_value == 0)
            return word;
        /*
        if(direction == -1)
        {
            for(i=rotate_value;i<word.length();i++)
                return_value += word.charAt(i);
            for(i=0;i<rotate_value;i++)
                return_value += word.charAt(i);
        }
        else
        {
            for(i=word.length()-rotate_value;i<word.length();i++)
                return_value += word.charAt(i);
            for(i=0;i<word.length()-rotate_value;i++)
                return_value += word.charAt(i);
        }*/
        //Above can be reduced
        int startval;
        if(direction == -1)
            startval = rotate_value; 
        else
           startval = word.length()-rotate_value;
        for(i=startval;i<word.length();i++)
                return_value += word.charAt(i);
        for(i=0;i<startval;i++)
                return_value += word.charAt(i);

        return return_value;
    }

    public static String caesar(int enc_dec, int Key, String text)  //1 for enc, -1 for dec
    {
        String return_value = "";
        int i;
        for(i=0;i<text.length();i++)
        {
            return_value += text.charAt(i) + enc_dec*Key;
        }

        return return_value;

    }


    public static void decrypt()
    {
        String path,key;
        int keyval;
        File file;
        BufferedReader in;
        String input_string="",st,decrypted_string=""; 
        Scanner sc = new Scanner(System.in);
        sop("\nEnter path : ");
        path = sc.nextLine();
        sop("\nEnter key : ");
        key = sc.nextLine();
        keyval = getValue(key);                                         
        file = new File(path);
        try{
            in = new BufferedReader(new FileReader(file)); 
            
            int word_counter = 0;
            while ((st = in.readLine()) != null) 
            {
                input_string += st;     
                sop(st+ "\n"); 
                decrypted_string+=  caesar(-1,keyval,rotate(-1,st,word_counter));
                word_counter++;

            }   
        }
        catch(FileNotFoundException ex)
        {
            sop("\nFile not found, create one or check the path!\n");
        }
        catch(Exception e)
        {
            sop(e+"\n");
        }

        sop("\n"+decrypted_string);

        //decryption part

    }

    

    public static void add(){}
    
    public static void newfile(){}
    public static boolean menu()
    {
        int choice;
        sop("\n1)Extract data\n2)Add data\n3)New file\n4)Exit\n\nEnter choice : ");
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
        //sop(rotate(1,"Namaskaram",4)+"");
        decrypt();
        /*while(menu())
        {

        }*/

        

    }
}