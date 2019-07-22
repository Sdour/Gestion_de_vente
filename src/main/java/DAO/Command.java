package DAO;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.query.Query;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;


public class Command extends AbstractCommand implements java.io.Serializable {

	CommandDAO cmd = new CommandDAO();

	public CommandDAO getCmd() {
		return cmd;
	}

	public void setCmd(CommandDAO cmd) {
		this.cmd = cmd;
	}

	public Command() {
	}

	public Command(Integer codecmd, Integer codeart, Integer codeclient) {
		super(codecmd, codeart, codeclient);
	}

	public Command(Integer codecmd, Integer codeart, Integer codeclient, Long quantitecmd, Date datecmd) {
		super(codecmd, codeart, codeclient, quantitecmd, datecmd);
	}

	public String creatCmd(Article a) {
		this.setCodeart(a.getCodeart());
		return "AddCmd";
	}

	public String addCommand() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
					this.setDatecmd(new Date());
					cmd.attachDirty(this);
					return "Commands";
				}
				return null;
			} else {
				facesContext.getExternalContext().redirect("User_login.faces");
				return null;
			}
		} catch (Exception e) {
			return "addcmdfailed";
		}

	}

	public void deleteCommand(Command a) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			if (session != null) {
				if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
					cmd.delete(a);
					return;
				}
			} else {
				facesContext.getExternalContext().redirect("User_login.faces");
			}
		} catch (Exception e) {
		}
	}

	public String editCommand(Command a) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
		if (session != null) {
			if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
				this.setCodeart(a.getCodeart());
				this.setCodecmd(a.getCodecmd());
				this.setQuantitecmd(a.getQuantitecmd());
				this.setDatecmd(a.getDatecmd());
				this.setCodeclient(a.getCodeclient());
				return "AddCmd";
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

	public void Generate(Command cmd) {
		ClientDAO art = new ClientDAO();
		ArticleDAO c = new ArticleDAO();
		File pdfDest = new File(
				"C:\\Users\\sdour\\Desktop\\Invoice_" + art.findById(cmd.getCodeclient()).getNomclient() + ".pdf");
		try {
			HtmlConverter.convertToPdf("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n"
					+ "    <meta charset=\"UTF-8\">\n" + "    <style>\n"
					+ "        #block_page{ width: 700px; margin: auto; font-family: 'Trebuchet MS', Arial, sans-serif ; }\n"
					+ "        #invoice{ height: 80px; }\n"
					+ "        h1{ text-align: center; color: rgb(209, 16, 16); }\n"
					+ "        #top{display: flex; justify-content: space-between; padding: 5px; color: rgb(209, 16, 16); }\n"
					+ "        #company { font-size: 1.1em; }\n"
					+ "        #middle{ display: flex; justify-content: space-between; background-color: rgb(209, 16, 16);color: white; padding: 20px; margin-bottom: 30px; }\n"
					+ "        #achat { text-align: right; }\n" + "        .upper, h1 { text-transform: uppercase; }\n"
					+ "        #table { height: 180px; padding: 20px; }\n"
					+ "        table { background-color: white; width: 660px; margin: auto; border: 1px; }\n"
					+ "        th { background-color: gray; color: white; }\n"
					+ "        td { height: 50px; text-align: center; }\n"
					+ "        #article { text-align: left; padding-left: 5px; }\n"
					+ "        #total { color: rgb(209, 16, 16); font-weight: bold; font-size: 1.3em; }\n"
					+ "        #bottom { padding: 15px; }\n" + "    </style>\n" + "    <title>Invoice</title>\n"
					+ "</head>\n" + "<body>\n" + "    <div id=\"block_page\">\n" + "        <div id=\"invoice\">\n"
					+ "            <h1>Invoice</h1>\n" + "        </div>\n" + "        <div id=\"top\">\n"
					+ "            <div id=\"company\">\n" + "                <p>Ray-Ban</br>\n"
					+ "                Genuine Since 1937</p>\n" + "            </div>\n"
					+ "            <div id=\"logo\">\n"
					+ "                <img src=\"logo.jpg\" alt=\"companyName\" id=\"logo\"/>\n"
					+ "            </div>\n" + "        </div>\n" + "        <div id=\"middle\">\n"
					+ "            <div id=\"client\">\n" + "                <p class=\"upper\">invoice To:</p>\n"
					+ "                " + art.findById(cmd.getCodeclient()).getNomclient() + "</br>\n"
					+ "                0" + art.findById(cmd.getCodeclient()).getTel() + "</p>\n"
					+ "            </div>\n" + "            <div id=\"achat\">\n"
					+ "                <p class=\"upper\">purchase invoice</br>\n" + "                INV-"
					+ cmd.getCodecmd() + "</p>\n" + "                <p>Date of invoice: " + cmd.getDatecmd() + "</p>\n"
					+ "            </div>\n" + "        </div>\n" + "        <div id=\"table\">\n"
					+ "            <table>\n" + "                <tr>\n" + "                    <th>Article</th>\n"
					+ "                    <th>Price</th>\n" + "                    <th>Quantity</th>\n"
					+ "                    <th>Total</th>\n" + "                </tr>\n" + "                <tr>\n"
					+ "                    <td id=\"article\">" + c.findById(cmd.getCodeart()).getNomart() + " </br> "
					+ c.findById(cmd.getCodeart()).getDescart() + " </td>\n" + "                    <td>"
					+ c.findById(cmd.getCodeart()).getPrixart() + "MAD</td>\n" + "                    <td>"
					+ cmd.getQuantitecmd() + "</td>\n" + "                    <td id=\"total\">"
					+ c.findById(cmd.getCodeart()).getPrixart() * cmd.getQuantitecmd() + " MAD</td>\n"
					+ "                </tr>\n" + "            </table>\n" + "        </div>\n"
					+ "        <div id=\"bottom\">\n" + "                <h4>Important Notice</h4>\n"
					+ "                <p>No item will be replaced or refunded if you don't have the invoice with you.</p>\n"
					+ "        </div>\n" + "    </div>\n" + "</body>\n" + "</html>", new FileOutputStream(pdfDest));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String nbrVentes() {
		try {
			List L = cmd.findAll();
			return String.valueOf(L.size());
		} catch (Exception e) {
			return "Erreur";
		}
	}

	public String nbrVentesCeMois() {
		try {
			Date referenceDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(referenceDate);
			c.add(Calendar.MONTH, -1);
			Date t = c.getTime();
			List<Command> L = cmd.findAll();
			List<Command> aide = new ArrayList<>();
			for (int i = 0; i < L.size(); i++) {
				Command com = L.get(i);
				if (com.getDatecmd().after(t)) {
					aide.add(com);
				}
			}
			return String.valueOf(aide.size());
		} catch (Exception e) {
			return "Erreur";
		}
	}

	public String prixVentes() {
		try {
			Article ar = new Article();
			List<Command> L = cmd.findAll();
			Float somme = (float) 0;
			for (int i = 0; i < L.size(); i++) {
				Command com = L.get(i);
				somme += com.getQuantitecmd() * (ar.getArticle().findById(com.getCodeart()).getPrixart());
			}
			return String.valueOf(somme);
		} catch (Exception e) {
			return "Erreur";
		}
	}

	public String prixVentesCeMois() {
		try {
			Article ar = new Article();
			Float somme = (float) 0;
			Date referenceDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(referenceDate);
			c.add(Calendar.MONTH, -1);
			Date t = c.getTime();
			List<Command> L = cmd.findAll();
			List<Command> aide = new ArrayList<>();
			for (int i = 0; i < L.size(); i++) {
				Command com = L.get(i);
				if (com.getDatecmd().after(t)) {
					aide.add(com);
					somme += com.getQuantitecmd() * (ar.getArticle().findById(com.getCodeart()).getPrixart());
				}
			}
			return String.valueOf(somme);
		} catch (Exception e) {
			return "Erreur";
		}
	}

}
