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

public class NguoiDungDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	
	/**
     * Load Nguoidung theo ma nguoi dung va mat khau 
     *
     * @return Nguoidung entities
     */
	public Nguoidung GetListID(String maNguoiDung, String matKhau) {
		try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			return (Nguoidung) sessionFactory.getCurrentSession()
                .createQuery(" from Nguoidung where MaNguoiDung = '" + maNguoiDung +"' and MatKhau='"+ matKhau +"'")
                .uniqueResult();                  
		} 
		catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}
		
		
	/**
     * Load list danh sach 
     *
     * @return List<Nguoidung>
     */
	public List<Nguoidung> Load() {		
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			List<Nguoidung> list = null;
			try {
				tx = session.beginTransaction();
				list = session.createQuery("from Nguoidung").list();
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
			Nguoidung entity = (Nguoidung) sessionFactory.getCurrentSession()
                .createQuery(" from Nguoidung where MaNguoiDung = '" + key +"'")
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
    
    public String Insert(Nguoidung entity) {
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
			Nguoidung entity = (Nguoidung) sessionFactory.getCurrentSession()
                .createQuery(" from Nguoidung where MaNguoiDung = '" + key +"'")
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
    
	public void UpdateData(String maNguoiDung, String matKhau, String tenNguoiDung, String dienGiai, boolean gioiTinh, Date ngaySinh, String dienThoai, String diaChi, String cmnd, String maViTri, String maTrinhDo, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();	
			Nguoidung entity = (Nguoidung) session.get(Nguoidung.class, maNguoiDung);
			
			entity.setMatKhau(matKhau);
			entity.setTenNguoiDung(tenNguoiDung);
			entity.setDienGiai(dienGiai);
			entity.setGioiTinh(gioiTinh);
			entity.setNgaySinh(ngaySinh);
			entity.setDienThoai(dienThoai);
			entity.setDiaChi(diaChi);
			entity.setCmnd(cmnd);
			entity.setMaViTri(maViTri);
			entity.setMaTrinhDo(maTrinhDo);
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
     * Kiem tra ma co dung khong
     *
     * @return error message
     */
    public String CheckDelete(String key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Nguoidung entity = (Nguoidung) sessionFactory.getCurrentSession()
                .createQuery(" from Nguoidung where MaNguoiDung = '" + key +"'")
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
    public void Delete(String maNguoiDung) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
        	Nguoidung entity = (Nguoidung) session.get(Nguoidung.class, maNguoiDung);
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
