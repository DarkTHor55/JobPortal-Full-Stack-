import React, { useEffect, useState } from 'react';

const AdminHome = () => {
  const [totalUsers, setTotalUsers] = useState(0);
  const [totalJobs, setTotalJobs] = useState(0);
  // const [totalPlacements, setTotalPlacements] = useState(0);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [usersResponse, jobsResponse] = await Promise.all([
        fetch('http://localhost:8080/api/user/total-users'),
        fetch('http://localhost:8080/api/job/total-jobs'),
        // fetch('http://localhost:8080/api/job/total-placements'),
      ]);
      const usersData = await usersResponse.json();
      const jobsData = await jobsResponse.json();
      // const placementsData = await placementsResponse.json();

      //  API returns { count: number }
      setTotalUsers(usersData.count); 
      setTotalJobs(jobsData.count);   
      // setTotalPlacements(placementsData.count); 
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  return (
    <div style={styles.container}>
      <div style={{ ...styles.card, ...styles.cardUsers }}>
        <h3 style={styles.cardTitle}>Total Users</h3>
        <p style={styles.cardValue}>{totalUsers}</p>
      </div>
      <div style={{ ...styles.card, ...styles.cardJobs }}>
        <h3 style={styles.cardTitle}>Total Jobs</h3>
        <p style={styles.cardValue}>{totalJobs}</p>
      </div>
      <div style={{ ...styles.card, ...styles.cardPlacements }}>
        <h3 style={styles.cardTitle}>Total Placements</h3>
        <p style={styles.cardValue}>{0}</p>
      </div>
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    justifyContent: 'space-around',
    padding: '20px',
    backgroundColor: '#f0f8ff',
    borderRadius: '8px',
  },
  card: {
    flex: '1',
    margin: '10px',
    padding: '20px',
    borderRadius: '8px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    textAlign: 'center',
    transition: 'transform 0.3s, background-color 0.3s',
    color: '#fff',
  },
  cardUsers: {
    backgroundColor: '#4caf50', 
  },
  cardJobs: {
    backgroundColor: '#2196f3', 
  },
  cardPlacements: {
    backgroundColor: '#ff9800', 
  },
  cardTitle: {
    fontSize: '1.4rem',
    margin: '0 0 10px 0',
    color: '#ffffff',
  },
  cardValue: {
    fontSize: '2.4rem',
    margin: '0',
    color: '#ffffff',
  },
};

export default AdminHome;
