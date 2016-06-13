package dao;


import model.NhaBepModel;
import java.util.*;
import org.hibernate.*;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class NhaBepDAO {
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();		
	
	/**
     * Load list danh sach 
     *
     * @return List<NhaBepModel>
     */
	public List<NhaBepModel> GetList() {		
		String sSql = "";
    	String sWhere = " where (1=1) ";
    	
    	
    	sWhere = sWhere + " ORDER BY c.CreatedDate DESC ";
    	sSql = "SELECT c.ID, c.MaHD, c.MaThucDon, c.GhiChu, c.SoLuong, c.Gia ,t.TenThucDon, c.Stock, c.Done ,"
    			+ " (SELECT b.TENBAN FROM Ban b where h.Maban = b.Maban) AS TenBan "    			
    			+ " FROM Chitiethoadon c "
    			+ " LEFT JOIN Thucdon t ON c.MaThucDon = t.MaThucDon "   
    			+ " LEFT JOIN Hoadon h ON c.MaHD = h.MaHD " 
    			+ sWhere;   	
    	        
        List<NhaBepModel> list = new ArrayList<>();
        try {
                    	
        	if(!(sessionFactory.getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE))
				sessionFactory.getCurrentSession().getTransaction().begin();			
			Query query =  sessionFactory.getCurrentSession()
                .createSQLQuery(sSql);
			List result = query.setMaxResults(50).list(); 
			sessionFactory.getCurrentSession().getTransaction().commit();
			for(int i=0; i<result.size(); i++){
				Object[] objects = (Object[]) result.get(i);
				NhaBepModel model = new NhaBepModel();
            	model.setiD((int)objects[0]);
				model.setMaHD(objects[1].toString());
				model.setMaThucDon(objects[2].toString());
				model.setGhiChu(objects[3].toString());
				model.setSoLuong((int)objects[4]);
				model.setGia((int)objects[5]);
				model.setTenThucDon(objects[6].toString());	
				model.setStock((boolean)objects[7]);
				model.setDone((boolean)objects[8]);
				model.setTenBan(objects[9].toString());
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
     * cap nhat 
     * Trạng thái còn hàng
     * 
     */  
	public void UpdateDataStock(int key, boolean stock) {
		String sSql = "";
    	String sWhere = " where c.ID = " + key;    	
    	
    	sSql = "UPDATE Chitiethoadon c SET c.Stock = " + stock + sWhere;       
        
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
	
	/**
     * cap nhat 
     * Trạng thái đã làm xong
     * 
     */  
	public void UpdateDataDone(int key, boolean done) {
		String sSql = "";
    	String sWhere = " where c.ID = " + key;    	
    	
    	sSql = "UPDATE Chitiethoadon c SET c.Done = " + done + sWhere;       
        
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
}
