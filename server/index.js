'use strict';

require('dotenv').config();
const express = require('express');
const app = express();

const securityRoutes = require('./api/routes/security');
const topicRoutes = require('./api/routes/topic');
const mediaRoutes = require('./api/routes/media')

var admin = require("firebase-admin");

var serviceAccount = require("./api/serviceAccountKey.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://profiumtest2.firebaseio.com"
});
app
    .use(express.static(__dirname + '/public'))
    .use('/uploads', express.static('uploads'))
    .use(express.json())
    .use('/api/topic', topicRoutes)
    .use('/api/uploads', mediaRoutes)
    .get('/', (req, res) => res.sendFile(__dirname + '/index.html'))
    .listen(3000, () => console.log('Server runnig on port 3000'));
