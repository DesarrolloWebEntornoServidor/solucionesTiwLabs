package es.uc3m.tiw.lab2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import es.uc3m.tiw.lab2.modelo.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	private Connection con;
	private ResourceBundle rb;

	
	public UsuarioDAOImpl(Connection con, ResourceBundle rb) {
		super();
		this.con = con;
		this.rb = rb;
		crearTablaUsuario();
		
	}

	@Override
	public Collection<Usuario> listarUsuarios() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		System.out.println("$$$$$$ COMPROBANDO USUARIOS");

		try (Statement st = con.createStatement()) {
			ResultSet resultados = st.executeQuery(rb.getString("seleccionarTodosUsuarios"));

			Usuario usuario;
			while (resultados.next()) {
				usuario = new Usuario();
				usuario.setId(resultados.getInt("id"));
				usuario.setNombre(resultados.getString("nombre"));
				usuario.setApellidos(resultados.getString("apellidos"));
				usuario.setUsuario(resultados.getString("usuario"));
				usuario.setPassword(resultados.getString("passwd"));
				listaUsuarios.add(usuario);
				System.out.println("$$$$$$"+usuario.getNombre());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaUsuarios;
	}

	@Override
	public Usuario recuperarUnUsuarioPorClave(int pk) {
		Usuario usuario = new Usuario();

		try (PreparedStatement ps = con.prepareStatement(rb.getString("seleccionarUsuarioPK"))) {
			ps.setInt(1, pk);
			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {
				usuario = new Usuario();
				usuario.setId(resultados.getInt("id"));
				usuario.setNombre(resultados.getString("nombre"));
				usuario.setApellidos(resultados.getString("apellidos"));
				usuario.setUsuario(resultados.getString("usuario"));
				usuario.setPassword(resultados.getString("passwd"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}

	@Override
	/**
	 * Se asume que el campo nombre es unique y por tanto solo recuperará un
	 * usuario en caso de existir
	 */
	public Usuario recuperarUnUsuarioPorNombre(String nombre) {
		Usuario usuario = new Usuario();

		try (PreparedStatement ps = con.prepareStatement(rb.getString("seleccionarUsuarioNombre"))) {
			ps.setString(1, nombre);
			ResultSet resultados = ps.executeQuery();
			while (resultados.next()) {
				usuario.setId(resultados.getInt("id"));
				usuario.setNombre(resultados.getString("nombre"));
				usuario.setApellidos(resultados.getString("apellidos"));
				usuario.setUsuario(resultados.getString("usuario"));
				usuario.setPassword(resultados.getString("passwd"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}

	@Override
	public void crearTablaUsuario() {

		try (Statement stmt = con.createStatement()){
			stmt.executeUpdate(rb.getString("crearTablaUsuario"));
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
	
	@Override
	public Usuario crearUsuario(Usuario nuevoUsuario){
		try(PreparedStatement ps = 
				con.prepareStatement(rb.getString("crearUsuario"))){
		ps.setString(1, nuevoUsuario.getNombre());
		ps.setString(2, nuevoUsuario.getApellidos());
		ps.setString(3, nuevoUsuario.getUsuario());
		ps.setString(4, nuevoUsuario.getPassword());
		ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recuperarUnUsuarioPorNombre(nuevoUsuario.getNombre());
	}

	@Override
	public void borrarUsuario(Usuario usuario)     {
		try(PreparedStatement ps = con.prepareStatement(rb.getString("borrarUsuario"))){
		ps.setInt(1, usuario.getId());
		ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Usuario actualizarUsuario(Usuario usuario)   {
		Usuario usuarioActualizado = new Usuario();

		try(PreparedStatement ps = con.prepareStatement(rb.getString("actualizarUsuario"))){
		ps.setString(1, usuario.getNombre());
		ps.setString(2, usuario.getPassword());
		ps.setInt(3, usuario.getId());
		ps.execute();
		usuarioActualizado = recuperarUnUsuarioPorClave(usuario.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarioActualizado;

	}

	@Override
	public void setConexion(Connection con) {
		this.con = con;

	}

	@Override
	public void setQuerys(ResourceBundle rb) {
		this.rb = rb;

	}

}
