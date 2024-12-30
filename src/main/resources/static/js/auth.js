// Authentication state management
const AUTH_TOKEN_KEY = 'jwt_token';

class AuthService {
    static getToken() {
        return localStorage.getItem(AUTH_TOKEN_KEY);
    }

    static setToken(token) {
        localStorage.setItem(AUTH_TOKEN_KEY, token);
    }

    static removeToken() {
        localStorage.removeItem(AUTH_TOKEN_KEY);
    }

    static isAuthenticated() {
        const token = this.getToken();
        return token != null;
    }
}

// Check if page is protected and redirect if not authenticated
document.addEventListener('DOMContentLoaded', async function() {
    const isProtectedPage = document.body.classList.contains('protected-page');

    if (isProtectedPage) {
        const token = localStorage.getItem('jwt_token');
        if (!token) {
            window.location.href = '/login';
            return;
        }

        try {
            // Verify token is valid by checking user role
            const response = await fetch('/api/auth/user', {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (!response.ok) {
                throw new Error('Invalid token');
            }

            // Setup navigation based on role
            const userData = await response.json();
            setupNavigation(userData.role);

            // Add token to all future requests
            setupAuthInterceptor();
        } catch (error) {
            console.error('Auth error:', error);
            localStorage.removeItem('jwt_token');
            window.location.href = '/login';
            return;
        }
    }
});

function setupNavigation(role) {
    const navLinks = document.querySelectorAll('.nav-link');

    // Show/hide navigation items based on role
    navLinks.forEach(link => {
        const href = link.getAttribute('href');

        // Handle student-specific pages
        if (role === 'ROLE_STUDENT') {
            if (href === '/courseRegister' || href === '/assignRole') {
                link.parentElement.style.display = 'none';
            }
        }

        // Handle teacher-specific pages
        if (role === 'ROLE_TEACHER') {
            if (href === '/myCourses') {
                link.parentElement.style.display = 'none';
            }
        }
    });
}

function setupAuthInterceptor() {
    const token = localStorage.getItem('jwt_token');
    if (!token) return;

    // Add Authorization header to all fetch requests
    const originalFetch = window.fetch;
    window.fetch = function() {
        let [resource, config] = arguments;

        // Don't add auth header for login/signup/public resources
        if (resource.includes('/api/auth/login') ||
            resource.includes('/api/auth/register') ||
            resource.includes('/login') ||
            resource.includes('/signup') ||
            resource.includes('/css/') ||
            resource.includes('/js/') ||
            resource.includes('/assets/')) {
            return originalFetch(resource, config);
        }

        config = config || {};
        config.headers = config.headers || {};
        config.headers['Authorization'] = `Bearer ${token}`;

        return originalFetch(resource, config)
            .then(async response => {
                if (response.status === 401) {
                    // Clear token and redirect to login on unauthorized
                    localStorage.removeItem('jwt_token');
                    window.location.href = '/login';
                    return Promise.reject('Unauthorized');
                }
                return response;
            })
            .catch(error => {
                if (error === 'Unauthorized') {
                    throw error;
                }
                // For network errors, also redirect to login
                localStorage.removeItem('jwt_token');
                window.location.href = '/login';
                throw error;
            });
    };
}

async function handleLogin(event) {
    console.log('Entering handleLogin'); // Log for debugging
    event.preventDefault();
    event.stopPropagation();

    const errorDiv = document.getElementById('loginError');
    if (errorDiv) {
        errorDiv.textContent = ''; // Clear previous errors
    }

    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');

    if (!usernameInput || !passwordInput) {
        if (errorDiv) errorDiv.textContent = 'Form inputs not found';
        return;
    }

    const username = usernameInput.value.trim();
    const password = passwordInput.value;

    if (!username || !password) {
        if (errorDiv) errorDiv.textContent = 'Please enter both username and password';
        return;
    }

    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || 'Login failed');
        }

        if (data.token) {
            localStorage.setItem('jwt_token', data.token);
            console.log('Login successful, redirecting to /courses'); // Log for debugging
            window.location.replace('/courses');
        } else {
            throw new Error('No token received');
        }
    } catch (error) {
        console.error('Login error:', error);
        if (errorDiv) {
            errorDiv.textContent = error.message || 'Invalid username or password';
        }
    }

    return false;
}

async function handleSignup(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const name = document.getElementById('name').value;

    try {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password, name })
        });

        if (!response.ok) {
            throw new Error('Signup failed');
        }

        window.location.href = '/login';
    } catch (error) {
        console.error('Signup error:', error);
        document.getElementById('signupError').textContent = 'Signup failed. Please try again.';
    }
}

function handleLogout() {
    localStorage.removeItem('jwt_token');
    window.location.href = '/login';
}

// Initialize auth functionality
function initAuth() {
    console.log('Initializing auth...'); // Log for debugging
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        // Remove any existing event listeners
        const newLoginForm = loginForm.cloneNode(true);
        loginForm.parentNode.replaceChild(newLoginForm, loginForm);

        newLoginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            e.stopPropagation();
            console.log('Login form submitted'); // Log for debugging
            await handleLogin(e);
            return false;
        });
    }

    const signupForm = document.getElementById('signupForm');
    if (signupForm) {
        const newSignupForm = signupForm.cloneNode(true);
        signupForm.parentNode.replaceChild(newSignupForm, signupForm);

        newSignupForm.addEventListener('submit', handleSignup);
    }

    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        const newLogoutBtn = logoutBtn.cloneNode(true);
        logoutBtn.parentNode.replaceChild(newLogoutBtn, logoutBtn);

        newLogoutBtn.addEventListener('click', handleLogout);
    }
}

// Call initAuth when DOM is loaded
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initAuth);
} else {
    initAuth();
}
