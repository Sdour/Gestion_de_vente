package DAO;

import org.hibernate.Session;

public interface IBaseHibernateDAO {
	public Session getSession();
}