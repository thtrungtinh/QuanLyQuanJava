package dao;

import entities.*;
import java.util.*;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class ViTriDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public List<Vitri> Load() {
		try {
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			 
 	            return sessionFactory.getCurrentSession().createSQLQuery(" exec NGUOIDUNG_Load ").addEntity(Vitri.class).list();
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}
}
