package lab17Controller;
import lab17Model.Department;
import lab17Model.Faculty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

@WebServlet(urlPatterns = "/DisplayFaculty", loadOnStartup = 1)
public class DisplayFaculty extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public DisplayFaculty()
    {
        super();
    }


    protected void doGet( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
    	List<Department> departments = new ArrayList<Department>();
    	Connection c = null;
         try
         {
             String url = "jdbc:mysql://cs3.calstatela.edu/cs3220stu03?useSSL=false&allowPublicKeyRetrieval=true";
             String username = "cs3220stu03";
             String password = "SdwHcoylf2Ai";

             c = DriverManager.getConnection( url, username, password );
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery( "select * from department;");
             while (rs.next()) {
 				departments.add(new Department(rs.getString("d_name")));
 			}
        
              rs = stmt.executeQuery( "select * from faculty");
             while (rs.next()) {
            	 Faculty faculty = new Faculty(rs.getString("name"));
            	 faculty.setChair(rs.getBoolean("isChair"));
                     departments.get(rs.getInt("department_id")-1).getFaculty().add(faculty);
                 }
            	 
             
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
        getServletContext().setAttribute( "departments", departments );
        request.getRequestDispatcher( "/DisplayFaculty.jsp" )
            .forward( request, response );
    }

    protected void doPost( HttpServletRequest request,
        HttpServletResponse response ) throws ServletException, IOException
    {
        doGet( request, response );
    }

}
