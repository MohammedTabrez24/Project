import java.sql.*;

public class Doctors {
    private Connection connection;

    public Doctors(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctor() throws SQLException {
        String veiw="select * from doctor";
        PreparedStatement preparedStatement=connection.prepareStatement(veiw);
        ResultSet resultSet=preparedStatement.executeQuery();
        System.out.println("|----did----|----dname----|----specialization----|");
        while(resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getNString(2);
            String specialization = resultSet.getNString(3);
            System.out.printf("|%-11s|%-13s|%-22s",id,name,specialization);
            System.out.println();
        }


    }
    public boolean doctorById(int dId) throws SQLException {
        String veiwById="select * from doctor where doctor_ID";
        PreparedStatement preparedStatement=connection.prepareStatement(veiwById);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            return true;
        }
        return false;

    }
}
