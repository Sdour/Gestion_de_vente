package DAO;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.hibernate.query.Query;

public class Client extends AbstractClient implements java.io.Serializable {

	ClientDAO cl = new ClientDAO();
	// Constructors

	public ClientDAO getClient() {
		return cl;
	}

	public void setClient(ClientDAO client) {
		this.cl = client;
	}

	public Client() {
	}

	public Client(Integer codeclient) {
		super(codeclient);
	}

	public Client(Integer codeclient, String nomclient, Long tel) {
		super(codeclient, nomclient, tel);
	}

	public Client(String nomclient, Long tel) {
		super(nomclient, tel);
	}

	public String addClient() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
					cl.attachDirty(this);
					return "Clients";
				}
				return null;
			} else {
				facesContext.getExternalContext().redirect("User_login.faces");
				return null;
			}
		} catch (Exception e) {
			return "addclientfailed";
		}
	}

	public void deleteClient(Client a) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
					cl.delete(a);
					return;
				}
			} else {
				facesContext.getExternalContext().redirect("User_login.faces");
				return;
			}
		} catch (Exception e) {
		}
	}

	public String editClient(Client a) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
		if (session != null) {
			if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
				this.setCodeclient(a.getCodeclient());
				this.setNomclient(a.getNomclient());
				this.setTel(a.getTel());
				return "EditClient";
			}
		} else {
			try {
				facesContext.getExternalContext().redirect("User_login.faces");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}

	public String Commands(Client a) {
		this.setCodeclient(a.getCodeclient());
		return "CmdClient";
	}

}
