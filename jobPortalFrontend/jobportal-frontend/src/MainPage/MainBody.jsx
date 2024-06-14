import React from 'react';

const SinglePage = () => {
    const styles = {
        body: {
            fontFamily: 'Arial, sans-serif',
            backgroundColor: '#f0f0f0', /* Light grey background */
            margin: 0,
            padding: 0,
        },
        container: {
            maxWidth: '800px',
            margin: '0 auto',
            padding: '20px',
            backgroundColor: '#fff', /* White background */
            borderRadius: '8px',
            boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
        },
        header: {
            textAlign: 'center',
            marginBottom: '20px',
        },
        heading: {
            color: '#007bff', /* Blue color for header */
            marginBottom: '10px',
        },
        subheading: {
            color: '#555', /* Dark grey color for subheading */
            fontSize: '1.1rem',
        },
        mainContent: {
            padding: '20px',
        },
        section: {
            marginBottom: '30px',
        },
        sectionHeading: {
            color: '#dc3545', /* Red color for section headings */
            marginBottom: '10px',
        },
        paragraph: {
            fontSize: '1.1rem',
            lineHeight: '1.6',
        },
        serviceList: {
            listStyleType: 'none',
            padding: 0,
        },
        serviceItem: {
            marginBottom: '5px',
            color: '#28a745', /* Green color for service items */
        },
        footer: {
            textAlign: 'center',
            marginTop: '20px',
        },
    };

    return (
        <div style={styles.container}>
            <header style={styles.header}>
                <h1 style={styles.heading}>Welcome to My Application</h1>
                <p style={styles.subheading}>Explore our services and get in touch with us!</p>
            </header>
            <main style={styles.mainContent}>
                <section style={styles.section}>
                    <h2 style={styles.sectionHeading}>About Us</h2>
                    <p style={styles.paragraph}>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla convallis libero in dui lacinia, sit amet molestie purus fermentum. Nulla ut lorem et lorem egestas convallis a sit amet ipsum. Donec in vehicula lectus. Cras semper nunc et massa pellentesque hendrerit. Donec id consequat est, vitae congue purus.
                    </p>
                </section>
                <section style={styles.section}>
                    <h2 style={styles.sectionHeading}>Our Services</h2>
                    <ul style={styles.serviceList}>
                        <li style={styles.serviceItem}>Service 1</li>
                        <li style={styles.serviceItem}>Service 2</li>
                        <li style={styles.serviceItem}>Service 3</li>
                        <li style={styles.serviceItem}>Service 4</li>
                    </ul>
                </section>
               </main>
        </div>
    );
};

export default SinglePage;
