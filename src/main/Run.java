package main;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("*.act")
public class Run extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public Run(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}
	public void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String RequestURI = req.getRequestURI();
		System.out.println(RequestURI);
		
		String contextPath = req.getContextPath();
		System.out.println(contextPath);
		
		String command = RequestURI.substring(contextPath.length());
		System.out.println(command);
		try{
			switch (command) {
			case "/getstaff.act":
				System.out.println("Get one staff");
				getStaff(req,resp);
				break;
			case "/getstafflist.act":
				getStaffList(req, resp);
				break;
			case "/getclassname.act":
				getClassName(req,resp);
				break;
			case "/classfilter.act":
				getFilterClass(req, resp);
				break;
			case "/searchbyname.act":
				getSearch(req, resp);
				break;
			case "/updatestatus.act":
				updateStatus(req, resp);
				break;
			case "/add.act":
				addStaff(req,resp);
				break;
			case "/update.act":
				updateStaff(req, resp);
				break;
			case "/delete.act":
				deleteStaff(req,resp);
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void deleteStaff(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		StaffDB db = new StaffDB();
		String id = req.getParameter("id");
		if(db.deleteStaff(id)){
			System.out.println("Deleted!");
		}else{
			System.out.println("Deleted! Failed");
		}	
	}
	public void updateStaff(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		StaffDB db = new StaffDB();
		
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String gender = req.getParameter("gender");
		String uni = req.getParameter("uni");
		String room = req.getParameter("room");
		
		System.out.println(id+" "+name+" "+gender+" "+uni+" "+room);
		
		if(db.udpateStaff(id, name, gender.equals("Male")?1:0, uni, room)){
			System.out.println("Updated!");
		}else{
			System.out.println("Update Failed");
		}
		
	}
	public void getStaff(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		StaffDB db = new StaffDB();
		String id = req.getParameter("id");
		System.out.println(id);
		
		ArrayList<Staff> staffs = db.getStaff(id);
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		String staff = new Gson().toJson(staffs);
		resp.getWriter().write(staff);
		System.out.println(staff);
	}
	
	
	public void addStaff(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		StaffDB db = new StaffDB();
		
		String id = "131N"+req.getParameter("id");
		String name = req.getParameter("name");
		String gender = req.getParameter("gender");
		String uni = req.getParameter("uni");
		String room = req.getParameter("room");
		

		
		Staff staff = new Staff(id,name, (gender.equals("Male"))?1:0, uni,room,1);
		ArrayList<Staff> staffs = db.getList("");
		ArrayList<String> staffid = new ArrayList<String>();
		
		for(int i=0;i<staffs.size();i++){
			staffid.add(staffs.get(i).getId());
		}
		
		if(staffid.contains(id)){
			resp.getWriter().write("x");
		}else{
			resp.getWriter().write("o");
			db = new StaffDB();
			if(db.addStaff(staff)){
				System.out.println("Inserted!");
			}else{
				System.out.println("Insert Failed!");
			}
		}		
	}
	
	/**
	 * Get Student List from database and filter by search, class, and status.
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void getStaffList(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		StaffDB db = new StaffDB();
		String room = req.getParameter("room");
		String status = req.getParameter("status");
		String input = req.getParameter("input");

		String filter;
		if(input!=null){
			filter = input;
		}else{
			filter="";
		}
		
		ArrayList<Staff> staffs = null;
		if(room.equals("All Class") && status.equals("All Status")){
			staffs = db.getList(filter);
		}else if(room.equals("All Class") && !status.equals("All Status")){
			staffs = db.getFilterStatus(status, filter);
		}else if(!room.equals("All Class") && status.equals("All Status")){
			staffs = db.getFilterClass(room, filter);
		}else if(!room.equals("All Class") && !status.equals("All Status")){
			staffs = db.getFilterStatusAndRoom(status, room,filter);
		}

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		String staff = new Gson().toJson(staffs);
		resp.getWriter().write(staff);  
	}
	
    /**
     * Select Like by class name (Combo)   	
     * @param req 
     * @param resp
     * @throws Exception
     */
	public void getFilterClass(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		ArrayList<Staff> staffs = null;
		StaffDB db = new StaffDB();
		String room = req.getParameter("room");
		String status = req.getParameter("status");
		String input = req.getParameter("input");
		String filter;
		if(input!=null){
			filter = input;
		}else{
			filter="";
		}
		if(room.equals("All Class") && status.equals("All Status")){
			getStaffList(req, resp);
			return;
		}else if(room.equals("All Class") && !status.equals("All Status")){
			staffs = db.getFilterStatus(status, filter);
		}else if(!room.equals("All Class") && status.equals("All Status")){
			staffs = db.getFilterClass(room, filter);
		}else if(!room.equals("All Class") && !status.equals("All Status")){
			staffs = db.getFilterStatusAndRoom(status, room,filter);
		}

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		String staff = new Gson().toJson(staffs);
		resp.getWriter().write(staff); 
		
	}
	
	/**
	 * Search LIKE by name input text (Input Text)
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void getSearch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		StaffDB db = new StaffDB();
		String name = req.getParameter("name");
		String room = req.getParameter("room");
		String status = req.getParameter("status");
		ArrayList<Staff> staffs = db.getSearch(name,room,status);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		String staff = new Gson().toJson(staffs);
		resp.getWriter().write(staff);
	}
	
	/**
	 * Update status to database (1:Active, 0:Drop).
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void updateStatus(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		StaffDB db = new StaffDB();
		int s = Integer.parseInt(req.getParameter("s"));
		String id = req.getParameter("id");
		
		if(db.update(id, s)){
			System.out.println("Updated!");
		}else{
			System.out.println("Update Fail!");
		}
	}
	
	/**
	 * Select DISTINT by class name from database.
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	public void getClassName(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		StaffDB db = new StaffDB();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		ArrayList<Staff> staffs = db.getClassName();
		System.out.println(staffs.size());
		
		String staff = new Gson().toJson(staffs);
		resp.getWriter().write(staff);
	}
}
