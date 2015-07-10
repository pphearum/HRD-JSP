<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Staff and Student Management</title>
        
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        
        <link href="style/style.css" type="text/css" rel="stylesheet"> 
        <script src="script/style.js"></script>
    </head>
    
    <body>
        <div id="container">
            <h2>Student and Staff Management</h2>
            <label>Search By Name: </label><input type="text" value="" id="n">
            
            <select id="room">
                <option>All Class</option>
                <!-- Retrieve Class Name from Database -->
            </select>
            
            <select id="status">
                <option>All Status</option>
                <option>Active</option>
                <option>Drop</option>
            </select>
            
            <div id="display">
                <table align="center" id="tbstaff">
                    <tr id="header">
                        <th>ID</th>
                        <th>Name</th>
                        <th>Gender</th>
                        <th>University</th>
                        <th>Class</th>
                        <th>Status</th>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>