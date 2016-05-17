package dao;

import entities.*;
import utilities.DataService;
import java.util.*;
import java.util.Date;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.sql.*;




public class TrinhDoDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	
	/**
     * Load list danh sach Trinh Do
     *
     * @return List<Vitri>
     */
	public List<Trinhdo> Load() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Trinhdo> list = null;
		try {
			tx = session.beginTransaction();
			list = session.createQuery("from Trinhdo").list();
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
     * Kiem tra ma trinh do da duoc su dung chua
     *
     * @return error message
     */
    public String CheckInsert(String key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Trinhdo entity = (Trinhdo) sessionFactory.getCurrentSession()
                .createQuery(" from Trinhdo where MaTrinhDo = '" + key +"'")
                .uniqueResult();   
			sessionFactory.getCurrentSession().getTransaction().commit();
			if(entity != null)
			{
				errMessage = "Mã này đã được sử dụng, không thể thêm !";
			}
		} 
		catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		} finally {
			
		}       
        return errMessage;        
    }
        
    /**
     * Them
     *
     */
    
    public String Insert(Trinhdo entity) {
		String errMesage = "";
    	Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.save(entity);
			tx.commit();
			errMesage = "Thêm mới thành công";
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			System.out.println("Error: " + e.toString());
			errMesage = "Lỗi cập nhật, không thể thêm !";
		} finally {
			session.close();
		}
		return errMesage;
	}
    
    /**
     * Kiem tra ma trinh do duoc cap nhat
     *
     * @return error message
     */
    public String CheckEdit(String key) {
        
        String errMessage = "";

        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Trinhdo entity = (Trinhdo) sessionFactory.getCurrentSession()
                .createQuery(" from Trinhdo where MaTrinhDo = '" + key +"'")
                .uniqueResult();   
			sessionFactory.getCurrentSession().getTransaction().commit();
			if(entity == null)
			{
				errMessage = "Mã này không đúng, không thể sửa !";
			}
		} 
		catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}        
        finally {
        	
        }
        return errMessage;
    }
    
    /**
     * cap nhat sua trinh do
     *
     * 
     */
    
	public void UpdateData(String maTrinhDo, String tenTrinhDo, String dienGiai, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Trinhdo entity = (Trinhdo) session.get(Trinhdo.class, maTrinhDo);
			entity.setTenTrinhDo(tenTrinhDo);
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
     * Kiem tra ma trinh do co dung khong
     *
     * @return error message
     */
    public String CheckDelete(String key) {
    	String errMessage = "";

        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Trinhdo entity = (Trinhdo) sessionFactory.getCurrentSession()
                .createQuery(" from Trinhdo where MaTrinhDo = '" + key +"'")
                .uniqueResult();  
			sessionFactory.getCurrentSession().getTransaction().commit();
			if(entity == null)
			{
				errMessage = "Mã này không đúng, không thể xóa !";
			}
		} 
		catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}        
        finally {
        	
        }
        return errMessage;
    }
    
    /**
     * Xoa trinh do
     *
     */
    public void Delete(String maTrinhDo) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
            Trinhdo entity = (Trinhdo) session.get(Trinhdo.class, maTrinhDo);
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
