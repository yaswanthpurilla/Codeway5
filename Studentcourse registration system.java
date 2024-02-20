import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CourseDatabase courseDatabase = new CourseDatabase();
        StudentDatabase studentDatabase = new StudentDatabase();

        // Sample course data
        courseDatabase.addCourse(new Course("CSCI101", "Introduction to Computer Science", "An introductory course to computer science concepts", 30, "Mon/Wed 9:00-10:30"));
        courseDatabase.addCourse(new Course("MATH201", "Calculus I", "Fundamental concepts of calculus", 25, "Tue/Thu 13:00-14:30"));
        courseDatabase.addCourse(new Course("ENG101", "English Composition", "Improving writing skills in English", 35, "Mon/Fri 11:00-12:30"));

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Student Course Registration System");
            System.out.println("1. View Course Listing");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    courseDatabase.displayCourseListing();
                    break;
                case 2:
                    studentDatabase.registerCourse(courseDatabase);
                    break;
                case 3:
                    studentDatabase.dropCourse();
                    break;
                case 4:
                    studentDatabase.displayRegisteredCourses();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        } while (choice != 5);

        scanner.close();
    }
}

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
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

    public String getSchedule() {
        return schedule;
    }
    
    public void decrementCapacity() {
        capacity--;
    }
    
    public void incrementCapacity() {
        capacity++;
    }
}

class CourseDatabase {
    private List<Course> courses;

    public CourseDatabase() {
        courses = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void displayCourseListing() {
        System.out.println("Course Listing:");
        for (Course course : courses) {
            System.out.println("Course Code: " + course.getCourseCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Capacity: " + course.getCapacity());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println();
        }
    }

    public List<Course> getCourses() {
        return courses;
    }
}

class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
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
}

class StudentDatabase {
    private List<Student> students;

    public StudentDatabase() {
        students = new ArrayList<>();
    }

    public void registerCourse(CourseDatabase courseDatabase) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        Student student = new Student(studentId, name);
        students.add(student);

        System.out.println("Available Courses:");
        List<Course> courses = courseDatabase.getCourses();
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getTitle());
        }
        System.out.print("Enter course number to register: ");
        int courseNumber = scanner.nextInt();
        if (courseNumber >= 1 && courseNumber <= courses.size()) {
            Course selectedCourse = courses.get(courseNumber - 1);
            if (selectedCourse.getCapacity() > 0) {
                student.registerCourse(selectedCourse);
                selectedCourse.decrementCapacity();
                System.out.println("Course registration successful.");
            } else {
                System.out.println("Course is already full. Registration failed.");
            }
        } else {
            System.out.println("Invalid course number.");
        }
    }

    public void dropCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Course Code to drop: ");
        String courseCode = scanner.nextLine();

        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                List<Course> registeredCourses = student.getRegisteredCourses();
                for (Course course : registeredCourses) {
                    if (course.getCourseCode().equals(courseCode)) {
                        registeredCourses.remove(course);
                        course.incrementCapacity();
                        System.out.println("Course dropped successfully.");
                        return;
                    }
                }
                System.out.println("Course not found in registered courses.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void displayRegisteredCourses() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();

        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                List<Course> registeredCourses = student.getRegisteredCourses();
                if (registeredCourses.isEmpty()) {
                    System.out.println("No courses registered for this student.");
                } else {
                    System.out.println("Registered Courses for Student " + studentId + ":");
                    for (Course course : registeredCourses) {
                        System.out.println("Course Code: " + course.getCourseCode());
                        System.out.println("Title: " + course.getTitle());
                        System.out.println("Description: " + course.getDescription());
                        System.out.println("Schedule: " + course.getSchedule());
                        System.out.println();
                    }
                }
                return;
            }
        }
        System.out.println("Student not found.");
    }
}