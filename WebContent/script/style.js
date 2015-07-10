   $(document).ready(function(){
       
        room = $('#room').val();
        status = $('#status').val();
        input = $('input').val();
       
        getList(); 
       
        $('#room').change(function(){
            classFilter();
        });
       
        $('#status').change(function(){
            statusFilter();
        });
       
        $('input').keyup(function(){
            search();
        });
       
    });
            
                function getList(){
                    room = $('#room').val();
                    status = $('#status').val();
                    input = $('input').val();
                    
                    $.post("getstaff.act",{
                        room : room,
                        status : status,
                        input : input,
                    },function(data){
                        $('#tbstaff').append(listDetail(data));
                        
                        $('img').click(function(){
                            updateStatus($(this));
                        });
                          
                    });    
                }

                function listDetail(data){
                    var str="";
                    for(var i=0; i<data.length; i++){
                        str +=  '<tr>'+
                                    '<td>'+data[i].id+'</td>'+
                                    '<td>'+data[i].name+'</td>'+
                                    '<td>'+setGender(data[i].gender)+'</td>'+
                                    '<td>'+data[i].university+'</td>'+
                                    '<td>'+data[i].room+'</td>'+
                                    '<td><img src="'+setStatus(data[i].status)+'" alt="Status" id="'+data[i].id+'"></td>'+
                                '</tr>';   
                    }
                     return str;
                }

                function setGender(gender){
                    return (gender==1)?"Male":"Female";
                }
                
                function setStatus(status){   
                    return (status==1)?"img/check.png":"img/uncheck.png";
                }

                function classFilter(){
                    room = $('#room').val();
                    input = $('input').val();
                    $.post("classfilter.act",{
                        room : room,
                        status: status,
                        input : input,
                    },function(data){
                        clearChild();
                        $('#tbstaff').append(listDetail(data));  
                        
                        $('img').click(function(){
                            updateStatus($(this));
                        });
                        
                    });
                }

                function statusFilter(){
                    status = $('#status').val();
                    input = $('input').val();
                    $.post("classfilter.act",{
                        room : room,
                        status: status,
                        input : input,
                    },function(data){
                        clearChild();
                        $('#tbstaff').append(listDetail(data));
                        
                        $('img').click(function(){
                            updateStatus($(this));
                        });
                        
                    });
                }

                function clearChild(){
                    $('#header').siblings().remove();
                }
            
                

                function search(){
                    var name = $('input').val();
                    $.post("searchbyname.act",{
                        name : name,
                        room : room,
                        status : status,
                    },function(data){
                        clearChild();
                        $('#tbstaff').append(listDetail(data));
                        
                        $('img').click(function(){
                            updateStatus($(this));
                        });
                        
                    });
                }
                
                function updateStatus(st){
                    src = st.attr('src');
                    var s;
                    var id = st.attr('id');
                    if(src=='img/check.png'){
                        st.attr('src','img/uncheck.png');
                        s = 0;
                    }else{
                        st.attr('src','img/check.png');
                        s = 1;
                    }
                    
                    $.post("updatestatus.act",{
                        s : s,
                        id: id,
                    }, function(data){
                        clearChild();
                        getList();
                    });
                }

                setSelectRoom();
                function setSelectRoom(){
                    $.post("getclassname.act",function(data){
                        for(i=0; i<data.length;i++){
                            $('select#room').append("<option>"+data[i].room+"</option>");
                        }
                    });
                }
                
                
                