package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.TypeOfBook;

public interface TypeOfBookDao extends Remote {
	public boolean addTypeOfBook(TypeOfBook typeOfBook) throws RemoteException;
	
	public List<TypeOfBook>	 getAllTypeOfBook() throws RemoteException;
	
	public TypeOfBook getTypeOfBookByName(String name) throws RemoteException;

	
}
