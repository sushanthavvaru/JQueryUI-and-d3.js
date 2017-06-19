$('document').ready(function(){
	$("#pickdate").datepicker({changeMonth:true, changeYear:true, showOtherMonths:true, showButtonPanel:true,
	closeText:"Close"});
	$("a").tooltip({show:{effect: "bounce", delay:1000}});	
	$("#panels").accordion({icons:{header:"ui-icon-folder-collapsed",activeHeader:"ui-icon-folder-open"}},
	{ event:"mouseover"});
	$("#verticalMenu").menu();
	$("#box").dialog({title:"Welcome to Demo", resizable:false, hide:1500, closeOnEscape:false,
		modal:true});
	$("#tabcontainer").tabs({event:"mouseover"});
	$("#auto").autocomplete({source:["USA", "India", "Mexico", "Canada"]})
	
	
	
	
	//form validation
	
	
	
	
	 function isEmpty(fieldValue) {
        return $.trim(fieldValue).length == 0;    
        } 
        
    function isValidState(state) {                                
        var stateList = new Array("AK","AL","AR","AZ","CA","CO","CT","DC",
        "DE","FL","GA","GU","HI","IA","ID","IL","IN","KS","KY","LA","MA",
        "MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ",
        "NM","NV","NY","OH","OK","OR","PA","PR","RI","SC","SD","TN","TX",
        "UT","VA","VT","WA","WI","WV","WY");
        for(var i=0; i < stateList.length; i++) 
            if(stateList[i] == $.trim(state))
                return true;
        return false;
        }  
        
    // copied from stackoverflow.com, not checked or validated for correctness.        
    function isValidEmail(emailAddress) {
        var pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
        return pattern.test(emailAddress);
        }    
		
	function isValidDate() {
		var day = $("#date").val();
		var month = $("#month").val();
		var year = $("#year").val();
    
		var calc_date = new Date(year, month-1, day);
		var calc_day = calc_date.getDate();
		var calc_month = calc_date.getMonth()+1;
		var calc_year = calc_date.getFullYear();
    
		if(day == calc_day && month == calc_month && year == calc_year)
			return true;
		else
			return false;
}
	var errorStatusHandle = $('#message_line');
    var elementHandle = new Array(13);
    elementHandle[0] = $('[name="fname"]');
    elementHandle[1] = $('[name="lname"]');
    elementHandle[2] = $('[name="address1"]');
    elementHandle[3] = $('[name="city"]');
    elementHandle[4] = $('[name="state"]');
    elementHandle[5] = $('[name="zip"]');
    elementHandle[6] = $('[name="area_phone"]');
    elementHandle[7] = $('[name="prefix_phone"]');
    elementHandle[8] = $('[name="phone"]');
    elementHandle[9] = $('[name="email"]');
	elementHandle[10] = $('[name="month"]');
    elementHandle[11] = $('[name="date"]');
    elementHandle[12] = $('[name="year"]');

         
    function isValidData() {
        if(isEmpty(elementHandle[0].val())) {
            elementHandle[0].addClass("error");
            errorStatusHandle.text("Please enter your first name");
            elementHandle[0].focus();
            return false;
            }
        if(isEmpty(elementHandle[1].val())) {
            elementHandle[1].addClass("error");
            errorStatusHandle.text("Please enter your last name");
            elementHandle[1].focus();            
            return false;
            }
        if(isEmpty(elementHandle[2].val())) {
            elementHandle[2].addClass("error");
            errorStatusHandle.text("Please enter your address");
            elementHandle[2].focus();            
            return false;
            }
        if(isEmpty(elementHandle[3].val())) {
            elementHandle[3].addClass("error");
            errorStatusHandle.text("Please enter your city");
            elementHandle[3].focus();            
            return false;
            }
        if(isEmpty(elementHandle[4].val())) {
            elementHandle[4].addClass("error");
            errorStatusHandle.text("Please enter your state");
            elementHandle[4].focus();            
            return false;
            }
        if(!isValidState(elementHandle[4].val())) {
            elementHandle[4].addClass("error");
            errorStatusHandle.text("The state appears to be invalid, "+
            "please use the two letter state abbreviation");
            elementHandle[4].focus();            
            return false;
            }
        if(isEmpty(elementHandle[5].val())) {
            elementHandle[5].addClass("error");
            errorStatusHandle.text("Please enter your zip code");
            elementHandle[5].focus();            
            return false;
            }
        if(!$.isNumeric(elementHandle[5].val())) {
            elementHandle[5].addClass("error");
            errorStatusHandle.text("The zip code appears to be invalid, "+
            "numbers only please. ");
            elementHandle[5].focus();            
            return false;
            }
        if(elementHandle[5].val().length != 5) {
            elementHandle[5].addClass("error");
            errorStatusHandle.text("The zip code must have exactly five digits")
            elementHandle[5].focus();            
            return false;
            }
        if(isEmpty(elementHandle[6].val())) {
            elementHandle[6].addClass("error");
            errorStatusHandle.text("Please enter your area code");
            elementHandle[6].focus();            
            return false;
            }            
        if(!$.isNumeric(elementHandle[6].val())) {
            elementHandle[6].addClass("error");
            errorStatusHandle.text("The area code appears to be invalid, "+
            "numbers only please. ");
            elementHandle[6].focus();            
            return false;
            }
        if(elementHandle[6].val().length != 3) {
            elementHandle[6].addClass("error");
            errorStatusHandle.text("The area code must have exactly three digits")
            elementHandle[6].focus();            
            return false;
            }   
        if(isEmpty(elementHandle[7].val())) {
            elementHandle[7].addClass("error");
            errorStatusHandle.text("Please enter your phone number prefix");
            elementHandle[7].focus();            
            return false;
            }           
        if(!$.isNumeric(elementHandle[7].val())) {
            elementHandle[7].addClass("error");
            errorStatusHandle.text("The phone number prefix appears to be invalid, "+
            "numbers only please. ");
            elementHandle[7].focus();            
            return false;
            }
        if(elementHandle[7].val().length != 3) {
            elementHandle[7].addClass("error");
            errorStatusHandle.text("The phone number prefix must have exactly three digits")
            elementHandle[7].focus();            
            return false;
            }
        if(isEmpty(elementHandle[8].val())) {
            elementHandle[8].addClass("error");
            errorStatusHandle.text("Please enter your phone number");
            elementHandle[8].focus();            
            return false;
            }            
        if(!$.isNumeric(elementHandle[8].val())) {
            elementHandle[8].addClass("error");
            errorStatusHandle.text("The phone number appears to be invalid, "+
            "numbers only please. ");
            elementHandle[8].focus();            
            return false;
            }
        if(elementHandle[8].val().length != 4) {
            elementHandle[8].addClass("error");
            errorStatusHandle.text("The phone number must have exactly four digits")
            elementHandle[8].focus();            
            return false;
            }  
        if(isEmpty(elementHandle[9].val())) {
            elementHandle[9].addClass("error");
            errorStatusHandle.text("Please enter your email address");
            elementHandle[9].focus();            
            return false;
            }            
        if(!isValidEmail(elementHandle[9].val())) {
            elementHandle[9].addClass("error");
            errorStatusHandle.text("The email address appears to be invalid,");
            elementHandle[9].focus();            
            return false;
            } 
		if(isEmpty(elementHandle[10].val())) {
            elementHandle[10].addClass("error");
            errorStatusHandle.text("Please Enter Month");
            elementHandle[10].focus();
            return false;
            }
        if(!$.isNumeric(elementHandle[10].val())) {
            elementHandle[10].addClass("error");
            errorStatusHandle.text("Please Enter number for month");
            elementHandle[10].focus();
            return false;
            }
        if(isEmpty(elementHandle[11].val())) {
            elementHandle[11].addClass("error");
            errorStatusHandle.text("Please Enter Day");
            elementHandle[11].focus();
            return false;
            }
        if(!$.isNumeric(elementHandle[11].val())) {
            elementHandle[11].addClass("error");
            errorStatusHandle.text("Please Enter number for Day");
            elementHandle[11].focus();
            return false;
            }
        if(isEmpty(elementHandle[12].val())) {
			elementHandle[12].addClass("error");
            errorStatusHandle.text("Please Enter Year");
            elementHandle[12].focus();
            return false;
            }
        if(!$.isNumeric(elementHandle[12].val())) {
            elementHandle[12].addClass("error");
            errorStatusHandle.text("Please Enter number for year");
            elementHandle[12].focus();
            return false;
            }
         var isValidDOB = true;
            if(!isValidDate()){
                  $('[name="month"]').addClass("error");
                  $('[name="date"]').addClass("error");
                  $('[name="year"]').addClass("error");
                  errorStatusHandle.text("Please Enter a valid date");
                  isValidDOB = false;
				  return false;
                 
                 }
                  if(isValidDOB){
                  var dob = new Date();
                  dob.setMonth($('[name="month"]').val());
                  dob.setDate($('[name="date"]').val());
                  dob.setFullYear($('[name="year"]').val());
                  
                  var ref = new Date("Jan 1, 2017");
                  var diff_year = ref.getFullYear() - dob.getFullYear();
                  var diff_month = ref.getMonth() - dob.getMonth();
                  var diff_day = ref.getDate() - dob.getDate();
                  
                  if(diff_year > 80 || (diff_year == 80 && diff_month > 0) || (diff_year == 80 && diff_month == 0 && diff_day > 0)) {
                  $('[name="month"]').addClass("error");
                  $('[name="date"]').addClass("error");
                  $('[name="year"]').addClass("error");
                  errorStatusHandle.text("age more than 80 cannot register");
                  return false;
                  }
                  if(diff_year < 10 || (diff_year == 10 && diff_month < 0) || (diff_year==10 && diff_month == 0 && diff_day < 0)) {
                  $('[name="month"]').addClass("error");
                  $('[name="date"]').addClass("error");
                  $('[name="year"]').addClass("error");
                  errorStatusHandle.text("Kid must be atleast 10 years old to Register");
                  return false;
                  }
                  } 	
		            	
        return true;
        }       

   elementHandle[0].focus();
   
   
/////// HANDLERS

// on blur, if the user has entered valid data, the error message
// should no longer show.
    elementHandle[0].on('blur', function() {
        if(isEmpty(elementHandle[0].val()))
            return;
        $(this).removeClass("error");
        errorStatusHandle.text("");
        });
        
    elementHandle[9].on('blur', function() {
        if(isEmpty(elementHandle[12].val()))
            return;
        if(isValidEmail(elementHandle[12].val())) {
            $(this).removeClass("error");
            errorStatusHandle.text("");
            }
        });        
/////////////////////////////////////////////////////////////////        

    elementHandle[4].on('keyup', function() {
        elementHandle[4].val(elementHandle[4].val().toUpperCase());
        });
        
    elementHandle[6].on('keyup', function() {
        if(elementHandle[6].val().length == 3)
            elementHandle[7].focus();
            });
            
    elementHandle[7].on('keyup', function() {
        if(elementHandle[7].val().length == 3)
            elementHandle[8].focus();
            });            

   
    $(':submit').on('click', function(event) {
		event.preventDefault();
        for(var i=0; i < 13; i++)
            elementHandle[i].removeClass("error");
        errorStatusHandle.text("");
		
		if(!isValidData()){
			
			console.log("Invalid data");
		}
		else{
			
			$("form").submit();
			console.log("valid data");
		}	
  
        });
        
    $(':reset').on('click', function() {
        for(var i=0; i < 13; i++)
            elementHandle[i].removeClass("error");
        errorStatusHandle.text("");
        });                             
	
	
})
