/**
 * 
 */

var $ = function(id)
{
	return document.getElementById(id);
}

var checkPass = function ()
{
    //Store the password field objects into variables ...
    var pass1 = document.getElementById('pass1');
    var pass2 = document.getElementById('pass2');
    //Store the Confimation Message Object ...
    var message = document.getElementById('confirmMessage');
    //Set the colors we will be using ...
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    //Compare the values in the password field 
    //and the confirmation field
    if(pass1.value == pass2.value){
        //The passwords match. 
        //Set the color to the good color and inform
        //the user that they have entered the correct password 
        pass2.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        // message.innerHTML = "Passwords Match!";
        $("getCustomer").submit();
    }else{
        //The passwords do not match.
        //Set the color to the bad color and
        //notify the user.
        pass2.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "Passwords Do Not Match!";
    }
}  

var checkPassHTML5 = function ()
{
    //Store the password field objects into variables ...
    var pass1 = document.getElementById('password');
    var pass2 = document.getElementById('passwordDup');
    //Store the Confimation Message Object ...
    var message = document.getElementById('confirmMessage');
    //Set the colors we will be using ...
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    //Compare the values in the password field 
    //and the confirmation field
    if(pass1.value == pass2.value && pass1.value != ""){
        //The passwords match. 
        //Set the color to the good color and inform
        //the user that they have entered the correct password 
        pass2.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        // check built-in HTML5 validation
        var isFormValid = $("getCustomer").checkValidity();
        
        // New way to check
        
        
        

        // check for validity and only submit page when form is valid
        if(isFormValid)
        	{
	        	// message.innerHTML = "Passwords Match! and form is valid";
	            $("getCustomer").submit();
        	}
        else
        	{
        		// A problem was found during validation so don't submit the form to the server
        		$("getCustomer").preventDefault();
        	}
        
    }else{
        //The passwords do not match.
        //Set the color to the bad color and
        //notify the user.
        pass2.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "Passwords Do Not Match!";
    }
}  

function validatePassword(){
	var pass2=document.getElementById("passwordDup").value;
	var pass1=document.getElementById("password").value;
	if(pass1!=pass2)
	    document.getElementById("passwordDup").setCustomValidity("Passwords Don't Match");
	else
	    document.getElementById("passwordDup").setCustomValidity('');  
	//empty string means no validation error
	}


window.onload = function()
{
	// $("calculate").onclick = checkPassHTML5;
	// $("subtotal").focus;
	$("password").onchange = validatePassword;
    $("passwordDup").onchange = validatePassword;

}
