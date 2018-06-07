package es.uc3m.tiw.lab2.dao;


import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import es.uc3m.tiw.lab2.modelo.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {

	private EntityManager em;
	private UserTransaction ut;

	@Override
	public Collection<Usuario> listarUsuarios() {

		return em.createQuery("select u from Usuario u", Usuario.class).getResultList();
	}

	@Override
	public Usuario recuperarUnUsuarioPorClave(int pk) {
		return em.find(Usuario.class, pk);

	}

	@Override
	/**
	 * Se asume que el campo nombre es unique y por tanto solo recuperará un
	 * usuario en caso de existir
	 */
	public Usuario recuperarUnUsuarioPorNombre(String nombre) {
		Query consulta = em.createQuery("select u from Usuario u where u.usuario=:nom", Usuario.class);
		consulta.setParameter("nom", nombre);
		return (Usuario) consulta.getResultList().get(0);
	}

	@Override
	public void crearTablaUsuario() {

		//


	}
	
	@Transactional
	@Override
	public Usuario crearUsuario(Usuario nuevoUsuario) {
		try {
			ut.begin();
			em.persist(nuevoUsuario);
			ut.commit();
			em.flush();
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			e.printStackTrace();
		}
		
		return recuperarUnUsuarioPorNombre(nuevoUsuario.getUsuario());
	}

	@Override
	public void borrarUsuario(Usuario usuario)     {
		try {
			ut.begin();
			em.remove(em.merge(usuario));
			
			ut.commit();
		} catch (NotSupportedException e) {
 			e.printStackTrace();
		} catch (SystemException e) {
 			e.printStackTrace();
		} catch (SecurityException e) {
 			e.printStackTrace();
		} catch (IllegalStateException e) {
 			e.printStackTrace();
		} catch (RollbackException e) {
 			e.printStackTrace();
		} catch (HeuristicMixedException e) {
 			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
 			e.printStackTrace();
		}
		
		

	}

	@Override
	public Usuario actualizarUsuario(Usuario usuario)   {
		try{
		ut.begin();
		em.merge(usuario);
		ut.commit();
		} catch (NotSupportedException e) {
 			e.printStackTrace();
		} catch (SystemException e) {
 			e.printStackTrace();
		} catch (SecurityException e) {
 			e.printStackTrace();
		} catch (IllegalStateException e) {
 			e.printStackTrace();
		} catch (RollbackException e) {
 			e.printStackTrace();
		} catch (HeuristicMixedException e) {
 			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
 			e.printStackTrace();
		}
		return recuperarUnUsuarioPorClave(usuario.getId());


	}

	@Override
	public void setConexion(EntityManager em) {
		this.em = em;
		
	}
	@Override
	public void setTransaction(UserTransaction ut){
		this.ut = ut;
	}

}
