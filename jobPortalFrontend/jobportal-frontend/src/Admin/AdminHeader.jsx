import { Link } from "react-router-dom";
import logo from "../../src/images/logo.png";
import { useState } from "react";

const AdminHeader = () => {
  const [loginDropdownOpen, setLoginDropdownOpen] = useState(false);
  const [signinDropdownOpen, setSigninDropdownOpen] = useState(false);
  const toggleLoginDropdown = () => {
    setLoginDropdownOpen(!loginDropdownOpen);
  };

  const toggleSigninDropdown = () => {
    setSigninDropdownOpen(!signinDropdownOpen);
  };
  return (
    <>
      <nav style={styles.navbar}>
        <div style={styles.logoContainer}>
          <img src={logo} alt="Logo" style={styles.logo} />
          <span style={styles.brandName}>
          <Link to="/home" style={styles.brandName}>
          Bitts
            </Link>
            </span>
        </div>
        <ul style={styles.navLinks}>
          <li style={styles.navItem}>
            <Link to="/home" style={styles.navLink}>
              Home
            </Link>
          </li>
          
          <li style={styles.navItem}>
            <Link to="/Category" style={styles.navLink}>
              Category
            </Link>
          </li>
          <li style={styles.navItem}>
            <Link to="/admin-job" style={styles.navLink}>
              Jobs
            </Link>
          </li>
          <li style={styles.navItem}>
            <Link to="/admin-user" style={styles.navLink}>
              User
            </Link>
          </li>
          <li style={styles.navItem}>
            <Link to="/Contact" style={styles.navLink}>
              Contact
            </Link>
          </li>

          
        </ul>
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
    marginLeft: "1rem",
  },
  navLink: {
    color: "#fff",
    textDecoration: "none",
    fontSize: "1.5rem",
    transition: "color 0.3s ease",
  },
  navLinkHover: {
    color: "#f0f0f0",
  },
};

export default AdminHeader;
