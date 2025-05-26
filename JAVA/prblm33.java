
import java.sql.*;

public class prblm33 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String pass = "your_password";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            conn.setAutoCommit(false);

            try (PreparedStatement debit = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
                 PreparedStatement credit = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?")) {

                // debit 100 from account 1
                debit.setInt(1, 100);
                debit.setInt(2, 1);
                debit.executeUpdate();

                // credit 100 to account 2
                credit.setInt(1, 100);
                credit.setInt(2, 2);
                credit.executeUpdate();

                conn.commit();
                System.out.println("Transaction successful");

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Transaction failed, rolled back");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
