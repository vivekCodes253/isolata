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
        Key = Key%26;
        String return_value = "";
        int i;
        for(i=0;i<text.length();i++)
        {
            //sop("\n"+text.charAt(i)+" becomes")
            //int val = text.charAt(i) + enc_dec*Key;
            
            return_value += (char)(text.charAt(i) + enc_dec*Key);
        }

        return return_value;

    }

    public static String decrypt()
    {
        String path,key;
        Scanner sc = new Scanner(System.in);
        sop("\nEnter path : ");
        path = sc.nextLine();
        sop("\nEnter key : ");
        key = sc.nextLine();
          
        return(decrypt(path,key));
    }


    public static String decrypt(String path, String key)
    {
        
        File file;
        int keyval;
        keyval = getValue(key);
        BufferedReader in;
        String input_string="",st,decrypted_string=""; 
        file = new File(path);
        try{
            in = new BufferedReader(new FileReader(file)); 
            
            int word_counter = 0;
            while ((st = in.readLine()) != null) 
            {
                input_string += st;     
                //sop(st+ "\n"); 
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

        //sop("\n"+decrypted_string);
        return(decrypted_string);
        //decryption part

    }

    public static String encrypt(String word, String key)
    {
        
        int i, keyval = getValue(key);
        keyval = keyval%26;
        String return_value = "";

        return_value = caesar(1,keyval,word);
        return return_value;

    }
 

    public static void add()
    {
        String path,newdata,key;
        int keyval;
        Scanner sc = new Scanner(System.in);
        sop("\nEnter path : ");
        path = sc.nextLine();
        sop("\nEnter key : ");
        key = sc.nextLine();
        keyval = getValue(key); 
        String data = decrypt(path,key);
        sop("\nEnter new data : ");
        newdata = sc.nextLine();
        //File file = File(path);
        //file.delete();
        data = data + newdata;
        sop(encrypt(data,key));
    }
    
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
            sop("\n"+decrypt()+"\n");
        else if(choice == 2)
            add();
        else
            newfile();


        return true;

    }

    public static void main(String[] args)
    {
        //sop(rotate(1,"Namaskaram",4)+"");
        //decrypt();
        while(menu())
        {

        }
        //sop(caesar(1,getValue("hel"),"not bad"));
        //caesar(1,"")
        //sop("\n");
        //sop(caesar(-1,getValue("hel"),"opu!cbe"));
        

    }
}