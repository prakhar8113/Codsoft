import java.util.Scanner;
import java.util.ArrayList;

class Question{
    String question;
    String[] options;
    int correct_option;

    public Question( String question , String[] options , int correct_option ) {
        this.question = question;
        this.options = options;
        this.correct_option = correct_option;
    }
}

class Quiz{
    static Scanner sc1 = new Scanner(System.in);
    static ArrayList<Quiz> quizes = new ArrayList<>();
    String title="";
    Question[] list;
    int n;

    public Quiz() {
        System.out.println("Enter the title of the Quiz : ");
        title = sc1.nextLine();
        System.out.println("Enter number of questions to be created : ");
        n = sc1.nextInt();
        sc1.nextLine();
        list = new Question[n];
        quizes.add( this );
    }

    public void createQuestions() {
        for( int i=0 ; i<n ; i++ ) {

            System.out.println("\nEnter Question-"+(i+1) );
            String question = sc1.nextLine();
            
            System.out.println("Enter number of options for this question");
            int n_options = sc1.nextInt();
            sc1.nextLine();
            String[] options = new String[ n_options ];

            for( int j=0 ; j<n_options ; j++ ) {
                System.out.println("Enter option-"+(j+1) );
                options[j] = sc1.nextLine();
            }

            System.out.println("Enter correct option : ");
            int correct_option = sc1.nextInt();
            sc1.nextLine();


            list[i] = new Question( question , options , correct_option );
        }
        System.out.println("\n\n");
    }

    public static void displayQuizes(){
        System.out.println("List of quizes available");
        for( int i=0 ; i<quizes.size() ; i++ ) {
            System.out.println((i+1)+". "+quizes.get(i).title );
        }
    }

    public static Quiz getQuiz( String title ) {
       for( int i=0 ; i<quizes.size() ; i++ ) {
           if( quizes.get(i).title.equals(title) ) {
              return quizes.get(i);
           }
       }
       return null;
    }
}

class TimerThread extends Thread {
    Test test;

    public TimerThread( Test t ) {
        this.test = t;
    }

    public void run() {
        for( int i=0 ; i<10 ; i++ ) {
            try{
                if( test.answered==true ) {
                    return;
                }
                Thread.sleep(1000);
            } catch( Exception e ) {
                System.out.println( e );
            }
        }

        test.answered_intime = false;
        System.out.println("Time is Up !!\nEnter 0 to continue...");
    }
}

class Test {
    Quiz quiz;
    static Scanner sc1 = new Scanner( System.in );
    int[] your_answer;
    boolean answered;
    boolean answered_intime;

    public Test( Quiz quiz ) {
        this.quiz = quiz;
        your_answer = new int[ quiz.n ];
    }

    public void displayQuestion( Question question , int qno ) {
        System.out.println( "Q-"+((char)(97+qno)) +")  "+question.question);
        for( int i=0 ; i<question.options.length ; i++ ) {
            System.out.println( (i+1)+")"+question.options[i] );
        }

        System.out.println("< Enter correct option number >");
    }

    public void startTimerForQuestion( int q_idx ) {
        try{
            answered = false;
            answered_intime = true;
            TimerThread timethread = new TimerThread( this );
            timethread.start();

            
            int option = sc1.nextInt();
            sc1.nextLine();
            answered = true;

            if( answered_intime==false ) 
               option = 0;
            
            your_answer[ q_idx ] = option;

            timethread.join();

        }catch( Exception e ) {
            System.out.println(e);
        }
    }

    public void takeTest() {
       System.out.println("---------- INSTRUCTION -----------------");
       System.out.println(" => +5 for correct answers ");
       System.out.println(" => -1 for incorrect answers ");
       System.out.println(" => 0 if question is not attempted ");
       for( int i=0 ; i<quiz.n ; i++ ) {
          System.out.println("New Question");
          displayQuestion( quiz.list[i] , i );
          startTimerForQuestion( i );
          System.out.println("\n\n");
       }
    }

    public void getResult( Quiz quiz ) {
        int total_marks = 0;
        System.out.println("<========= YOUR RESULT ==========>");
        Question[] q_list = quiz.list;
        for( int i=0 ; i<q_list.length ; i++ ) {
            int marks = ( q_list[i].correct_option==your_answer[i] ? 5 : ( your_answer[i]==0 ? 0 : -1 ) );
            total_marks += marks;
            System.out.println( "Q-"+((char)(97+i))+") Correct Answer="+q_list[i].correct_option+"  Your Answer="+( your_answer[i]==0 ? "not attempted" : your_answer[i] )+"  marks awarded="+ marks );
        }

        System.out.println("========> YOUR TOTAL SCORE : "+total_marks+" <========= ");
        System.out.println("\n\n");
    }

}

public class Main{
    private static Scanner sc = new Scanner( System.in );

    public static void main(String[] args) {
        while( true ) {
            System.out.println(" <============  QUIZ APPLICATION ===========>");
            System.out.println("Enter 1 for Creating the Quiz");
            System.out.println("Enter 2 for Attempting the Question");
            System.out.println("Enter 3 to exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch( choice ) {
                case 1 :  Quiz q = new Quiz();
                          q.createQuestions();
                          break;
                case 2 :  Quiz.displayQuizes();
                          System.out.println("Enter the title of the quiz to be attempted : ");
                          String quiz_title = sc.nextLine();
                          Quiz ref =  Quiz.getQuiz( quiz_title );
                          if( ref==null ) 
                             System.out.println("No such quiz exist !!");
                          else{
                             Test t = new Test( ref );
                             t.takeTest();
                             t.getResult( ref );
                          }
                          break;
                case 3 : return;
                default : System.out.println("Invalid Choice !!");

            }
        }
    }
}