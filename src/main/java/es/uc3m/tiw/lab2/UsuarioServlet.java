package es.uc3m.tiw.lab2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import es.uc3m.tiw.lab2.dao.UsuarioDAO;
import es.uc3m.tiw.lab2.dao.UsuarioDAOImpl;
import es.uc3m.tiw.lab2.modelo.Usuario;

/**
 * Servlet implementation class UsuarioServlet
 */
// @WebServlet("/usuario")
@WebServlet(urlPatterns = "/usuario", loadOnStartup = 1, initParams = {
		@WebInitParam(name = "configuracion", value = "es.uc3m.tiw.lab2.persistencia") })

public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDAO dao;
	private Connection con;
	private static final String ALTA = "ALTA", EDITAR = "EDITAR", BORRAR = "BORRAR";
	@PersistenceContext(unitName="laboratoriosPU")
    EntityManager em;
    @Resource
    UserTransaction ut;
	/**
	 * @see Servlet#init(ServletConfig)
	 */

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//this.config = config;
		dao = new UsuarioDAOImpl();
		dao.setConexion(em);
		dao.setTransaction(ut);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");

		String pagina = "/login.jsp";

		if (accion.equalsIgnoreCase(ALTA)) {
			pagina = "/altausuario.jsp";

		} else if (accion.equalsIgnoreCase(EDITAR)) {
			Usuario usuario = recuperarDatosUsuario(request);
			request.setAttribute("usuario", usuario);
			pagina = "/editarusuario.jsp";

		} else if (accion.equalsIgnoreCase(BORRAR)) {
			Usuario usuario = recuperarDatosUsuario(request);
			pagina = "/login.jsp";
			borrarUsuario(usuario);
		}
		
		this.getServletContext().getRequestDispatcher(pagina).forward(request, response);

	}

	/**
	 * Obtiene los datos del usuario a editar o borrar
	 * 
	 * @param request
	 * @return
	 */
	private Usuario recuperarDatosUsuario(HttpServletRequest request) {
		Usuario usuario ;
		
		int pk =(Integer.parseInt(request.getParameter("id")));
		//usuario.setNombre(request.getParameter("nombre"));
		//usuario.setPassword(request.getParameter("password"));
		usuario = dao.recuperarUnUsuarioPorClave(pk);
		return usuario;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accion = request.getParameter("accion");
		HttpSession sesion = request.getSession();
		boolean autenticado = false;
		String pagina = "/login.jsp";

		if (accion.equalsIgnoreCase(ALTA)) {
			Usuario usuario = new Usuario();
			usuario.setNombre(request.getParameter("nombre"));
			usuario.setPassword(request.getParameter("password"));
			usuario.setApellidos(request.getParameter("apellidos"));
			usuario.setUsuario(request.getParameter("usuario"));
			altaUsuario(usuario);
			
		} else if (accion.equalsIgnoreCase(EDITAR)) {
			Usuario usuario = recuperarDatosUsuario(request);
			modificarUsuario(usuario);
		}

		this.getServletContext().getRequestDispatcher(pagina).forward(request, response);

	}

	/**
	 * Modifica los datos del usuario con el UsuarioDao
	 * 
	 * @param usuario
	 */
	private void modificarUsuario(Usuario usuario) {
		dao.actualizarUsuario(usuario);

	}

	/**
	 * Borra los datos de un usuario con el UsuarioDao
	 * 
	 * @param usuario
	 */
	private void borrarUsuario(Usuario usuario) {

		dao.borrarUsuario(usuario);

	}

	/**
	 * Crea un usuario en la base de datos con el UsuarioDao
	 * 
	 * @param usuario
	 */
	private void altaUsuario(Usuario usuario) {

		dao.crearUsuario(usuario);

	}
}
