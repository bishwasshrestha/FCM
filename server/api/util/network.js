'use strict';

const fetch = require('node-fetch');

const makeRequest = async (url, method, headers, data) => {
    const response = await fetch(url, {
        method: method || 'GET',
        headers,
        body: JSON.stringify(data)
    });
    return response.json()
}

module.exports = {makeRequest}