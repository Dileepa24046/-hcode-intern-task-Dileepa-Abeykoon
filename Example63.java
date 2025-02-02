import java.util.Scanner;
import java.io.*;

class Example63 {
    private static Student[] students = new Student[1];
    private static int studentCount = 0;

    public static void main(String[] args) {
        students = FileUtil.loadData("students.dat");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    searchStudent(scanner);
                    break;
                case 4:
                    updateStudent(scanner);
                    break;
                case 5:
                    deleteStudent(scanner);
                    break;
                case 6:
                    FileUtil.saveData(students, "students.dat");
                    System.out.println("Exit");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void resizeArray() {
        Student[] newArray = new Student[students.length * 2];
        for (int i = 0; i < studentCount; i++) {
            newArray[i] = students[i];
        }
        students = newArray;
    }

    private static void addStudent(Scanner scanner) {
        if (studentCount == students.length) {
            resizeArray();
        }

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter student grade: ");
        String grade = scanner.nextLine();
        System.out.print("Enter course: ");
        String[] courses = scanner.nextLine().split(", ");

        students[studentCount] = new Student(name, id, courses, grade);
        studentCount++;
        System.out.println("Student added successfully.");
    }

    private static void viewStudents() {
        if (studentCount == 0) {
            System.out.println("No students found.");
            return;
        }
        for (int i = 0; i < studentCount; i++) {
            System.out.println(students[i].displayInfo());
        }
    }

    private static void searchStudent(Scanner scanner) {
        System.out.print("Enter student name or ID: ");
        String searchTerm = scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getName().equalsIgnoreCase(searchTerm) || String.valueOf(students[i].getId()).equals(searchTerm)) {
                System.out.println(students[i].displayInfo());
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void updateStudent(Scanner scanner) {
        System.out.print("Enter student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId() == id) {
                System.out.print("Enter new name: ");
                students[i].setName(scanner.nextLine());
                System.out.print("Enter new grade: ");
                students[i].setGrade(scanner.nextLine());
                System.out.print("Enter new courses: ");
                students[i].setCourses(scanner.nextLine().split(", "));
                System.out.println("Student updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId() == id) {
                for (int j = i; j < studentCount - 1; j++) {
                    students[j] = students[j + 1];
                }
                studentCount--;
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }
}

class Person {
    private String name;
    private int id;

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String displayInfo() {
        return "Name: " + name + ", ID: " + id;
    }
}

class Student extends Person {
    private static final long serialVersionUID = 1L;
    private String[] courses;
    private String grade;

    public Student(String name, int id, String[] courses, String grade) {
        super(name, id);
        this.courses = courses;
        this.grade = grade;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String displayInfo() {
        String courseList = String.join(", ", courses);
        return super.displayInfo() + ", Grade: " + grade + ", Courses: " + courseList;
    }
}

class Teacher extends Person {
    private String subject;
    private String department;

    public Teacher(String name, int id, String subject, String department) {
        super(name, id);
        this.subject = subject;
        this.department = department;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String displayInfo() {
        return super.displayInfo() + ", Subject: " + subject + ", Department: " + department;
    }
}

class FileUtil {
    public static void saveData(Student[] students, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static Student[] loadData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Student[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Welcome");
            return new Student[1];
        }
    }
}
