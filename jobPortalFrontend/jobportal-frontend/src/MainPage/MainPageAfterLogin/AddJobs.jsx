import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function AddJobs() {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    jobCategoryId: '', // Changed category to jobCategoryId to match backend
    companyName: '',
    jobType: '',
    salaryRange: '',
    experienceLevel: '',
    requiredSkills: '',
    street: '',
    city: '',
    state: '',
    pincode: '',
    country: '',
  });

  const [logo, setLogo] = useState(null);
  const [categories, setCategories] = useState([]);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/job/category/fetch/all');
      const data = await response.json();
      setCategories(data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  };

  const handleLogoChange = (e) => {
    setLogo(e.target.files[0]);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleAddJob = async (e) => {
    e.preventDefault();

    const finalFormData = new FormData();
    for (const key in formData) {
      finalFormData.append(key, formData[key]);
    }
    if (logo) {
      finalFormData.append('companyLogo', logo);
    }

    const token = localStorage.getItem('jwtToken');

    try {
      const response = await fetch('http://localhost:8080/api/job/register', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        body: finalFormData,
      });

      if (response.ok) {
        setMessage('Job added successfully.');
        navigate('/home');
      } else {
        setMessage('Failed to add job. Please try again later.');
      }
    } catch (error) {
      console.error('Error:', error);
      setMessage('Something went wrong. Please try again later.');
    }
  };

  return (
    <div style={styles.container}>
      <h2>Add Job</h2>
      <form onSubmit={handleAddJob}>
        <div style={styles.inputContainer}>
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
            placeholder="Title"
            style={styles.input}
          />
          <textarea
            name="description"
            value={formData.description}
            onChange={handleChange}
            placeholder="Description"
            style={styles.input}
          />
          <select
            name="jobType"
            value={formData.jobType}
            onChange={handleChange}
            style={styles.input}
          >
            <option value="">Select Job Type</option>
            <option value="Part-time">Part-time</option>
            <option value="Full-time">Full-time</option>
            <option value="Part-time & Full-time">Part-time & Full-time</option>
          </select>
          <label style={{ fontSize: 'small' }}>Company Logo</label>
          <input
            type="file"
            accept="image/*"
            placeholder="Company Logo"
            onChange={handleLogoChange}
            style={styles.input}
          />
          <input
            type="text"
            name="companyName"
            value={formData.companyName}
            onChange={handleChange}
            placeholder="Company Name"
            style={styles.input}
          />
          <select
            name="jobCategoryId" // Change here to match the backend field name
            value={formData.jobCategoryId}
            onChange={handleChange}
            style={styles.input}
          >
            <option value="">Select Category</option>
            {categories.map((category) => (
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
          <input
            type="text"
            name="salaryRange"
            value={formData.salaryRange}
            onChange={handleChange}
            placeholder="Salary Range"
            style={styles.input}
          />
          <input
            type="text"
            name="experienceLevel"
            value={formData.experienceLevel}
            onChange={handleChange}
            placeholder="Experience Level"
            style={styles.input}
          />
          <input
            type="text"
            name="requiredSkills"
            value={formData.requiredSkills}
            onChange={handleChange}
            placeholder="Required Skills"
            style={styles.input}
          />
          <input
            type="text"
            name="street"
            value={formData.street}
            onChange={handleChange}
            placeholder="Street"
            style={styles.input}
          />
          <input
            type="text"
            name="city"
            value={formData.city}
            onChange={handleChange}
            placeholder="City"
            style={styles.input}
          />
          <input
            type="text"
            name="state"
            value={formData.state}
            onChange={handleChange}
            placeholder="State"
            style={styles.input}
          />
          <input
            type="text"
            name="pincode"
            value={formData.pincode}
            onChange={handleChange}
            placeholder="Pincode"
            style={styles.input}
          />
          <input
            type="text"
            name="country"
            value={formData.country}
            onChange={handleChange}
            placeholder="Country"
            style={styles.input}
          />
        </div>
        <button type="submit" style={styles.button}>
          Add Job
        </button>
      </form>
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
    backgroundColor: '#007bff',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
  message: {
    marginTop: '1rem',
    color: 'green',
  },
};

export default AddJobs;
