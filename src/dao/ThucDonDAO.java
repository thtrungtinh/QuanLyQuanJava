package dao;

import entities.*;
import model.NguoiDungModel;
import utilities.DataService;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.hibernate.*;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.transform.Transformers;

public class ThucDonDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	
	/**
     * Load list danh sach 
     *
     * @return List<Thucdon>
     */
	public List<Thucdon> Load() {		
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			List<Thucdon> list = null;
			try {
				tx = session.beginTransaction();
				list = session.createQuery("from Thucdon").list();
				tx.commit();
			} catch (Exception e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
				System.out.println("Error: " + e);
			} finally {
				session.close();
			}
			return list;
	}
	
	public List<Thucdon> GetList(String maNhom)
	{
		String sql = "";
		if(maNhom == "")
			sql = " from Thucdon ";
		else {
			sql = " from Thucdon t WHERE t.maNhom = '"+ maNhom +"'";
		}
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Thucdon> list = null;
		try {
			tx = session.beginTransaction();
			list = session.createQuery(sql).list();
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
	
	public List<Thucdon> GetList_ChiTiet(String maHD)
	{
		String sql = "";		
		sql = " SELECT h.MAHD, H.MATHUCDUON, H.SOLUONG, H.GIA FROM HOADON h WHERE h.MaNhom = N'"+ maHD +"'";
		
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Thucdon> list = null;
		try {
			tx = session.beginTransaction();
			list = session.createQuery(sql).list();
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
     * Kiem tra ma da duoc su dung chua
     *
     * @return error message
     */
    public String CheckInsert(String key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Thucdon entity = (Thucdon) sessionFactory.getCurrentSession()
                .createQuery(" from Thucdon where MaThucDon = '" + key +"'")
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
    
    public String Insert(Thucdon entity) {
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
     * Kiem tra ma duoc cap nhat
     *
     * @return error message
     */
    public String CheckEdit(String key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Thucdon entity = (Thucdon) sessionFactory.getCurrentSession()
                .createQuery(" from Thucdon where MaThucDon = '" + key +"'")
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
     * cap nhat 
     *
     * 
     */
    
	public void UpdateData(String key, String ten, byte[] hinhAnh, String dienGiai, int gia, boolean status, String maNhom) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();	
			Thucdon entity = (Thucdon) session.get(Thucdon.class, key);
			
			entity.setTenThucDon(ten);			
			entity.setDienGiai(dienGiai);
			entity.setGia(gia);			
			entity.setStatus(status);
			entity.setMaNhom(maNhom);
						
			entity.setUpdatedBy(DataService.GetUserID());
			entity.setUpdatedDate(new Date());
			
			
			entity.setHinhAnh(hinhAnh);
			
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
     * Kiem tra ma co dung khong
     *
     * @return error message
     */
    public String CheckDelete(String key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Thucdon entity = (Thucdon) sessionFactory.getCurrentSession()
                .createQuery(" from Thucdon where MaThucDon = '" + key +"'")
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
     * Xoa 
     *
     */
    public void Delete(String key) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
        	Thucdon entity = (Thucdon) session.get(Thucdon.class, key);
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
