$(document).ready(function () {




    // Function to get lecture name from cookie
    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
    }

    // Retrieve lecture name from cookie and display it
    var lectureName = getCookie('lecture');
    $("#username").text(lectureName);
    $("#lectureName").text(lectureName);
    $("#lecturerName").val(lectureName);


    // var lectureProfile = getCookie('Profile');
    // $("#lectureProfile").attr("src", "/img/lectures/" + lectureProfile);

    // Handle logout button click
    $("#Logoutbotton").on("click", () => {
        Swal.fire({
            title: 'Are you sure you want to log out?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, logout'
        }).then((result) => {
            // If user confirms, delete cookie and redirect
            if (result.isConfirmed) {
                document.cookie = "lecture=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/";
                window.location.href = "index.html";
            }
        });
    });


    function closeAllTabs() {
        $("#DashboardContain, #Check-In-Contain, #Check-out-Contain, #Request-Permission-Contain , #classcontain").css("display", "none");
    }
    
    $('#ClassTab').on('click', function () {
        closeAllTabs();
        $("#classcontain").css("display", "block");
    });

    $('#DashboardTab').on('click', function () {
        closeAllTabs();
        $("#DashboardContain").css("display", "block");
    });


    $('#Check-In-Tab').on('click', function () {
        closeAllTabs();
        $("#Check-In-Contain").css("display", "block");
    });


    $('#Check-out-Tab').on('click', function () {
        closeAllTabs();
        $("#Check-out-Contain").css("display", "block");
    });


    $('#Request-Permission-Tab').on('click', function () {
        closeAllTabs();
        $("#Request-Permission-Contain").css("display", "block");



    });


    function retrieveLectureProfileByUsername() {
        var fullname = $("#username").text(); // Assuming you have an element with id "username" where you display the fullname
        
        $.ajax({
            type: "GET",
            url: "/getLectureByFullname/" + fullname,
            success: function(response) {
                $("#lectureProfile").attr("src", "/img/lectures/" + response.profile);
            },
            error: function(xhr, status, error) {
                console.error("Error retrieving lecture profile:", error);
                // Handle error, such as displaying an error message to the user
            }
        });
    }
    
    // Call the function to retrieve the lecture profile by username
    retrieveLectureProfileByUsername();
    
 

});
