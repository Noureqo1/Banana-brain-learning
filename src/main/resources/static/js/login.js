document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const loginError = document.getElementById('loginError');

    if (!loginForm) {
        console.error('Login form not found');
        return;
    }

    loginForm.addEventListener('submit', async function(e) {
        e.preventDefault(); // Prevent form from submitting normally

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('/api/auth/login-form', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`
            });

            if (response.ok) {
                const data = await response.json();
                // Store the JWT token
                localStorage.setItem('jwt_token', data.token);

                // Make a request to get user role
                const userResponse = await fetch('/api/auth/user', {
                    headers: {
                        'Authorization': `Bearer ${data.token}`
                    }
                });

                if (userResponse.ok) {
                    const userData = await userResponse.json();
                    // Redirect based on role
                    if (userData.role === 'ROLE_STUDENT') {
                        window.location.href = '/studentHome';
                    } else if (userData.role === 'ROLE_TEACHER') {
                        window.location.href = '/teacherHome';
                    } else {
                        window.location.href = '/';
                    }
                } else {
                    // If can't get role, redirect to default home
                    window.location.href = '/';
                }
            } else {
                const errorData = await response.text();
                loginError.textContent = 'Invalid username or password';
                console.error('Login failed:', errorData);
            }
        } catch (error) {
            loginError.textContent = 'An error occurred during login';
            console.error('Login error:', error);
        }
    });
});
