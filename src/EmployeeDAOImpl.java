import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO{
    //set connection
    public static String driverName = "org.sqlite.JDBC";
    public static String url = "jdbc:sqlite:MIT_Company.sqlite";
    public static Connection conn = null;

    //Constant Operators
    //SQL CRUD
    public static final String GET_ALL_EMP = "select * from Employee";
    public static final String ADD_NEW_EMP = "insert into Employee " +
            "(emp_id,name,position,email,salary) values (?,?,?,?,?)";
    public static final String FIND_EMP_BY_ID = "select * from Employee" +
            " where emp_id = ?";

    //Create Instants
    private static EmployeeDAOImpl instance = new EmployeeDAOImpl();
    public static EmployeeDAOImpl getInstance(){
        return instance;
    }

    //constructor
    public EmployeeDAOImpl(){
        //load class driver
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @Override
    public List<Employee> getAllEmp() {
        List<Employee> emp = new ArrayList<Employee>();

        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(GET_ALL_EMP);

            while (rs.next()){
                emp.add(new Employee(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5)));
            }
            //close connection
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return emp;
    }

    @Override
    public void addEmp(Employee emp) {
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(ADD_NEW_EMP);
            //set parameter
            ps.setString(1,emp.getEmp_id());
            ps.setString(2,emp.getName());
            ps.setString(3,emp.getPosition());
            ps.setString(4,emp.getEmail());
            ps.setDouble(5,emp.getSalary());

            if (ps.execute() == false){
                System.out.println("Successful add a new data to database.");
            }else {
                System.out.println("Could not add new data to database.");
            }
            //close connection
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee findById(String id) {

        Employee myemp = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(FIND_EMP_BY_ID);
            //set parameter
            ps.setString(1,id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                 myemp = new Employee(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5));

            }else {
                System.out.println("Could not found employee id: "+id);

            }
            //close connection
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myemp;
    }

    @Override
    public void updateEmp(Employee employee) {

    }

    @Override
    public void deleteEmp(String id) {

    }
}
