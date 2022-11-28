package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entity.OrderDetail;

public interface OrderDetailDao extends Remote {
	
	public boolean addOrderDetail(OrderDetail orderDetail) throws RemoteException;
	
}
