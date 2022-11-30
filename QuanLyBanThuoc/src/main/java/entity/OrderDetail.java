package entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -464940622459165196L;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "book_Id")
	private Book book;
	
	private double quantity;
	private double totalPrice;
	
	
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public OrderDetail() {
		super();
	}
	
	public OrderDetail(Book book, double quantity) {
		super();
		this.book = book;
		this.quantity = quantity;
		this.totalPrice = book.getPrice()*quantity;
	}
	@Override
	public String toString() {
		return "OrderDetail [book=" + book + ", quantity=" + quantity + ", totalPrice=" + totalPrice + "]";
	}
	
	

}
