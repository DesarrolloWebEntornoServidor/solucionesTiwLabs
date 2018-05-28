package es.uc3m.tiw.lab1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;

/**
 * Servlet implementation class Ejercicio4Servlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN_JSP = "/login.jsp";
	private static final String LISTADO_JSP = "/listado.jsp";
	private static final String ERROR_JSP = "/error.jsp";
	private ServletConfig config;

	@Override
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    this.config = config;
	}
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		config.getServletContext().getRequestDispatcher(LOGIN_JSP).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nombre = request.getParameter("nombre");
		String password = request.getParameter("clave");

		String pagina = "";

		if (nombre.equals("usuario1") || password.equals("password1")) {

			pagina = LISTADO_JSP;

		} else {
			pagina = ERROR_JSP;

		}
		config.getServletContext().getRequestDispatcher(pagina).forward(request, response);

	}
}
