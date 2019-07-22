package DAO;

import java.io.IOException;
import java.util.Enumeration;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

public class Article extends AbstractArticle implements java.io.Serializable {

	ArticleDAO art = new ArticleDAO();
	// Constructors

	public ArticleDAO getArticle() {
		return art;
	}

	public void setArticle(ArticleDAO article) {
		this.art = article;
	}

	public Article() {
	}

	public Article(Integer codeart) {
		super(codeart);
	}

	public Article(Integer codeart, String nomart, String descart, Float prixart) {
		super(codeart, nomart, descart, prixart);
	}

	public String addArticle() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
					art.attachDirty(this);
					return "Articles";
				}
				return null;
			} else {
				facesContext.getExternalContext().redirect("User_login.faces");
				return "addfailed";
			}
		} catch (Exception e) {
			return "addfailed";
		}
	}

	public void deleteArticle(Article a) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
					art.delete(a);
					return;
				}
			}else {
			facesContext.getExternalContext().redirect("User_login.faces");}
		} catch (Exception e) {
		}
	}

	public String editArticle(Article a) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
		if (session != null) {
			if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
				this.setCodeart(a.getCodeart());
				this.setNomart(a.getNomart());
				this.setDescart(a.getDescart());
				this.setPrixart(a.getPrixart());
				return "AddArticle";
			}
		}else {
		try {
			facesContext.getExternalContext().redirect("User_login.faces");
		} catch (IOException e) {
			e.printStackTrace();
		}
			return null;
		}
		return null;
	}

}
