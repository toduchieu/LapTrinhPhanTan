package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.hql.ast.origin.hql.parse.HQLParser.new_key_return;

import dao.TypeOfBookDao;
import entity.TypeOfBook;
import util.HibernateUtil;

public  class TypeOfBookDaoImpl extends UnicastRemoteObject implements TypeOfBookDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7629041601287634790L;
	private EntityManager em;
	public TypeOfBookDaoImpl() throws RemoteException {
		em = HibernateUtil.getInstance().getEntityManager();
	}
	
	public boolean addTypeOfBook(TypeOfBook typeOfBook) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.persist(typeOfBook);		
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}	
		return false;
	}	

	public List<TypeOfBook> getAllTypeOfBook() throws RemoteException {

		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			@SuppressWarnings("unchecked")
			List<TypeOfBook> ls =  em.createNativeQuery("db.TypeOfBooks.find({})",TypeOfBook.class).getResultList();
			tr.commit();
			return ls;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}		
		return null;
	}
	

	public TypeOfBook getTypeOfBookByName(String name) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();		
			
			TypeOfBook typeOfBook = (TypeOfBook) em.createNativeQuery("db.TypeOfBooks.find({'name': '"+name+"'})",TypeOfBook.class).getSingleResult();	
			tr.commit();
			return typeOfBook;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return null;
	}
public static void main(String[] args) throws RemoteException {
	TypeOfBookDaoImpl tesTypeOfBookDaoImpl = new TypeOfBookDaoImpl();
	System.out.println(tesTypeOfBookDaoImpl.getTypeOfBookByName("Sách Giáo trình"));
	
}
}
