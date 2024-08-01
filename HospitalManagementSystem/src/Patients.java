import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
    private Connection connection;
    private Scanner scanner;

    public Patients(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatients() throws SQLException {
        System.out.println("Enter patient ID");
        int pid=scanner.nextInt();
        System.out.println("Enter patient name");
        String pname=scanner.next();
        System.out.println("enter patient age");
        int page=scanner.nextInt();
        System.out.println("enter pdisease");
        String pDisease=scanner.next();
        String querry="insert into Patients (Patient_ID,Patient_name,Patient_Age,Disease) values (?,?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(querry);
        preparedStatement.setInt(1,pid);
        preparedStatement.setString(2,pname);
        preparedStatement.setInt(3,page);
        preparedStatement.setString(4,pDisease);
        int rowCreated= preparedStatement.executeUpdate();
        if (rowCreated>0){
            System.out.println("Patients added successfully");
        }
        else{
            System.out.println("failed to add patient");
        }

    }
    public void viewPatients() throws SQLException {
        String veiw="select * from Patients";
        PreparedStatement preparedStatement=connection.prepareStatement(veiw);
        ResultSet resultSet= preparedStatement.executeQuery();
        System.out.println("|----pid----|----pname----|----page----|----disease----|");
        while(resultSet.next()){
            int id=resultSet.getInt(1);
            String name=resultSet.getNString(2);
            int age=resultSet.getInt(3);
            String disease=resultSet.getNString(4);
            System.out.printf("|%-11s|%-13s|%-12s|%-15s",id,name,age,disease);
            System.out.println();
        }

    }
    public boolean patientById(int pid) throws SQLException {
        String veiwById="select * from Patients where patient_ID";
        PreparedStatement preparedStatement=connection.prepareStatement(veiwById);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            return true;
        }
        return false;

    }
}
