package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.bson.types.ObjectId;
import org.hibernate.hql.ast.origin.hql.parse.HQLParser.new_key_return;

import dao.BookDao;
import entity.Book;
import util.HibernateUtil;

public class BookDaoImpl  extends UnicastRemoteObject implements BookDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3692853915209401270L;
	private EntityManager em;
	public BookDaoImpl() throws RemoteException {
		em = HibernateUtil.getInstance().getEntityManager();
	}

	public boolean addBook(Book book) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {		
			tr.begin();
			em.merge(book);

			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}
	
	

	public List<Book> getBookByTypeId(ObjectId typeId) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {	
			tr.begin();
//			String query = "db.dsBooks.aggregate([{"
//					+ "    '$match': {"
//					+ "        'type_of_book_id': ObjectId('"+typeId+"')"		
//					+ "    }"
//					+ "}])";
			String query = "db.Books.aggregate([{'$match':{'type_of_book_id': ObjectId('"+typeId+"')}}])";
			@SuppressWarnings("unchecked")
			List<Book> books = em.createNativeQuery(query,Book.class).getResultList();
			tr.commit();
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}		
		return null;
	}

	public Book getBookByBookNameVaTypeId(String name, ObjectId typeId) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {			
			tr.begin();
			String query = "db.Books.aggregate([{"
					+ "    '$match': {"
					+ "        'book_name': '"+name+"' ,'type_of_book_id': ObjectId('"+typeId+"'),"
					+ "    }\r\n"
					+ "}])";		
			Book book = (Book) em.createNativeQuery(query,Book.class).getSingleResult();
			tr.commit();
			return book;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return null;
	}

	public List<Book> getAllBook() throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tr=em.getTransaction();
		try {
			tr.begin();
			@SuppressWarnings("unchecked")
			List<Book> books=em.createNativeQuery("db.Books.find({})",Book.class).getResultList();
			tr.commit();
			return books;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public boolean updateBook(Book book) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {
			tr.begin();
			em.merge(book);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}

	public List<Book> findBook(String bookId) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {		
			tr.begin();		
			String query="db.Books.find({ $text: { $search: '"+bookId+"' } } )";		
			@SuppressWarnings("unchecked")
			List<Book> books=em.createNativeQuery(query,Book.class).getResultList();
			tr.commit();
			return books;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}	
		return null;
	}
public static void main(String[] args) throws RemoteException {
	ObjectId aId = new ObjectId("63864291a0b1e0024842987a");
	BookDaoImpl bookDaoImpl =new BookDaoImpl();
	System.out.println(bookDaoImpl.getBookByTypeId(aId));
}
}
