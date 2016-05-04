package utilities;


public class DataService {
	private static String userID;
	private static String userName;
	
	public static String GetUserID()
	{
		return userID;
	}
	
	public static void SetUserID(String user)
	{
		userID = user;
	}
	
	public static String GetUserName()
	{
		return userName;
	}
	
	public static void SetUserName(String name)
	{
		userName = name;
	}
	
	

}
