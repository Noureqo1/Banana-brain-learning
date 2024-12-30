document.addEventListener('DOMContentLoaded', function() {
    const logoutBtn = document.getElementById('logoutBtn');

    if (logoutBtn) {
        logoutBtn.addEventListener('click', async function(e) {
            e.preventDefault();

            try {
                // Get the JWT token
                const token = localStorage.getItem('jwt_token');

                if (token) {
                    // Call logout endpoint if you have one
                    try {
                        await fetch('/api/auth/logout', {
                            method: 'POST',
                            headers: {
                                'Authorization': `Bearer ${token}`
                            }
                        });
                    } catch (error) {
                        console.warn('Logout endpoint failed:', error);
                        // Continue with local logout even if server logout fails
                    }

                    // Clear local storage
                    localStorage.removeItem('jwt_token');
                }

                // Redirect to login page
                window.location.href = '/login';

            } catch (error) {
                console.error('Logout error:', error);
                // If there's an error, still try to redirect to login
                window.location.href = '/login';
            }
        });
    }
});
