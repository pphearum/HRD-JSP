package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class StaffDB {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<Staff> staffs;
	private Staff staff;
	
	public StaffDB() throws Exception{
		InitialContext init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/theajaxdb");
		con = ds.getConnection();
		System.out.println("Connection Success!");
	}
	
	public boolean addStaff(Staff staff) throws SQLException{
		String sql = "INSERT INTO hrd_students(stu_id, stu_name, stu_gender, stu_university, stu_class)"
				+ " VALUES(?,?,?,?,?);";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1,staff.getId());
		pstmt.setString(2, staff.getName());
		pstmt.setInt(3, staff.getGender());
		pstmt.setString(4, staff.getUniversity());
		pstmt.setString(5, staff.getRoom());
		
		int i = pstmt.executeUpdate();
		if(i>0){
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Update Status (1:Active, 0:Drop).
	 * @param id
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public boolean update(String id, int status) throws SQLException{
		String sql = "UPDATE hrd_students SET stu_status =? WHERE stu_id =?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, status);
		pstmt.setString(2, id);
		int i = pstmt.executeUpdate();
		
		if(i>0){
			if(pstmt!=null){ pstmt.close(); }
			if(con!=null){ con.close(); }
			return true;
		}
		return false;
	}
	
	/**
	 * Get Data From database (LIKE input parameter).
	 * @param input
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Staff> getList(String input) throws SQLException{
		String sql = "SELECT * FROM hrd_students WHERE stu_name LIKE '%"+input+"%' ORDER BY stu_id DESC";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		staffs = new ArrayList<>();
		try{
		while(rs.next()){
			staff = new Staff();
			staff.setId(rs.getString("stu_id"));
			staff.setName(rs.getString("stu_name"));
			staff.setGender(rs.getInt("stu_gender"));
			staff.setUniversity(rs.getString("stu_university"));
			staff.setRoom(rs.getString("stu_class"));
			staff.setStatus(rs.getInt("stu_status"));
			staffs.add(staff);
		}
			return staffs;
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
	}
	
	/**
	 * Search by name in database
	 * @param name
	 * @param room
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Staff> getSearch(String name, String room, String status) throws SQLException{
		String sql="";
		int st;
		if(status.equals("Active")){
			st = 1;
		}else{
			st = 0;
		}
		
		if(room.equals("All Class") && status.equals("All Status")){
			sql = "SELECT * FROM hrd_students WHERE stu_name LIKE '%"+name+"%' ORDER BY stu_id DESC";
		}else if(!room.equals("All Class") && status.equals("All Status")){
			sql = "SELECT * FROM hrd_students WHERE stu_name LIKE '%"+name+"%' AND stu_class ='"+room+"' ORDER BY stu_id DESC";
		}else if(room.equals("All Class") && !status.equals("All Status")){
			sql = "SELECT * FROM hrd_students WHERE stu_name LIKE '%"+name+"%' AND stu_status ="+st+" ORDER BY stu_id DESC";
		}else if(!room.endsWith("All Class") && !status.equals("All Status")){
			sql = "SELECT * FROM hrd_students WHERE stu_name LIKE '%"+name+"%' AND stu_status ="+st+" AND stu_class ='"+room+"' ORDER BY stu_id DESC";
		}
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		staffs = new ArrayList<>();
		try{
		while(rs.next()){
			staff = new Staff();
			staff.setId(rs.getString("stu_id"));
			staff.setName(rs.getString("stu_name"));
			staff.setGender(rs.getInt("stu_gender"));
			staff.setUniversity(rs.getString("stu_university"));
			staff.setRoom(rs.getString("stu_class"));
			staff.setStatus(rs.getInt("stu_status"));
			staffs.add(staff);
		}
		return staffs;
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
	}
	
	/**
	 * Get ClassName From database 
	 * @param room
	 * @param filter
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Staff> getFilterClass(String room, String filter) throws SQLException{
		String sql = "SELECT * FROM hrd_students WHERE stu_class ='"+room+"' AND stu_name LIKE '%"+filter+"%' ORDER BY stu_id DESC";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		staffs = new ArrayList<>();
		try{
		while(rs.next()){
			staff = new Staff();
			staff.setId(rs.getString("stu_id"));
			staff.setName(rs.getString("stu_name"));
			staff.setGender(rs.getInt("stu_gender"));
			staff.setUniversity(rs.getString("stu_university"));
			staff.setRoom(rs.getString("stu_class"));
			staff.setStatus(rs.getInt("stu_status"));
			staffs.add(staff);
		}
			return staffs;
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
	}
	
	/**
	 * GET Status Filter from database
	 * @param str_status
	 * @param filter
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Staff> getFilterStatus(String str_status, String filter) throws SQLException{
		int status=0;
		if(str_status.equals("Active")){
			status = 1;
		}
		if(str_status.equals("Drop")){
			status = 0;
		}

		String sql = "SELECT * FROM hrd_students WHERE stu_status ="+status+" AND stu_name LIKE '%"+filter+"%' ORDER BY stu_id DESC";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		staffs = new ArrayList<>();
		try{
		while(rs.next()){
			staff = new Staff();
			staff.setId(rs.getString("stu_id"));
			staff.setName(rs.getString("stu_name"));
			staff.setGender(rs.getInt("stu_gender"));
			staff.setUniversity(rs.getString("stu_university"));
			staff.setRoom(rs.getString("stu_class"));
			staff.setStatus(rs.getInt("stu_status"));
			staffs.add(staff);
		}
		return staffs;
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
	}
	
	/**
	 * Get Filter both class and room name
	 * @param str_status
	 * @param room
	 * @param filter
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Staff> getFilterStatusAndRoom(String str_status, String room, String filter) throws SQLException{
		int status=0;
		if(str_status.equals("Active")){
			status = 1;
		}
		if(str_status.equals("Drop")){
			status = 0;
		}

		String sql = "SELECT * FROM hrd_students WHERE stu_status ="+status+" AND stu_class ='"+room+"' AND stu_name LIKE '%"+filter+"%' ORDER BY stu_id DESC";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		staffs = new ArrayList<>();
		try{
		while(rs.next()){
			staff = new Staff();
			staff.setId(rs.getString("stu_id"));
			staff.setName(rs.getString("stu_name"));
			staff.setGender(rs.getInt("stu_gender"));
			staff.setUniversity(rs.getString("stu_university"));
			staff.setRoom(rs.getString("stu_class"));
			staff.setStatus(rs.getInt("stu_status"));
			staffs.add(staff);
		}
		return staffs;
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
	}
	
	/**
	 * Select DISTINCT by class name in from database
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Staff> getClassName() throws SQLException{
		String sql = "SELECT DISTINCT stu_class FROM hrd_students";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		staffs = new ArrayList<>();
		try{
		while(rs.next()){
			staff = new Staff();
			staff.setRoom(rs.getString("stu_class"));
			staffs.add(staff);
		}
		return staffs;
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
	}
	
	
	public ArrayList<Staff> getStaff(String id) throws SQLException{
		String sql = "SELECT * FROM hrd_students WHERE stu_id = ?;";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		staffs = new ArrayList<>();
		try{
		if(rs.next()){
			staff = new Staff();
			staff.setId(rs.getString("stu_id"));
			staff.setName(rs.getString("stu_name"));
			staff.setGender(rs.getInt("stu_gender"));
			staff.setUniversity(rs.getString("stu_university"));
			staff.setRoom(rs.getString("stu_class"));
			staff.setStatus(rs.getInt("stu_status"));
			staffs.add(staff);
		}
		return staffs;
		}finally{
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
	}
	
	public boolean udpateStaff(String id, String name, int gender, String uni, String room) throws SQLException{
		
		String sql = "UPDATE hrd_students "
				+ "SET stu_name = ?, "
				+ "stu_gender = ?, "
				+ "stu_university = ?, "
				+ "stu_class = ? "
				+ "WHERE stu_id = ?;";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setInt(2, gender);
		pstmt.setString(3, uni);
		pstmt.setString(4, room);
		pstmt.setString(5, id);
		
		int i = pstmt.executeUpdate();
		if(i>0){
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				pstmt.close();
			}
			return true;
		}
		return false;
	}
	
	public boolean deleteStaff(String id) throws SQLException{
		String sql = "DELETE FROM hrd_students WHERE stu_id = ?;";
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		int i = pstmt.executeUpdate();
		if(i>0){
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
			return true;
		}
		return false;
	}
	
	
}
