package DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.LockOptions;
import org.hibernate.query.*;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String PROFIL = "profil";

	public void save(User transientInstance) {
		log.debug("saving User instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void persist(User transientInstance) {
		log.debug("persisting User instance");
		try {
			getSession().flush();
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
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

	public User findById(java.lang.Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getSession().get("DAO.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List results = getSession().createCriteria("DAO.User").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding User instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "SELECT * FROM User WHERE " + propertyName + "=\"" + value + "\" ;";
			Query queryObject = getSession().createSQLQuery(queryString);
			// queryObject.setParameter(0, value);
			List<User> l = new ArrayList<User>();
			List<Object> result = (List<Object>) queryObject.list();
			Iterator itr = result.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				User u = new User();
				Integer id = Integer.parseInt(String.valueOf(obj[0]));
				String name = String.valueOf(obj[1]);
				String password = String.valueOf(obj[2]);
				String profil = String.valueOf(obj[3]);
				
				u.setId(id);
				u.setName(name);
				u.setPassword(password);
				u.setProfil(profil);
				l.add(u);
			}
			return l;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<User> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<User> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List<User> findByProfil(Object profil) {
		return findByProperty(PROFIL, profil);
	}

	public List<User> findAll() {
		log.debug("finding all User instances");
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			User session = (User) facesContext.getExternalContext().getSessionMap().get("user");
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();
			HttpServletResponse response = (HttpServletResponse) ec.getResponse();
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if (session != null) {
				if (session.getProfil().equals("admin")) {
					String queryString = "from User";
					Query<User> queryObject = getSession().createQuery(queryString);
					return queryObject.list();
				}
			}
			facesContext.getExternalContext().redirect("User_login.faces");
			return null;
		} catch (RuntimeException | IOException re) {
			log.error("find all failed", re);
		}
		return null;
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		Transaction tr = null;
		try {
			tr = getSession().beginTransaction();
			int i = 0;
			while (i == 0) {
				getSession().merge(instance);
				i++;
				tr.commit();
			}
			
		} catch (RuntimeException re) {
			log.error("attaching dirty failed", re);
			throw re;
		}
	}

	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}