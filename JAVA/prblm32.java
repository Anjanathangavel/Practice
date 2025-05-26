import java.sql.*;
class StudentDAO {
    Connection con;

    public StudentDAO() throws Exception {
        con = DriverManager.getConnection("jdbc:sqlite:students.db");
        Statement stmt = con.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS students (id INTEGER PRIMARY KEY, name TEXT, marks INTEGER)");
    }

    public void insertStudent(int id, String name, int marks) throws Exception {
        PreparedStatement ps = con.prepareStatement("INSERT INTO students VALUES (?, ?, ?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, marks);
        ps.executeUpdate();
    }

    public void updateStudent(int id, String name) throws Exception {
        PreparedStatement ps = con.prepareStatement("UPDATE students SET name=? WHERE id=?");
        ps.setString(1, name);
        ps.setInt(2, id);
        ps.executeUpdate();
    }
}

