package es.uc3m.tiw.lab1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.uc3m.tiw.lab1.dominios.Usuario;

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

	private Usuario usuario;
	private ArrayList<Usuario> usuarios;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	    this.config = config;
		usuarios = new ArrayList<Usuario>();
		usuarios.add(new Usuario("Carlos", "Tessier", "carlos", "1234"));
		usuarios.add(new Usuario("Maria", "perez", "maria", "12345678"));
		usuarios.add(new Usuario("Daniel", "Garcia", "dani", "12345678"));

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

		String user = request.getParameter("login").toLowerCase();
		String password = request.getParameter("password");
		String mensaje = "";
		String pagina = LOGIN_JSP;

		HttpSession sesion = request.getSession();
		Usuario u = comprobarUsuario(user, password);
		
		Map<String, String> errores = new HashMap<String, String>();

		
		if (user.equals("")|| password.equals("")) {
			
			if (user.equals("")) {
				errores.put("login", "El user no puede quedar en blanco");	
			}
			if (password.equals("")) {
				errores.put("password", "El campo password no puede estar vacío");
					
			}
			
			request.setAttribute("errores", errores);
		}
		else if (!u.getNombre().equals("")) {
			pagina = LISTADO_JSP;
			request.setAttribute("usuarios", usuarios);
			sesion.setAttribute("usuario", u);
			sesion.setAttribute("autenticado", true);

		} else {

			errores.put("loginFailed","Usuario o password incorrectos");
			request.setAttribute("errores", errores);
			//pagina = ERROR_JSP;
			sesion.setAttribute("autenticado",false);

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
