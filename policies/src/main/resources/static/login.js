document.addEventListener("DOMContentLoaded", function () {
  const loginForm = document.getElementById("login-form");
  loginForm.addEventListener("submit", function (event) {
    event.preventDefault();
    const formData = new FormData(loginForm);
    const data = {
      username: formData.get("username"),
      password: formData.get("password"),
    };

    // Make a POST request to your backend authentication endpoint
    fetch("/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((response) => response.json())
      .then((response) => {
        if (response.token) {
          // Save the JWT token in localStorage or as a cookie
          localStorage.setItem("token", response.token);
          // Redirect the user to a protected page
          window.location.href = "/dashboard";
        } else {
          alert("Login failed. Please check your credentials.");
        }
      })
      .catch((error) => {
        console.error("Error occurred during login:", error);
      });
  });
});

// Protected Dashboard Page
document.addEventListener("DOMContentLoaded", function () {
  const dashboardContent = document.getElementById("dashboard-content");
  if (dashboardContent) {
    // Fetch protected data from the server
    sendAuthenticatedRequest("/api/dashboard", "GET", null)
      .then(response => {
        // Display the protected data on the dashboard
        dashboardContent.innerHTML = `Welcome, ${response.username}! You are logged in as ${response.roles.join(', ')}.`;
      })
      .catch(error => {
        console.error("Error while accessing protected dashboard:", error);
        dashboardContent.innerHTML = "Error accessing dashboard.";
      });
  }
});
