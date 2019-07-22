package DAO;


public abstract class AbstractUser implements java.io.Serializable {

	private Integer id;
	private String name;
	private String password;
	private String profil;

	public AbstractUser() {
	}

	public AbstractUser(Integer id) {
		this.id = id;
	}

	public AbstractUser(Integer id, String name, String password, String profil) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.profil = profil;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfil() {
		return this.profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

}