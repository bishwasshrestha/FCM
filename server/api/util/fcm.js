'use strict';

const network = require('./network');

const sendDataMessage = (data) => {
    network.makeRequest(
        'https://fcm.googleapis.com/fcm/send',
        'POST',
        {
            'Authorization' : 'key=' + process.env.SERVER_KEY,
            'Content-Type' : 'application/json'
        },
        {
            "to" : "/topics/weather",
            "data" : {
                "action" : "take photo"
            }
        }
        ).then(response => {
            console.log(response);
        }).catch(error => console.log(error));
}

module.exports = {sendDataMessage}