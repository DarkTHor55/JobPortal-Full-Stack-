import  { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function AdminSignup1() {
  const [email, setEmail] = useState('');
  const [otp, setOtp] = useState('');
  const [otpSent, setOtpSent] = useState(false);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handleOtpChange = (e) => {
    setOtp(e.target.value);
  };
  
  const handleRequestOtp = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/admin/request-otp', {
        method: 'POST',
        body: JSON.stringify(email),
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.ok) {
        setOtpSent(true);
        setMessage('OTP sent to your email.');

      } else {
        alert("Email Already exits")
        console.error('Failed to send OTP');
        console.error('Response status:', response.status);
        console.error('Response text:', await response.text());
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const handleVerifyOtp = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/admin/verify-otp', {
        method: 'POST',
        body: JSON.stringify(otp),
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      if (response.ok) {
        const result = await response.json();
        console.log(result);
        setMessage('OTP verified successfully.');
        setOtpSent(false);
        localStorage.setItem('email', JSON.stringify(email));
        setEmail('');
        setOtp('');
        navigate("/admin-signin-second")
      } else {
        console.error('Failed to verify OTP');
        console.error('Response status:', response.status);
        console.error('Response text:', await response.text());
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div style={styles.container}>
      <h2>OTP Verification</h2>
      {!otpSent ? (
        <div style={styles.inputContainer}>
          <input
            type="email"
            value={email}
            onChange={handleEmailChange}
            placeholder="Enter your email"
            style={styles.input}
          />
          <button onClick={handleRequestOtp} style={styles.button}>
            Send email
          </button>
        </div>
      ) : (
        <div style={styles.inputContainer}>
          <input
            type="text"
            value={otp}
            onChange={handleOtpChange}
            placeholder="Enter OTP"
            style={styles.input}
          />
          <button onClick={handleVerifyOtp} style={styles.button}>
            Verify OTP
          </button>
        </div>
      )}
      {message && <p style={styles.message}>{message}</p>}
    </div>
  );
}

const styles = {
  container: {
    maxWidth: '400px',
    margin: '0 auto',
    padding: '2rem',
    textAlign: 'center',
    backgroundColor: '#f9f9f9',
    borderRadius: '8px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
  },
  inputContainer: {
    display: 'flex',
    flexDirection: 'column',
    gap: '1rem',
  },
  input: {
    padding: '0.5rem',
    fontSize: '1rem',
    borderRadius: '4px',
    border: '1px solid #ccc',
  },
  button: {
    padding: '0.5rem 1rem',
    fontSize: '1rem',
    borderRadius: '4px',
    border: 'none',
    backgroundColor: '#007bff',
    color: '#fff',
    cursor: 'pointer',
  },
  message: {
    marginTop: '1rem',
    fontSize: '1rem',
    color: 'green',
  },
};

export default AdminSignup1;

export const saveDataToLocalStorage = (key, value) => {
  localStorage.setItem(key, value);
};

export const getDataFromLocalStorage = (key) => {
  return localStorage.getItem(key);
};

export const removeDataFromLocalStorage = (key) => {
  localStorage.removeItem(key);
};