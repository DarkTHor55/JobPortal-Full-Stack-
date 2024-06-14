import  { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const AllJobs = () => {
  const [jobs, setJobs] = useState([]);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/job/fetch/all');
      const data = await response.json();
      const sortedJobs = data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
      setJobs(sortedJobs);
    } catch (error) {
      console.error('Error fetching jobs:', error);
    }
  };

  const handleApplyJob = async (jobId) => {
    const token = localStorage.getItem('jwtToken');
    try {
      const response = await fetch(`http://localhost:8080/api/job/apply/${jobId}`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.ok) {
        setMessage('Successfully applied for the job.');
      } else {
        setMessage('Failed to apply for the job. Please try again later.');
      }
    } catch (error) {
      console.error('Error applying for job:', error);
      setMessage('Something went wrong. Please try again later.');
    }
  };

  const createImageUrl = (bytes) => {
    if (bytes) {
      const byteCharacters = atob(bytes);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' }); 
      return URL.createObjectURL(blob);
    }
    return null;
  };

  return (
    <div style={styles.container}>
      <h2>Jobs List</h2>
      <div style={styles.listContainer}>
        {jobs.map((job) => (
          <div key={job.id} style={styles.card}>
            {job.companyLogo && (
              <img src={createImageUrl(job.companyLogo)} alt={`${job.companyName} logo`} style={styles.logo} />
            )}
            <h3 style={styles.cardTitle}>{job.title}</h3>
            <p style={styles.cardDescription}>{job.description}</p>
            <p style={styles.cardCompanyName}>Company: {job.companyName}</p>
            <p style={styles.cardJobType}>Job Type: {job.jobType}</p>
            <p style={styles.cardSalaryRange}>Salary: {job.salaryRange}</p>
            <p style={styles.cardExperienceLevel}>Experience Level: {job.experienceLevel}</p>
            <button onClick={() => handleApplyJob(job.id)} style={styles.applyButton}>Apply</button>
          </div>
        ))}
      </div>
      {message && <p style={styles.message}>{message}</p>}
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '20px',
    backgroundColor: '#f0f8ff',
    borderRadius: '8px',
  },
  listContainer: {
    maxWidth: '800px',
    width: '100%',
  },
  card: {
    padding: '20px',
    borderRadius: '8px',
    backgroundColor: '#fff',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    marginBottom: '10px',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  cardTitle: {
    fontSize: '1.4rem',
    marginBottom: '10px',
    color: '#333',
  },
  cardDescription: {
    fontSize: '1rem',
    marginBottom: '10px',
    color: '#666',
  },
  cardCompanyName: {
    fontSize: '1rem',
    color: '#333',
  },
  cardJobType: {
    fontSize: '1rem',
    color: '#333',
  },
  cardSalaryRange: {
    fontSize: '1rem',
    color: '#333',
  },
  cardExperienceLevel: {
    fontSize: '1rem',
    color: '#333',
  },
  cardCreatedAt: {
    fontSize: '1rem',
    color: '#007bff',
  },
  logo: {
    maxWidth: '100px',
    maxHeight: '100px',
    marginBottom: '10px',
  },
  applyButton: {
    padding: '10px',
    border: 'none',
    borderRadius: '5px',
    backgroundColor: '#28a745',
    color: 'white',
    cursor: 'pointer',
  },
  message: {
    marginTop: '1rem',
    color: 'green',
  },
};

export default AllJobs;
