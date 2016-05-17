package dao;

import entities.*;
import model.*;
import utilities.DataService;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;


public class ChamCongDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
				
	/**
     * Load list danh sach 
     *
     * @return List<Chamcong>
     */
	public List<Chamcong> Load() {		
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			List<Chamcong> list = null;
			try {
				tx = session.beginTransaction();
				list = session.createQuery("from Chamcong").list();
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
    public List<ChamCongModel> GetList(String maCa, String maNguoiDung, int nam, int thang) {
        String sSql = "";
    	String sWhere = " where (1=1) ";
    	if(maCa != "")
    		sWhere = sWhere + " AND c.MaCa = '" + maCa +"' ";
    	if(maNguoiDung != "")
    		sWhere = sWhere + " AND c.MaNguoiDung = '" + maNguoiDung +"' ";
    	if(nam != 0)
    		sWhere = sWhere + " AND c.Nam = '" + nam +"' ";
    	if(thang != 0)
    		sWhere = sWhere + " AND c.Thang = '" + thang +"' ";
    	sWhere = sWhere + " ORDER BY c.Ngay, c.Thang, c.Nam ";
    	sSql = "SELECT c.MaChamCong, c.MaCa, c.MaNguoiDung, c.Ngay, c.Thang, c.Nam ,c.DienGiai,"
    			+ "c.CreatedBy, c.CreatedDate, c.UpdatedBy, c.UpdatedDate,"
    			+ " ca.TenCa, n.TenNguoiDung " 
    			+ "FROM Chamcong c "
    			+ "LEFT JOIN Calamviec ca ON "
    			+ "c.MaCa = ca.MaCa "
    			+ "LEFT JOIN Nguoidung n ON "
    			+ "c.MaNguoiDung = n.MaNguoiDung "
    			+ sWhere;   	
    	        
        List<ChamCongModel> list = new ArrayList<>();
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();
			for(int i=0; i<result.size(); i++){
				Object[] objects = (Object[]) result.get(i);
				ChamCongModel model = new ChamCongModel();
            	model.setMaChamCong((int)objects[0]);
				model.setMaCa(objects[1].toString());
				model.setMaNguoiDung(objects[2].toString());
				model.setNgay((int)objects[3]);
				model.setThang((int)objects[4]);
				model.setNam((int)objects[5]);
				model.setDienGiai(objects[6].toString());
				
				model.setCreatedBy(objects[7].toString());
				model.setCreatedDate((Date)objects[8]);
				model.setUpdatedBy(objects[9].toString());
				model.setUpdatedDate((Date)objects[10]);
				model.setTenCa(objects[11].toString());
				model.setTenNguoiDung(objects[12].toString());
				
				list.add(model);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return list;
    } 
    
    /**
     * Kiem tra ca, nguoi dung, ngay, thang, nam
     *
     * @return error message
     */
    public String CheckInsert(String maCa, String maNguoiDung, int ngay, int thang, int nam) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Chamcong entity = (Chamcong) sessionFactory.getCurrentSession()
                .createQuery(" from Chamcong where Maca = '"+ maCa +"' and MaNguoiDung = '" + maNguoiDung +"'"
                		+ " and Ngay = "+ ngay+ " and Thang = " + thang + " and Nam = "+ nam)
                .uniqueResult();   
			sessionFactory.getCurrentSession().getTransaction().commit();
			if(entity != null)
			{
				errMessage = "Người này đã làm ca này rồi, không thể thêm !";
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
    
    public String Insert(Chamcong entity) {
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
    public String CheckEdit(int key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Chamcong entity = (Chamcong) sessionFactory.getCurrentSession()
                .createQuery(" from Chamcong where MaChamCong = '" + key +"'")
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
    
	public void UpdateData(int key, String maCa, String maNguoiDung, int ngay, int thang, int nam, String dienGiai) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();	
			Chamcong entity = (Chamcong) session.get(Chamcong.class, key);
			
			entity.setMaCa(maCa);
			entity.setMaNguoiDung(maNguoiDung);
			entity.setNgay(ngay);
			entity.setThang(thang);
			entity.setNam(nam);
			entity.setDienGiai(dienGiai);			
						
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
    public String CheckDelete(int key) {
    	String errMessage = "";
        try {			
			if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Chamcong entity = (Chamcong) sessionFactory.getCurrentSession()
                .createQuery(" from Chamcong where MaChamCong = '" + key +"'")
                .uniqueResult();   
			sessionFactory.getCurrentSession().getTransaction().commit();
			if(entity == null)
			{
				errMessage = "Mã này không đúng, không thể xoas !";
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
    public void Delete(int key) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
        	Chamcong entity = (Chamcong) session.get(Chamcong.class, key);
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
