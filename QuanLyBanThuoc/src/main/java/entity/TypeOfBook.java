package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bson.types.ObjectId;

@Entity
@Table(name = "TypeOfBooks")
public class TypeOfBook implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 429038174040995522L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private ObjectId id;
	@Column(name = "name")
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ObjectId getId() {
		return id;
	}
	public TypeOfBook(String name) {
		super();
		this.name = name;
	}

	public TypeOfBook() {
		super();
	}
	
	public TypeOfBook(ObjectId id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public TypeOfBook(ObjectId id2) {
		// TODO Auto-generated constructor stub
		this.id=id2;
	}

	@Override
	public String toString() {
		return "TypeOfBook [id=" + id + ", name=" + name + "]";
	}
	
}
