package DAO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.LockOptions;
import org.hibernate.query.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(CommandDAO.class);
	// property constants
	public static final String CODEART = "codeart";
	public static final String CODECLIENT = "codeclient";
	public static final String QUANTITECMD = "quantitecmd";

	public void save(Command transientInstance) {
		log.debug("saving Command instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Command persistentInstance) {
		log.debug("deleting Command instance");
		try {
			Transaction tr = getSession().beginTransaction();
			getSession().delete(persistentInstance);
			tr.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Command findById(java.lang.Integer id) {
		log.debug("getting Command instance with id: " + id);
		try {
			Command instance = (Command) getSession().get("DAO.Command", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Command instance) {
		log.debug("finding Command instance by example");
		try {
			List results = getSession().createCriteria("DAO.Command").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Command instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "SELECT * FROM Command WHERE " + propertyName + "=\"" + value + "\" ;";
			Query queryObject = getSession().createSQLQuery(queryString);
			// queryObject.setParameter(0, value);
			List<Command> l = new ArrayList<Command>();
			List<Object> result = (List<Object>) queryObject.list();

			Iterator itr = result.iterator();
			while (itr.hasNext()) {

				Object[] obj = (Object[]) itr.next();
				Command c = new Command();

				Integer codecmd = Integer.parseInt(String.valueOf(obj[0]));
				Integer codeart = Integer.parseInt(String.valueOf(obj[1]));
				Integer codeclient = Integer.parseInt(String.valueOf(obj[2]));
				Long quantitecmd = Long.parseLong(String.valueOf(obj[3]));
				Date datecmd = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(obj[4]));

				c.setCodeart(codeart);
				c.setCodeclient(codeclient);
				c.setCodecmd(codecmd);
				c.setQuantitecmd(quantitecmd);
				c.setDatecmd(datecmd);

				l.add(c);
			}
			return l;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List findByCodeart(Object codeart) {
		return findByProperty(CODEART, codeart);
	}

	public List findByCodeclient(Object codeclient) {
		return findByProperty(CODECLIENT, codeclient);
	}

	public List findByQuantitecmd(Object quantitecmd) {
		return findByProperty(QUANTITECMD, quantitecmd);
	}

	public List findAll() {
		log.debug("finding all Command instances");
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) ec.getResponse();
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session != null) {
				if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
					String queryString = "from Command";
					Query queryObject = getSession().createQuery(queryString);
					return queryObject.list();
				}
			}
			facesContext.getExternalContext().redirect("User_login.faces");
		} catch (RuntimeException | IOException re) {
			log.error("find all failed", re);
		}
		return null;
	}

	public Command merge(Command detachedInstance) {
		log.debug("merging Command instance");
		try {
			Command result = (Command) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Command instance) {
		log.debug("attaching dirty User instance");
		Transaction tr = null;
		try {
			tr = getSession().beginTransaction();
			int i = 0;
			while (i == 0) {
				getSession().merge(instance);
				tr.commit();
				i++;
			}

		} catch (RuntimeException re) {
			log.error("attaching dirty failed", re);
			throw re;
		}
	}

	public void attachClean(Command instance) {
		log.debug("attaching clean Command instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}