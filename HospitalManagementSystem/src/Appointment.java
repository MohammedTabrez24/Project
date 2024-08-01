import java.sql.*;
import java.util.Scanner;

public class Appointment {
    private static String url="jdbc:mysql://localhost:3306/Hospital";
    private static String user="root";
    private static String password="root";

    public static void main(String[] args) throws SQLException {
        Scanner scanner=new Scanner(System.in);
        Connection connection=DriverManager.getConnection(url,user,password);
        Patients patients=new Patients(connection,scanner);
        boolean logout=true;
        while(logout){
        System.out.println("Enter your option");
        System.out.println("1. add patient");
        System.out.println("2. view patient");
        System.out.println("3. view doctor");
        System.out.println("4. book Appointment");
        System.out.println("5. log out");


        int option=scanner.nextInt();
        Doctors doctors=new Doctors(connection);

            switch (option){
                case 1: patients.addPatients();
                break;
                case 2: patients.viewPatients();
                break;
                case 3: doctors.viewDoctor();
                break;
                case 4: Appointment.bookApp(connection,scanner,patients,doctors);
                break;
                case 5: logout=false;

            }
        }



    }
    public static void bookApp(Connection connection,Scanner scanner,Patients patients,Doctors doctors) throws SQLException {
        System.out.println("Enter the patientId");
        int pid= scanner.nextInt();
        System.out.println("Enter the doctorId");
        int did= scanner.nextInt();
        System.out.println("Enter the appointment date");
        String App_date=scanner.next();
        if(patients.patientById(pid) && doctors.doctorById(did)){
            if(doctorsavailable(did,App_date,connection)){
                System.out.println("Enter the App_id");
                int App_id=scanner.nextInt();
                String querry="insert into appointment (App_ID,App_Date,patient_ID,Doctor_ID) values (?,?,?,?) ";
                PreparedStatement preparedStatement=connection.prepareStatement(querry);
                preparedStatement.setInt(1,App_id);
                preparedStatement.setString(2,App_date);
                preparedStatement.setInt(3,pid);
                preparedStatement.setInt(4,did);
                int rowCreated=preparedStatement.executeUpdate();
                if(rowCreated>0){
                    System.out.println("App booked successfully");
                }
                else{
                    System.out.println("failed to book app");
                }

            }
            else{
                System.out.println("Doctor is not available");
            }
        }
        else{
            System.out.println("Either patients and doctors are not available");
        }

    }
    public static boolean doctorsavailable(int did , String App_Date,Connection connection) throws SQLException {
        String querry="select count(*) from appointment where Doctor_ID=? and App_ID=?";
        PreparedStatement preparedStatement= connection.prepareStatement(querry);
        preparedStatement.setInt(1,did);
        preparedStatement.setString(2,App_Date);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            int count=resultSet.getInt(1);
            if(count==0){
                return true;
            }
            else {
                return false;
            }
        }
        return false;

    }

}
