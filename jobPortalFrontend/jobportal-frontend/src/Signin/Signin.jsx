import  { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';



const Signin = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    phoneNumber: '',
    role: '',
    registrationDate: '',
    status: ''
  });
    const [addressData, setAddressData] = useState({
      street: '',
      city: '',
      pincode: '',
      state: '',
      country: ''
    });
    const [emailData, setEmailData] = useState('')

    useEffect(() => {
      const storedEmail = localStorage.getItem('email');
      if (storedEmail) {
        setEmailData(storedEmail);
      }
    }, []); 

  const navigate = useNavigate();
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };
  const handleChangeA = (e) => {
    const { name, value } = e.target;
    setAddressData({
      ...addressData,
      [name]: value
    });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const combinedData = {
      ...formData,
      ...addressData,
      registrationDate: new Date().toISOString(),
      role:"USER",
      status: "ACTIVE",

      
    };

    try {
      const response = await fetch('http://localhost:8080/api/user/register', {
        method: 'POST',
        body: JSON.stringify(combinedData),
        headers: {
          "Content-Type": "application/json",
        },
      });

  
      if (response.ok) {
        console.log('Data submitted successfully');
        const data = await response.json();
        console.log(data);
        navigate('/home');
      } else {
        alert("Data not submitted successfully - please try again make sure all fields are in propermanner format");
        console.error('Failed to submit data');
        console.error('Response status:', response.status);
        console.error('Response text:', await response.text());
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.heading}>Sign In</h2>
      <form style={styles.form} onSubmit={handleSubmit}>
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="firstName">First Name</label>
          <input
          type="text"
          name="firstName"
          id="firsttName"
          value={formData.firstName}
          onChange={handleChange}
          style={styles.input}
          required
        />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="lastName">Last Name</label>
          <input
            type="text"
            name="lastName"
            id="lastName"
            value={formData.lastName}
            onChange={handleChange}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="email">Email</label>
          <input
          type="email"
          name="email"
          id="email"
          value={emailData}
          style={styles.input}
          readOnly
        />
        </div>
        
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="password">Password</label>
          <input
            type="password"
            name="password"
            id="password"
            value={formData.password}
            onChange={handleChange}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="phoneNumber">Phone Number</label>
          <input
            type="text"
            name="phoneNumber"
            id="phoneNumber"
            value={formData.phoneNumber}
            onChange={handleChange}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="street">Street</label>
          <input
            type="text"
            name="street"
            id="street"
            value={addressData.street}
            onChange={handleChangeA}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="city">City</label>
          <input
            type="text"
            name="city"
            id="city"
            value={addressData.city}
            onChange={handleChangeA}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="pincode">Pincode</label>
          <input
            type="text"
            name="pincode"
            id="pincode"
            value={addressData.pincode}
            onChange={handleChangeA}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="state">State</label>
          <input
            type="text"
            name="state"
            id="state"
            value={addressData.state}
            onChange={handleChangeA}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label} htmlFor="country">Country</label>
          <input
            type="text"
            name="country"
            id="country"
            value={addressData.country}
            onChange={handleChangeA}
            style={styles.input}
            required
          />
        </div>
        
        
        <button type="submit" style={styles.button}>Next</button>
      </form>
    </div>
  );
}
const styles = {
  container: {
    maxWidth: '600px',
    margin: '0 auto',
    padding: '2rem',
    backgroundColor: '#f9f9f9',
    borderRadius: '8px',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
  },
  heading: {
    textAlign: 'center',
    marginBottom: '1rem',
    fontSize: '2rem',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
  },
  formGroup: {
    marginBottom: '1rem',
  },
  label: {
    marginBottom: '0.5rem',
    fontSize: '1rem',
  },
  input: {
    padding: '0.5rem',
    fontSize: '1rem',
    borderRadius: '4px',
    border: '1px solid #ccc',
    width: '100%',
  },
  button: {
    padding: '0.75rem',
    fontSize: '1rem',
    backgroundColor: '#333',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
};


export default Signin;

    
