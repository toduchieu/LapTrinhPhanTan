package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entity.Account;

public interface AccountDao extends Remote {
	
	public boolean addAccount(Account account) throws RemoteException;
	
	public boolean updateAccount(Account account) throws RemoteException;
	
	public Account findAccountByName(String ten) throws RemoteException;
}
