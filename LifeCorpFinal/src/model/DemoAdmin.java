package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DEMO_ADMIN database table.
 * 
 */
@Entity
@Table(name="DEMO_ADMIN", schema="TESTUSER")
@NamedQuery(name="DemoAdmin.findAll", query="SELECT d FROM DemoAdmin d")
public class DemoAdmin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String email;

	private String password;

	public DemoAdmin() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}