
document.addEventListener('DOMContentLoaded', () => {
    const takePhotoBtn = document.querySelector('#takePhoto')
    takePhotoBtn.addEventListener('click', () => {
        makeRequest('/api/topic', 'POST', {'Content-Type': 'application/json'}, {'action': 'take photo'})
            .then(response => console.log('res listen ' + response.stringify));
    });
})

const makeRequest = async (url, method, headers, data) => {
    const response = await fetch(url, {
        method: method || 'GET',
        headers,
        body: JSON.stringify(data)
    })
    console.log('response makerequest' + response.stringify);
    return response.json
}