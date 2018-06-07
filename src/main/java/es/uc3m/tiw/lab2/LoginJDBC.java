package es.uc3m.tiw.lab2;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uc3m.tiw.lab2.modelo.Usuario;
import es.uc3m.tiw.lab2.Conector;
import es.uc3m.tiw.lab2.dao.UsuarioDAO;
import es.uc3m.tiw.lab2.dao.UsuarioDAOImpl;

//@WebServlet(urlPatterns = "/loginJDBC")
@WebServlet(urlPatterns = "/loginJDBC", loadOnStartup = 1, initParams = {
		@WebInitParam(name = "configuracion", value = "es.uc3m.tiw.lab2.persistencia") })

public class LoginJDBC extends HttpServlet {

	private static final String LOGIN_JSP = "/login.jsp";
	private static final String LISTADO_JSP = "/listado.jsp";
	// private static final String ERROR_JSP = "/error.jsp";
	private static final long serialVersionUID = 1L;
	// private ServletConfig config;
	// private Usuario usuario;
	private ArrayList<Usuario> usuarios;
	private UsuarioDAO dao;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// this.config = config;
		String configuracion = getInitParameter("configuracion");

		ResourceBundle rb = ResourceBundle.getBundle(configuracion);

		Conector conector = Conector.getInstance();
		Connection con = conector.crearConexionMySQL(rb);

		// Connection con = conector.crearConexionMySQLConJNDI(rb);
		dao = new UsuarioDAOImpl(con, rb);

		usuarios = (ArrayList<Usuario>) dao.listarUsuarios();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sesion = request.getSession();
		boolean autenticado;

		if (sesion.getAttribute("autenticado") != null) {
			autenticado = (boolean) sesion.getAttribute("autenticado");
		} else {
			autenticado = false;
		}
		String pagina = LOGIN_JSP;
		if (autenticado) {
			pagina = LISTADO_JSP;

		}
		this.getServletContext().getRequestDispatcher(pagina).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user = request.getParameter("login");
		String password = request.getParameter("password");
		String mensaje = "";
		String pagina = "";
		boolean autenticado = false;
		pagina = LOGIN_JSP;
		HttpSession sesion = request.getSession();
		Usuario u = comprobarUsuario(user, password);
		usuarios = (ArrayList<Usuario>) dao.listarUsuarios();

		Map<String, String> errores = new HashMap<String, String>();

		if (user.equals("") || password.equals("")) {

			if (user.equals("")) {
				errores.put("login", "El user no puede quedar en blanco");
			}
			if (password.equals("")) {
				errores.put("password", "El campo password no puede estar vacío");

			}

			request.setAttribute("errores", errores);

		} else if (u.getId() > 0) {

			pagina = LISTADO_JSP;
			request.setAttribute("usuarios", usuarios);
			sesion.setAttribute("usuario", u);
			sesion.setAttribute("autenticado", true);

		} else {

			errores.put("loginFailed", "Usuario o password incorrectos");
			request.setAttribute("errores", errores);
			// pagina = ERROR_JSP;
			sesion.setAttribute("autenticado", false);
		}

		this.getServletContext().getRequestDispatcher(pagina).forward(request, response);

	}

	private Usuario comprobarUsuario(String user, String password) {
		Usuario u = new Usuario();
		for (Usuario usuario : usuarios) {
			if (user.equals(usuario.getUsuario()) && password.equals(usuario.getPassword())) {
				u = usuario;
				break;
			}
		}
		return u;
	}

}
