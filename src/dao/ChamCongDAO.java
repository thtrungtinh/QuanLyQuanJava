package dao;

import entities.*;
import model.ChamCongModel;
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

public class ChamCongDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static SqlConnection connection = new SqlConnection();
				
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
        CallableStatement cstmt = null;        
        List<ChamCongModel> list = new ArrayList<>();
        try {
            cstmt = connection.getConnection().prepareCall(
                    " exec dbo.CHAMCONG_GetList @MaCa = '"+ maCa +"', @MaNguoiDung = '" + maNguoiDung + "', @Nam = "+ nam +", @Thang = " + thang );  
            ResultSet result = cstmt.executeQuery();
            while (result.next()) 
            {
            	ChamCongModel model = new ChamCongModel();
            	model.setMaChamCong(result.getInt("MaChamCong"));
				model.setMaCa(result.getString("MaCa"));
				model.setMaNguoiDung(result.getString("MaNguoiDung"));
				model.setNgay(result.getInt("Ngay"));
				model.setThang(result.getInt("Thang"));
				model.setNam(result.getInt("Nam"));
				model.setDienGiai(result.getString("DienGiai"));
				
				model.setCreatedBy(result.getString("CreatedBy"));
				model.setCreatedDate(result.getDate("CreatedDate"));
				model.setUpdatedBy(result.getString("UpdatedBy"));
				model.setUpdatedDate(result.getDate("UpdatedDate"));
				model.setTenCa(result.getString("TenCa"));
				model.setTenNguoiDung(result.getString("TenNguoiDung"));
				
				list.add(model);
			}
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException ex) {
                	System.out.println("Error: " + ex);
                }
            }
        }
        return list;
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
    public String CheckEdit(int maChamCong) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.CHAMCONG_CheckEdit(?,?)}");
            cstmt.setInt("MaChamCong", maChamCong);
            cstmt.registerOutParameter("Message", java.sql.Types.NVARCHAR);
            cstmt.execute();
            errMessage = cstmt.getNString("Message");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException ex) {
                	System.out.println("Error: " + ex);
                }
            }
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
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.CHAMCONG_CheckDelete(?,?)}");
            cstmt.setInt("MaChamCong", key);
            cstmt.registerOutParameter("Message", java.sql.Types.NVARCHAR);
            cstmt.execute();
            errMessage = cstmt.getNString("Message");
        } catch (Exception ex) {
            System.out.println("Error: " + ex);
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException ex) {
                	System.out.println("Error: " + ex);
                }
            }
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
