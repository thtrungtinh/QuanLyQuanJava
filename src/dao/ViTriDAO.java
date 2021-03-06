package dao;

import entities.*;
import utilities.DataService;
import java.util.*;
import java.util.Date;
import org.hibernate.*;
import java.sql.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;



public class ViTriDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	
	/**
     * Load list danh sach vi tri 
     *
     * @return List<Vitri>
     */
	public List<Vitri> Load() {		
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			List<Vitri> list = null;
			try {
				tx = session.beginTransaction();
				list = session.createQuery("from Vitri").list();
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			return list;
	}
	
	/**
     * Kiem tra ma vi tri da duoc su dung chua
     *
     * @return error message
     */
    public String CheckInsert(String maViTri) {        
        String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Vitri entity = (Vitri) sessionFactory.getCurrentSession()
                .createQuery(" from Vitri where MaViTri = '" + maViTri +"'")
                .uniqueResult();    
			if(entity != null)
			{
				errMessage = "Mã này đã được sử dụng, không thể thêm !";
			}
		} 
		catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
        return errMessage;
    }
    
    /**
     * Kiem tra ma vi tri duoc cap nhat
     *
     * @return error message
     */
    public String CheckEdit(String maViTri) {        
        String errMessage = "";
        try {
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Vitri entity = (Vitri) sessionFactory.getCurrentSession()
                .createQuery(" from Vitri where MaViTri = '" + maViTri +"'")
                .uniqueResult();    
			if(entity == null)
			{
				errMessage = "Mã này không đúng, không thể sửa !";
			}
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return errMessage;
    }
    
    /**
     * cap nhat sua vi tri
     *
     * 
     */
    
	public void UpdateData(String maViTri, String tenViTri, String dienGiai, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Vitri entity = (Vitri) session.get(Vitri.class, maViTri);
			entity.setTenViTri(tenViTri);
			entity.setDienGiai(dienGiai);
			entity.setStatus(status);
			entity.setUpdatedBy(DataService.GetUserID());
			entity.setUpdatedDate(new Date());
			session.update(entity);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
    
    /**
     * Kiem tra ma vi tri co dung khong
     *
     * @return error message
     */
    public String CheckDelete(String maViTri) {        
        String errMessage = "";

        try {
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Vitri entity = (Vitri) sessionFactory.getCurrentSession()
                .createQuery(" from Vitri where MaViTri = '" + maViTri +"'")
                .uniqueResult();    
			if(entity == null)
			{
				errMessage = "Mã này không đúng, không thể xóa !";
			}
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return errMessage;
    }
    
    /**
     * Xoa vi tri
     *
     */
    public void Delete(String maViTri) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
            Vitri entity = (Vitri) session.get(Vitri.class, maViTri);
            session.delete(entity);
            tx.commit();
        } catch (HibernateException  e) {
        	if(tx != null)
        		tx.rollback();
            e.printStackTrace();
            System.out.println("Error: " + e);
        } finally {            
            session.close();
        }

   }
}
