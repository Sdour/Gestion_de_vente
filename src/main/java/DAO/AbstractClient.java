package DAO;


public abstract class AbstractClient implements java.io.Serializable {

	private Integer codeclient;
	private String nomclient;
	private Long tel;

	public AbstractClient() {
	}

	public AbstractClient(Integer codeclient) {
		this.codeclient = codeclient;
	}

	public AbstractClient(Integer codeclient, String nomclient, Long tel) {
		this.codeclient = codeclient;
		this.nomclient = nomclient;
		this.tel = tel;
	}

	public AbstractClient(String nomclient, Long tel) {
		this.nomclient = nomclient;
		this.tel = tel;
	}

	public Integer getCodeclient() {
		return this.codeclient;
	}

	public void setCodeclient(Integer codeclient) {
		this.codeclient = codeclient;
	}

	public String getNomclient() {
		return this.nomclient;
	}

	public void setNomclient(String nomclient) {
		this.nomclient = nomclient;
	}

	public Long getTel() {
		return this.tel;
	}

	public void setTel(Long tel) {
		this.tel = tel;
	}

}