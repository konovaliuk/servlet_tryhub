const doRequest = async (path, method) => {
    const response = await new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open(method, path, false);
        xhr.onreadystatechange = () => {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                resolve(xhr.responseText);
            }
        }
        xhr.send();
    }).then((response) => {
        return response;
    });

    return JSON.parse(response);
}