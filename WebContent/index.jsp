<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
        
<html>
    <head>
    
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Staff and Student Management</title>
        
        <!-- JQuery Library -->
        <script src="jquery-library/jquery-1.11.3.min.js"></script>
        
        <!-- Bootstrap Library-->
        <link href="bootstrap-3.3.5-dist/css/bootstrap.min.css" type="text/css" rel="stylesheet">
        <script src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
        
        <!-- JS Plug In -->
        <script src="craftpip-jquery-confirm-4093790/dist/jquery-confirm.min.js"></script>
        <link href="craftpip-jquery-confirm-4093790/dist/jquery-confirm.min.css" rel="stylesheet" type="text/css">
        <script src="sweetalert-master/dist/sweetalert.min.js"></script>
        <link href="sweetalert-master/dist/sweetalert.css" type="text/css" rel="stylesheet">
        
        <!-- My Style CSS -->
        <link href="style/style.css" type="text/css" rel="stylesheet">
        
        <!-- Font Awesome -->
        <link href="font/font-awesome-4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">    
        
        <!-- My Script -->
        <script src="script/style.js"></script>
        
    </head>
    
    <body>
        <div class="container">
        
            <div class="row">
                <div class="col-sm-12">
                    <h2 class="text-center"><span>S</span>tudent and <span>S</span>taff <span>M</span>anagement</h2>
                    <div id="filter">
                        <form class="form-inline" role="form">
                            <div class="form-group" id="search">
                                <label>Search By Name: </label>
                                
<!--                                <div class="input-group">-->
<!--                                    <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span></span>-->
                                    <input class="form-control" type="text" value="" id="n" placeholder="Enter name">
<!--                                </div>-->
                                
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
                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#updateModal" onclick="addstaff()">Add New</button>
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
                            <h4 class="modal-title text-center">Add New Staff</h4>
                        </div>
                        <div class="modal-body">

                                <form class="form-horizontal" role="form" id="addform">
                                    
                                    <div class="row">
                                    	<div class="form-group" id="validId">
	                                        <label class="control-label col-sm-3">ID:</label>
	                                        <div class="col-sm-8">
	                                            <input type="text" class="form-control" id="modal_id" placeholder="Enter ID">
                                                <span class="glyphicon  form-control-feedback" aria-hidden="false"></span>
                                                <span id="inputError2Status" class="sr-only">(error)</span>
	                                        </div>
                                        </div>
                                    </div>
                                    
                                    <div class="row">
                                    	<div class="form-group" id="validName">
	                                        <label class="control-label col-sm-3">Name:</label>
	                                        <div class="col-sm-8">
	                                            <input type="text" class="form-control" id="modal_name" placeholder="Enter Name">
                                                <span class="glyphicon form-control-feedback" aria-hidden="false"></span>
                                                <span id="inputError2Status" class="sr-only">(error)</span>
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
                                                    <option>SETEC</option>
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
                                            <button type="button" class="btn btn-success process" onclick="addOrUpdate()" id="modal_add">Add Now !</button><span id="errMsg"></span>
                                        </div> 
                                    </div>
                                </form>
                            
                        </div>
                    </div>

                </div>
              </div>
            <!-- End Update Modal -->
            
        </div>
    </body>
</html>