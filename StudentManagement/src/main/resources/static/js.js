$(document).ready(function() {

    function getCookie(name) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) return parts.pop().split(';').shift();
      }
      var adminturn=(getCookie('admin'));

      $("#username").text(adminturn);
    


    $("#BTNLogin").on("click",function(){
        var a=$("#username").val();
        var b=$("#password").val();

    })

    function CloseTab(){
        $("#Container-Fluid-Student").css("display","none");
        $("#container-fluid-Dashboard").css("display","none");
        $("#Container-Fluid-Enrollment").css("display","none");
        $("#Container-Fluid-Lecture").css("display","none");
        $("#Container-Fluid-Course").css("display","none");
        $("#Container-Fluid-Class").css("display","none");
        $("#timeScheduleModal").css("display","none");
    }

    $('#ClassTab').on('click', function() {
        CloseTab();
        $("#Container-Fluid-Class").css("display","block");
        $("#classList").css("display","none");
        $(".addclasstext").css("display","none");
        $("#addClassBtn").css("display","none");
    });


    $('#ScheduleTab').on('click', function() {
        CloseTab();
        $("#timeScheduleModal").css("display","block");
    });


    $('#sidebarCollapse').on('click', function() {
        $('.sidebar').toggleClass('active');
    });



    $("#DashboardTab").on("click", function() {
        CloseTab();
        $("#container-fluid-Dashboard").css("display","block");

    });




    $("#StudentTab").on("click", function() {
        CloseTab();
        $("#Container-Fluid-Student").css("display","block");
    });





    $("#CoursesTab").on("click", function() {
        CloseTab();
        $("#Container-Fluid-Course").css("display","block");
    });






    $("#TeachersTab").on("click", function() {
        CloseTab();
        $("#Container-Fluid-Lecture").css("display","block");
    });


    $('#addLectureBtn').on('click', function() {
        $('#addLectureModal').modal('show');
        $("#BtnGenerateLecture").css("display","block");
        $("#updateLectureBtn").css("display","none");
        $("#addLectureModalLabeltext").text("Add Lecture");

    });


    $("#EnrollmentTab").on("click", function() {
        $("#enrollmentModalwarning").modal("show");
        CloseTab();
        $("#Container-Fluid-Enrollment").css("display", "block");
    });


    document.querySelector('#enrollmentModalwarning .modal-footer .btn-secondary').addEventListener('click', function() {
        $('#enrollmentModalwarning').modal('hide'); 
    });
    


    



    $("#addCourseBtn").on("click",function(){
        $("#addCourseModal").modal("show");
    })
    $("#addStudentBtn").on("click",function(){
        $("#addStudentModal").modal("show");
        $("#saveStudentBtn").css("display","block");
        $("#updateStudentBtn").css("display","none");
        $("#exampleModalLabelText").text("Add Student");
    })





    
    // Function to fetch courses from the backend
    function fetchCourses() {
        $.ajax({
            type: "GET",
            url: "/courses", 
            success: function(response) {
                $('#course').empty();
                
    
                // Loop through the courses and populate the select options
                response.forEach(function(course) {


                    var option = $('<option>', {
                        value: course.courseName, // Assuming 'courseName' is used as the value for the option
                        course_id:course.id,
                        text: course.courseName // Using 'courseName' as the text for the option
                    });
                    $('#course').append(option);
                });
    
                // Update the total course count
                $("#BoxTotallCourse").text(response.length);
    
                // Display courses
                displayCourses(response);
    
                // Populate teacher skill select element if it exists
                var selectElement = $('#teacher-skill');
                var selectElement2 = $('#student-skill');
                if (selectElement.length !== 0) {
                    selectElement.empty();
                    response.forEach(function(course) {
                        var option = $('<option>', {
                            value: course.courseName, // Assuming 'courseName' is used as the value for the option
                            text: course.courseName // Using 'courseName' as the text for the option
                        });
                        selectElement.append(option);
                    });
                }

                if (selectElement2.length !== 0) {
                    selectElement2.empty();
                    response.forEach(function(course2) {
                        var option = $('<option>', {
                            value: course2.courseName, // Assuming 'courseName' is used as the value for the option
                            text: course2.courseName // Using 'courseName' as the text for the option
                        });
                        selectElement2.append(option);
                    });
                }
            },
            error: function(xhr, status, error) {
                console.error("Error fetching courses:", error);
            }
        });
    }

    $("#saveCourseBtn").on("click", function(){
        var courseName = $("#course-title").val();
        var courseData = {
            courseName: courseName
        };
        $.ajax({
            type: "POST",
            url: "/save-course",
            contentType: "application/json",
            data: JSON.stringify(courseData),
            success: function(response) {
                Swal.fire({
                    title: "success!",
                    text: "Course '" + courseName + "' added successfully",
                    icon: "success"
                  }).then(()=>{
                    fetchCourses();
                    $("#addCourseModal").modal("hide");
                  })
            },
            error: function(xhr, status, error) {
                alert("An error occurred while saving the course: " + xhr.responseText);
            }
        });
    });
    // Function to display courses in HTML cards
    function displayCourses(courses) {
        var cardContainer = $("#course-container");
        cardContainer.empty(); // Clear existing cards

        courses.forEach(function(course) {
            var card = `
            <div class="container" id="course-container">
            <div class="card mb-3">
                <div style="display: flex;justify-content: space-between;box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;" class="card-body">
                    <h5 class="card-title" th:text="">${course.courseName}</h5>
                    <div>
                    <button class="btn btn-danger btn-sm float-end BtnCourseIDDelete" data-deleteid="${course.id}" >Delete</button>
                    <button class="btn btn-primary btn-sm float-end me-2 BtnCourseIDUpdate" data-updateid="${course.id}">Update</button>
                    </div>
                </div>
            </div>
        </div>
        `;
            cardContainer.append(card); 
        });
    }
    fetchCourses();


    $("#tabclasshave").on("click",()=>{
        $("#classList").css("display","block");
        $(".addclasstext").css("display","none");
        $("#addClassBtn").css("display","none");
    })


    $("#addclasstab").on("click",()=>{
        $("#classList").css("display","none");
        $(".addclasstext").css("display","block");
        $("#addClassBtn").css("display","block");

    })

    $("#addClassBtn").on("click",function(){
        $("#addClassModal").modal("show");
    });


    $("#saveClassBtn").on("click", function(){
        var className = $("#class-title").val();
        var classeData = {
            className: className
        };
        $.ajax({
            type: "POST",
            url: "/save-class",
            contentType: "application/json",
            data: JSON.stringify(classeData),
            success: function(response) {
                Swal.fire({
                    title: "success!",
                    text: "Class '" + className + "' added successfully",
                    icon: "success"
                  }).then(()=>{
                    fetchClasses();
                    $("#class-title").val("");
                    $("#addClassModal").modal("hide");
                  })
            },
            error: function(xhr, status, error) {
                alert("An error occurred while saving the course: " + xhr.responseText);
            }
        });
    });


    fetchClasses();


    function fetchClasses() {
        $.get("/classes", function(classes) {
            var classListDiv = $("#classList");
            var classSelect = $("#classSelect");
    
            classListDiv.empty(); 
            classSelect.empty();
    
            // Add a default option
            classSelect.append($('<option value="">-- Select Class --</option>'));
    
            classes.forEach(function(classObj) {
                var classBox = $("<div class='class-box'></div>");
    
                // Display class name
                var className = $("<p>" + classObj.className + "</p>");
                classBox.append(className);
    
                // Add update button with data-id attribute
                var updateBtn = $("<button class='update-btn' data-id='" + classObj.classId + "'>Update</button>");
                updateBtn.on("click", function() {
                    var classId = $(this).data("id");

                });
                classBox.append(updateBtn);
    
                // Add delete button with data-id attribute
                var deleteBtn = $("<button class='delete-btn' data-id='" + classObj.classId + "'>Delete</button>");
                deleteBtn.on("click", function() {
                    var classId = $(this).data("id");
                    deleteClass(classId);
                });
                classBox.append(deleteBtn);
    
                // Append the class box to the classListDiv
                classListDiv.append(classBox);
    
                // Populate the select element with class options
                classSelect.append($('<option>', {
                    class_id: classObj.classId,
                    value: classObj.classId, // Assuming 'classId' is used as the value for the option
                    text: classObj.className // Using 'className' as the text for the option
                }));
            });
        });
    }
    
    
    function deleteClass(classId) {
        $.ajax({
            url: "/delete-class/" + classId,
            type: "DELETE",
            success: function(response) {
                console.log(response);
                // Optionally, you can update the UI after successful deletion
                fetchClasses();
            },
            error: function(xhr, status, error) {
                console.error("An error occurred while deleting the class: " + xhr.responseText);
                // Handle error, display message to the user, etc.
            }
        });
    }


    $(document).on("click", ".BtnCourseIDDelete", function() {
        var courseId = $(this).data("deleteid"); 
        Swal.fire({
            title: 'Are you sure?',
            text: 'You will not be able to recover this course!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: "DELETE",
                    url: "/delete-course/" + courseId,
                    success: function(response) {
                        fetchCourses();
                    },
                    error: function(xhr, status, error) {
                        alert("An error occurred while deleting the course: " + xhr.responseText);
                    }
                });
            }
        });
    });
    

    $(document).on("click", ".BtnCourseIDUpdate", function() {
        var courseId = $(this).data("updateid"); // Get the course ID
        var currentCourseName = $(this).closest(".card-body").find(".card-title").text(); // Get the current course name
        $("#updateCourseModal").modal("show"); // Show the modal popup
        $("#updateCourseModal #courseName").val(currentCourseName); // Populate the modal with the current course name
        $("#updateCourseForm").off("submit").on("submit", function(event) {
            event.preventDefault(); // Prevent default form submission
            var updatedCourseName = $("#updateCourseModal #courseName").val(); // Get the updated course name
            $.ajax({
                type: "POST",
                url: "/update-course/" + courseId,
                contentType: "application/json", // Set the Content-Type header to application/json
                data: JSON.stringify({ courseName: updatedCourseName }), // Send the updated course name as JSON data
                success: function(response) {
                    Swal.fire({
                        title: "Success!",
                        text: "Course updated successfully!",
                        icon: "success"
                    }).then((result) => {
                        if (result.isConfirmed || result.isDismissed) {
                            fetchCourses();
                            $("#updateCourseModal").modal("hide");
                        }
                    });
                },
                error: function(xhr, status, error) {
                    alert("An error occurred while updating the course: " + xhr.responseText);
                }
            });
            
        });
    });


    function fetchStudent() {
        $.ajax({
            type: "GET",
            url: "/Student",
            success: function(response) {
                $("#BoxTotallStudent").text(response.length);
                var studentTable = $('#studentTable').DataTable();
                if (studentTable) {
                    studentTable.destroy(); 
                }
                var studentTableBody = $('#studentTable tbody');
                studentTableBody.empty(); 
                response.forEach(function(student) {
                    studentTableBody.append(`
                        <tr>
                            <td>${student.studentId}</td>
                            <td>${student.fullname}</td>
                            <td>${student.email}</td>
                            <td>${student.dob}</td>
                            <td>
                            <center>                        
                            <input BtnDeletestudentID="${student.studentId}" type="button" value="Delete" class="btn btn-danger btndeletestudent" name="" id="">
                            <input BtnUpdateStudentID="${student.studentId}" type="button" value="Update" class="btn btn-primary btnupdateStudent" name="" id="">
                            </center>
                            </td>
                        </tr>
                    `)               
                });
                $('#studentTable').DataTable({
                    searching: true, 
                    ordering: true, 
                    paging: true,  
                });
            },
            error: function(xhr, status, error) {
                console.error(xhr.responseText);
            }
        });
    }

    $(document).on("click", ".btnupdateStudent", function(){
        var studentId = $(this).attr('BtnUpdateStudentID'); 
        if (studentId) { 
            $.ajax({
                type: "GET",
                url: `/studentss/${studentId}`, 
                success: function(response) {
                    $("#saveStudentBtn").css("display","none");
                    $("#updateStudentBtn").css("display","block");
                    $("#exampleModalLabelText").text("Update Student")
                    $("#addStudentModal").modal("show");
                    $("#updateStudentBtn").attr("updatestuid",studentId)
                    $('#studentName').val(response.fullname);
                    $('#studentAddress').val(response.address);
                    $('#studentDOB').val(response.dob);
                    $('#studentEmail').val(response.email);
                    $('#studentPhoneNumber').val(response.studentPhoneNumber);
                    $('#parentName').val(response.parentName);
                    $('#parentPhoneNumber').val(response.parentPhoneNumber);
                    $('#studentPassword').val(response.password);
                    $('#studentProfile').attr('src', response.profile);
                    $('#student-skill').val(response.subject);

                },
                error: function(xhr, status, error) {
                    console.error(xhr.responseText);
                    alert("Failed to retrieve student data.");
                }
            });
        } else {
            console.error("Student ID is undefined or empty.");
        }
    });


    $(document).on("click", "#updateStudentBtn", function(){
        var studentId = $(this).attr('updatestuid'); 
        if (studentId) { 
            var updatedStudentData = {
                fullname: $('#studentName').val(),
                address: $('#studentAddress').val(),
                dob: $('#studentDOB').val(),
                email: $('#studentEmail').val(),
                parentName: $('#parentName').val(),
                parentPhoneNumber: $('#parentPhoneNumber').val(),
                password: $('#studentPassword').val(),
                profile: $('#studentProfile').val(), // Assuming profile is a file path
                studentPhoneNumber: $('#studentPhoneNumber').val(),
                subject:$("#student-skill").val()
            };

            // Make a PUT request to update the student data
            $.ajax({
                type: "PUT",
                url: `/updateStudent/${studentId}`,
                contentType: "application/json",
                data: JSON.stringify(updatedStudentData),
                success: function(response) {
                    Swal.fire({
                        title: "Success!",
                        text: "Student updated successfully!",
                        icon: "success"
                    });
                    $('#studentName').val("");
                    $('#studentAddress').val("");
                    $('#studentDOB').val("");
                    $('#studentEmail').val("");
                    $('#parentName').val("");
                    $('#parentPhoneNumber').val("");
                    $('#studentPassword').val("");
                    $('#studentProfile').val("");
                    $('#studentPhoneNumber').val("");
                    $("#addStudentModal").modal("hide");
                    $("#student-skill").val("");
                    
                    fetchStudent();
                },  
                error: function(xhr, status, error) {
                    console.error(xhr.responseText);
                    alert("Failed to update student data.");
                }
            });
        } else {
            console.error("Student ID is undefined or empty.");
        }
    });



    $(document).on("click", ".btndeletestudent", function() {
        var studentId = $(this).attr('btndeletestudentid');
        $.ajax({
            type: "DELETE",
            url: `/deleteStudent/${studentId}`,
            success: function(response) {
                Swal.fire({
                    title: "Success!",
                    text: "Your Data has deleted success",
                    icon: "success"
                })
                fetchStudent();
            },
            error: function(xhr, status, error) {
                console.error(xhr.responseText);
                alert("Failed to delete student.");
            }
        });

    });




    fetchStudent();
    $("#saveStudentBtn").click(function() {
        var formData = new FormData();
        formData.append('fullname', $("#studentName").val());
        formData.append('email', $("#studentEmail").val());
        formData.append('dob', $("#studentDOB").val());
        formData.append('address', $("#studentAddress").val());
        formData.append('studentProfile', $("#studentProfile")[0].files[0]); // Append file data
        formData.append('studentPhoneNumber', $("#studentPhoneNumber").val());
        formData.append('parentName', $("#parentName").val());
        formData.append('parentPhoneNumber', $("#parentPhoneNumber").val());
        formData.append('password', $("#studentPassword").val());
        formData.append('Subject', $("#student-skill").val());


        $.ajax({
            type: "POST",
            url: "/saveStudent",
            processData: false, // Prevent jQuery from automatically processing the FormData
            contentType: false, // Prevent jQuery from setting the Content-Type
            data: formData,
            success: function(response) {
                Swal.fire({
                    title: "Success",
                    text: "Student data Added successfully",
                    icon: "success"
                }).then(()=>{
                    $("#addStudentModal").modal("hide");
                    $("#studentName").val("");
                    $("#studentEmail").val("");
                    $("#studentDOB").val("");
                    $("#studentAddress").val("");
                    $("#studentProfile").val("");
                    $("#studentPhoneNumber").val("");
                    $("#parentName").val("");
                    $("#parentPhoneNumber").val("");
                    $("#studentPassword").val("");
                    fetchStudent();
                })
            },
            error: function(xhr, status, error) {
                console.error("Error saving student data:", error);
            }
        });
    });



    function fetchLectureData() {
        $.ajax({
            url: "/getAllLectures",
            type: "GET",
            dataType: "json",
        success: function(lectures) {
            $("#BoxTotallLecture").text(lectures.length)
        var lecturetable=$("#lectureTable").DataTable();
        if (lecturetable) {
            lecturetable.destroy(); 
        }
        var lectureTableBody=$("#lectureTable tbody");
        lectureTableBody.empty();
        lectures.forEach(function(lecture) {
        
            lectureTableBody.append(`
                <tr>
                    <td>${lecture.lecturerId}</td>
                    <td>${lecture.fullname}</td>
                    <td>${lecture.email}</td>
                    <td>${lecture.address}</td>
                    <td>${lecture.dob}</td>
                    <td>${lecture.skill}</td>
                    <td>
                        <center style="display:flex;justify-content: center;">                        
                            <input style="margin-right:10px" BtnDeletelectureID="${lecture.lecturerId}" type="button" value="Delete" class="btn btn-danger btndeletelecture" name="" id="">
                            <input BtnUpdatelectureID="${lecture.lecturerId}" type="button" value="Update" class="btn btn-primary btnupdatelecture" name="" id="">
                        </center>
                    </td>
                </tr>
            `);
        });
        $('#lectureTable').DataTable({
            searching: true, 
            ordering: true, 
            paging: true,  
        });
        
    },

            error: function(xhr, status, error) {
                console.error("Error occurred while fetching lecture data: ", error);
            }
        });
    }


    fetchLectureData();


    $("#BtnGenerateLecture").on("click", function () {
        var formData = new FormData(); // Create FormData object
        formData.append('fullname', $("#teacher-name").val());
        formData.append('email', $("#teacher-email").val());
        formData.append('address', $("#teacher-address").val());
        formData.append('dob', $("#teacher-dob").val());
        formData.append('password', $("#teacher-password").val());
        formData.append('profile', $("#teacher-profile")[0].files[0]); // Append file data
        formData.append('skill', $("#teacher-skill option:selected").text());
        
        $.ajax({
            type: "POST",
            url: "/saveLecture",
            data: formData,
            processData: false, // Prevent jQuery from processing the data
            contentType: false, // Prevent jQuery from setting contentType
            success: function (response) {
                Swal.fire({
                    title: "Success",
                    text: "Lecture data Added successfully",
                    icon: "success"
                }).then(()=>{
                    fetchLectureData();
                    $("#addLectureModal").modal("hide");
                    $("#teacher-name").val("");
                    $("#teacher-email").val("");
                    $("#teacher-address").val("");
                    $("#teacher-dob").val("");
                    $("#teacher-password").val("");
                    $("#teacher-profile").val("");
                    $("#teacher-skill").val("");
                });
            },
            error: function (xhr, status, error) {
                console.error("Error occurred: ", xhr.responseText);
            }
        });
    });




    $(document).on("click", ".btndeletelecture", function() {
        var lectureid = $(this).attr('btndeletelectureid'); 
        $.ajax({
            type: "DELETE",
            url: `/deletelecture/${lectureid}`,
            success: function(response) {
                Swal.fire({
                    title: "Success!",
                    text: "Your Data has been deleted successfully",
                    icon: "success"
                });
                fetchLectureData();
            },
            error: function(xhr, status, error) {
                console.error(xhr.responseText);
                alert("Failed to delete lecture.");
            }
        });
    });
    $(document).on("click", ".btnupdatelecture", function() {
        $('#addLectureModal').modal('show');
        $("#BtnGenerateLecture").css("display","none");
        $("#updateLectureBtn").css("display","block");
        $("#addLectureModalLabeltext").text("Update Lecture");
        var LectureUpdateID=$(this).attr("btnupdatelectureid");

        $.ajax({
            type: "GET",
            url: "/getLectureById/" + LectureUpdateID,
            success: function(response) {
                $("#updateLectureBtn").attr("IDUPDATELECTURE",LectureUpdateID)
                $("#teacher-name").val(response.fullname);
                $("#teacher-email").val(response.email);
                $("#teacher-address").val(response.address);
                $("#teacher-dob").val(response.dob); 
                $("#teacher-skill").val(response.skill).change();
                $("#teacher-password").val(response.password);
                $("#teacher-profile").attr('src',response.profile);
            },
            error: function(xhr, status, error) {

            }
        });

    });
    $("#updateLectureBtn").on("click", function() {
        var updateId = $(this).attr("IDUPDATELECTURE");


        var lectureData = {
            fullname: $("#teacher-name").val(),
            email: $("#teacher-email").val(),
            address: $("#teacher-address").val(),
            dob: $("#teacher-dob").val(),
            password: $("#teacher-password").val(),
            skill: $("#teacher-skill").val(),
        };
        
        $.ajax({
            type: "PUT",
            url: "/updateLecture/" + updateId,
            contentType: "application/json",
            data: JSON.stringify(lectureData),
            success: function(response) {
                Swal.fire({
                    title: "Success!",
                    text: "Your Data has been Updated successfully",
                    icon: "success"
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Reset the form fields to their initial state
                        $("#teacher-name").val("");
                        $("#teacher-email").val("");
                        $("#teacher-address").val("");
                        $("#teacher-dob").val("");
                        $("#teacher-password").val("");
                        $("#teacher-skill").val(""); // Assuming you want to clear the selected skill
                        
                        // Optionally, close the modal or perform any other actions
                        $('#addLectureModal').modal('hide');
                    }
                });
                fetchLectureData();
            },
            error: function(xhr, status, error) {
                console.error("Failed to update lecture:", xhr.responseText);
            }
        });
    });

    $("#LogoutTab").on("click", () => {
        Swal.fire({
            title: 'Are you certain you desire to log out?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, logout'
        }).then((result) => {
            // If user confirms, delete cookie and redirect
            if (result.isConfirmed) {
                document.cookie = "admin=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/";
                window.location.href = "index.html";
            }
        });
     })

     $("#addScheduleBtn").on("click", function() {
        $("#timeScheduleModals").modal("show");
        $("#UpdateTime").css("display","none");
        $("#saveStudyScheduleBtn").css("display","block");


    });

     $("#saveStudyScheduleBtn").on("click", function() {
        var dayOfWeek = $("#dayOfWeek").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var studyScheduleData = {
            dayOfWeek: dayOfWeek,
            startTime: startTime,
            endTime: endTime
        };
        $.ajax({
            type: "POST",
            url: "/save-study-schedule",
            contentType: "application/json",
            data: JSON.stringify(studyScheduleData),
            success: function(response) {
                Swal.fire({
                    title: "Success!",
                    text: "Study schedule saved successfully!",
                    icon: "success",
                    confirmButtonText: "OK"
                }).then((result) => {
                    // Clear input fields upon confirmation
                    if (result.isConfirmed) {
                        $("#dayOfWeek").val("");
                        $("#startTime").val("");
                        $("#endTime").val("");
                        $("#timeScheduleModals").modal("hide");
                        fetchStudySchedules();
                    }
                });
            },
            error: function(xhr, status, error) {
                alert("An error occurred while saving study schedule: " + xhr.responseText);
            }
        });
    });


    function fetchStudySchedules() {
        $.ajax({
            type: "GET",
            url: "/get-study-schedules", 
            success: function(response) {


                var timeSelect = $("#timeEnrollment");                
                timeSelect.empty();
                response.forEach(function(item) {
                    var optionText = item.dayOfWeek + " - " + item.startTime + " to " + item.endTime;
                    var optionValue = item.time;
                    timeSelect.append($('<option></option>').attr('datatimeid',item.timeId).attr('value', optionValue).text(optionText));
                });

                displayStudySchedules(response); 
            },
            error: function(xhr, status, error) {
                alert("An error occurred while fetching study schedules: " + xhr.responseText);
            }
        });
    }
    function displayStudySchedules(schedules) {

        
        var tableBody = $("#studyScheduleTableBody");
        tableBody.empty();
    
        schedules.forEach(function(schedule) {
            var row = $("<tr>");
            row.append("<td>" + schedule.dayOfWeek + "</td>");
            row.append("<td>" + schedule.startTime + "</td>");
            row.append("<td>" + schedule.endTime + "</td>");
        var actionColumn = $("<td>");
        var updateButton = $('<button dataid style="margin-right: 5px;"">').text("Update").attr("data-id", schedule.timeId).addClass("btn btn-primary btn-sm btnupdatetime").click(function() {
            handleUpdate(schedule);
        });
        var deleteButton = $("<button >").text("Delete").addClass("btn btn-danger btn-sm").click(function() {
            handleDelete(schedule);
        });
        actionColumn.append(updateButton);
        actionColumn.append(deleteButton);
        row.append(actionColumn);
            tableBody.append(row);
        });
    }

    fetchStudySchedules();

function handleDelete(schedule) {
    Swal.fire({
        title: 'Are you sure?',
        text: 'You will not be able to recover this study schedule!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: 'DELETE',
                url: '/delete-study-schedule/' + schedule.timeId,
                success: function(response) {
                    $("#scheduleRow_" + schedule.timeId).remove();
                    Swal.fire('Deleted!', 'Study schedule has been deleted.', 'success');
                    fetchStudySchedules();
                },
                error: function(xhr, status, error) {
                    Swal.fire('Error!', 'An error occurred while deleting the study schedule: ' + error, 'error');
                }
            });
        }
    });
}


function handleUpdate(schedule) {
    $("#dayOfWeek").val(schedule.dayOfWeek);
    $("#startTime").val(schedule.startTime);
    $("#endTime").val(schedule.endTime);

    $("#timeScheduleModals").modal("show");
    
    $("#UpdateTime").css("display","block");
    $("#saveStudyScheduleBtn").css("display","none");
    $("#UpdateTime").attr("timeIdUpdate", schedule.timeId);

}



$(document).on("click", "#UpdateTime", function() {
  
    var updatedDayOfWeek = $("#dayOfWeek").val();
    var updatedStartTime = $("#startTime").val();
    var updatedEndTime = $("#endTime").val();


    var timeId = $(this).attr("timeidupdate");


    if (timeId === undefined || timeId === null) {
        console.error("Error: Unable to retrieve timeId.");
        return;
    }


    var updatedSchedule = {
        dayOfWeek: updatedDayOfWeek,
        startTime: updatedStartTime,
        endTime: updatedEndTime
    };


    $.ajax({
        type: "PUT",
        url: "/update-study-schedule/" + timeId,
        contentType: "application/json",
        data: JSON.stringify(updatedSchedule),
        success: function(response) {
            Swal.fire({
                title: "Success!",
                text: response,
                icon: "success",
            }).then(() => {
                $("#dayOfWeek").val("");
                $("#startTime").val("");
                $("#endTime").val("");
                $("#timeScheduleModals").modal("hide");
                fetchStudySchedules();
            });
        },
        error: function(xhr, status, error) {

        }
    });
});





$('#course').change(function() {
    var selectedSubject = $(this).val();
    $.ajax({
        type: "GET",
        url: `/studentsBySubject/${selectedSubject}`,
        success: function(response) {
   
            $('#studentSelect').empty(); 
            if (response.length > 0) {
                response.forEach(function(student) {
                    $('#studentSelect').append(`<option value="${student.studentId}">${student.fullname}</option>`);
                });
            } else {
                $('#studentSelect').append(`<option value="">No students found for the selected subject</option>`);
            }
        },
        error: function(xhr, status, error) {
        }
    });


    $.ajax({
        type: "GET",
        url: `/lecturesBySkill/${selectedSubject}`,
        success: function(response) {
            $('#lecturerSelect').empty(); 
            if (response.length > 0) {
                response.forEach(function(lecture) {
                    $('#lecturerSelect').append(`<option value="${lecture.lecturerId}">${lecture.fullname}</option>`);
                });
            } else {
                $('#lecturerSelect').append(`<option value="">No lectures found for the selected skill</option>`);
            }
        },
        error: function(xhr, status, error) {
            alert("Failed to fetch lectures by skill.");
        }
    });

});

$("#btn-enrollment").on("click", (event) => {
    event.preventDefault();
    
    const classId = $("#classSelect").val();
    const startDate = $("#startDate").val();
    const endDate = $("#endDate").val();
    const courseId = parseInt($("#course option:selected").attr("course_id"));
    const timeId = $("#timeEnrollment option:selected").attr("datatimeid");
    const studentId = $("#studentSelect").val();
    const lecturerId = $("#lecturerSelect").val();

    const enrollmentData = {
        myClass: { classId: classId },
        startDate: startDate,
        endDate: endDate,
        course: { id: courseId },
        studyTime: { timeId: timeId },
        student: { studentId: studentId },
        lecturer: { lecturerId: lecturerId }
    };
    

    console.log(enrollmentData);

    $.ajax({
        url: '/enrollments',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(enrollmentData),
        success: function (response) {
            console.log('Enrollment successful:', response);
        },
        error: function (xhr, status, error) {
            console.error('Error occurred while enrolling:', xhr.responseText.trim());
            // Extract error message from xhr object
            var errorMessage = xhr.responseText.trim();
            if (errorMessage === '') {
                errorMessage = 'Unknown error occurred';
            }
            // Display error message to the user
            alert('Error occurred while enrolling: ' + errorMessage);
        }
    });
});





})
