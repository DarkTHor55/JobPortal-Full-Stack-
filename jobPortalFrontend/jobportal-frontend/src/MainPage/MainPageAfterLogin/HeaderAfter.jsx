import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import logo from '../../images/logo.png';

const HeaderAfter = () => {
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearchChange = (event) => {
    setSearchQuery(event.target.value);
  };

  const handleSearchSubmit = (event) => {
    event.preventDefault();
    // Implement search logic here, e.g., navigate to a search results page
    console.log('Search query:', searchQuery);
  };
  const [userDropdownOpen, setUserDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setUserDropdownOpen(!userDropdownOpen);
  };

  return (
    <>
      <nav style={styles.navbar}>
        <div style={styles.logoContainer}>
          <img src={logo} alt="Logo" style={styles.logo} />
          <span style={styles.brandName}>Bitts</span>
        </div>
        <ul style={styles.navLinks}>
          <li style={styles.navItem}>
            <Link to="/home2" style={styles.navLink}>
              Home
            </Link>
          </li>
          <li style={styles.navItem}>
            <Link to="/addjob" style={styles.navLink}>
              AbbJobs
            </Link>
          </li>
          <li style={styles.navItem}>
            <Link to="/All-Jobs" style={styles.navLink}>
              Jobs
            </Link>
          </li>
          <li style={styles.navItem}>
            <Link to="/about2" style={styles.navLink}>
              About
            </Link>
          </li>

          <li style={styles.navItem}>
            <Link to="/contact2" style={styles.navLink}>
              Contact
            </Link>
          </li>
          <li style={styles.navItem} onMouseEnter={() => setUserDropdownOpen(true)}onMouseLeave={() => setUserDropdownOpen(false)}>
          <span style={styles.navLink}>User</span>
          {userDropdownOpen && (
            <ul style={styles.dropdownMenu}>
              <li style={styles.dropdownItem}>
                <Link to="/profile" style={styles.dropdownLink}>Profile</Link>
              </li>
              <li style={styles.dropdownItem}>
                <Link to="/logout" style={styles.dropdownLink}>Logout</Link>
              </li>
              
            </ul>
          )}   </li>
        </ul>
        <form onSubmit={handleSearchSubmit} style={styles.searchForm}>
          <input
            type="text"
            value={searchQuery}
            onChange={handleSearchChange}
            placeholder="Search..."
            style={styles.searchInput}
          />
          <button type="submit" style={styles.searchButton}>Search</button>
        </form>
      </nav>
    </>
  );
};

const styles = {
  navbar: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "0.9em 1rem",
    backgroundColor: "#333",
    color: "#fff",
  },
  logoContainer: {
    display: "flex",
    alignItems: "center",
  },
  logo: {
    height: "80px",
    marginRight: "0.5rem",
  },
  brandName: {
    fontSize: "2.5rem",
    fontWeight: "bold",
  },
  navLinks: {
    listStyle: "none",
    display: "flex",
    margin: 0,
    padding: 0,
  },
  navItem: {
    marginLeft: '1rem',
  },
  navLink: {
    color: '#fff',
    textDecoration: 'none',
    fontSize: '1rem',
    transition: 'color 0.3s ease',
  },
  searchForm: {
    display: 'flex',
    alignItems: 'center',
  },
  searchInput: {
    padding: '0.3rem',
    borderRadius: '4px 0 0 4px',
    border: '1px solid #ccc',
    fontSize: '1rem',
  },
  searchButton: {
    padding: '0.3rem 0.6rem',
    borderRadius: '0 4px 4px 0',
    border: 'none',
    backgroundColor: '#555',
    color: '#fff',
    fontSize: '1rem',
    cursor: 'pointer',
  },
};

export default HeaderAfter;
