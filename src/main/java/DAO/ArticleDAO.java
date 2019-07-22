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

public class ArticleDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(ArticleDAO.class);
	// property constants
	public static final String NOMART = "nomart";
	public static final String DESCART = "descart";
	public static final String PRIXART = "prixart";

	public void save(Article transientInstance) {
		log.debug("saving Article instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void persist(Article transientInstance) {
		log.debug("persisting User instance");
		try {
			getSession().flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void delete(Article persistentInstance) {
		log.debug("deleting Article instance");
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

	public Article findById(java.lang.Integer id) {
		log.debug("getting Article instance with id: " + id);
		try {
			Article instance = (Article) getSession().get("DAO.Article", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Article instance) {
		log.debug("finding Article instance by example");
		try {
			List results = getSession().createCriteria("DAO.Article").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("rawtypes")
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Article instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Article as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNomart(Object nomart) {
		return findByProperty(NOMART, nomart);
	}

	public List findByDescart(Object descart) {
		return findByProperty(DESCART, descart);
	}

	public List findByPrixart(Object prixart) {
		return findByProperty(PRIXART, prixart);
	}

	public List findAll() {
		log.debug("finding all Article instances");
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) ec.getResponse();
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session != null) {
				if (session.getProfil().equals("admin") || session.getProfil().equals("vente")) {
					String queryString = "from Article";
					Query queryObject = getSession().createQuery(queryString);
					return queryObject.list();
				}
			}
			facesContext.getExternalContext().redirect("User_login.faces");
			return null;
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Article merge(Article detachedInstance) {
		log.debug("merging Article instance");
		try {
			Article result = (Article) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Article instance) {
		log.debug("attaching dirty Article instance");
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

	public void attachClean(Article instance) {
		log.debug("attaching clean Article instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}