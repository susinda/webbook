var startApp = function() {
    gapi.load('auth2', function(){
        // Retrieve the singleton for the GoogleAuth library and set up the client.
        auth2 = gapi.auth2.init({
        client_id: '264767066704-r9odamulampqqsjj4a2egh3hm5u8vc26.apps.googleusercontent.com',
        cookiepolicy: 'single_host_origin',
        // Request scopes in addition to 'profile' and 'email'
        //scope: 'additional_scope'
      });
      attachSignin(document.getElementById('customGoogleBtn'));
    });
  };

  function attachSignin(element) {
    console.log(element.id);
    auth2.attachClickHandler(element, {},
        function(googleUser) {
         onSignIn(googleUser);
        }, function(error) {
          alert(JSON.stringify(error, undefined, 2));
        });
  }

function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
  
  $("#loginLink").html(profile.getEmail());
}

function fb_login() {
    FB.login( function() {}, { scope: 'email,public_profile' } );
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
      testAPI();
      var uid = response.authResponse.userID;
      var accessToken = response.authResponse.accessToken;
     
      //window.location = "https://carpool.lkauto.org/carpool/login.html";
    } else {
      // The person is not logged into your app or we are unable to tell.
    	$("#btnTopRightLogin").html("Login ");
    }
  }

function checkLoginState() {
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
   
function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
    	console.log('Welcome! loginLink .... ');
    	 $("#loginLink").html(response.name);
    });
  }
   


var reg_url = "https://carpool.lkauto.org/webbook.api-1.0/user/service/register";
var version_url = "https://carpool.lkauto.org/webbook.api-1.0/user/service/version";
var loc_url = "https://carpool.lkauto.org/webbook.api-1.0/user/service/location";
var login_url = "https://carpool.lkauto.org/webbook.api-1.0/user/service/login";

window.onload = function WindowLoad(event) {
 httpGET("name", "email", version_url);
 startApp();
}

function httpGET(name, email, endpoint) {
        var http = new XMLHttpRequest();
        var url = endpoint;
        var params = "name=" + name + "&email=" + email;
        http.open("GET", url, true);

        //Send the proper header information along with the request
        http.setRequestHeader("Content-type", "application/json");
        
        http.onreadystatechange = function() {//Call a function when the state changes.
        
            if(http.readyState == 4 && http.status == 200) {
                var resultJson = JSON.parse(http.responseText);
                document.getElementById("lblVersion").innerHTML = resultJson.Version;
            }
        }
        http.send(params);
}

function httpPOST(body, url) {
        var http = new XMLHttpRequest();
        http.open("POST", url, true);
        http.setRequestHeader("Content-type", "application/json");

        http.onreadystatechange = function() { 
            if(http.readyState == 4 && http.status == 200) {
                console.log("http POST success");
                alert(http.responseText);
                //document.getElementById("lblResult").innerHTML = "User registration successful " + http.responseText;
                
                $("loginLink").html("profile.getEmail()");
            }
        }
        http.send(body);
}


function  registerClick() {
        var name = document.getElementById("txtFName").value;
        var email = document.getElementById("txtEmail").value;
        var pwd1 = document.getElementById("txtPass1").value;
        var pwd2 = document.getElementById("txtPass2").value;

        if (pwd1 != pwd2) {
             alert ("Passwors does not mach");
             return;
        } else {
             var body  = {};
             body.Name = name;
	         body.Email = email;
	         body.Password = pwd1;      
	         body = JSON.stringify(body);
	         httpPOST(body, reg_url);
}
}

function loginClick()  {
        
        var email = document.getElementById("txtEmailX").value;
        var pwd1 = document.getElementById("txtPass1X").value;
        var body2  = {};
        body2.Email = email;
        body2.Password = pwd1;      
        body2 = JSON.stringify(body2);
        httpPOST(body2, login_url);
}


/* document.getElementById("btnUpdateLocation").addEventListener("click", function () {
        var body1 = {};
        body1.Phone = "0716049075";
        body1.Lat = "6.678678";
        body1.Lon = "79.909091";
        body1 = JSON.stringify(body1)
        httpPOST(body1, loc_url);
}); */
   