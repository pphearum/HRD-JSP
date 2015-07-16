$(document).ready(function() {
    help = false;
	room = $('#room').val();
	status = $('#status').val();
	input = $('input').val();

    /* Get Staff Data From Database */
	getList();
    
    /* Retrieve Class Name From Database */
    setSelectRoom();

    /* Filter by Room Combo */    
	$('#room').change(function() {
		classFilter();
	});

	/* Filter by Status Combo */
	$('#status').change(function() {
		statusFilter();
	});

	/* Filter by Search Name */
	$('#search input').keyup(function() {
		search();
	}); 
         
    /* set default add button id when modal hide*/
    $('#updateModal').on('hidden.bs.modal', function (e) {
           resetModal();
    }); 
    
    
});

/**
 * Add New Staff Record
 */
function addstaff(){
    resetModal();
    help = true;
    $('#modal_add').text("Add Now !");
    $('.modal-title').text("Add New Staff Infomation");
    $('#modal_id').attr('disabled',false);
}

/**
 * Add and Update Staff Info
 * help = false : Add Staff
 * help = true	: Update Staff
 */
function addOrUpdate(){
	var id = $('#modal_id').val();
    var name = $('#modal_name').val();
    
    if(help){
        if(id=="" && name==""){
            $('#validId').addClass('has-error has-feedback');
            $('#validName').addClass('has-error has-feedback');
            $('.glyphicon').addClass('glyphicon-remove');
            
        }else if(id=="" && name!=""){
            $('#validId').addClass('has-error has-feedback');
            $('#validId .glyphicon').addClass('glyphicon-remove');
            
            $('#validName').removeClass('has-error has-feedback');
            $('#validName .glyphicon').removeClass('glyphicon-remove');
            
        }else if(id!="" && name==""){
            $('#validName').addClass('has-error has-feedback');
            $('#validName .glyphicon').addClass('glyphicon-remove');
            
            $('#validId').removeClass('has-error has-feedback');
            $('#validId .glyphicon').removeClass('glyphicon-remove');
        }
        else if(id!="" && name!="" && !$.isNumeric(id)){
            $('#errMsg').text('Enter Number Only !');
            
            $('#validId').addClass('has-error has-feedback');
            $('#validId .glyphicon').addClass('glyphicon-remove');
        }else if(id!="" && name!="" && $.isNumeric(id) && name.length>20){
            $('#errMsg').text('Name too long !');
            
            $('#validName').addClass('has-error has-feedback');
            $('#validName .glyphicon').addClass('glyphicon-remove')
        }else{
           addStaff(); 
        }
    }else{
        if(name==""){
            $('#validName').addClass('has-error has-feedback');
            $('#validName .glyphicon').addClass('glyphicon-remove');
        }else if(name!="" && name.length>20){
            $('#errMsg').text('Name too long !');
            $('#validName').addClass('has-error has-feedback');
            $('#validName .glyphicon').addClass('glyphicon-remove');
        }else{    
            updateStaff();
            $('#updateModal').modal('hide');
        }
    }  
}


/* Get Staff from Database  */
function getList() {
	room = $('#room').val();
	status = $('#status').val();
	input = $('input').val();

	$.post("getstafflist.act", {
		room : room,
		status : status,
		input : input,
	}, function(data) {
		$('#tbstaff').append(listDetail(data));

		$('img').click(function() {
			updateStatus($(this));
		});

	});
}


/**
 * Manage data to table and style it 
 * @param data
 * @returns {String} : as table tag with data
 */
function listDetail(data) {
	var str = "";
	for (var i = 0; i < data.length; i++) {
		str +=  '<tr>' + 
                    '<td>' + data[i].id + '</td>' + 
                    '<td>' + data[i].name+ '</td>' + 
                    '<td>' + setGender(data[i].gender) + '</td>' + 
                    '<td>' + data[i].university + '</td>' + 
                    '<td>' + data[i].room+ '</td>' + 
                    '<td><img src="' + setStatus(data[i].status)+ '" alt="Status" id="' + data[i].id + '"></td>'+
                    '<td>'+
                        '<i class="fa fa-pencil-square-o fa-2x" data-toggle="modal" title="Update Staff Info" data-target="#updateModal" onclick="getStaff(\''+data[i].id+'\')"></i>'+' '+
                        '<i class="fa fa-trash-o fa-2x" title="Delete Staff Info" onclick="deleteStaff(\''+data[i].id+'\')"></i>'+
                    '</td>'+
                '</tr>';
	}
	return str;
}


/**
 * Set Gender Male or Female to Table 
 * 		1 : Males
 * 		0 : Female
 * @param gender
 * @returns : String as Male or Female
 */
function setGender(gender) {
	return (gender == 1) ? "Male" : "Female";
}


/**
 * Set Status Active or Drop  
 * @param status
 * @returns : String as Location and Image Name
 */
function setStatus(status) {
	return (status == 1) ? "img/check.png" : "img/uncheck.png";
}


/* Filter Class in Combo */
function classFilter() {
	room = $('#room').val();
	input = $('input').val();
	$.post("classfilter.act", {
		room : room,
		status : status,
		input : input,
	}, function(data) {
		clearChild();
		$('#tbstaff').append(listDetail(data));

		$('img').click(function() {
			updateStatus($(this));
		});

	});
}

/* Filter Status in Combo */
function statusFilter() {
	status = $('#status').val();
	input = $('input').val();
	$.post("classfilter.act", {
		room : room,
		status : status,
		input : input,
	}, function(data) {
		clearChild();
		$('#tbstaff').append(listDetail(data));

		$('img').click(function() {
			updateStatus($(this));
		});

	});
}


/* Clear Table Row */
function clearChild() {
	$('#header').siblings().remove();
}

/* Search name by LIKE String */
function search() {
	var name = $('input').val();
	$.post("searchbyname.act", {
		name : name,
		room : room,
		status : status,
	}, function(data) {
		clearChild();
		$('#tbstaff').append(listDetail(data));

		$('img').click(function() {
			updateStatus($(this));
		});

	});
}

/* Update Status if image is clicked. */
function updateStatus(st) {
	src = st.attr('src');
	var s;
	var id = st.attr('id');
	if (src == 'img/check.png') {
		st.attr('src', 'img/uncheck.png');
		s = 0;
	} else {
		st.attr('src', 'img/check.png');
		s = 1;
	}

	$.post("updatestatus.act", {
		s : s,
		id : id,
	}, function(data) {
//		clearChild();
//		getList();
	});
}

/*  Query Class Name From Database */
function setSelectRoom() {
    $('select#room').empty();
    $('select#room').append("<option>All Class</option>");
	$.post("getclassname.act", function(data) {
		for (i = 0; i < data.length; i++) {
			$('select#room').append("<option>" + data[i].room + "</option>");
		}
	});
}

/**
 * Get All Id From Control In Modal
 */
function getModalId(){
     id = $('#modal_id').val();
     name = $('#modal_name').val();
     gender = $('#modal_gender').val();
     uni = $('#modal_uni').val();
     room = $('#modal_room').val();
}

/* Add new staff */
function addStaff(){
    getModalId();
    $.post("add.act",{
        id:id,
        name:name,
        gender:gender,
        uni:uni,
        room:room,
    },function(data){
        if(data=="x"){
            $('#errMsg').text('Oop! Aready Exist !');
            
            $('#validId').addClass('has-error has-feedback');
            $('#validId .glyphicon').addClass('glyphicon-remove');
            
            $('#validName').removeClass('has-error has-feedback');
            $('#validName .glyphicon').removeClass('glyphicon-remove');
        }else{
            clearChild();
            getList();
            resetModal();
            $('#updateModal').modal('hide');
            swal({ type: "success",  title: "Save Successfully!",   timer: 1000,   showConfirmButton: false });
            setSelectRoom();
        }
    });
}

/* Clear input staff */
function resetModal(){
    $('#modal_id').val("");
    $('#modal_name').val("");
    $('#modal_gender').val("Male");
    $('#modal_uni').val("RUPP");
    $('#modal_room').val("BTB");
    
    $('#validName').removeClass('has-error has-feedback');
    $('#validName .glyphicon').removeClass('glyphicon-remove');
    
    $('#validId').removeClass('has-error has-feedback');
    $('#validId .glyphicon').removeClass('glyphicon-remove');
    
    $('#errMsg').text("");
}

/* Get Staff Detail */
function getStaff(id){
    $.post("getstaff.act",{
        id:id
    },function(data){
        getDBToModal(data);
        help = false;
        $('#modal_add').text("Update Now !");
        $('.modal-title').text("Update Staff Infomation");
    });
}

/**
 * Add Staff Info to Modal Form
 * @param data
 */
function getDBToModal(data){
    $('#modal_id').val(""+data[0].id+"");
    $('#modal_name').val(""+data[0].name+"");
    $('#modal_gender').val(""+setGender(data[0].gender)+"");
    $('#modal_uni').val(""+data[0].university+"");
    $('#modal_room').val(""+data[0].room+"");
    
    $('#modal_id').attr('disabled',true);
}

/**
 * Update Staff Info Using Id
 * @param id
 */
function updateStaff(id){
    id = $('#modal_id').val();
     name = $('#modal_name').val();
     gender = $('#modal_gender').val();
     uni = $('#modal_uni').val();
     room = $('#modal_room').val();
    $.post("update.act",{
        id:id,
        name:name,
        gender:gender,
        uni:uni,
        room:room,
    },function(data){
        clearChild();
        getList();
        swal({ type: "success",  title: "Update Successfully!",   timer: 1000,   showConfirmButton: false });
    });
}

/**
 * Delete Staff Info Using Id
 * @param id
 */
function deleteStaff(id){
     $.confirm({
            title: 'Delete Confirm!',
            content: 'Are you sure you want to delete staff id '+id+' ?',
            confirm: function(){
            
                $.post("delete.act",{
                    id:id
                },function(data){
                    clearChild();
                    getList();
                    setSelectRoom();
                });
            },
            cancel: function(){
                
            }
       });
    
    
}
