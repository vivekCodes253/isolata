//ISOLATA
//Java realtime application to encrypt and retrieve passwords
//Rotate text progressively
//Caesar up by sum fof keys
//Decyption : Caesar down by sum of key %26
//Rotate left progressively
//NEW task - add file selection

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer; 

public class isolata
{
    public static int safe_lower = 48;
    public static int safe_upper = 125;
    public static String space_replacer = "]"; //replace space 
    public static String newline_replacer = "{}"; //replace newline
    public static String default_data_store_path = "data";
    public static String data_store_path = "";
    public static String file_cache = "";
    public boolean current_file_has_changed = false;
    //---------IO
    public static Scanner sc;

    /*Function Name : sop [i/o operation]
    Purpose         : Simplifies System.out.print()
    Input           : String to output
    Return          : --    */
    public static void sop(String s){System.out.print(s);}

    /*Function Name : fileread [file operation]
    Purpose         : read data from file
    Input           : filepath
    Return          : Read message or status    */
    public static String fileread(String filepath)
    {
        File file = new File(filepath);
        String str, read_string = "";
        try{
            BufferedReader in = new BufferedReader(new FileReader(file)); 
            while ((str = in.readLine()) != null) 
            {
                read_string += str;     
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
        return read_string;
    }

    /*Function Name : filewrite [file operation]
    Purpose         : write string into file
    Input           : filepath, data string and a boolean whether to overwrite or not, not means append
    Return          : Status    */
    public static String filewrite(String filepath, String data, boolean overwrite)
    {
        File file;
        BufferedWriter writer;   
        try{
            writer = new BufferedWriter(new FileWriter(filepath,!overwrite));
        }
        catch(FileNotFoundException ex)
        {
            return("\nFile not found, create one or check the path!\n");
        }
        catch(Exception e)
        {
            return(""+e);
        }
        try{           
            writer.write(data);
            writer.close();
        }
        catch(Exception e)
        {
            return(""+e);
        }     
        return "Successfully appended!"; 
    }


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

    /*Function Name : extract includes io operations
    Purpose         : Accepts path and key to call the main extract function
    Input           : User input of path and key
    Return          : Value returned by the main extract function    */
    public static String extract()
    {
        String path,key;
        path = data_store_path;
        key = getKeyString();
        return(extract(path,key));
    }
    
    /*Function Name : extract
    Purpose         : Read data from file, decrypt using the rotate and caesar scheme
    Input           : path - path to datafile, key - String key to decrypt
    Return          : Decrypted string  or error message   */
    public static String extract(String path, String key)
    {   
        String decrypted_string="";    
        decrypted_string = decrypt(fileread(path),key);
        return(decrypted_string);
    }

    /*Function Name : fileselector
    Purpose         : Select a file to write data onto or use the default
    Input           : --
    Return          : --   */
    public static void fileSelector()
    {
        int choice;
        String filename;
        data_store_path = default_data_store_path;
        sop("\n1) Enter custom file [creates file if it doesnt exist] \n2) Use default");
        choice = Integer.valueOf(getUserData("Enter choice : "));
        if(choice==1)
        {
            filename = getUserData("\nEnter file name : ");
            if(newfile(filename))
                data_store_path = filename;
            else    
                sop("Error creating file");
        }
    
        
    }

    /*Function Name : encrypt
    Purpose         : To encryot using the rotate+caesar scheme reading word by word, also appends special character to replace space
    Input           : data - String to encrypt, key - String key to encrypt
    Return          : Encrypted string    
    Comments        : Slightly inefficient to read and reencrypt everything again*/
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

    /*Function Name : decrypt
    Purpose         : To decrypt using the rotate+caesar scheme reading word by word, also appends special character to replace space
    Input           : data - String to decrypt, key - String key to decrypt
    Return          : Encrypted string*/
    public static String decrypt(String data, String key)
    {
        int i, keyval = getKeyValue(key),word_counter = 0;
        String return_value = "",temp ="";
        StringTokenizer st = new StringTokenizer(data,space_replacer);
        while (st.hasMoreTokens())
        {   
            temp = st.nextToken();
            
            if(temp.equals(newline_replacer))
                return_value+="\n";
            else
            { 
                return_value += caesar(-1,keyval,rotate(-1,temp,word_counter));
                word_counter++;
                if(st.hasMoreTokens())
                    return_value+=" "; 
            }                 
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
        path = data_store_path;
        key = getKeyString(); 
        String data = extract(path,key).replaceAll("\n","");
        newdata = getUserData();
        int old_length = data.length();
        String old_data = data;
        data = data + newdata;
        return filewrite(path,(""+(encrypt(data,key)).substring(old_length)+space_replacer+newline_replacer+space_replacer),false);
    }

    /*Function Name : newfile
    Purpose         : makes a new file in the current working directory
    Input           : filename
    Return          : status of creation   */
    public static Boolean newfile(String name)
    {
        try
        {
            File myFile = new File(name);
            myFile.createNewFile();
        }
        catch(Exception e)
        {
            return(false);
        }

        return true; 
    }

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
            sop("\n"+extract()+"\n");
        else if(choice == 2)
            sop("\n"+add()+"\n");
        else
            fileSelector();
        return true;
    }

    /*Function Name : init
    Purpose         : initialise global variables and create basic files (directory for list of files, and creates default path)
    Input           : --
    Return          : --    */
    public static void init()
    { 
        newfile(default_data_store_path); 
        data_store_path = default_data_store_path;       
    }

    public static void main(String[] args)
    {
        init();
        fileSelector();
        while(menu()){}   
    }
}