package es.uc3m.tiw.lab1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private List<String> listausuarios;


	@Override
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    this.config = config;
	    listausuarios = new ArrayList<>();
		listausuarios.add("carlos");
		listausuarios.add("eva");
		listausuarios.add("marta");
		listausuarios.add("oscar");
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

		String nombre = request.getParameter("nombre").toLowerCase();
		String password = request.getParameter("clave");

		String pagina = "";

		if (listausuarios.contains(nombre) || password.equals("1234")) {

			pagina = LISTADO_JSP;
			request.setAttribute("usuarios", listausuarios);
			

		} else {
			pagina = ERROR_JSP;

		}
		config.getServletContext().getRequestDispatcher(pagina).forward(request, response);

	}
}
