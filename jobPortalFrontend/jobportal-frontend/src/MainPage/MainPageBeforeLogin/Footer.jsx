import React from 'react';
import { Link } from 'react-router-dom';
import logo from '../../images/logo.png';
import playstoreLogo from '../../images/playstore.png';  // Assume you have a Play Store logo

const Footer = () => {
  return (
    <footer style={styles.footer}>
      <div style={styles.footerTop}>
        <img src={logo} alt="Logo" style={styles.footerLogo} />
        <div style={styles.socialIcons}>
          <a href="https://facebook.com" style={styles.socialIcon} target="_blank" rel="noopener noreferrer">
            Facebook
          </a>
          <a href="https://twitter.com" style={styles.socialIcon} target="_blank" rel="noopener noreferrer">
            Twitter
          </a>
          <a href="https://instagram.com" style={styles.socialIcon} target="_blank" rel="noopener noreferrer">
            Instagram
          </a>
          <a href="dark55thor@gmail.com" style={styles.socialIcon}>
            Email
          </a>
        </div>
        <a href="playstore" target="_blank" rel="noopener noreferrer">
          <img src={playstoreLogo} alt="Play Store" style={styles.playstoreLogo} />
        </a>
      </div>
      <div style={styles.footerContainer}>
        <div style={styles.footerSection}>
          <h4 style={styles.footerHeading}>Company</h4>
          <ul style={styles.footerLinks}>
            <li style={styles.footerLinkItem}>
              <Link to="/about" style={styles.footerLink}>
                About Us
              </Link>
            </li>
            <li style={styles.footerLinkItem}>
              <Link to="/careers" style={styles.footerLink}>
                Careers
              </Link>
            </li>
            <li style={styles.footerLinkItem}>
              <Link to="/employer-home" style={styles.footerLink}>
                Employer Home
              </Link>
            </li>
            <li style={styles.footerLinkItem}>
              <Link to="/credits" style={styles.footerLink}>
                Credits
              </Link>
            </li>
          </ul>
        </div>
        <div style={styles.footerSection}>
          <h4 style={styles.footerHeading}>Support</h4>
          <ul style={styles.footerLinks}>
            <li style={styles.footerLinkItem}>
              <Link to="/help-center" style={styles.footerLink}>
                Help Center
              </Link>
            </li>
            <li style={styles.footerLinkItem}>
              <Link to="/summons-notices" style={styles.footerLink}>
                Summons/Notices
              </Link>
            </li>
            <li style={styles.footerLinkItem}>
              <Link to="/grievances" style={styles.footerLink}>
                Grievances
              </Link>
            </li>
            <li style={styles.footerLinkItem}>
              <Link to="/report-issue" style={styles.footerLink}>
                Report Issue
              </Link>
            </li>
          </ul>
        </div>
        <div style={styles.footerSection}>
          <h4 style={styles.footerHeading}>Legal</h4>
          <ul style={styles.footerLinks}>
            <li style={styles.footerLinkItem}>
              <Link to="/privacy-policy" style={styles.footerLink}>
                Privacy Policy
              </Link>
            </li>
            <li style={styles.footerLinkItem}>
              <Link to="/terms-conditions" style={styles.footerLink}>
                Terms & Conditions
              </Link>
            </li>
          </ul>
        </div>
        <div style={styles.footerSection}>
          <h4 style={styles.footerHeading}>Safety</h4>
          <ul style={styles.footerLinks}>
            <li style={styles.footerLinkItem}>
              <Link to="/fraud-alert" style={styles.footerLink}>
                Fraud Alert
              </Link>
            </li>
            <li style={styles.footerLinkItem}>
              <Link to="/trust-safety" style={styles.footerLink}>
                Trust & Safety
              </Link>
            </li>
          </ul>
        </div>
      </div>
      <div style={styles.footerBottom}>
        <p style={styles.footerText}>
          &copy; {new Date().getFullYear()} Bitts. All rights reserved.
        </p>
      </div>
    </footer>
  );
};

const styles = {
  footer: {
    backgroundColor: '#333',
    color: '#fff',
    padding: '2rem 1rem',
    marginTop: '2rem',
  },
  footerTop: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    marginBottom: '2rem',
  },
  footerLogo: {
    height: '80px',
    marginBottom: '1rem',
  },
  socialIcons: {
    display: 'flex',
    justifyContent: 'center',
    gap: '1rem',
    marginBottom: '1rem',
  },
  socialIcon: {
    color: '#fff',
    textDecoration: 'none',
    fontSize: '1rem',
  },
  playstoreLogo: {
    height: '50px',
  },
  footerContainer: {
    display: 'flex',
    justifyContent: 'space-between',
    flexWrap: 'wrap',
  },
  footerSection: {
    flex: '1 1 200px',
    margin: '1rem',
  },
  footerHeading: {
    fontSize: '1.5rem',
    marginBottom: '1rem',
  },
  footerText: {
    fontSize: '1rem',
    lineHeight: '1.5',
  },
  footerLinks: {
    listStyle: 'none',
    padding: 0,
  },
  footerLinkItem: {
    marginBottom: '0.5rem',
  },
  footerLink: {
    color: '#fff',
    textDecoration: 'none',
    fontSize: '1rem',
  },
  footerBottom: {
    textAlign: 'center',
    borderTop: '1px solid #444',
    paddingTop: '1rem',
    marginTop: '1rem',
  },
};

export default Footer;
