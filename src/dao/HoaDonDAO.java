package dao;

import entities.*;
import model.ChiTietHoaDonModel;
import model.HoaDonModel;
import utilities.DataService;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class HoaDonDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	/**
     * Load ma ca
     *
     * @return key
     */
	public String GetMaCa(String time) {	
		String key = "";
		String sSql = "";
    	String sWhere = " where (1=1) and CAST('" + time + "' AS time) BETWEEN BatDau AND KetThuc LIMIT 1";   
    	sSql = "SELECT c.MaCa FROM calamviec c "    			   			   			  			
    			+ sWhere;          
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();			
			key = (String) result.get(0);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return key;
	}
	/**
	 * Get list báo cáo
	 * @return List<HoaDonModel>
	 */
	public List<HoaDonModel> GetList(Date tu, Date den, String maBan, String nhanVien, String maCa)
	{
		List<HoaDonModel> list = new ArrayList<>();
		ChiTietHoaDonDAO dao = new ChiTietHoaDonDAO();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String sSql = "";
    	String sWhere = " where (1=1)  and h.Status = 2 ";
    	if(maBan != "%")
    		sWhere += " and h.MaBan = '"+ maBan +"'";  
    	if(nhanVien != "%")
    		sWhere += " and h.CreatedBy = '"+ nhanVien + "'";
    	if(maCa != "%")
    		sWhere += " and h.MaCa = '"+ maCa + "'";
    	sWhere = sWhere + " and (h.CreatedDate BETWEEN '" + formatter.format(tu)+ " 00:00:00" + "' AND '" + formatter.format(den) + " 23:59:59"+ "') ";
    	sWhere = sWhere + " ORDER BY h.CreatedDate ";
    	sSql = "SELECT h.MaHD, h.MaBan, b.TenBan, h.NumOfCus "    			
    			+ " FROM Hoadon h "
    			+ " LEFT JOIN Ban b ON "
    			+ " h.MaBan = b.MaBan "    			
    			+ sWhere;          
        
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();
			for(int i=0; i<result.size(); i++){
				Object[] objects = (Object[]) result.get(i);
				HoaDonModel model = new HoaDonModel();
            	
				model.setMaHD(objects[0].toString());
				model.setMaBan(objects[1].toString());
				model.setTenBan(objects[2].toString());	
				model.setTongTien(dao.GetSumBillID(objects[0].toString()));		
				model.setNumOfCus((int)objects[3]);
				list.add(model);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return list;		
	}
	
	/**
     * Load list danh sach 
     *
     * @return List<Hoadon>
     */
	public List<Hoadon> Load() {		
			Session session = sessionFactory.openSession();
			Transaction tx = null;
			List<Hoadon> list = null;
			try {
				tx = session.beginTransaction();
				list = session.createQuery("from Hoadon").list();
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
     * Tạo mã hoá đơn tự động
     *
     * @return error message
     */
    public String GenerateBillID() {
    	Date now = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(now);
    	DecimalFormat nf3 = new DecimalFormat("#000");
    	String sSql = "";
    	String key = "HD";
    	
    	key += (cal.get(cal.DAY_OF_MONTH));
    	key += (cal.get(cal.MONTH));
    	key += (cal.get(cal.YEAR));
    	
    	sSql = "SELECT * FROM HOADON h WHERE h.MaHD LIKE '"+ key +"%' ORDER BY MaHD DESC LIMIT 1";
    	try {
        	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list();
			sessionFactory.getCurrentSession().getTransaction().commit();
			if(result.size() > 0 )
			{
				Object[] entity = (Object[]) result.get(0);				
				String subkey[] = entity[0].toString().split("[-]");
				int getSubKey =  Integer.parseInt(subkey[1]) + 1;
				key = subkey[0] + "-" + nf3.format(getSubKey);
			}
			else
				key = key + "-001";
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return key;
    }
	
    /**
     * Load key 
     *
     * @return List<Thucdon>
     */
	public String GetKeyTop1(String maBan) {	
		String key = "";
		String sSql = "";
    	String sWhere = " where (1=1) and h.MaBan = '" +maBan+ "' and Status = 1 ";
    	
    	sWhere = sWhere + " ORDER BY h.CreatedDate DESC LIMIT 1 ";
    	sSql = "SELECT h.MaHD "    			
    			+ " FROM Hoadon h "    			   			  			
    			+ sWhere;          
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();
			for(int i=0; i<result.size(); i++){
				key = (String) result.get(i);						
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return key;
	}
	
	public int GetStatusTop1(String maBan) {	
		int status = 0;
		String sSql = "";
    	String sWhere = " where (1=1) and h.MaBan = '" +maBan+ "' and Status = 1 ";
    	
    	sWhere = sWhere + " ORDER BY h.CreatedDate DESC LIMIT 1 ";
    	sSql = "SELECT h.Status "    			
    			+ " FROM Hoadon h "    			   			  			
    			+ sWhere;          
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();
			for(int i=0; i<result.size(); i++){
				status =  (int)result.get(i);				
								
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return status;
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
			Hoadon entity = (Hoadon) sessionFactory.getCurrentSession()
                .createQuery(" from Hoadon where MaHD = '" + key +"'")
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
    
    public String Insert(Hoadon entity) {
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
			Hoadon entity = (Hoadon) sessionFactory.getCurrentSession()
                .createQuery(" from Hoadon where MaHD = '" + key +"'")
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
     * cap nhat 
     *
     * 
     */
    
	public void UpdateDataStatus(String key, int status) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();	
			Hoadon entity = (Hoadon) session.get(Hoadon.class, key);			
						
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
	
	public void UpdateDataKeyTable(String key, String maBan) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();	
			Hoadon entity = (Hoadon) session.get(Hoadon.class, key);			
						
			entity.setMaban(maBan);			
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
			Nhomthucdon entity = (Nhomthucdon) sessionFactory.getCurrentSession()
                .createQuery(" from Nhomthucdon where MaNhom = '" + key +"'")
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
     * Xoa 
     *
     */
    public void Delete(String key) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
        	tx = session.beginTransaction();
        	Hoadon entity = (Hoadon) session.get(Hoadon.class, key);
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
