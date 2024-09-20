import java.util.*;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private int enrolled;

    public Course(String code, String title, String description, int capacity) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public boolean isAvailable() {
        return enrolled < capacity;
    }

    public void enroll() {
        if (isAvailable()) {
            enrolled++;
        } else {
            throw new RuntimeException("Course is full");
        }
    }

    public void drop() {
        if (enrolled > 0) {
            enrolled--;
        } else {
            throw new RuntimeException("No students enrolled");
        }
    }

    @Override
    public String toString() {
        return String.format("Code: %s, Title: %s, Description: %s, Capacity: %d, Enrolled: %d",
                code, title, description, capacity, enrolled);
    }
}

class Student {
    private String id;
    private String name;
    private Set<String> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(String courseCode) {
        registeredCourses.add(courseCode);
    }

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }
}

class CourseDatabase {
    private Map<String, Course> courses;

    public CourseDatabase() {
        courses = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    public Course getCourse(String code) {
        return courses.get(code);
    }

    public Collection<Course> getAllCourses() {
        return courses.values();
    }

    public void removeCourse(String code) {
        courses.remove(code);
    }
}

class StudentDatabase {
    private Map<String, Student> students;

    public StudentDatabase() {
        students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    public Student getStudent(String id) {
        return students.get(id);
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }
}

class CourseSystem {
    CourseDatabase courseDatabase;
    StudentDatabase studentDatabase;

    public CourseSystem() {
        courseDatabase = new CourseDatabase();
        studentDatabase = new StudentDatabase();
    }

    public void displayCourses() {
        for (Course course : courseDatabase.getAllCourses()) {
            System.out.println(course);
        }
    }

    public void registerStudentForCourse(String studentId, String courseCode) {
        Student student = studentDatabase.getStudent(studentId);
        Course course = courseDatabase.getCourse(courseCode);

        if (student == null || course == null) {
            throw new RuntimeException("Student or course not found");
        }

        if (course.isAvailable()) {
            course.enroll();
            student.registerCourse(courseCode);
            System.out.println("Registration successful");
        } else {
            System.out.println("Course is full");
        }
    }

    public void dropStudentFromCourse(String studentId, String courseCode) {
        Student student = studentDatabase.getStudent(studentId);
        Course course = courseDatabase.getCourse(courseCode);

        if (student == null || course == null) {
            throw new RuntimeException("Student or course not found");
        }

        if (student.getRegisteredCourses().contains(courseCode)) {
            course.drop();
            student.dropCourse(courseCode);
            System.out.println("Course dropped successfully");
        } else {
            System.out.println("Student is not registered for this course");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CourseSystem system = new CourseSystem();

        Course javaCourse = new Course("CS101", "Introduction to Java", "Learn the basics of Java programming.", 30);
        Course pythonCourse = new Course("CS102", "Introduction to Python", "Learn the basics of Python programming.", 25);

        // Add courses to the database
        system.courseDatabase.addCourse(javaCourse);
        system.courseDatabase.addCourse(pythonCourse);

        // Create some students
        Student alice = new Student("S001", "Alice Smith");
        Student bob = new Student("S002", "Bob Johnson");

        // Add students to the database
        system.studentDatabase.addStudent(alice);
        system.studentDatabase.addStudent(bob);

        // Display available courses
        system.displayCourses();

        // Register Alice for Java course
        system.registerStudentForCourse("S001", "CS101");

        // Drop Alice from Java course
        system.dropStudentFromCourse("S001", "CS101");
    }
}
