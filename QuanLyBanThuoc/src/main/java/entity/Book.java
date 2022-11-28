package entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.bson.types.ObjectId;
import org.hibernate.ogm.options.shared.IndexOption;
import org.hibernate.ogm.options.shared.IndexOptions;


@Entity
@Table(name = "Books",indexes = {
		@Index(columnList = "bookname",name="bookname_Indexes")
		
})
@IndexOptions(
	    @IndexOption(forIndex = "bookname_Indexes", options = "{ text: true}"))

public class Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2101165558644982762L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ObjectId id;
	private String bookName;
	private double price;
	private int quantityInStock;
	private String author;
	private String manufacturer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "type_of_book_id")
	private TypeOfBook typeOfBook;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public TypeOfBook getTypeOfBook() {
		return typeOfBook;
	}

	public void setTypeOfBook(TypeOfBook typeOfBook) {
		this.typeOfBook = typeOfBook;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Book(ObjectId id, String bookName, double price, int quantityInStock, String author, String manufacturer,
			TypeOfBook typeOfBook) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.author = author;
		this.manufacturer = manufacturer;
		this.typeOfBook = typeOfBook;
	}

	public Book(String bookName, double price, int quantityInStock, String author, String manufacturer,
			TypeOfBook typeOfBook) {
		super();
		this.bookName = bookName;
		this.price = price;
		this.quantityInStock = quantityInStock;
		this.author = author;
		this.manufacturer = manufacturer;
		this.typeOfBook = typeOfBook;
	}

	public Book() {
		super();
	}

	@Override
	public String toString() {
		return "Thuoc [id=" + id + ", bookName=" + bookName + ", price=" + price + ", quantityInStock="
				+ quantityInStock + ", author=" + author + ", manufacturer=" + manufacturer + ", typeOfBook="
				+ typeOfBook + "]";
	}
	
	
}
