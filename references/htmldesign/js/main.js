// Common Javascript functionality for the HR App Prototype

document.addEventListener('DOMContentLoaded', () => {
    // Utility: Add 'active' class to bottom navigation based on current URL
    const currentPath = window.location.pathname.split('/').pop() || 'index.html';
    
    const navItems = document.querySelectorAll('.nav-item');
    navItems.forEach(item => {
        const href = item.getAttribute('href');
        if (href === currentPath) {
            item.classList.add('active');
        } else {
            item.classList.remove('active');
        }
    });

    // Mock Authentication Logic for Login Page
    const loginForm = document.getElementById('loginForm');
    const loginBtn = document.getElementById('loginBtn');
    
    if (loginForm && loginBtn) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;
            
            // Simple visual validation mock
            if(email && password.length >= 8) {
                loginBtn.innerHTML = '<i class="ph ph-spinner fa-spin"></i> Loading...';
                loginBtn.disabled = true;
                
                // Simulate network request
                setTimeout(() => {
                    window.location.href = 'dashboard.html';
                }, 1000);
            } else {
                // Shake animation for error
                loginBtn.classList.add('shake');
                setTimeout(() => loginBtn.classList.remove('shake'), 500);
                alert("Please enter a valid email and password (min 8 characters).");
            }
        });
    }

    // Toggle Password Visibility
    const togglePassword = document.getElementById('togglePassword');
    const passwordInput = document.getElementById('password');
    if(togglePassword && passwordInput) {
        togglePassword.addEventListener('click', () => {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            // Toggle icon (assuming Phosphor icons)
            togglePassword.classList.toggle('ph-eye');
            togglePassword.classList.toggle('ph-eye-closed');
        });
    }
});

// Utility function to format dates
function formatDate(dateString) {
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
}
