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
				System.out.println("List");
				getStudentList(req, resp);
				break;
			case "/getclassname.act":
				System.out.println("Get room");
				getClassName(req,resp);
				break;
			case "/classfilter.act":
				System.out.println("Filter Class");
				getFilterClass(req, resp);
				break;
			case "/searchbyname.act":
				System.out.println("Search Name");
				getSearch(req, resp);
				break;
			case "/updatestatus.act":
				System.out.println("Update status");
				updateStatus(req, resp);
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void getStudentList(HttpServletRequest req, HttpServletResponse resp) throws Exception{
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
		System.out.println(staff);  
	}
	
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
			getStudentList(req, resp);
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
		System.out.println(staff);  
		
	}
	
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
		System.out.println(staff);  
	}
	
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
	
	public void getClassName(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		StaffDB db = new StaffDB();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		ArrayList<Staff> staffs = db.getClassName();
		System.out.println(staffs.size());
		
		String staff = new Gson().toJson(staffs);
		resp.getWriter().write(staff);
		System.out.println(staff);

	}

}
