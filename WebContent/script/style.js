$(document).ready(function() {
    help = false;
	room = $('#room').val();
	status = $('#status').val();
	input = $('input').val();

    /* Get Staff Data From Database */
	getList();
    
    /* Retrieve Class Name From Database */
    setSelectRoom();

	$('#room').change(function() {
		classFilter();
	});

	$('#status').change(function() {
		statusFilter();
	});

	$('input').keyup(function() {
		search();
	}); 
    
        
    /* set default add button id when modal hide*/
    $('#updateModal').on('hidden.bs.modal', function (e) {

    });
    
});

function addstaff(){
    resetModal();
    help = true;
    $('#modal_add').text("Add Now !");
    $('.modal-title').text("Add New Staff Infomation");
    $('#modal_id').attr('disabled',false);
}

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
            $('#validName .glyphicon').addClass('glyphicon-remove')
            
            $('#validId').removeClass('has-error has-feedback');
            $('#validId .glyphicon').removeClass('glyphicon-remove');
        }else{
            addStaff();
            $('#updateModal').modal('hide');
        }
    }else{
        updateStaff();
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


/*  Manage data to table and style it
    Return String as table tage with data
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
                        '<button type="button" class="btn btn-default" data-toggle="modal" data-target="#updateModal" onclick="getStaff(\''+data[i].id+'\')" >Update</button>'+' '+
                        '<button type="button" class="btn btn-danger" onclick="deleteStaff(\''+data[i].id+'\')">Delete</button>'+
                    '</td>'+
                '</tr>';
	}
	return str;
}


/*  
    Set Gender Male or Female to table (1:Males, 0:Female)
    Return String as Male or Female
*/
function setGender(gender) {
	return (gender == 1) ? "Male" : "Female";
}


/*
    Set Status Active or Drop
    Return String as location and image name
*/
function setStatus(status) {
	return (status == 1) ? "img/check.png" : "img/uncheck.png";
}


/* Filter Class in combo */
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
		clearChild();
		getList();
	});
}

/*  Query Class Name From Database */
function setSelectRoom() {
	$.post("getclassname.act", function(data) {
		for (i = 0; i < data.length; i++) {
			$('select#room').append("<option>" + data[i].room + "</option>");
		}
	});
}

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
        clearChild();
        getList();
        resetModal();
    });
}

/* Clear input staff */
function resetModal(){
    $('#modal_id').val("");
    $('#modal_name').val("");
    $('#modal_gender').val("Male");
    $('#modal_uni').val("RUPP");
    $('#modal_room').val("BTB");
}

/* Get Staff Detial */
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

function getDBToModal(data){
    $('#modal_id').val(""+data[0].id+"");
    $('#modal_name').val(""+data[0].name+"");
    $('#modal_gender').val(""+setGender(data[0].gender)+"");
    $('#modal_uni').val(""+data[0].university+"");
    $('#modal_room').val(""+data[0].room+"");
    
    $('#modal_id').attr('disabled',true);
}

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
    });
}

function deleteStaff(id){
    $.post("delete.act",{
        id:id
    },function(data){
        clearChild();
        getList();
    });
}
