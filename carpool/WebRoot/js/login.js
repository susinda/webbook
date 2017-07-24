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
  
  $("#btnTopRightLogin").html(profile.getEmail());
  
}




function fbLogout() {
	console.log('function fbLogout()');
	
	var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('google User signed out.');
    });
	
}



   
var reg_url = "https://carpool.lkauto.org/webbook.api/user/service/register";
var version_url = "https://carpool.lkauto.org/webbook.api/user/service/version";
var loc_url = "https://carpool.lkauto.org/webbook.api/user/service/location";
var login_url = "https://carpool.lkauto.org/webbook.api/user/service/login";

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


var reg_url = "https://carpool.lkauto.org/webbook.api/user/service/register";
function httpPOST(body, url) {
        var http = new XMLHttpRequest();
        http.open("POST", url, true);
        http.setRequestHeader("Content-type", "application/json");

        http.onreadystatechange = function() { 
            if(http.readyState == 4 && http.status == 200) {
                console.log("http POST success");
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
   