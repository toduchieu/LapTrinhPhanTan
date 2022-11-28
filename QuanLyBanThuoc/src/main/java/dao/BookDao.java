package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import org.bson.types.ObjectId;

import entity.Book;

public interface BookDao extends Remote {
	
	public boolean addBook(Book book) throws RemoteException;
	
	public List<Book> getBookByTypeId(ObjectId typeId) throws RemoteException;
	
	public Book getBookByBookNameVaTypeId(String bookName,ObjectId typeId) throws RemoteException;
	
	public List<Book> getAllBook ()throws RemoteException;
	
	public boolean updateBook(Book book) throws RemoteException;
	
	public List<Book> findBook(String key) throws RemoteException;

	
	
	
}
