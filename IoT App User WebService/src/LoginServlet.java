import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.UserDao;
import com.dto.User;
import com.google.gson.Gson;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	
	private UserDao userDB;
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		userDB = new UserDao();
		User u = userDB.login(request.getParameter("email"), request.getParameter("password"));
		WebResponse webResponse = new WebResponse();
		if(u != null) {
			webResponse.setResult("sucess");
			webResponse.setData(u);
		} else {
			webResponse.setResult("error");
			webResponse.setData(u);
		}
		PrintWriter writer = response.getWriter();
		writer.print(new Gson().toJson(webResponse));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
