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
        <script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
        
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
                                <input class="form-control" type="text" value="" id="n" placeholder="Enter name">
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
                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#updateModal">Add New</button>
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
                                <th>Option</th>
                            </tr>
                        </table>
                    </div>
                </div>  
                
            </div>
            <!-- Update Modal -->
              <div class="modal fade" id="updateModal" role="dialog">
                <div class="modal-dialog">
                    
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Add New Staff</h4>
                        </div>
                        <div class="modal-body">

                                <form class="form-horizontal" role="form" id="addform">
                                    
                                    <div class="row">
                                    	<div class="form-group">
	                                        <label class="control-label col-sm-3">ID:</label>
	                                        <div class="col-sm-8">
	                                            <input type="email" class="form-control" id="modal_id" placeholder="Enter ID">
	                                        </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                    	<div class="form-group">
	                                        <label class="control-label col-sm-3">Name:</label>
	                                        <div class="col-sm-8">
	                                            <input type="email" class="form-control" id="modal_name" placeholder="Enter Name">
	                                        </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                    	<div class="form-group">
	                                       <label class="control-label col-sm-3">Gender:</label>
	                                       <div class="col-sm-4">
	                                           <select class="form-control" id="modal_gender">
                                                    <option>Male</option>
                                                    <option>Female</option>
                                               </select>
	                                       </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                    	<div class="form-group">
	                                       <label class="control-label col-sm-3">University:</label>
	                                       <div class="col-sm-4">
	                                           <select class="form-control" id="modal_uni">
                                                    <option>RUPP</option>
                                                    <option>NUM</option>
                                                    <option>SETECT</option>
                                                    <option>PPIU</option>
                                                    <option>NU</option>
                                                    <option>UP</option>
                                               </select>
	                                       </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                    	<div class="form-group">
	                                       <label class="control-label col-sm-3">Class:</label>
	                                       <div class="col-sm-4">
	                                           <select class="form-control" id="modal_room">
                                                    <option>BTB</option>
                                                    <option>KPS</option>
                                                    <option>PP</option>
                                                    <option>SR</option>
                                               </select>
	                                       </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                        <div class="text-center">
                                            <button type="button" class="btn btn-success" id="add">Add</button>
                                        </div>
                                    </div>
                                    
                                </form>
                            
                        </div>
                    </div>

                </div>
              </div>
        </div>
    </body>
</html>