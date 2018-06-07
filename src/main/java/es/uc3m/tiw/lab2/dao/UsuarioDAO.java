package es.uc3m.tiw.lab2.dao;


import java.util.Collection;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import es.uc3m.tiw.lab2.modelo.Usuario;



public interface UsuarioDAO {
	
	public abstract void crearTablaUsuario() ;
	
	public abstract Usuario actualizarUsuario(Usuario usuario)  ;

	public abstract void borrarUsuario(Usuario usuario)  ;

	public abstract Usuario crearUsuario(Usuario nuevoUsuario)  ;

	public abstract Usuario recuperarUnUsuarioPorNombre(String nombre)  ;

	public abstract Usuario recuperarUnUsuarioPorClave(int pk)  ;

	public abstract Collection<Usuario> listarUsuarios()  ;

	
	public void setConexion(EntityManager em);
	
	public void setTransaction(UserTransaction ut);
	
}
