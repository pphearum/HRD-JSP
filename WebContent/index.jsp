<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Staff and Student Management</title>
        
        <!-- Jquery libruary -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        
        <!-- Bootstrap libruary-->
        <link href="bootstrap-3.3.5-dist/css/bootstrap.min.css" type="text/css" rel="stylesheet">
        
        <!-- My Style CSS -->
        <link href="style/style.css" type="text/css" rel="stylesheet">
        
        <!-- My Script -->
        <script src="script/style.js"></script>
    </head>
    
    <body>
        <div class="container">
            <div class="row">
                
                <div class="col-sm-12">
                    <h2 class="text-center">Student and Staff Management</h2> 
                    <div id="filter">
                        <form class="form-inline" role="form">
                            <div class="form-group">
                                <label>Search By Name: </label>
                                <input class="form-control" type="text" value="" id="n">
                            </div>
                            <select class="form-control" id="room">
                                <option>All Class</option>
                                <!-- Retrieve Class Name from Database -->
                            </select>
                            <select class="form-control" id="status">
                                <option>All Status</option>
                                <option>Active</option>
                                <option>Drop</option>
                            </select>
                        </form> 
                    </div>
                    <div id="display">
                        <table class="table table-bordered text-center" id="tbstaff">
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
                
                
                
            </div>
        </div>
        <div id="wrapper">
            
            
            <div id="display">
                
            </div>
        </div>
    </body>
</html>