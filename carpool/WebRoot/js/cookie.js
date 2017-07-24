function createCookie(name,value,days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + value + expires + "; path=/";
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name,"",-1);
}

function writeCook() {
	console.log("writeCook");
	createCookie('ppkcookie','testcookie',7);
}

function readCook() {
	var x = readCookie('ppkcookie')
	console.log(x);
	if (x) {
	    alert (x);
	} else {
		alert ("Please login first");
	}
}


function fb_login() {
	console.log('fb_login() function called');
    FB.login( function() {}, { scope: 'email,public_profile' } );
    checkLoginState()
}

function loginWithFBmeAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me?fields=id,name,email,permissions', function(response) {
    	console.log('Welcome! loginLink .... ');
    	createCookie('fbcplkusr', response.email + "--" + response.name ,7);
    	showOptions();
    	$("#userNameHeader").text(response.name);
    });
  }


function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
      //window.location = "https://carpool.lkauto.org/index.html";
      var uid = response.authResponse.userID;
      var accessToken = response.authResponse.accessToken;
      console.log('response.name ' + response.name);
      console.log('accessToken ' + accessToken);
  	  createCookie('fbcplkat', (uid + "-" + accessToken) ,7);
  	  loginWithFBmeAPI();
    } else {
	  changeUiOnFbLogout();
    }
  }

function checkLoginState() {
  console.log('checkLoginState function called');
  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
}

window.fbAsyncInit = function() {
    FB.init({
      appId      : '415953682121092',
      cookie     : true,
      xfbml      : true,
      version    : 'v2.8'
    });
    FB.Event.subscribe('auth.statusChange', checkLoginState);
    FB.AppEvents.logPageView();   
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));


function changeUiOnFbLogout() {
    
//	FB.logout(function (response) {
//	            //Do what ever you want here when logged out like reloading the page
//	        	 console.log('FB.logout(function (response)');
//	            //window.location.reload();
//	        });
	
	eraseCookie("fbcplkusr");
	eraseCookie("fbcplkat");
	hideOptions();
	$("#userNameHeader").text("Login");
}
    
function changeUiOnFbLogin(name) {
	showOptions();
	$("#userNameHeader").text(name);
}

function hideOptions(show) {
	$("#loginFbHeaderLi").show();
	$("#myProfileHeaderLi").hide();
	$("#logoutHeaderLi").hide();
}

function showOptions(show) {
	$("#loginFbHeaderLi").hide();
	$("#myProfileHeaderLi").show();
	$("#logoutHeaderLi").show();
}