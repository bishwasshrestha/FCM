'use strict';

const router = require('express').Router();
const fcm = require('../util/fcm');

router.post('/', (req, res) => {
    res.send({message: 'post message works'});
    fcm.sendDataMessage(req.body)
})

module.exports = router;



