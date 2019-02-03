import java.util.Scanner;
import isolata.*;

public class trial
{


    /*Function Name : sop
    Purpose         : Simplifies System.out.print()
    Input           : String to output
    Return          : --    */
    public static void sop(String s){System.out.print(s);}


    /*Function Name : 
    Purpose         : 
    Input           : 
    Return          :     */
    public static void test(){}


    /*Function Name : menu
    Purpose         : Display menu, get user choice and perform operation
    Input           : 
    Return          : True if menu needs to be displayed again, false if exit condition    */
    public static boolean menu()
    {
        int choice;
        Scanner sc = new Scanner(System.in);
        choice = sc.nextInt();
        if(choice==4)
            return false;
        return true;
    }

    public static void main(String[] args)
    {
        test();
        
    }
}