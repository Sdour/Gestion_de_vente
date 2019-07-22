package DAO;

public abstract class AbstractArticle implements java.io.Serializable {

	private Integer codeart;
	private String nomart;
	private String descart;
	private Float prixart;

	public AbstractArticle() {
	}

	public AbstractArticle(Integer codeart) {
		this.codeart = codeart;
	}

	public AbstractArticle(Integer codeart, String nomart, String descart, Float prixart) {
		this.codeart = codeart;
		this.nomart = nomart;
		this.descart = descart;
		this.prixart = prixart;
	}

	public Integer getCodeart() {
		return this.codeart;
	}

	public void setCodeart(Integer codeart) {
		this.codeart = codeart;
	}

	public String getNomart() {
		return this.nomart;
	}

	public void setNomart(String nomart) {
		this.nomart = nomart;
	}

	public String getDescart() {
		return this.descart;
	}

	public void setDescart(String descart) {
		this.descart = descart;
	}

	public Float getPrixart() {
		return this.prixart;
	}

	public void setPrixart(Float prixart) {
		this.prixart = prixart;
	}

}