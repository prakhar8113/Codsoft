import java.util.Random;
import java.util.Scanner;

class NumberGame{
    static Random rand_obj;
    static Scanner sc;

    public NumberGame() {
         rand_obj = new Random();
         sc = new Scanner( System.in );
    }

    public int getRandomNumber( ) {
        System.out.println("In get Random");
        return ( 1+rand_obj.nextInt(5) ) ;
    }

    public int nextLevel( int level , int actual_num ) {
         System.out.println("  <------------ LEVEL-"+level+"------------->   ");
         int guessed_num = 0;

         int max_attempts = 5;
         int attempt_count = 1;

         while( attempt_count<=max_attempts ) {
             System.out.println("Enter the number you are guessing number !!");
             guessed_num = sc.nextInt();
             if( guessed_num==actual_num )
                return (max_attempts-attempt_count+1);
             else{
                System.out.println("You have guessed wrong number !!");
                System.out.println( "Still "+(max_attempts-attempt_count)+" more attempts are leftout !! " );
            }
            attempt_count++;
         }

         return -1;
    }

    public void startGame( ) {
         int level = 0;
         int total_score  = 0;
         System.out.println("<================ NUMBER GAME ====================>");

         while( true ) {
            System.out.println("Enter 'next' to MOVE TO NEXT LEVEL\nEnter 'stop' to STOP GAME\n");
            String choice = sc.next();
            
            if( choice.equals("next") ) {
                  int actual_num = this.getRandomNumber();
                  int points= this.nextLevel( ++level , actual_num );

                  if( points!=-1 ) {
                     System.out.println("Actual Number = "+actual_num );
                     System.out.println("Hurray!! You have cleared level-"+level+" !!\nNumber of points earned = "+points );
                     System.out.println("Your total score till now = "+total_score +" !! ");
                     total_score += points;
                  }
                  else{
                     System.out.println("You have failed to clear this level-"+level+" !!");
                     System.out.println("Your total score = "+total_score +" !! ");
                     break;
                  }
            }
            else if( choice.equals("stop") ) {
                 System.out.println("Your total score = "+total_score +" !! ");
                 break;
            }
            else{
                System.out.println();
            }
         }
    } 
}

public class Main{
    public static void main(String[] args) {
        NumberGame game_obj = new NumberGame();

        game_obj.startGame( );
    }
}