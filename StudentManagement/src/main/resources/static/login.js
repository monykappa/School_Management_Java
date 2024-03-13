$(document).ready(function() {
    
    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
      }
      var adminturn=(getCookie('admin'));
      var lectureturn=(getCookie('lecture'));
      var studentturn=(getCookie('student'));
    

    function getCookies(value) {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            const pair = cookie.split('=');
            if (pair[1] === value) {
                return pair[0];
            }
        }
        return null;
    }
    console.log(getCookies(adminturn));
    if(getCookies(adminturn)==="admin")
    {
        window.location.href = "admin.html";
    }
    if(getCookies(lectureturn)==="lecture")
    {
        window.location.href = "lecture.html";
    }
    if(getCookies(studentturn)==="student")
    {
        window.location.href = "student.html";
    }
    




    $("#togglePasswordsee").on("click", () => {
        $("#password").attr("type", "text");
        $("#togglePasswordclose").css("display","block");
        $("#togglePasswordsee").css("display","none");
    });
    $("#togglePasswordclose").on("click",()=>{
        $("#password").attr("type", "password");
        $("#togglePasswordclose").css("display","none");
        $("#togglePasswordsee").css("display","block");
    })
    $("#loginForm").submit(function(event) {
        event.preventDefault(); 
    
        var formData = {
            username: $("#username").val(),
            password: $("#password").val()
        };
    
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/login",
            data: JSON.stringify(formData),
            success: function(data, textStatus, xhr) {
                if (data === "admin") {
                    
                    document.cookie = `admin=${formData.username}; path=/`; 
                    window.location.href = "admin.html";
                } else if (data === "student") {
                  
                    document.cookie = `student=${formData.username}; path=/`; 
                    window.location.href = "student.html";
                } else if (data === "lecture") {
                   
                    document.cookie = `lecture=${formData.username}; path=/`; 
                    window.location.href = "lecture.html";
                } else {
                    console.error("Invalid user type");
                    Swal.fire({
                        title: "Error!",
                        text: "Invalid user type",
                        icon: "error"
                    });
                    return; 
                }           
            },
            error: function(xhr, status, error) {
                console.error("Login failed. Error: " + xhr.responseText);
                Swal.fire({
                    title: "Error!",
                    text: "Login failed. Please try again later.",
                    icon: "error"
                });
            }
        });
        
    });
    
    

});
