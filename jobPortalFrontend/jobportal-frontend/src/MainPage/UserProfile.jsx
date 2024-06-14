import React, { useState, useEffect } from 'react';

const UserProfile = () => {
  const [profile, setProfile] = useState(null);
  const [message, setMessage] = useState('');
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    bio: '',
    website: '',
    linkedlnProfileLink: '',
    githubProfileLink: '',
    resumeLink: null,
    profilePicLink: null,
  });

  useEffect(() => {
    fetchUserProfile();
  }, []);

  const fetchUserProfile = async () => {
    const token = localStorage.getItem('jwtToken');
    try {
      const response = await fetch('http://localhost:8080/api/user/profile', {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.ok) {
        const data = await response.json();
        setProfile(data);
        setFormData({
          bio: data.bio || '',
          website: data.website || '',
          linkedlnProfileLink: data.linkedlnProfileLink || '',
          githubProfileLink: data.githubProfileLink || '',
          resumeLink: data.resumeLink || null, // Ensure resumeLink is set properly
          profilePicLink: null,
        });
      } else {
        setMessage('Failed to fetch profile. Please try again later.');
      }
    } catch (error) {
      console.error('Error fetching profile:', error);
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

  const handleInputChange = (e) => {
    const { name, value, files } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: files ? files[0] : value,
    }));
  };

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('jwtToken');
    const formDataToSubmit = new FormData();
    formDataToSubmit.append('bio', formData.bio);
    formDataToSubmit.append('website', formData.website);
    formDataToSubmit.append('linkedlnProfileLink', formData.linkedlnProfileLink);
    formDataToSubmit.append('githubProfileLink', formData.githubProfileLink);
    if (formData.resumeLink) {
      formDataToSubmit.append('resumeLink', formData.resumeLink);
    }
    if (formData.profilePicLink) {
      formDataToSubmit.append('profilePicLink', formData.profilePicLink);
    }

    try {
      const response = await fetch('http://localhost:8080/api/user/update/profile', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        body: formDataToSubmit,
      });

      if (response.ok) {
        const updatedProfile = await response.json();
        setProfile(updatedProfile);
        setIsEditing(false);
        setMessage('Profile updated successfully.');
      } else {
        setMessage('Failed to update profile. Please try again later.');
      }
    } catch (error) {
      console.error('Error updating profile:', error);
      setMessage('Something went wrong. Please try again later.');
    }
  };

  return (
    <div style={styles.container}>
      <h2>User Profile</h2>
      {profile ? (
        <div style={styles.profileContainer}>
          {profile.profilePicture && (
            <img src={createImageUrl(profile.profilePicture)} alt="Profile" style={styles.profilePicture} />
          )}
          <div style={styles.detailsContainer}>
            <p><strong>Bio:</strong> {profile.bio}</p>
            <p><strong>Website:</strong> <a href={profile.website} target="_blank" rel="noopener noreferrer">{profile.website}</a></p>
            <p><strong>LinkedIn:</strong> <a href={profile.linkedlnProfileLink} target="_blank" rel="noopener noreferrer">{profile.linkedlnProfileLink}</a></p>
            <p><strong>GitHub:</strong> <a href={profile.githubProfileLink} target="_blank" rel="noopener noreferrer">{profile.githubProfileLink}</a></p>
            {profile.resumeLink && (
              <p><strong>Resume:</strong> <a href={profile.resumeLink} target="_blank" rel="noopener noreferrer">View Resume</a></p>
            )}
          </div>
          <button onClick={() => setIsEditing(true)} style={styles.editButton}>Edit Profile</button>
        </div>
      ) : (
        <p>Loading profile...</p>
      )}
      {message && <p style={styles.message}>{message}</p>}

      {isEditing && (
        <form onSubmit={handleFormSubmit} style={styles.form}>
          <label>
            Bio:
            <textarea
              name="bio"
              value={formData.bio}
              onChange={handleInputChange}
              style={styles.input}
            />
          </label>
          <label>
            Website:
            <input
              type="text"
              name="website"
              value={formData.website}
              onChange={handleInputChange}
              style={styles.input}
            />
          </label>
          <label>
            LinkedIn:
            <input
              type="text"
              name="linkedlnProfileLink"
              value={formData.linkedlnProfileLink}
              onChange={handleInputChange}
              style={styles.input}
            />
          </label>
          <label>
            GitHub:
            <input
              type="text"
              name="githubProfileLink"
              value={formData.githubProfileLink}
              onChange={handleInputChange}
              style={styles.input}
            />
          </label>
          <label>
            Resume:
            <input
              type="file"
              name="resumeLink"
              onChange={handleInputChange}
              style={styles.input}
            />
          </label>
          <label>
            Profile Picture:
            <input
              type="file"
              name="profilePicLink"
              onChange={handleInputChange}
              style={styles.input}
            />
          </label>
          <button type="submit" style={styles.saveButton}>Save</button>
          <button type="button" onClick={() => setIsEditing(false)} style={styles.cancelButton}>Cancel</button>
        </form>
      )}
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
  profileContainer: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: '20px',
    backgroundColor: '#fff',
    borderRadius: '8px',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
  },
  profilePicture: {
    maxWidth: '150px',
    maxHeight: '150px',
    borderRadius: '50%',
    marginBottom: '20px',
  },
  detailsContainer: {
    textAlign: 'center',
  },
  editButton: {
    marginTop: '1rem',
    padding: '0.5rem 1rem',
    backgroundColor: '#007bff',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
  message: {
    marginTop: '1rem',
    color: 'red',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    width: '100%',
    maxWidth: '400px',
  },
  input: {
    marginBottom: '1rem',
    padding: '0.5rem',
    fontSize: '1rem',
    borderRadius: '4px',
    border: '1px solid #ccc',
  },
  saveButton: {
    padding: '0.5rem 1rem',
    backgroundColor: '#28a745',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
  cancelButton: {
    marginTop: '0.5rem',
    padding: '0.5rem 1rem',
    backgroundColor: '#dc3545',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
};

export default UserProfile;
