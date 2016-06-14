package dao;

import entities.*;
import model.ChamCongModel;
import model.ChiTietHoaDonModel;
import model.NguoiDungModel;
import utilities.DataService;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.*;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.transform.Transformers;

public class ChiTietHoaDonDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();	
	
	/**
     * get list danh sach truy vấn số món ăn trong thời gian
     *
     * @return List<ChiTietHoaDonModel>
     */
	public List<ChiTietHoaDonModel> GetList(Date tu, Date den, String maThucDon) {		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String sSql = "";
    	String sWhere = " where (1=1) and c.Status = 2 ";
    	sWhere = sWhere + " and c.SoLuong > 0";
    	sWhere = sWhere + " and (c.CreatedDate BETWEEN '" + formatter.format(tu)+ " 00:00:00" + "' AND '" + formatter.format(den) + " 23:59:59"+ "') ";
    	if(maThucDon != "%")
    		sWhere += " and c.MaThucDon = '"+ maThucDon + "'";
    	sWhere = sWhere + " ORDER BY c.CreatedDate ";
    	sSql = "SELECT c.ID, c.MaHD, c.MaThucDon, c.GhiChu, c.SoLuong, c.Gia ,t.TenThucDon "    			
    			+ " FROM Chitiethoadon c "
    			+ " LEFT JOIN Thucdon t ON "
    			+ " c.MaThucDon = t.MaThucDon "    			
    			+ sWhere;   	
    	        
        List<ChiTietHoaDonModel> list = new ArrayList<>();
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();
			for(int i=0; i<result.size(); i++){
				Object[] objects = (Object[]) result.get(i);
				ChiTietHoaDonModel model = new ChiTietHoaDonModel();
            	model.setiD((int)objects[0]);
				model.setMaHD(objects[1].toString());
				model.setMaThucDon(objects[2].toString());
				model.setGhiChu(objects[3].toString());
				model.setSoLuong((int)objects[4]);
				model.setGia((int)objects[5]);
				model.setTenThucDon(objects[6].toString());			
								
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
     * Load list danh sach 
     *
     * @return List<Thucdon>
     */
	public List<ChiTietHoaDonModel> GetList(String MaHD, int status) {		
		String sSql = "";
    	String sWhere = " where (1=1) and c.MaHD = '" +MaHD+ "' and c.Status = "+ status +" ";
    	sWhere = sWhere + " and c.SoLuong > 0";
    	
    	sWhere = sWhere + " ORDER BY c.CreatedDate ";
    	sSql = "SELECT c.ID, c.MaHD, c.MaThucDon, c.GhiChu, c.SoLuong, c.Gia ,t.TenThucDon "    			
    			+ " FROM Chitiethoadon c "
    			+ " LEFT JOIN Thucdon t ON "
    			+ " c.MaThucDon = t.MaThucDon "    			
    			+ sWhere;   	
    	        
        List<ChiTietHoaDonModel> list = new ArrayList<>();
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();
			for(int i=0; i<result.size(); i++){
				Object[] objects = (Object[]) result.get(i);
				ChiTietHoaDonModel model = new ChiTietHoaDonModel();
            	model.setiD((int)objects[0]);
				model.setMaHD(objects[1].toString());
				model.setMaThucDon(objects[2].toString());
				model.setGhiChu(objects[3].toString());
				model.setSoLuong((int)objects[4]);
				model.setGia((int)objects[5]);
				model.setTenThucDon(objects[6].toString());			
								
				list.add(model);
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return list;
	}
	/**
	 * Get tong tien cua hoa don
	 * 
	 */
	public int GetSumBillID(String MaHD) {	
		
		int tongTien = 0;
		String sSql = "";
    	String sWhere = " where (1=1) and c.MaHD = '" +MaHD+ "' and c.Status = 2 and c.SoLuong > 0";    	
    	sWhere = sWhere + " ORDER BY c.CreatedDate ";
    	
    	sSql = "SELECT c.SoLuong, c.Gia "    			
    			+ " FROM Chitiethoadon c "    			  			
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
				tongTien += (int)objects[0] * (int)objects[1];
            }
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return tongTien;
	}
    
    /**
     * Them
     *
     */
    
    public String Insert(Chitiethoadon entity) {
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
    
    public String CheckEdit(String maHD, String maThucDon) {        
        String errMessage = "";
        try {
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
        	Chitiethoadon entity = (Chitiethoadon) sessionFactory.getCurrentSession()
                .createQuery(" from Chitiethoadon where MaHD = '" + maHD +"' and MaThucDon = '" + maThucDon +"' and status = 1" )
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
     * cap nhat 
     *
     * 
     */
    
	public void UpdateDataStatus(String key, int status) {
		String sSql = "";
    	String sWhere = " where c.MaHD = '" +key+ "' ";    	
    	
    	sSql = "UPDATE Chitiethoadon c SET c.Status = " + status + sWhere;       
        
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			query.executeUpdate();
			sessionFactory.getCurrentSession().getTransaction().commit();			
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        
	}
	
	public void UpdateDataNumber(String key, String maThucDon, int soLuong ) {
		String sSql = "";
    	String sWhere = " where c.MaHD = '" +key+ "' and c.MaThucDon = '"+ maThucDon +"' and c.Status = 1 LIMIT 1";    	
    	
    	sSql = "UPDATE Chitiethoadon c SET c.SoLuong = " + soLuong + sWhere;         
        
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			query.executeUpdate();
			sessionFactory.getCurrentSession().getTransaction().commit();			
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        
	}
	
	public void UpdateDataMaHD(int key, String maHd) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Chitiethoadon entity = (Chitiethoadon) session.get(Chitiethoadon.class, key);
			entity.setMaHd(maHd);
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
	
	public int GetNumber(String maHD, String maThucDon)
	{
		int number = 0;
		String sSql = "";
    	String sWhere = " where (1=1) and c.MaHD = '" + maHD + "' and c.MaThucDon = '" + maThucDon + "' and c.Status = 1 " ;
    	
    	sWhere = sWhere + " ORDER BY c.CreatedDate LIMIT 1";
    	sSql = "SELECT c.SoLuong "    			
    			+ " FROM Chitiethoadon c "    			   			
    			+ sWhere;     	        
        
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();
			
			for(int i=0; i<result.size(); i++){
				number =  (int)result.get(i);				
								
            }
            
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            
        }
        return number ;
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
