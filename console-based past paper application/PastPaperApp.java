import java.io.*;
import java.nio.file.*;
import java.util.*;

// ABSTRACT CLASS TO REPRESNET USER
abstract class User {
    private String username, password, fullName, email;
    int choice;

    // CONSTRUCTOR
    public User() {}

    // GETTERS
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    // SETTERS
    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    // Abstract method to login
    public abstract void login();

    // Abstract method to sign up
    public abstract void signup();

    // View the user's profile
    public abstract void viewProfile();
}

// ***Student class extends User***
class Student extends User {
    Scanner sc = new Scanner(System.in);

    Paper p1 = new Paper(this);

    //CONSTRUCTOR
    public Student() {}

    @Override
    public void login() {
        // Implementation for student login
        System.out.println("\n--- Log In Your Account ---");
        System.out.print("Enter Username : ");
        String inputUsername = sc.next();
        System.out.print("Enter Password : ");
        String inputPassword = sc.next();

        boolean loginSuccess = false;

        try {
            File fileOBJ = new File("user.txt");
            Scanner fileScanner = new Scanner(fileOBJ);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");

                if (userData.length == 4 && inputUsername.equals(userData[0]) && inputPassword.equals(userData[1])) {
                    setUserName(userData[0]);
                    setPassword((userData[1]));
                    setFullName(userData[2]);
                    setEmail(userData[3]);

                    loginSuccess = true;
                    System.out.println("\nLogin successful! Welcome, " + getFullName());
                    p1.paper();
                }

            }
            if (!loginSuccess) {
                System.out.println("Login failed. Please check your username and password.");
                PastPaperApp.main(null);
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    @Override
    public void signup() {
        // Implementation for student registration
        System.out.println("\n--- Create Your Account ---");

        try {
            FileWriter writer = new FileWriter("user.txt", true);
            boolean usernameExists;

            System.out.print("Enter your full name : ");
            setFullName(sc.nextLine());

            System.out.print("Enter your e-mail address : ");
            setEmail(sc.nextLine());

            do {
                System.out.print("Create username : ");
                setUserName(sc.next());

                usernameExists = false;

                File fileOBJ = new File("user.txt");
                Scanner fileScanner = new Scanner(fileOBJ);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");

                if (userData.length == 4 && getUsername().equals(userData[0])) {
                    System.out.print("Username already exists. Please try a different one: ");
                    usernameExists = true;
                    break;
                }
            }
        } while (usernameExists);

            System.out.print("Create password : ");
            setPassword(sc.next());

            writer.write(getUsername() + "," + getPassword() + "," + getFullName() + "," + getEmail() + "\n");
            writer.close();

            System.out.println("Account created successfully!\n");
            System.out.print("Do you want to login your account?[Y|N] : ");
            char askLogin = sc.next().charAt(0);

            if (askLogin == 'Y' || askLogin == 'y')
                login();
            else
                System.out.println("\nThank you :) Have a nice day!");

        } 
        catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void viewProfile() {
        System.out.println("\n--- User Profile ---");
        System.out.println("*Full Name : " + getFullName());
        System.out.println("*E-Mail    : " + getEmail());
        System.out.println("*User Name : " + getUsername());
        System.out.println("*Password  : " + getPassword());

        System.out.println("\n1. Edit Profile");
        System.out.println("2. Change Password");
        System.out.println("3. Back");

        System.out.print("\nEnter your choice: ");
        choice = sc.nextInt();

        switch(choice) {
            /*case 1:
                // EDIT PROFILE
                break; */
            case 2:
                changePassword();
                break;
            case 3:
                p1.paper();
                break;
            default:
                System.out.println("Inalid Selection!");
                break;
        }
    }

    void changePassword() {
        System.out.print("\nEnter New password : ");
        String newPassword = sc.next();
         StringBuilder fileContent = new StringBuilder();

        try {
            File fileOBJ = new File("user.txt");
            Scanner fileScanner = new Scanner(fileOBJ);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");


                if (userData.length == 4 && getUsername().equals(userData[0])) {
                   line = userData[0] + "," + newPassword + "," + userData[2] + "," + userData[3];
                }
                fileContent.append(line).append("\n");
            }
            
            try{
                FileWriter writer = new FileWriter("user.txt");
                writer.write(fileContent.toString());
                writer.close();
                System.out.println("Password updated successfully!\n");

                p1.paper();
            }
            catch(IOException e) {
                System.out.println(e);
            }

            fileScanner.close();
        }
        catch(FileNotFoundException e) {
            System.out.println(e);
        }
    }
}

class Paper {
    Scanner sc = new Scanner(System.in);
    
    Student st1;

    public Paper(Student st1) {
        this.st1 = st1;
    }

    int choice;

    void paper() {
        System.out.println("1. Find  Papers");
        System.out.println("2. View Profile");
        System.out.println("3. Change Password");
        System.out.println("4. Logout");

        System.out.print("\nEnter your choice: ");
        int choice = sc.nextInt();

        switch(choice) {
            case 1:
                findPapers();
                break;
            case 2:
                st1.viewProfile();
                break;
            case 3:
                st1.changePassword();
                break;
            case 4:
                System.out.println("\nLogged out. Have a grate day, " + st1.getFullName());
                System.exit(0);
                break;
            default:
                System.out.println("invalid Selection!");
                break;
        }
    } 

    void  findPapers() {
        System.out.println("\n--- Select the year ---");
        System.out.println("1. First Year");
        System.out.println("2. Second Year");
        System.out.println("3. Third Year");
        System.out.println("4. Forth Year");
        System.out.println("5. Back");

        System.out.print("\nEnter your choice: ");
        choice = sc.nextInt();

        switch(choice) {
            case 1:
                firstYear();
                break;
            case 2:
                secondYear();
                break;
            case 3:
                thirdYear();
                break;
            case 4:
                forthYear();
                break;
            case 5:
                paper();
                break;
            default:
                System.out.println("Invalid Selection!");
                break;
        }
    }

    void firstYear() {
        System.out.println("\n--- Select the Semester ---");
        System.out.println("1. First Semester");
        System.out.println("2. Second Semester");
        System.out.println("3. Back");

        System.out.print("\nEnter your choice: ");
        choice = sc.nextInt();

        switch(choice) {
            case 1:
                firstYearFirstSemester();
                break;
            case 2:
                firstYearSecondSemester();
                break;
            case 3:
                findPapers();
                break;
            default:
                System.out.println("Inalid Selection!");
                break;
        }
    }

    void firstYearFirstSemester() {
        System.out.println("\n--- Select the Year ---");
        System.out.println("1. 2022");
        System.out.println("2. 2021");
        System.out.println("3. 2020");
        System.out.println("4. 2019");
        System.out.println("5. 2018");
        System.out.println("6. Back");

        System.out.print("\nEnter your choice: ");
        choice = sc.nextInt();

        switch(choice) {
            case 1:
                firstYearFirstSemesterSUbject();
                break;
            case 6:
                firstYear();
                break;
            default:
                System.out.println("Inalid Selection!");
                break;
        }
    }

    void firstYearFirstSemesterSUbject() {
        System.out.println("\n--- Select the Subject ---");
        System.out.println("1. MATHEMATICS FOR TECHNOLOGY");
        System.out.println("2. FUNDAMENTALS OF COMPUTER PROGRAMMING");
        System.out.println("3. FUNDAMENTALS OF WEB TECHNOLOGIES");
        System.out.println("4. PRINCIPAL OF MANAGEMENT");
        System.out.println("5. ESSENTIAL OF ICT");
        System.out.println("6. Back");

        System.out.print("\nEnter your choice: ");
        choice = sc.nextInt();

        switch(choice) {
            case 1:
                downloadPaper("TICT1123 Mathematics for Technology.PDF");
                firstYearFirstSemesterSUbject();
                break;
            case 2:
                downloadPaper("TICT1134 Fundamentals of Computer Programming (Practical).PDF");
                firstYearFirstSemesterSUbject();
                break;
            case 3:
                downloadPaper("TICT1142 Fundamental of Web Technologies (Theory).PDF");
                downloadPaper("TICT1142 Fundamental of Web Technologies (Practical).PDF");
                firstYearFirstSemesterSUbject();
                break;
            case 4:
                downloadPaper("TICT1152 Principles of Management.PDF");
                firstYearFirstSemesterSUbject();
                break;
            case 5:
                downloadPaper("TICT1114 Essentials of Information and Communication Technology (Theory).PDF");
                downloadPaper("TICT1114 Essentials of Information and Communication Technology (Practical).PDF");
                firstYearFirstSemesterSUbject();
                break;
            case 6:
                firstYearFirstSemester();
                firstYearFirstSemesterSUbject();
                break;
            default:
                System.out.println("Inalid Selection!");
                break;
        }
    }

    void firstYearSecondSemester() {
        System.out.println("\n--- Select the Year ---");
        System.out.println("1. 2022");
        System.out.println("2. 2021");
        System.out.println("3. 2020");
        System.out.println("4. 2019");
        System.out.println("5. 2018");
        System.out.println("6. Back");

        System.out.print("\nEnter your choice: ");
        choice = sc.nextInt();

        switch(choice) {
            case 1:
                firstYearSecondSemesterSUbject();
                break;
            case 6:
                firstYear();
                break;
            default:
                System.out.println("Inalid Selection!");
                break;
        }
    }

    void firstYearSecondSemesterSUbject() {
        System.out.println("\n--- Select the Subject ---");
        System.out.println("1. DISCRETE STRUCTURES");
        System.out.println("2. OBJECT ORIENTED PROGRAMMING");
        System.out.println("3. OPERATING SYSTEMS ");
        System.out.println("4. ELECTRONICS AND DIGITAL CIRCUIT DESIGNS");
        System.out.println("5. COMPUTATIONAL ENGINEERING DRAWING");
        System.out.println("6. IT LAW");
        System.out.println("7. SOCIAL HARMONY AND ACTIVE CITIZENSHIP");
        System.out.println("8. Back");

        System.out.print("\nEnter your choice: ");
        choice = sc.nextInt();

        switch(choice) {
            case 1:
                downloadPaper("TICT1212 Discrete Structures.PDF");
                firstYearSecondSemesterSUbject();
                break;
            case 2:
                downloadPaper("TICT1224 Object Oriented Programming (Theory).PDF");
                downloadPaper("TICT1224 Object Oriented Programming (Practical).PDF");
                firstYearSecondSemesterSUbject();
                break;
            case 3:
                downloadPaper("TICT1233 Operating System (Theory).PDF");
                downloadPaper("TICT1233 Operating System (Practical).PDF");
                firstYearSecondSemesterSUbject();
                break;
            case 4:
                downloadPaper("TICT1243 Electronic and Digital Circuit Designs.PDF");
                firstYearSecondSemesterSUbject();
                break;
            case 5:
                downloadPaper("TICT1252 Computational Engineering Drawing (Theory).PDF");
                firstYearSecondSemesterSUbject();
                break;
            case 6:
                downloadPaper("TICT1261 IT Law.PDF");
                firstYearSecondSemesterSUbject();
                break;
            case 7:
                downloadPaper("AUX1212 Social Harmony and Active Citizenship.PDF");
                firstYearSecondSemesterSUbject();
                break;
            case 8:
                firstYearSecondSemester();
                firstYearSecondSemesterSUbject();
                break;
            default:
                System.out.println("Inalid Selection!");
                break;
        }
    }

    void downloadPaper(String paperFileName) {
        System.out.print("\nEnter the destination path to save the paper : ");
        String destinationPath = sc.next();

        try {
            // Perform the file copy operation here
            Path sourcePath = Paths.get(paperFileName);
            Path destinationFilePath = Paths.get(destinationPath, paperFileName);
            Files.copy(sourcePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("Paper downloaded successfully to " + destinationFilePath.toString());
        } catch (IOException e) {
            System.out.println("Error while downloading paper: " + e.getMessage());
    }
}


    void secondYear() {
        System.out.println("\nunder developing....\n");
        paper();
    }

    void thirdYear() {
        System.out.println("\nunder developing....\n");
        paper();
    }

    void forthYear() {
        System.out.println("\nunder developing....\n");
        paper();
    }
}


// MAIN METHOD
public class PastPaperApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // STUDNET CLASS OBJECT
        Student st1 = new Student(); 

        System.out.println("\n*** Welcome to Past Paper App ***");

        System.out.println("1. Log In");
        System.out.println("2. Sign Up");
        System.out.println("3. Exit");

        System.out.print("\nEnter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                st1.login();
                break;
            case 2:
                st1.signup();
                break;
            case 3:
                System.exit(3);
                break;
            default:
                System.out.println("Invalid Input!");
                break;
        }

        sc.close();
    } 
}