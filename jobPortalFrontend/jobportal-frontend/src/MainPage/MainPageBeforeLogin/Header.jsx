import { Link } from "react-router-dom";
import logo from "../../images/logo.png";
import { useState } from "react";

const Header = () => {
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
            <Link to="/about" style={styles.navLink}>
              About
            </Link>
          </li>
          <li style={styles.navItem}>
            <Link to="/contact" style={styles.navLink}>
              Contact
            </Link>
          </li>
          <li style={styles.navItem} onMouseEnter={() => setLoginDropdownOpen(true)}onMouseLeave={() => setLoginDropdownOpen(false)}>
          <span style={styles.navLink}>Login</span>
          {loginDropdownOpen && (
            <ul style={styles.dropdownMenu}>
              <li style={styles.dropdownItem}>
                <Link to="/login" style={styles.dropdownLink}>Login as User</Link>
              </li>
              <li style={styles.dropdownItem}>
                <Link to="/login-admin" style={styles.dropdownLink}>Login as Admin</Link>
              </li>
            </ul>
          )}   </li>
          <li style={styles.navItem} onMouseEnter={() => setSigninDropdownOpen(true)}onMouseLeave={() => setSigninDropdownOpen(false)}>
          <span style={styles.navLink}>Signin</span>
          {signinDropdownOpen && (
            <ul style={styles.dropdownMenu}>
              <li style={styles.dropdownItem}>
                <Link to="/signin-user" style={styles.dropdownLink}>Signin as User</Link>
              </li>
              <li style={styles.dropdownItem}>
                <Link to="/signin-admin" style={styles.dropdownLink}>Signin as Admin</Link>
              </li>
            </ul>
          )}
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

export default Header;
