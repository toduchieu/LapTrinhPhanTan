package daoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import dao.AccountDao;
import entity.Account;
import util.HibernateUtil;

public class AccountDaoImpl extends UnicastRemoteObject implements AccountDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5680460319162083507L;
	private EntityManager em;

	public AccountDaoImpl() throws RemoteException {
		em = HibernateUtil.getInstance().getEntityManager();
	}

	public boolean addAccount(Account account) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {		
			tr.begin();
			em.persist(account);
			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}

	public boolean updateAccount(Account account) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {		
			tr.begin();
			em.merge(account);

			tr.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return false;
	}

	public Account findAccountByName(String name) throws RemoteException {
		EntityTransaction tr = em.getTransaction();
		try {			
			tr.begin();
			String query="db.Accounts.find({'username':'"+name+"'})";
			Account account=(Account) em.createNativeQuery(query,Account.class).getSingleResult();
			tr.commit();
			return account;
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
		}
		return null;
	}

}
