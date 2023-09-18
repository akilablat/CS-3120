package lab17Controller;
import lab17Model.Department;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddFaculty")
public class AddFaculty extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public AddFaculty()
    {
        super();
    }

    protected void doGet( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
    	List<Department> departments = ((List<Department>) getServletContext().getAttribute( "departments" ));
    	 request.setAttribute("departments", departments);
        request.getRequestDispatcher( "/AddFaculty.jsp" )
            .forward( request, response );
    }

    protected void doPost( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
    	String departmentName = request.getParameter( "department" );
    	String name = request.getParameter( "faculty" );
    	int department_id = 0;
       
        Connection c = null;
        try
        {
            String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu03?useSSL=false&allowPublicKeyRetrieval=true";
            String username = "cs3220stu03";
            String password = "SdwHcoylf2Ai";

            c = DriverManager.getConnection( url, username, password );
            
            Statement stmr = c.createStatement();
            ResultSet rs = stmr.executeQuery( "select * from department;");
            while (rs.next()) {
				if( departmentName.equals(rs.getString("d_name"))) {
					department_id = rs.getInt("id");
				}
			}
   
            String sql = "insert into faculty (name,isChair,department_id) values (?, ?, ?);";
			PreparedStatement stmt =c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1,name);
			if( request.getParameter( "chair" ) != null ) {
				stmt.setBoolean(2, true);
			}
			else {
				stmt.setBoolean(2, false);
			}
			stmt.setInt(3,department_id);
			stmt.executeUpdate();
			
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (c != null)
					c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

        response.sendRedirect( "DisplayFaculty" );
    }

}
