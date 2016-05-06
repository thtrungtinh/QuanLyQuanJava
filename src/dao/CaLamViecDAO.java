package dao;

import entities.*;
import utilities.DataService;
import java.util.*;
import java.util.Date;
import org.hibernate.*;
import java.sql.*;




public class CaLamViecDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private static SqlConnection connection = new SqlConnection();
	
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
    public String CheckInsert(String maCa) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.CALAMVIEC_CheckInsert(?,?)}");
            cstmt.setString("MaCa", maCa);
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
    public String CheckEdit(String maCa) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.CALAMVIEC_CheckEdit(?,?)}");
            cstmt.setString("MaCa", maCa);
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
			entity.setBatDau(new java.sql.Timestamp(batDau.getTime()));
			entity.setKetThuc(new java.sql.Timestamp(ketThuc.getTime()));
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
    public String CheckDelete(String maCa) {
        CallableStatement cstmt = null;
        String errMessage = "";

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call dbo.CALAMVIEC_CheckDelete(?,?)}");
            cstmt.setString("MaCa", maCa);
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
