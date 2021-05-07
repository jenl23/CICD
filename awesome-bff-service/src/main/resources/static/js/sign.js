$('.form').find('input, textarea').on('keyup blur focus', function (e) {
  
  var $this = $(this),
      label = $this.prev('label');

	  if (e.type === 'keyup') {
			if ($this.val() === '') {
          label.removeClass('active highlight');
        } else {
          label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
    	if( $this.val() === '' ) {
    		label.removeClass('active highlight'); 
			} else {
		    label.removeClass('highlight');   
			}   
    } else if (e.type === 'focus') {
      
      if( $this.val() === '' ) {
    		label.removeClass('highlight'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('highlight');
			}
    }

});

$('.tab a').on('click', function (e) {
  
  e.preventDefault();
  
  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');
  
  target = $(this).attr('href');

  $('.tab-content > div').not(target).hide();
  
  $(target).fadeIn(600);
  
});

$('#signinBtn').click(function(self){
	
	var accountTemp = new Object();
	accountTemp.username = $('#signinUsername').val();
	accountTemp.password = $('#signinPassword').val();
	
	var account = JSON.stringify(accountTemp);
	
	$.ajax({
        url:'/v1/login'
        ,type:'POST'
    	,dataType:'json'
		,contentType:'application/json; charset=UTF-8'
        ,data:account
       	,success:function(result){
       		alert("signin success!");
            $(location).attr('href', '/index');
        }
	
		,error:function(result){
			alert("signin failed!");
		}
    });
});

$('#signupBtn').click(function(self){
	
	var accountTemp = new Object();
	accountTemp.username = $('#username').val();
	accountTemp.password = $('#password').val();
	accountTemp.name = $('#name').val();
	accountTemp.mobile = $('#mobile').val();
	accountTemp.address = $('#address').val();
	accountTemp.scope = 'customer';
	
	var account = JSON.stringify(accountTemp);
	
	$.ajax({
        url:'/v1/accounts'
        ,type:'POST'
    	,dataType:'json'
		,contentType:'application/json; charset=UTF-8'
        ,data:account
       	,success:function(result){
       		alert("signup success!");
       		$('#username').val('');
       		$('#password').val('');
       		$('#name').val('');
       		$('#mobile').val('');
       		$('#address').val('');
        }
		,error:function(result){
			alert("signup failed!");
		}
    });
});