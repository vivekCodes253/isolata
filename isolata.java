//ISOLATA
//Java realtime application to encrypt and retrieve passwords
//Rotate text progressively
//Caesar up by sum fof keys
//Decyption : Caesar down by sum of key %26
//Rotate left progressively


import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer; 


public class isolata
{
    public static int safe_lower = 48;
    public static int safe_upper = 125;
    public static char space_replacer = ']'; //replace space 
    public static String newline_replacer = "--00--"; //replace newline
    public static String data_store_path = "data";
    //---------IO
    public static Scanner sc;

    /*Function Name : sop
    Purpose         : Simplifies System.out.print()
    Input           : String to output
    Return          : --    */
    public static void sop(String s){System.out.print(s);}


    /*Function Name : getUserData  [i/o operation]
    Purpose         : Get user data 
    Input           : --
    Return          : String tring accepted from the user  */
    public static String getUserData(String message)
    {
        Scanner sc = new Scanner(System.in);
        sop("\n"+message);
        String return_value = sc.nextLine();
        return(return_value);
    }

    public static String getUserData()
    {   
        return(getUserData("\nEnter new data : "));
    }

    
    /*Function Name : getKeyString  [i/o operation]
    Purpose         : Gets the key string from user 
    Input           : --
    Return          : Key string accepted from the user  */
    public static String getKeyString()
    {
        return(getUserData("\nEnter key : "));   
    }


    
    /*Function Name : getKeyValue
    Purpose         : Generates the sum of characters in int and mod by the safe range for a numeric key representation
    Input           : The key in String format
    Return          : Generated sum modded by safe range value  */
    public static int getKeyValue(String key)
    {
        int i,return_value=0;
        for(i=0;i<key.length();i++)
            return_value += key.charAt(i);
        return_value = return_value%(safe_upper-safe_lower);
        return return_value;
    }


    /*Function Name : rotate
    Purpose         : Rotates string left or right
    Input           : direction (-1 left rotation, 1 right rotation), word - input string, rotate_value - amount of rotation
    Return          : Rotated string    */
    public static String rotate(int direction, String word, int rotate_value)   
    {
        String return_value ="";
        int i;
        rotate_value = rotate_value % word.length();
        if(rotate_value == 0)
            return word;
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


    /*Function Name : caesar
    Purpose         : Does caesar cipher shift within the safe_range
    Input           : enc_dec (1 for encoding : increments character value, -1 for decoding, decrements character value), key - shifting value, text - input string
    Return          : caesar cipher text    */
    public static String caesar(int enc_dec, int Key, String text)  
    {
        String return_value = "";
        int i;
        for(i=0;i<text.length();i++)
        {
            int val = text.charAt(i) + enc_dec*Key;
            if(val>safe_upper)
                val = val%safe_upper + safe_lower;
            else if(val<safe_lower)
                val = val + safe_upper - safe_lower;  
            return_value += (char)(val);
        }
        return return_value;
    }


    /*Function Name : decrypt
    Purpose         : Accepts path and key to call the main decrypt function
    Input           : User input of path and key
    Return          : Value returned by the main decrypt function    */
    public static String decrypt()
    {
        String path,key;
        path = data_store_path;
        key = getKeyString();
        return(decrypt(path,key,true));
    }
    

    /*Function Name : decrypt
    Purpose         : Read data from file, decrypt using the rotate and caesar scheme
    Input           : path - path to datafile, key - String key to decrypt
    Return          : Decrypted string  or error message   */
    public static String decrypt(String path, String key, boolean return_for_display)
    {   
        File file;
        int keyval;
        keyval = getKeyValue(key);
        BufferedReader in;
        String input_string="",str,decrypted_string="",decrypted_string_display=""; 
        file = new File(path);
        try{
            in = new BufferedReader(new FileReader(file)); 
            int word_counter = 0;
            while ((str = in.readLine()) != null) 
            {
                input_string += str;     
            }   
            StringTokenizer st = new StringTokenizer(input_string,space_replacer+"");
            while (st.hasMoreTokens())
            {
                String tval =st.nextToken();
                if(!tval.equals(newline_replacer))
                {
                    String caesarop = caesar(-1,keyval,rotate(-1,tval,word_counter));;
                    decrypted_string += caesarop;
                    decrypted_string_display += caesarop;
                    word_counter++;
                    decrypted_string+=" ";
                    decrypted_string_display+=" ";
                }
                else
                {
                    decrypted_string_display+="\n";
                }
            }
        }
        catch(FileNotFoundException ex)
        {
            return("\nFile not found, create one or check the path!\n");
        }
        catch(Exception e)
        {
            return(e+"\n");
        }
        if(return_for_display)
            return(decrypted_string_display);
        else    
            return(decrypted_string);
    }


    /*Function Name : encrypt
    Purpose         : To encryot using the rotate+caesar scheme reading word by word, also appends special character to replace space
    Input           : data - String to encrypt, key - String key to encrypt
    Return          : Encrypted string    
    Comments        : Slightly inefficient to read and reencrypt everything again
    TODO            : Fix the comment issue*/
    public static String encrypt(String data, String key)       
    {    
        int i, keyval = getKeyValue(key),word_counter = 0;
        String return_value = "";
        StringTokenizer st = new StringTokenizer(data);
        while (st.hasMoreTokens())
        {           
            return_value += rotate(1,caesar(1,keyval,st.nextToken()),word_counter);
            word_counter++;
            return_value+=space_replacer;      
        }
        return return_value;
    }

 
    /*Function Name : add
    Purpose         : To read from an already encrypted file and append more encrypted data
    Input           : --
    Return          : Success message or the error report as string    
    TODO            : Work on this function, proper input and output options, note sequence of operations, file not found must be prompt*/
    public static String add()
    {
        String path,newdata,key;
        File file;
        BufferedWriter writer;
        int keyval;
        path = data_store_path;
        try{
            writer = new BufferedWriter(new FileWriter(path,true));
        }
        catch(FileNotFoundException ex)
        {
            return("\nFile not found, create one or check the path!\n");
        }
        catch(Exception e)
        {
            return(""+e);
        }
        key = getKeyString();
        keyval = getKeyValue(key); 
        String data = decrypt(path,key,false);
        newdata = getUserData();
        int old_length = data.length();
        data = data + newdata;
        try{           
            writer.write(""+encrypt(data,key).substring(old_length));
            writer.write(newline_replacer+space_replacer);
            writer.close();
        }
        catch(Exception e)
        {
            return(""+e);
        }     
        return "Successfully appended!";  
    }
    

    /*Function Name : newfile
    Purpose         : 
    Input           : 
    Return          :     */
    public static void newfile(){}


    /*Function Name : menu
    Purpose         : Display menu, get user choice and perform operation
    Input           : 
    Return          : True if menu needs to be displayed again, false if exit condition    */
    public static boolean menu()
    {
        int choice;
        sop("\n1)Extract data\n2)Add data\n3)New file\n4)Exit");
        choice = Integer.valueOf(getUserData("Enter choice : "));
        if(choice==4)
            return false;
        if(choice == 1)
            sop("\n"+decrypt()+"\n");
        else if(choice == 2)
            sop("\n"+add()+"\n");
        else
            newfile();
        return true;
    }


    /*Function Name : init
    Purpose         : initialise global variables
    Input           : --
    Return          : --    */
    public static void init()
    {
        Scanner sc = new Scanner(System.in);
        try
        {
            File database = new File(data_store_path);
            database.createNewFile(); 
        }
        catch(Exception e)
        {
            sop(e+"");
        }
        
    }

    public static void main(String[] args)
    {
        init();
        while(menu())
        {

        }
    /*
        for(i=1;i<300;i++)
        {
            sop("\n"+i+") "+(char)i);
        }
        */
    }
}