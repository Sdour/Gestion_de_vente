package DAO;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.query.Query;

public class User extends AbstractUser implements java.io.Serializable {

	UserDAO us = new UserDAO();

	public UserDAO getUser() {
		return us;
	}

	public void setUser(UserDAO user) {
		this.us = user;
	}

	public User() {
	}

	public User(Integer id) {
		super(id);
	}

	public User(Integer id, String name, String password, String profil) {
		super(id, name, password, profil);
	}

	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		List list = us.findByName(this.getName());
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (((User) list.get(i)).getPassword().equals(this.getPassword())) {
					this.setId(((User) list.get(i)).getId());
					this.setProfil(((User) list.get(i)).getProfil());
					context.getExternalContext().getSessionMap().put("user", this);
					return "success";
				}
			}
		}
		FacesMessage msg = new FacesMessage("erreur");
		context.addMessage("loginMsg", msg);
		return "fail";
	}

	public String logout() {
//		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ec = context.getExternalContext();

		final HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		HttpServletResponse response = (HttpServletResponse) ec.getResponse();
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
		request.getSession(false).invalidate();

		return "User_login.jsp";
	}

	public String addUser() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin")) {
					us.attachDirty(this);
					return "Users";
				}
				return null;
			} else {
				facesContext.getExternalContext().redirect("User_login.faces");
				return null;
			}
		} catch (Exception e) {
			return "adduserfailed";
		}
	}

	public String delete() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin")) {
					us.delete(this);
					return "addusersucced";
				}
				return "";
			}
			facesContext.getExternalContext().redirect("User_login.faces");
			return "";
		} catch (Exception e) {
			return "adduserfailed";
		}
	}

	public void deleteUser(User u) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin")) {
					us.delete(u);
					return;
				}
				return;
			} else {
				facesContext.getExternalContext().redirect("User_login.faces");
			}
		} catch (Exception e) {
		}
	}

	public String editUser(User u) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin")) {
					this.setId(u.getId());
					this.setName(u.getName());
					this.setPassword(u.getPassword());
					this.setProfil(u.getProfil());
					return "EditUser";
				}
				return null;
			}
			facesContext.getExternalContext().redirect("User_login.faces");
		} catch (Exception e) {
		}
		return "";
	}

}
