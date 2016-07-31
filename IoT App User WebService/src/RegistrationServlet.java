
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;
import com.google.gson.Gson;

@WebServlet("/Registration")
public class RegistrationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private static UserDao userDb = null;
	
    public RegistrationServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		userDb = UserDao.getDatabase();
		System.out.println("Inserting data into database");
		String result = userDb.register( 
				request.getParameter("name"), 
				request.getParameter("email"), 
				request.getParameter("password"), 
				request.getParameter("recoveryEmail"), 
				request.getParameter("mobile"));
		System.out.println("Data inserted!");
		WebResponse webResponse = new WebResponse();
		webResponse.setResult(result);
		response.getWriter().print(new Gson().toJson(webResponse));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
