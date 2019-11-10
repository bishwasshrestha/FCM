'use strict';

const mysql = require('mysql2/promise');

const pool = mysql.createPool({
    host: process.env.DB_HOST,
	user: process.env.DB_USER,
	database: process.env.DB_DATABASE,
    password: process.env.DB_PASSWORD,
    waitForConnections: true,
	connectionLimit: 10,
	queueLimit: 0
});

// const User = require('./users')(pool);
const Media = require('./media')(pool);

module.exports = {
	Media
};