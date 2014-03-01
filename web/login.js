/**
 * @author :Kamlesh
 */
function formFocus(){
    document.getElementById("UserName").focus();
}
function SetCookie(name, value){
    document.cookie = name + "=" + value + ";path=/;";
}

function getCookie(c_name){
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1) 
                c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
}
function checkCookie(){
    var u = getCookie('username');
    if (!(u == null || u == "")) {
        window.top.location.href = "./";
    }
}

function sendRequest(url,u,p){
    var xmlhttp;
    if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){
            var s =xmlhttp.responseText;
            var obj =eval('('+s+')');
            if(obj.valid){
                SetCookie("zpv", obj.zpv);
                SetCookie("zrole", obj.userrole);
                var load = document.getElementById('loading-div');
                var lm = document.getElementById('loading-mask-div');
                load.style.visibility="visible";
                lm.style.visibility="visible";
                 window.top.location.href = "./";
                load.style.visibility="hidden";
            }
            else{
                showErrorMsg(true);
                //load.style.visibility="visible";
                //lm.style.visibility="visible";
            }

        //            document.getElementById("second_lower1_1").innerHTML=s;
                    
        }
    }
    xmlhttp.open("POST",url,true);
     var param ='u='+u+'&p='+p;
    xmlhttp.setRequestHeader("Content-length", param.length);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send(param);

}
function validatelogin(){
    var _u = document.getElementById("UserName");
    var _p = document.getElementById("Password");
   
    var t= isEmpty(_u,_p);
    if(t){
       showErrorMsg(t);
    }else{
        sendRequest("user/verifyUser.do",_u.value,_p.value);
    }
    showErrorMsg(t);
    
}
function showErrorMsg(t){
    setVisibility(t, document.getElementById("errmsg"));
    setVisibility(t, document.getElementById("errmsg1"));
    var _m = document.getElementById("validateMsg");
    _m.innerHTML="Invalid User Name or Password";
    setVisibility(t, _m);
}

function isEmpty(u,p){
    if(u.value=="" || p.value=="")
        return true;
    else
        return false;
}
function setVisibility(v, el){
    el.style.visibility = (v ? 'visible' : 'hidden');
}
