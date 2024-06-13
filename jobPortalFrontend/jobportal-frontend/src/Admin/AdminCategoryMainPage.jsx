import React, { useState, useEffect } from 'react';

const AdminCategoryMainPage = () => {
  const [jobCategories, setJobCategories] = useState([]);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [status, setStatus] = useState('active'); 
  const [editId, setEditId] = useState(null);

  useEffect(() => {
    fetchJobCategories();
  }, []);

  const fetchJobCategories = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/job/category/fetch/all');
      const data = await response.json();
      setJobCategories(data);
    } catch (error) {
      console.error('Error fetching job categories:', error);
    }
  };

  const handleCreateOrUpdateCategory = async (e) => {
    e.preventDefault();
    const category = { name, description, status };

    try {
      const url = editId ? `http://localhost:8080/api/job/category/update/${editId}` : 'http://localhost:8080/api/job/category/add';
      const method = editId ? 'PUT' : 'POST';
      const response = await fetch(url, {
        method,
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(category),
      });

      if (response.ok) {
        fetchJobCategories();
        setName('');
        setDescription('');
        setStatus('active');
        setEditId(null);
      } else {
        console.error('Failed to create or update job category');
      }
    } catch (error) {
      console.error('Error creating or updating job category:', error);
    }
  };

  const handleDeleteCategory = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/api/job/category/delete/${id}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        fetchJobCategories();
      } else {
        console.error('Failed to delete job category');
      }
    } catch (error) {
      console.error('Error deleting job category:', error);
    }
  };

  const handleEditCategory = (category) => {
    setName(category.name);
    setDescription(category.description);
    setStatus(category.status);
    setEditId(category.id);
  };

  return (
    <div style={styles.container}>
      <form onSubmit={handleCreateOrUpdateCategory} style={styles.form}>
        <h2>{editId ? 'Update Job Category' : 'Create Job Category'}</h2>
        <div style={styles.formGroup}>
          <label style={styles.label}>Name:</label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            style={styles.input}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Description:</label>
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            style={styles.textarea}
            required
          />
        </div>
        <div style={styles.formGroup}>
          <label style={styles.label}>Status:</label>
          <select
            value={status}
            onChange={(e) => setStatus(e.target.value)}
            style={styles.select}
            required
          >
            <option value="active">Active</option>
            <option value="deactive">Deactive</option>
          </select>
        </div>
        <button type="submit" style={styles.button}>{editId ? 'Update' : 'Create'}</button>
      </form>

      <div style={styles.listContainer}>
        <h2>Job Categories</h2>
        {jobCategories.map((category) => (
          <div key={category.id} style={styles.card}>
            <h3 style={styles.cardTitle}>{category.name}</h3>
            <p style={styles.cardDescription}>{category.description}</p>
            <p style={styles.cardStatus}>Status: {category.status}</p>
            <button onClick={() => handleEditCategory(category)} style={styles.editButton}>Edit</button>
            <button onClick={() => handleDeleteCategory(category.id)} style={styles.deleteButton}>Delete</button>
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
  form: {
    maxWidth: '400px',
    width: '100%',
    marginBottom: '20px',
    padding: '20px',
    borderRadius: '8px',
    backgroundColor: '#fff',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
  },
  formGroup: {
    marginBottom: '15px',
  },
  label: {
    display: 'block',
    marginBottom: '5px',
    color: '#555',
  },
  input: {
    width: 'calc(100% - 10px)',
    padding: '8px',
    border: '1px solid #ccc',
    borderRadius: '5px',
    boxSizing: 'border-box',
  },
  textarea: {
    width: 'calc(100% - 10px)',
    padding: '8px',
    border: '1px solid #ccc',
    borderRadius: '5px',
    boxSizing: 'border-box',
    minHeight: '100px',
  },
  select: {
    width: 'calc(100% - 10px)',
    padding: '8px',
    border: '1px solid #ccc',
    borderRadius: '5px',
    boxSizing: 'border-box',
  },
  button: {
    width: '100%',
    padding: '10px',
    border: 'none',
    borderRadius: '5px',
    backgroundColor: '#007bff',
    color: 'white',
    fontSize: '16px',
    cursor: 'pointer',
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
  cardStatus: {
    fontSize: '1rem',
    color: '#007bff',
  },
  editButton: {
    marginRight: '10px',
    padding: '10px',
    border: 'none',
    borderRadius: '5px',
    backgroundColor: '#4caf50',
    color: 'white',
    cursor: 'pointer',
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

export default AdminCategoryMainPage;
