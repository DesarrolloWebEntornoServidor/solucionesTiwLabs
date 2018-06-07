package es.uc3m.tiw.lab2.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;

import es.uc3m.tiw.lab2.modelo.Usuario;



public interface UsuarioDAO {
	
	public abstract void crearTablaUsuario() ;
	
	public abstract Usuario actualizarUsuario(Usuario usuario)  ;

	public abstract void borrarUsuario(Usuario usuario)  ;

	public abstract Usuario crearUsuario(Usuario nuevoUsuario)  ;

	public abstract Usuario recuperarUnUsuarioPorNombre(String nombre)  ;

	public abstract Usuario recuperarUnUsuarioPorClave(int pk)  ;

	public abstract Collection<Usuario> listarUsuarios()  ;

	public abstract void setConexion(Connection con);

	public abstract void setQuerys(ResourceBundle rb);
}
