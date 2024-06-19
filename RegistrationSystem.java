import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolled;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
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

    public String getSchedule() {
        return schedule;
    }

    public boolean registerStudent() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return courseCode + ": " + title + " (" + enrolled + "/" + capacity + " enrolled)\n" +
                "Description: " + description + "\n" +
                "Schedule: " + schedule;
    }
}

class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }

    @Override
    public String toString() {
        return studentID + ": " + name + "\n" +
                "Registered Courses: " + registeredCourses.toString();
    }
}

class CourseDatabase {
    private Map<String, Course> courses;

    public CourseDatabase() {
        courses = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCourseCode(), course);
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public void displayCourses() {
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }
}

class StudentDatabase {
    private Map<String, Student> students;

    public StudentDatabase() {
        students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getStudentID(), student);
    }

    public Student getStudent(String studentID) {
        return students.get(studentID);
    }
}

public class RegistrationSystem {
    private CourseDatabase courseDatabase;
    private StudentDatabase studentDatabase;

    public RegistrationSystem(CourseDatabase courseDatabase, StudentDatabase studentDatabase) {
        this.courseDatabase = courseDatabase;
        this.studentDatabase = studentDatabase;
    }

    public void registerStudent(String studentID, String courseCode) {
        Student student = studentDatabase.getStudent(studentID);
        Course course = courseDatabase.getCourse(courseCode);

        if (student != null && course != null && course.registerStudent()) {
            student.registerCourse(course);
            System.out.println("Student " + studentID + " registered for course " + courseCode);
        } else {
            System.out.println("Registration failed for student " + studentID + " in course " + courseCode);
        }
    }

    public void dropCourse(String studentID, String courseCode) {
        Student student = studentDatabase.getStudent(studentID);
        Course course = courseDatabase.getCourse(courseCode);

        if (student != null && course != null && course.dropStudent()) {
            student.dropCourse(course);
            System.out.println("Student " + studentID + " dropped course " + courseCode);
        } else {
            System.out.println("Dropping failed for student " + studentID + " in course " + courseCode);
        }
    }

    public void displayAvailableCourses() {
        courseDatabase.displayCourses();
    }

    public static void main(String[] args) {
        CourseDatabase courseDatabase = new CourseDatabase();
        StudentDatabase studentDatabase = new StudentDatabase();

        courseDatabase.addCourse(new Course("CS101", "Intro to Computer Science", "Basic concepts of computer science", 30, "MWF 9-10"));
        courseDatabase.addCourse(new Course("MATH101", "Calculus I", "Introduction to calculus", 25, "TTh 11-12:30"));

        studentDatabase.addStudent(new Student("S1001", "John Doe"));
        studentDatabase.addStudent(new Student("S1002", "Jane Smith"));

        RegistrationSystem system = new RegistrationSystem(courseDatabase, studentDatabase);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Display Courses\n2. Register for Course\n3. Drop Course\n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    system.displayAvailableCourses();
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();
                    system.registerStudent(studentID, courseCode);
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextLine();
                    System.out.print("Enter course code: ");
                    courseCode = scanner.nextLine();
                    system.dropCourse(studentID, courseCode);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
