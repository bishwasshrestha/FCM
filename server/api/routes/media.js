const router = require('express').Router();
const upload = require('../util/multer');
const db = require('../database/db');

router.post('/',upload.single('my_media'), db.Media.uploadFile, (req, res) => res.send({message: 'uploaded'}));

module.exports = router;