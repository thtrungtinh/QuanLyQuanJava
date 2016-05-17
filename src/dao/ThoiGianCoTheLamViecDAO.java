package dao;

import entities.*;
import model.NguoiDungModel;
import model.ThoiGianLamViecModel;
import utilities.DataService;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.hibernate.*;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.transform.Transformers;

public class ThoiGianCoTheLamViecDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
		
		
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
     * getlist
     *
     * @return list model
     */
    public List<ThoiGianLamViecModel> GetList() {        
    	List<ThoiGianLamViecModel> entities = new ArrayList<ThoiGianLamViecModel>();
    	try {
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(" call THOIGIANCOTHELAMVIEC_GetList() ");
            List result = query.list();                
            /*while (result.next()) 
            {
				ThoiGianLamViecModel model = new ThoiGianLamViecModel();
				model.setMaCa(result.getString("MaCa"));
				model.setMaNguoiDung(result.getString("MaNguoiDung"));
				model.setDienGiai(result.getString("DienGiai"));
				model.setStatus(result.getBoolean("Status"));
				model.setCreatedBy(result.getString("CreatedBy"));
				model.setCreatedDate(result.getDate("CreatedDate"));
				model.setUpdatedBy(result.getString("UpdatedBy"));
				model.setUpdatedDate(result.getDate("UpdatedDate"));
				model.setTenCa(result.getString("TenCa"));
				model.setTenNguoiDung(result.getString("TenNguoiDung"));
				
				list.add(model);
			}*/
            for(int i=0; i<result.size(); i++){
            	
            	Object[] objects = (Object[]) result.get(i);
            	ThoiGianLamViecModel model = new ThoiGianLamViecModel();
            	model.setMaCa(objects[0].toString());
				model.setMaNguoiDung(objects[1].toString());
				model.setDienGiai(objects[2].toString());
				model.setStatus((boolean) objects[3]);
				model.setCreatedBy(objects[4].toString());
				model.setCreatedDate((Date) objects[5]);
				model.setUpdatedBy(objects[6].toString());
				model.setUpdatedDate((Date) objects[7]);
				model.setTenCa(objects[8].toString());
				model.setTenNguoiDung(objects[9].toString());
            	entities.add(model);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {            
        }
        return entities;
    }
	
	/**
     * Kiem tra ma da duoc su dung chua
     *
     * @return error message
     */
    public String CheckInsert(ThoigiancothelamviecId key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Thoigiancothelamviec entity = (Thoigiancothelamviec) sessionFactory.getCurrentSession()
                .createQuery(" FROM Thoigiancothelamviec  WHERE MaCa = '"+ key.getMaCa() +"' AND MaNguoiDung = '"+ key.getMaNguoiDung() +"' ")
                .uniqueResult();   
			sessionFactory.getCurrentSession().getTransaction().commit();
			if(entity != null)
			{
				errMessage = "Nhân viên này đã làm ở ca này rồi, không thể thêm !";
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
    
    public String Insert(Thoigiancothelamviec entity) {
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
    public String CheckEdit(ThoigiancothelamviecId key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Thoigiancothelamviec entity = (Thoigiancothelamviec) sessionFactory.getCurrentSession()
                .createQuery(" FROM Thoigiancothelamviec  WHERE MaCa = '"+ key.getMaCa() +"' AND MaNguoiDung = '"+ key.getMaNguoiDung() +"' ")
                .uniqueResult();   
			sessionFactory.getCurrentSession().getTransaction().commit();
			if(entity == null)
			{
				errMessage = "Nhân viên chưa làm ở ca này, không thể sửa !";
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
    
	public void UpdateData(ThoigiancothelamviecId key, String dienGiai, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();	
			Thoigiancothelamviec entity = (Thoigiancothelamviec) session.get(Thoigiancothelamviec.class, key);
			
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
     * Kiem tra ma co dung khong
     *
     * @return error message
     */
    public String CheckDelete(ThoigiancothelamviecId key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Thoigiancothelamviec entity = (Thoigiancothelamviec) sessionFactory.getCurrentSession()
                .createQuery(" FROM Thoigiancothelamviec  WHERE MaCa = '"+ key.getMaCa() +"' AND MaNguoiDung = '"+ key.getMaNguoiDung() +"' ")
                .uniqueResult();   
			sessionFactory.getCurrentSession().getTransaction().commit();
			if(entity == null)
			{
				errMessage = "Nhân viên chưa làm ở ca này, không thể xóa !";
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
    public void Delete(ThoigiancothelamviecId key) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
        	Thoigiancothelamviec entity = (Thoigiancothelamviec) session.get(Thoigiancothelamviec.class, key);
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
