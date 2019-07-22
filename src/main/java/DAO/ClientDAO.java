package DAO;

import java.io.IOException;
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

public class ClientDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(ClientDAO.class);
	// property constants
	public static final String NOMCLIENT = "nomclient";
	public static final String TEL = "tel";

	public void save(Client transientInstance) {
		log.debug("saving Client instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void persist(Client transientInstance) {
		log.debug("persisting User instance");
		try {
			getSession().flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void delete(Client persistentInstance) {
		log.debug("deleting Client instance");
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

	public Client findById(java.lang.Integer id) {
		log.debug("getting Client instance with id: " + id);
		try {
			Client instance = (Client) getSession().get("DAO.Client", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Client instance) {
		log.debug("finding Client instance by example");
		try {
			List results = getSession().createCriteria("DAO.Client").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Client instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Client as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNomclient(Object nomclient) {
		return findByProperty(NOMCLIENT, nomclient);
	}

	public List findByTel(Object tel) {
		return findByProperty(TEL, tel);
	}

	public List findAll() {
		log.debug("finding all Client instances");
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) ec.getResponse();
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session != null) {
				if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
					String queryString = "from Client";
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

	public Client merge(Client detachedInstance) {
		log.debug("merging Client instance");
		try {
			Client result = (Client) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Client instance) {
		log.debug("attaching dirty Client instance");
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

	public void attachClean(Client instance) {
		log.debug("attaching clean Client instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}