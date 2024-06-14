import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const styles = {
  formGroup: {
    marginBottom: '1rem',
  },
  label: {
    display: 'block',
    marginBottom: '0.5rem',
    fontWeight: 'bold',
  },
  input: {
    width: '100%',
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
  error: {
    color: 'red',
  },
};

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();
  const role = 'USER';

  const handleLogin = async () => {
    console.log('Email:', email);
    console.log('Password:', password);

    try {
      const response = await fetch('http://localhost:8080/api/user/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password, role }),
      });

      if (response.ok) {
        const data = await response.json();
        const token = data.jwtToken;        
        
        localStorage.setItem('jwtToken', token);
        console.log(token);
        
        navigate('/homeAfter');
      } else {
        const errorText = await response.text();
        setErrorMessage(errorText || 'Invalid email or password');
      }
    } catch (error) {
      console.error('Error:', error);
      setErrorMessage('Something went wrong. Please try again later.');
    }
  };

  return (
    <div>
      <h2>Login</h2>
      <div style={styles.formGroup}>
        <label style={styles.label} htmlFor="email">Email</label>
        <input
          type="email"
          name="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={styles.input}
          required
        />
      </div>
      <div style={styles.formGroup}>
        <label style={styles.label} htmlFor="password">Password</label>
        <input
          type="password"
          name="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={styles.input}
          required
        />
      </div>
      {errorMessage && <p style={styles.error}>{errorMessage}</p>}
      <button onClick={handleLogin} style={styles.button}>Login</button>
    </div>
  );
}

export default Login;
