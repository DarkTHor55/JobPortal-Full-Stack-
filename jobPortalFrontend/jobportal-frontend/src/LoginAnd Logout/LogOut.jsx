import React from 'react';
import { useNavigate } from 'react-router-dom';


const LogOut = () => {
  const navigate = useNavigate();


    const handleLogout = async () => {
        const token = localStorage.getItem('jwtToken');
        try {
            const response = await fetch('http://localhost:8080/api/user/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                },
            });

            if (response.ok) {
                console.log('Logout successful.');
                localStorage.clear();
                navigate("/home")
            } else {
                console.error('Failed to logout.');
            }
        } catch (error) {
            console.error('Error logging out:', error);
        }
    };

    const buttonStyle = {
        padding: '10px 20px',
        backgroundColor: '#dc3545',
        color: '#fff',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
        fontSize: '16px',
        marginTop: '20px',
    };

    return (
        <div style={styles.container}>
            <h2>Logout Page</h2>
            <p>Are you sure you want to logout?</p>
            <button style={buttonStyle} onClick={handleLogout}>Logout</button>
        </div>
    );
};

const styles = {
    container: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: '20px',
        backgroundColor: '#f0f0f0',
        borderRadius: '8px',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    },
};

export default LogOut;
