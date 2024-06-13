import { useState, useEffect } from 'react';

const AdminJobsPanel = () => {
  const [jobs, setJobs] = useState([]);

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

  const handleDeleteJob = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/job/delete/${id}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        fetchJobs();
      } else {
        console.error('Failed to delete job');
      }
    } catch (error) {
      console.error('Error deleting job:', error);
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
      const blob = new Blob([byteArray], { type: 'image/jpeg' }); // Adjust the type according to your image format
      return URL.createObjectURL(blob);
    }
    return null;
  };

  return (
    <div style={styles.container}>
      <h2>Admin Jobs Panel</h2>
      <div style={styles.listContainer}>
        {jobs.map((job) => (
          <div key={job.id} style={styles.card}>
            {job.companyLogo && (
              <img src={createImageUrl(job.companyLogo)} alt={`${job.companyName} logo`} style={styles.logo} />
            )}
            <h3 style={styles.cardTitle}>{job.title}</h3>
            <p style={styles.cardDescription}>{job.description}</p>
            {/* Display other job details */}
            <p style={styles.cardCreatedAt}>Created At: {job.createdAt}</p>
            <button onClick={() => handleDeleteJob(job.id)} style={styles.deleteButton}>Delete</button>
          </div>
        ))}
      </div>
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
  cardCreatedAt: {
    fontSize: '1rem',
    color: '#007bff',
  },
  logo: {
    maxWidth: '100px',
    maxHeight: '100px',
    marginBottom: '10px',
  },
  deleteButton: {
    padding: '10px',
    border: 'none',
    borderRadius: '5px',
    backgroundColor: '#f44336',
    color: 'white',
    cursor: 'pointer',
  },
};

export default AdminJobsPanel;
