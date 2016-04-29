package dao;

import entities.*;
import java.util.*;

import javax.naming.StringRefAddr;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.*;
import org.hibernate.hql.internal.antlr.SqlStatementParserTokenTypes;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class NguoiDungDAO {
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public boolean GetListID(String maNguoiDung, String matKhau) {
		try {
			boolean isLogin = false;
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			SQLQuery result = sessionFactory.getCurrentSession()
                .createSQLQuery(" exec NGUOIDUNG_GetListID @MaNguoiDung = '" + maNguoiDung +"' , @MatKhau='"+ matKhau +"'")
                .addEntity(Nguoidung.class);
                
            List list = result.list();
            if(list.size() >0)
            	isLogin = true;
           return isLogin;
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return false;
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
