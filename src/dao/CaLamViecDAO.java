package dao;

import entities.*;
import model.BanModel;
import model.CaModel;
import utilities.DataService;
import java.util.*;
import java.util.Date;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.sql.*;

public class CaLamViecDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	/**
     * Load list danh sach 
     *
     * style = 0 all
     * style = 1 % tat ca
     * @return List<BanModel>
     */
	
	public List<CaModel> GetList( int style)	{
		
		List<CaModel> list = new ArrayList<>();		
		String sSql = "";
    	String sWhere = " where (1=1)  and Status = 1 ";
    	
    	sWhere = sWhere + " ORDER BY c.CreatedDate ";
    	sSql = "SELECT c.MaCa, c.TenCa "    			
    			+ " FROM Calamviec c "    			   			  			
    			+ sWhere;   
    	if(style == 1)
    	{
    		CaModel model = new CaModel("%", "Tất cả");
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
				CaModel model = new CaModel(entity[0].toString(), entity[1].toString());
	    		list.add(model);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return list;	
	}
	
	/**
     * Load list danh sach ca lam viec
     *
     * @return List<Vitri>
     */
	public List<Calamviec> Load() {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		List<Calamviec> list = null;
		try {
			tx = session.beginTransaction();
			list = session.createQuery("from Calamviec").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			System.out.println(e);
		} finally {
			session.close();
		}
		return list;
	}
	
	/**
     * Kiem tra ma ca da duoc su dung chua
     *
     * @return error message
     */
    public String CheckInsert(String key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Calamviec entity = (Calamviec) sessionFactory.getCurrentSession()
                .createQuery(" from Calamviec where MaCa = '" + key +"'")
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
    
    public String Insert(Calamviec entity) {
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
     * Kiem tra ma ca duoc cap nhat
     *
     * @return error message
     */
    public String CheckEdit(String key) {
    	String errMessage = "";

        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Calamviec entity = (Calamviec) sessionFactory.getCurrentSession()
                .createQuery(" from Calamviec where MaCa = '" + key +"'")
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
     * cap nhat sua
     *
     * 
     */
    
	public void UpdateData(String maCa, String tenCa, Date batDau, Date ketThuc, int nhanVienToiThieu, int nhanVienToiDa, int LuongCaTheoNgay, String dienGiai, boolean status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Calamviec entity = (Calamviec) session.get(Calamviec.class, maCa);
			entity.setMaCa(maCa);
			entity.setTenCa(tenCa);
			entity.setBatDau(new java.sql.Time(batDau.getTime()));
			entity.setKetThuc(new java.sql.Time(ketThuc.getTime()));
			entity.setNhanVienToiThieu(nhanVienToiThieu);
			entity.setNhanVienToiDa(nhanVienToiDa);
			entity.setLuongCaTheoNgay(LuongCaTheoNgay);
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
     * Kiem tra ma 
     *
     * @return error message
     */
    public String CheckDelete(String key) {
    	String errMessage = "";

        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Calamviec entity = (Calamviec) sessionFactory.getCurrentSession()
                .createQuery(" from Calamviec where MaCa = '" + key +"'")
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
    public void Delete(String maCa) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
            Calamviec entity = (Calamviec) session.get(Calamviec.class, maCa);
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
