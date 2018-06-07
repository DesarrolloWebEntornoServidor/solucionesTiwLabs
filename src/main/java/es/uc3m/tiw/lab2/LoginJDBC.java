package es.uc3m.tiw.lab2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import es.uc3m.tiw.lab2.dao.UsuarioDAO;
import es.uc3m.tiw.lab2.dao.UsuarioDAOImpl;
import es.uc3m.tiw.lab2.modelo.Usuario;

//@WebServlet(urlPatterns = "/loginJDBC")
@WebServlet(urlPatterns = "/loginJDBC", loadOnStartup = 1, initParams = {
		@WebInitParam(name = "configuracion", value = "es.uc3m.tiw.lab2.persistencia") })

public class LoginJDBC extends HttpServlet {

	private static final String LOGIN_JSP = "/login.jsp";
	private static final String LISTADO_JSP = "/listado.jsp";
	// private static final String ERROR_JSP = "/error.jsp";
	private static final long serialVersionUID = 1L;
	// private Usuario usuario;
	private List<Usuario> usuarios;
	private UsuarioDAO dao;
	@PersistenceContext(unitName="laboratoriosPU")
	private EntityManager em;
	@Resource
	UserTransaction ut;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// creamos unos usuarios de prueba al inicio para que no falle dao.listarUsuarios();
		Usuario u1 = new Usuario("Juan", "Sanz", "jsanz", "123456");
		Usuario u2 = new Usuario("Ana", "Alba", "aalba", "123456");
		Usuario u3 = new Usuario("Benito", "Bueno", "bbueno", "123456");
		Usuario u4 = new Usuario("Carlos", "Tessier", "tessier", "123456");
		try {
			ut.begin();
			em.persist(u1);
			em.persist(u2);
			em.persist(u3);
			em.persist(u4);
			
			ut.commit();

		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (NotSupportedException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		} catch (RollbackException e1) {
			e1.printStackTrace();
		} catch (HeuristicMixedException e1) {
			e1.printStackTrace();
		} catch (HeuristicRollbackException e1) {
			e1.printStackTrace();
		}
		
		dao = new UsuarioDAOImpl(); 
		dao.setConexion(em);
		dao.setTransaction(ut);
		
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
		
		usuarios = (List<Usuario>) dao.listarUsuarios();
		Usuario u = comprobarUsuario(user, password);

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
