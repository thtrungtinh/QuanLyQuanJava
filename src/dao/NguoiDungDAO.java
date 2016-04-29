package dao;

import entities.*;
import java.util.*;
import org.hibernate.*;

import org.hibernate.resource.transaction.spi.TransactionStatus;

public class NguoiDungDAO {
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public Nguoidung GetListID(String maNguoiDung, String matKhau) {
		try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			return (Nguoidung) sessionFactory.getCurrentSession()
                .createSQLQuery(" exec NGUOIDUNG_GetListID @MaNguoiDung = '" + maNguoiDung +"' , @MatKhau='"+ matKhau +"'")
                .addEntity(Nguoidung.class).uniqueResult();                  
		} 
		catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}
	
	public List<Nguoidung> Load() {
		try {
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			 
 	            return sessionFactory.getCurrentSession().createSQLQuery(" exec NGUOIDUNG_Load ").addEntity(Nguoidung.class).list();
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}
	
}
