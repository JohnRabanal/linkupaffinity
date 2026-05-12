import express from 'express';
import mysql from 'mysql2';
import cors from 'cors';
import bodyParser from 'body-parser';

const app = express();
const PORT = 3000;

app.use(cors());
app.use(bodyParser.json());

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '', 
    database: 'rabanal_db',
    port: 3306 
});

db.connect((err) => {
    if (err) {
        console.error('Error connecting to MySQL:', err);
        return;
    }
    console.log('Connected to the rabanal_db database.');
});

// --- AUTHENTICATION ---

// User & Driver Login
app.post('/user-login', (req, res) => {
    const { email, password } = req.body;
    // Check both email and password
    const query = 'SELECT * FROM users WHERE email = ? AND password = ?';
    db.query(query, [email, password], (err, results) => {
        if (err) return res.status(500).json({ error: err.message });
        if (results.length > 0) {
            res.json({ message: 'Login successful!', user: results[0] });
        } else {
            res.status(401).json({ message: 'Invalid email or password' });
        }
    });
});

// Admin Login
app.post('/admin-login', (req, res) => {
    const { email, password } = req.body;
    const query = 'SELECT * FROM admin WHERE email = ? AND password = ?';
    db.query(query, [email, password], (err, results) => {
        if (err) return res.status(500).json({ error: err.message });
        if (results.length > 0) {
            res.json({ message: 'Welcome Admin!', admin: results[0] });
        } else {
            res.status(401).json({ message: 'Invalid Admin credentials' });
        }
    });
});

// --- USER MANAGEMENT (CRUD) ---

// Register Route
app.post('/user-signup', (req, res) => {
    const { username, password, email, role } = req.body;
    const userRole = role || 'Commuter'; 
    const query = 'INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)';
    db.query(query, [username, password, email, userRole], (err, result) => {
        if (err) return res.status(500).json({ error: err.message });
        res.json({ success: true, message: 'User account created!' });
    });
});

// Fetch Users Route (Maps user_id to id for React)
app.get('/get-users', (req, res) => {
    const sql = "SELECT user_id AS id, username, email, role FROM users";
    db.query(sql, (err, data) => {
        if (err) return res.status(500).json(err);
        return res.json(data);
    });
}); 

// UPDATE: Edit user info
app.post('/update-user', (req, res) => {
    const { username, email, role, user_id, password } = req.body;
    
    let sql, params;
    
    if (password && password.trim() !== "") {
        sql = "UPDATE users SET username = ?, email = ?, role = ?, password = ? WHERE user_id = ?";
        params = [username, email, role, password, user_id];
    } else {
        sql = "UPDATE users SET username = ?, email = ?, role = ? WHERE user_id = ?";
        params = [username, email, role, user_id];
    }

    db.query(sql, params, (err, result) => {
        if (err) return res.status(500).json({ error: err.message });
        return res.json({ success: true, message: "User updated successfully!" });
    });
});

// DELETE: Remove User
app.post('/delete-user', (req, res) => {
    const { id } = req.body;
    const sql = "DELETE FROM users WHERE user_id = ?";
    db.query(sql, [id], (err, result) => {
        if (err) return res.status(500).json({ error: err.message });
        return res.json({ success: true, message: "User deleted successfully!" });
    });
});

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
}); 