// ==========================================
// HR INTERVIEW PORTAL - MAIN JAVASCRIPT
// ==========================================

const API_BASE_URL = "http://localhost:8080";


// ==========================================
// REGISTER
// ==========================================

const registerForm =
    document.getElementById("registerForm");

if (registerForm) {

    registerForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();

            const name =
                document.getElementById("fullName").value.trim();

            const email =
                document.getElementById("email").value.trim();

            const password =
                document.getElementById("password").value.trim();

            const role =
                document.getElementById("role").value;

            const userData = {
                name: name,
                email: email,
                password: password,
                role: role
            };

            try {

                const response =
                    await fetch(
                        `${API_BASE_URL}/api/users/register`,
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(userData)
                        }
                    );

                if (!response.ok) {

                    const errorText =
                        await response.text();

                    throw new Error(errorText);
                }

                alert(
                    "Registration successful!"
                );

                window.location.href =
                    "login.html";

            } catch (error) {

                console.error(
                    "Registration error:",
                    error
                );

                alert(
                    "Registration failed.\n\n" +
                    "Please make sure the backend is running."
                );

            }

        }
    );

}


// ==========================================
// LOGIN
// ==========================================

const loginForm =
    document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();

            const email =
                document.getElementById("email").value.trim();

            const password =
                document.getElementById("password").value.trim();

            try {

                const response =
                    await fetch(
                        `${API_BASE_URL}/api/users/login`,
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify({
                                    email: email,
                                    password: password
                                })
                        }
                    );

                if (!response.ok) {

                    const errorText =
                        await response.text();

                    throw new Error(errorText);
                }

                const user =
                    await response.json();

                console.log(
                    "Logged in user:",
                    user
                );

                localStorage.setItem(
                    "loggedInUser",
                    JSON.stringify(user)
                );


                if (
                    user.role === "HR" ||
                    user.role === "RECRUITER"
                ) {

                    window.location.href =
                        "hr-dashboard.html";

                } else {

                    window.location.href =
                        "dashboard.html";

                }

            } catch (error) {

                console.error(
                    "Login error:",
                    error
                );

                alert(
                    "Invalid email or password."
                );

            }

        }
    );

}


// ==========================================
// LOGOUT
// ==========================================

const logoutBtn =
    document.getElementById("logoutBtn");

if (logoutBtn) {

    logoutBtn.addEventListener(
        "click",
        function (event) {

            event.preventDefault();

            localStorage.removeItem(
                "loggedInUser"
            );

            window.location.href =
                "../index.html";

        }
    );

}


// ==========================================
// CANDIDATE DASHBOARD USER INFORMATION
// ==========================================

const userNameElement =
    document.getElementById("userName");

const profileNameElement =
    document.getElementById("profileName");

const profileEmailElement =
    document.getElementById("profileEmail");

const profileRoleElement =
    document.getElementById("profileRole");


const storedUser =
    localStorage.getItem("loggedInUser");


if (storedUser) {

    try {

        const user =
            JSON.parse(storedUser);


        if (userNameElement) {

            userNameElement.textContent =
                user.name || "Candidate";

        }


        if (profileNameElement) {

            profileNameElement.textContent =
                user.name || "-";

        }


        if (profileEmailElement) {

            profileEmailElement.textContent =
                user.email || "-";

        }


        if (profileRoleElement) {

            profileRoleElement.textContent =
                user.role || "-";

        }

    } catch (error) {

        console.error(
            "User information error:",
            error
        );

    }

}


// ==========================================
// CANDIDATE - VIEW JOBS
// ==========================================

const viewJobsBtn =
    document.getElementById("viewJobsBtn");

if (viewJobsBtn) {

    viewJobsBtn.addEventListener(
        "click",
        function () {

            window.location.href =
                "jobs.html";

        }
    );

}


// ==========================================
// LOAD JOB COUNT
// ==========================================

const jobCountElement =
    document.getElementById("jobCount");


async function loadJobCount() {

    if (!jobCountElement) {
        return;
    }

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/jobs`
            );

        if (!response.ok) {
            throw new Error(
                "Failed to load jobs."
            );
        }

        const jobs =
            await response.json();

        jobCountElement.textContent =
            jobs.length;

    } catch (error) {

        console.error(
            "Job count error:",
            error
        );

        jobCountElement.textContent =
            "0";

    }

}

loadJobCount();


// ==========================================
// JOBS PAGE
// ==========================================

const jobsContainer =
    document.getElementById("jobsContainer");


async function loadJobs() {

    if (!jobsContainer) {
        return;
    }

    jobsContainer.innerHTML = `
        <p>
            Loading jobs...
        </p>
    `;

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/jobs`
            );

        if (!response.ok) {

            throw new Error(
                "Failed to load jobs."
            );

        }

        const jobs =
            await response.json();


        if (jobs.length === 0) {

            jobsContainer.innerHTML = `
                <div class="no-jobs">
                    <p>
                        No jobs are currently available.
                    </p>
                </div>
            `;

            return;

        }


        jobsContainer.innerHTML = "";


        jobs.forEach(
            function (job) {

                const jobCard =
                    document.createElement(
                        "div"
                    );


                jobCard.className =
                    "job-card";


                jobCard.innerHTML = `

                    <h2>
                        ${job.title}
                    </h2>

                    <p class="job-description">
                        ${job.description}
                    </p>

                    <p>
                        <strong>
                            Requirements:
                        </strong>

                        ${job.requirements}
                    </p>

                    <p>
                        <strong>
                            Job ID:
                        </strong>

                        ${job.id}
                    </p>

                    <button
                        class="dashboard-btn"
                        type="button"
                        onclick="applyForJob(${job.id})">

                        Apply Now

                    </button>

                `;


                jobsContainer.appendChild(
                    jobCard
                );

            }
        );

    } catch (error) {

        console.error(
            "Jobs loading error:",
            error
        );

        jobsContainer.innerHTML = `
            <div class="no-jobs">

                <p>
                    Unable to load jobs.
                    Please make sure the backend
                    is running.
                </p>

            </div>
        `;

    }

}


loadJobs();


// ==========================================
// APPLY FOR JOB
// ==========================================

function applyForJob(jobId) {

    localStorage.setItem(
        "selectedJobId",
        jobId
    );

    window.location.href =
        "apply.html";

}


// ==========================================
// APPLICATION FORM
// ==========================================

const applicationForm =
    document.getElementById(
        "applicationForm"
    );


if (applicationForm) {

    applicationForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const fullName =
                document.getElementById(
                    "fullName"
                ).value.trim();


            const email =
                document.getElementById(
                    "email"
                ).value.trim();


            const phone =
                document.getElementById(
                    "phone"
                ).value.trim();


            const experience =
                document.getElementById(
                    "experience"
                ).value;


            const coverLetter =
                document.getElementById(
                    "coverLetter"
                ).value.trim();


            const storedUser =
                localStorage.getItem(
                    "loggedInUser"
                );


            if (!storedUser) {

                alert(
                    "Please login first."
                );

                window.location.href =
                    "login.html";

                return;

            }


            let user;

            try {

                user =
                    JSON.parse(
                        storedUser
                    );

            } catch (error) {

                alert(
                    "Invalid login information."
                );

                return;

            }


            const selectedJobId =
                localStorage.getItem(
                    "selectedJobId"
                );


            if (!selectedJobId) {

                alert(
                    "No job selected."
                );

                return;

            }


            const applicationData = {

                jobId:
                    Number(selectedJobId),

                userId:
                    user.id,

                fullName:
                    fullName,

                email:
                    email,

                phone:
                    phone,

                experience:
                    experience,

                coverLetter:
                    coverLetter,

                status:
                    "PENDING"

            };


            try {

                const response =
                    await fetch(
                        `${API_BASE_URL}/api/applications`,
                        {

                            method:
                                "POST",

                            headers: {

                                "Content-Type":
                                    "application/json"

                            },

                            body:
                                JSON.stringify(
                                    applicationData
                                )

                        }
                    );


                if (!response.ok) {

                    const errorText =
                        await response.text();

                    throw new Error(
                        errorText
                    );

                }


                const application =
                    await response.json();


                console.log(
                    "Application created:",
                    application
                );


                alert(
                    "Application submitted successfully!"
                );


                localStorage.removeItem(
                    "selectedJobId"
                );


                applicationForm.reset();


                window.location.href =
                    "dashboard.html";


            } catch (error) {

                console.error(
                    "Application error:",
                    error
                );


                alert(
                    "Unable to submit application.\n\n" +
                    "Please make sure the backend is running."
                );

            }

        }
    );

}


// ==========================================
// CANDIDATE - VIEW MY APPLICATIONS
// ==========================================

const candidateViewApplicationsBtn =
    document.getElementById(
        "viewApplicationsBtn"
    );


const myApplicationsSection =
    document.getElementById(
        "myApplicationsSection"
    );


const myApplicationsContainer =
    document.getElementById(
        "myApplicationsContainer"
    );


if (candidateViewApplicationsBtn) {

    candidateViewApplicationsBtn.addEventListener(
        "click",
        function () {

            console.log(
                "Candidate View Applications clicked"
            );


            if (myApplicationsSection) {

                myApplicationsSection.scrollIntoView({
                    behavior: "smooth"
                });

            }


            loadMyApplications();

        }
    );

}


// ==========================================
// LOAD MY APPLICATIONS
// ==========================================

async function loadMyApplications() {

    if (!myApplicationsContainer) {
        return;
    }


    myApplicationsContainer.innerHTML = `
        <p>
            Loading your applications...
        </p>
    `;


    const storedUser =
        localStorage.getItem(
            "loggedInUser"
        );


    if (!storedUser) {

        myApplicationsContainer.innerHTML = `
            <div class="no-jobs">

                <p>
                    Please login first.
                </p>

            </div>
        `;

        return;

    }


    let user;


    try {

        user =
            JSON.parse(
                storedUser
            );

    } catch (error) {

        console.error(
            "User data error:",
            error
        );


        myApplicationsContainer.innerHTML = `
            <div class="no-jobs">

                <p>
                    Invalid login information.
                </p>

            </div>
        `;

        return;

    }


    if (!user.id) {

        myApplicationsContainer.innerHTML = `
            <div class="no-jobs">

                <p>
                    User ID is missing.
                    Please login again.
                </p>

            </div>
        `;

        return;

    }


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/applications`
            );


        if (!response.ok) {

            throw new Error(
                "Failed to load applications."
            );

        }


        const applications =
            await response.json();


        console.log(
            "All applications:",
            applications
        );


        const myApplications =
            applications.filter(
                function (application) {

                    return String(
                        application.userId
                    ) === String(
                        user.id
                    );

                }
            );


        console.log(
            "My applications:",
            myApplications
        );


        if (myApplications.length === 0) {

            myApplicationsContainer.innerHTML = `
                <div class="no-jobs">

                    <p>
                        You have not submitted
                        any applications yet.
                    </p>

                </div>
            `;

            return;

        }


        myApplicationsContainer.innerHTML =
            "";


        myApplications.forEach(
            function (application) {

                const applicationCard =
                    document.createElement(
                        "div"
                    );


                applicationCard.className =
                    "job-card";


                const currentStatus =
                    application.status ||
                    "PENDING";


                applicationCard.innerHTML = `

                    <h2>
                        Application #${application.id}
                    </h2>


                    <p>
                        <strong>
                            Job ID:
                        </strong>

                        ${application.jobId}

                    </p>


                    <p>
                        <strong>
                            Name:
                        </strong>

                        ${application.fullName}

                    </p>


                    <p>
                        <strong>
                            Email:
                        </strong>

                        ${application.email}

                    </p>


                    <p>
                        <strong>
                            Phone:
                        </strong>

                        ${application.phone}

                    </p>


                    <p>
                        <strong>
                            Experience:
                        </strong>

                        ${application.experience}

                    </p>


                    <p>
                        <strong>
                            Cover Letter:
                        </strong>
                    </p>


                    <p class="job-description">

                        ${
                            application.coverLetter ||
                            "No cover letter provided."
                        }

                    </p>


                    <p>

                        <strong>
                            Application Status:
                        </strong>

                        <span
                            style="
                                font-weight: 700;
                                margin-left: 8px;
                            "
                        >

                            ${currentStatus}

                        </span>

                    </p>

                `;


                myApplicationsContainer.appendChild(
                    applicationCard
                );

            }
        );


    } catch (error) {

        console.error(
            "My applications error:",
            error
        );


        myApplicationsContainer.innerHTML = `
            <div class="no-jobs">

                <p>
                    Unable to load your applications.
                    Please make sure the backend
                    is running.
                </p>

            </div>
        `;

    }

}


// ==========================================
// HR DASHBOARD USER INFORMATION
// ==========================================

const hrUserNameElement =
    document.getElementById(
        "hrUserName"
    );


const hrProfileNameElement =
    document.getElementById(
        "hrProfileName"
    );


const hrProfileEmailElement =
    document.getElementById(
        "hrProfileEmail"
    );


const hrProfileRoleElement =
    document.getElementById(
        "hrProfileRole"
    );


if (storedUser) {

    try {

        const user =
            JSON.parse(
                storedUser
            );


        if (hrUserNameElement) {

            hrUserNameElement.textContent =
                user.name || "HR";

        }


        if (hrProfileNameElement) {

            hrProfileNameElement.textContent =
                user.name || "-";

        }


        if (hrProfileEmailElement) {

            hrProfileEmailElement.textContent =
                user.email || "-";

        }


        if (hrProfileRoleElement) {

            hrProfileRoleElement.textContent =
                user.role || "-";

        }

    } catch (error) {

        console.error(
            "HR user information error:",
            error
        );

    }

}


// ==========================================
// HR - CREATE JOB
// ==========================================

const jobForm =
    document.getElementById(
        "jobForm"
    );


const showJobFormBtn =
    document.getElementById(
        "showJobFormBtn"
    );


const jobFormSection =
    document.getElementById(
        "jobFormSection"
    );


if (showJobFormBtn) {

    showJobFormBtn.addEventListener(
        "click",
        function () {

            if (jobFormSection) {

                jobFormSection.style.display =
                    "block";


                jobFormSection.scrollIntoView({
                    behavior: "smooth"
                });

            }

        }
    );

}


if (jobForm) {

    jobForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const title =
                document.getElementById(
                    "jobTitle"
                ).value.trim();


            const description =
                document.getElementById(
                    "jobDescription"
                ).value.trim();


            const requirements =
                document.getElementById(
                    "jobRequirements"
                ).value.trim();


            const storedUser =
                localStorage.getItem(
                    "loggedInUser"
                );


            if (!storedUser) {

                alert(
                    "Please login as HR first."
                );

                window.location.href =
                    "login.html";

                return;

            }


            let user;


            try {

                user =
                    JSON.parse(
                        storedUser
                    );

            } catch (error) {

                console.error(
                    "User data error:",
                    error
                );

                alert(
                    "Invalid login information."
                );

                return;

            }


            if (!user.id) {

                alert(
                    "HR user ID is missing. " +
                    "Please login again."
                );

                return;

            }


            const jobData = {

                title:
                    title,

                description:
                    description,

                requirements:
                    requirements,

                createdBy:
                    user.id

            };


            try {

                const response =
                    await fetch(
                        `${API_BASE_URL}/api/jobs`,
                        {

                            method:
                                "POST",

                            headers: {

                                "Content-Type":
                                    "application/json"

                            },

                            body:
                                JSON.stringify(
                                    jobData
                                )

                        }
                    );


                if (!response.ok) {

                    const errorText =
                        await response.text();

                    throw new Error(
                        errorText
                    );

                }


                const createdJob =
                    await response.json();


                console.log(
                    "Created job:",
                    createdJob
                );


                alert(
                    "Job posted successfully!"
                );


                jobForm.reset();


                if (jobFormSection) {

                    jobFormSection.style.display =
                        "none";

                }


                loadHRJobs();


            } catch (error) {

                console.error(
                    "Create job error:",
                    error
                );


                alert(
                    "Unable to create job.\n\n" +
                    "Please make sure the Spring Boot " +
                    "backend is running."
                );

            }

        }
    );

}


// ==========================================
// HR - VIEW POSTED JOBS
// ==========================================

const viewPostedJobsBtn =
    document.getElementById(
        "viewPostedJobsBtn"
    );


const postedJobsSection =
    document.getElementById(
        "postedJobsSection"
    );


if (viewPostedJobsBtn) {

    viewPostedJobsBtn.addEventListener(
        "click",
        function () {

            if (postedJobsSection) {

                postedJobsSection.scrollIntoView({
                    behavior: "smooth"
                });

            }


            loadHRJobs();

        }
    );

}


// ==========================================
// LOAD HR JOBS
// ==========================================

const hrJobsContainer =
    document.getElementById(
        "hrJobsContainer"
    );


async function loadHRJobs() {

    if (!hrJobsContainer) {
        return;
    }


    hrJobsContainer.innerHTML = `
        <p>
            Loading jobs...
        </p>
    `;


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/jobs`
            );


        if (!response.ok) {

            throw new Error(
                "Failed to load jobs."
            );

        }


        const jobs =
            await response.json();


        if (jobs.length === 0) {

            hrJobsContainer.innerHTML = `
                <div class="no-jobs">

                    <p>
                        No jobs have been posted yet.
                    </p>

                </div>
            `;

            return;

        }


        hrJobsContainer.innerHTML =
            "";


        jobs.forEach(
            function (job) {

                const jobCard =
                    document.createElement(
                        "div"
                    );


                jobCard.className =
                    "job-card";


                jobCard.innerHTML = `

                    <h2>
                        ${job.title}
                    </h2>


                    <p class="job-description">
                        ${job.description}
                    </p>


                    <p>
                        <strong>
                            Requirements:
                        </strong>

                        ${job.requirements}
                    </p>


                    <p>
                        <strong>
                            Job ID:
                        </strong>

                        ${job.id}
                    </p>


                    <p>
                        <strong>
                            Created By:
                        </strong>

                        ${job.createdBy}
                    </p>

                `;


                hrJobsContainer.appendChild(
                    jobCard
                );

            }
        );


    } catch (error) {

        console.error(
            "HR jobs error:",
            error
        );


        hrJobsContainer.innerHTML = `
            <div class="no-jobs">

                <p>
                    Unable to load jobs.
                    Please make sure the backend
                    is running.
                </p>

            </div>
        `;

    }

}


loadHRJobs();


// ==========================================
// HR - VIEW APPLICATIONS
// ==========================================

const viewHRApplicationsBtn =
    document.getElementById(
        "viewApplicationsBtn"
    );


const applicationsSection =
    document.getElementById(
        "applicationsSection"
    );


const hrApplicationsContainer =
    document.getElementById(
        "hrApplicationsContainer"
    );


if (viewHRApplicationsBtn) {

    viewHRApplicationsBtn.addEventListener(
        "click",
        function () {

            if (applicationsSection) {

                applicationsSection.scrollIntoView({
                    behavior: "smooth"
                });

            }


            loadHRApplications();

        }
    );

}


// ==========================================
// LOAD HR APPLICATIONS
// ==========================================

async function loadHRApplications() {

    if (!hrApplicationsContainer) {
        return;
    }


    hrApplicationsContainer.innerHTML = `
        <p>
            Loading applications...
        </p>
    `;


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/applications`
            );


        if (!response.ok) {

            throw new Error(
                "Failed to load applications."
            );

        }


        const applications =
            await response.json();


        console.log(
            "Applications:",
            applications
        );


        if (applications.length === 0) {

            hrApplicationsContainer.innerHTML = `
                <div class="no-jobs">

                    <p>
                        No candidate applications
                        have been submitted yet.
                    </p>

                </div>
            `;

            return;

        }


        hrApplicationsContainer.innerHTML =
            "";


        applications.forEach(
            function (application) {

                const applicationCard =
                    document.createElement(
                        "div"
                    );


                applicationCard.className =
                    "job-card";


                const currentStatus =
                    application.status ||
                    "PENDING";


                applicationCard.innerHTML = `

                    <h2>
                        ${application.fullName}
                    </h2>


                    <p>
                        <strong>
                            Application ID:
                        </strong>

                        ${application.id}

                    </p>


                    <p>
                        <strong>
                            Email:
                        </strong>

                        ${application.email}

                    </p>


                    <p>
                        <strong>
                            Phone:
                        </strong>

                        ${application.phone}

                    </p>


                    <p>
                        <strong>
                            Experience:
                        </strong>

                        ${application.experience}

                    </p>


                    <p>
                        <strong>
                            Job ID:
                        </strong>

                        ${application.jobId}

                    </p>


                    <p>
                        <strong>
                            User ID:
                        </strong>

                        ${application.userId}

                    </p>


                    <p>
                        <strong>
                            Cover Letter:
                        </strong>
                    </p>


                    <p class="job-description">
                        ${
                            application.coverLetter ||
                            "No cover letter provided."
                        }
                    </p>


                    <p>

                        <strong>
                            Status:
                        </strong>

                        ${currentStatus}

                    </p>


                    <div
                        style="
                            margin-top: 20px;
                            display: flex;
                            gap: 10px;
                            flex-wrap: wrap;
                        "
                    >

                        <button
                            class="dashboard-btn"
                            type="button"
                            onclick="
                                updateApplicationStatus(
                                    ${application.id},
                                    'SHORTLISTED'
                                )
                            "
                        >

                            Shortlist

                        </button>


                        <button
                            class="dashboard-btn"
                            type="button"
                            onclick="
                                updateApplicationStatus(
                                    ${application.id},
                                    'REJECTED'
                                )
                            "
                        >

                            Reject

                        </button>

                    </div>

                `;


                hrApplicationsContainer.appendChild(
                    applicationCard
                );

            }
        );


    } catch (error) {

        console.error(
            "Application loading error:",
            error
        );


        hrApplicationsContainer.innerHTML = `
            <div class="no-jobs">

                <p>
                    Unable to load applications.
                    Please make sure the backend
                    is running.
                </p>

            </div>
        `;

    }

}


// ==========================================
// HR - UPDATE APPLICATION STATUS
// ==========================================

async function updateApplicationStatus(
    applicationId,
    newStatus
) {

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/applications/${applicationId}`,
                {

                    method:
                        "PUT",

                    headers: {

                        "Content-Type":
                            "application/json"

                    },

                    body:
                        JSON.stringify({

                            status:
                                newStatus

                        })

                }
            );


        if (!response.ok) {

            const errorText =
                await response.text();


            console.error(
                "Status update error:",
                errorText
            );


            throw new Error(
                errorText
            );

        }


        const updatedApplication =
            await response.json();


        console.log(
            "Application status updated:",
            updatedApplication
        );


        alert(
            "Application status changed to " +
            newStatus
        );


        loadHRApplications();


    } catch (error) {

        console.error(
            "Update status error:",
            error
        );


        alert(
            "Unable to update application status."
        );

    }

}
