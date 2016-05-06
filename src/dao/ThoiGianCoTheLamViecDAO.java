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
	private static SqlConnection connection = new SqlConnection();
	
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
                .createSQLQuery(" exec NGUOIDUNG_GetListID @MaNguoiDung = '" + maNguoiDung +"' , @MatKhau='"+ matKhau +"'")
                .addEntity(Nguoidung.class).uniqueResult();                  
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
     * getlist
     *
     * @return list model
     */
    public List<ThoiGianLamViecModel> GetList() {
        CallableStatement cstmt = null;        
        List<ThoiGianLamViecModel> list = null;
        try {
            cstmt = connection.getConnection().prepareCall(
                    " exec dbo.THOIGIANCOTHELAMVIEC_GetList ");            
            ResultSet result = cstmt.executeQuery();
            while (result.next()) 
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
     * Kiem tra ma da duoc su dung chua
     *
     * @return error message
     */
    public String CheckInsert(ThoigiancothelamviecId key) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.THOIGIANCOTHELAMVIEC_CheckInsert(?,?)}");
            cstmt.setString("MaCa", key.getMaCa());
            cstmt.setString("MaNguoiDung", key.getMaNguoiDung());
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
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.THOIGIANCOTHELAMVIEC_CheckEdit(?,?)}");
            cstmt.setString("MaCa", key.getMaCa());
            cstmt.setString("MaNguoiDung", key.getMaNguoiDung());
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
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.THOIGIANCOTHELAMVIEC_CheckDelete(?,?)}");
            cstmt.setString("MaCa", key.getMaCa());
            cstmt.setString("MaNguoiDung", key.getMaNguoiDung());
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
