import java.util.Scanner;

class Subject{
    String subject_name, subject_code;
    float max_marks, acquired_marks;

    public Subject( String subject_name , String subject_code , float max_marks , float acquired_marks ) {
        this.subject_name = subject_name;
        this.subject_code = subject_code;
        this.max_marks = max_marks;
        this.acquired_marks = acquired_marks;
    }
}

class MarksCalculator{
    private static Scanner sc;
    private float total_marks, marks_obtained, percentage ;
    private Subject[] subjects;
    private int num;

    public MarksCalculator() {
       sc = new Scanner( System.in );
       total_marks = 0;
       marks_obtained = 0;
       percentage = 0;

       this.useCalculator();
       this.calculatePercentage();
    }

    private void calculatePercentage() {
        for( int i=0 ; i<num ; i++ ) {
            total_marks += subjects[i].max_marks;
            marks_obtained += subjects[i].acquired_marks;
        }
        percentage = (marks_obtained/total_marks)*100;
    }

    private Subject getSubjectDetails() {
        System.out.print("\n\nEnter Subject name :- ");
        String subject_name = sc.next();

        System.out.print("Enter Subject code :- ");
        String subject_code = sc.next();

        System.out.print("Enter Maximum marks :- ");
        float max_marks = sc.nextFloat();

        System.out.print("Enter Acquired marks :- ");
        float acquired_marks = sc.nextFloat();

        return ( new Subject(subject_name, subject_code, max_marks, acquired_marks) );
    }

    private void useCalculator() {
        System.out.println("\nEnter number of subjects :- ");
        num = sc.nextInt();

        subjects = new Subject[num];

        for( int i=0 ; i<num ; i++ ) {
            System.out.println(" Enter Subject"+(i+1)+" Details => ");
            subjects[i] = this.getSubjectDetails();
        }
    }

    public float getPercentage() {
        return percentage;
    }

    public String getGrade() {
        if( percentage==0 )
            return "AB";
        else if( percentage>=90 )
           return "S";
        else if( percentage>=75 ) 
           return "A";
        else if( percentage>=65 )
           return "B";
        else if( percentage>=55 )
           return "C";
        else if( percentage>=40 )
           return "D";
        else
           return "F";
    }

    public void displaySubjectWise() {
        for( int i=0 ; i<num ; i++ ) {
           System.out.println(" ==== SUBJECT NAME : "+subjects[i].subject_name+" ==== ");
           System.out.println("SUBJECT CODE : "+subjects[i].subject_code );
           System.out.println("Percentage : "+ (100*subjects[i].acquired_marks/subjects[i].max_marks)+" \n" );
        }
    }
}

public class Main{
     public static void main(String[] args) {
        MarksCalculator cal_obj = new MarksCalculator();
       
        System.out.println("\n\n<----- Subject Wise Percentage ----->");
        cal_obj.displaySubjectWise();

        System.out.println("\n\n<----- Overall Percentage ------> ");
        System.out.println("My Percentage : "+cal_obj.getPercentage() );

        System.out.println("\n\n<------- GRADE ----------> ");
        System.out.println("My Grade : "+cal_obj.getGrade() );
        
     }
}