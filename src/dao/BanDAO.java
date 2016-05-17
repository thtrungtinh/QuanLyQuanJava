package dao;

import entities.*;
import model.BanModel;
import utilities.DataService;
import java.util.*;
import java.util.Date;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.sql.*;




public class BanDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	/**
     * Load list danh sach 
     *
     * style = 0 all
     * style = 1 % tat ca
     * @return List<BanModel>
     */
	
	public List<BanModel> GetList( int style)	{
		
		List<BanModel> list = new ArrayList<>();		
		String sSql = "";
    	String sWhere = " where (1=1)  and Status = 1 ";
    	
    	sWhere = sWhere + " ORDER BY b.CreatedDate ";
    	sSql = "SELECT b.MaBan, b.TenBan "    			
    			+ " FROM Ban b "    			   			  			
    			+ sWhere;   
    	if(style == 1)
    	{
    		BanModel model = new BanModel("%", "Tất cả");
    		list.add(model);
    	}    	
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();
			for(int i=0; i<result.size(); i++){
				Object[] entity = (Object[]) result.get(i);	
				BanModel model = new BanModel(entity[0].toString(), entity[1].toString());
	    		list.add(model);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return list;	
	}
	
	/**
     * Load list danh sach Trinh Do
     *
     * @return List<Vitri>
     */
	public List<Ban> Load() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Ban> list = null;
		try {
			tx = session.beginTransaction();
			list = session.createQuery("from Ban").list();
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
			Ban entity = (Ban) sessionFactory.getCurrentSession()
                .createQuery(" from Ban where MaBan = '" + key +"'")
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
    
    public String Insert(Ban entity) {
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
			Ban entity = (Ban) sessionFactory.getCurrentSession()
                .createQuery(" from Ban where MaBan = '" + key +"'")
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
		} finally {
			
		}       
        return errMessage;  
    }
    
    /**
     * cap nhat sua trinh do
     *
     * 
     */
    
	public void UpdateData(String key, String tenBan, String dienGiai, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Ban entity = (Ban) session.get(Ban.class, key);
			entity.setTenBan(tenBan);
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
			Ban entity = (Ban) sessionFactory.getCurrentSession()
                .createQuery(" from Ban where MaBan = '" + key +"'")
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
		} finally {
			
		}       
        return errMessage;  
    }
    
    /**
     * Xoa trinh do
     *
     */
    public void Delete(String key) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
            Ban entity = (Ban) session.get(Ban.class, key);
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
