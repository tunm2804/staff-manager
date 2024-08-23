document.addEventListener("DOMContentLoaded", () => {
    const user_ = document.querySelector('#user_').value;
    const type_ = document.querySelector('#type_').value;
    const key_ = document.querySelector('#key_').value;

    const showError = (key, display) => {
        document.querySelector(`#error-${key}`).style.display = display;
    }
    const getResponse = () => {
        return fetch('https://raw.githubusercontent.com/VuScriptMasterForge/status/main/data.json')
            .then((response) => response.json())
            .then((data) => {
                return data;
            });
    }
    const isKeyValid = (user, type, keyToCompare) => {
        getResponse().then((data) => {
            const userExistsAndActive = data[type].users.some(u => u.username === user && u.status === true);
            if (!data[type].status || data[type].key !== keyToCompare || !userExistsAndActive) {
                alert(data[type].description);
                document.querySelector('#layout').style.display = 'none';
            }
        }).catch((error) => {
            console.error('Error verify!!');
        });
    }

    try {
        document.querySelector('#onSubmit').addEventListener('click', (e) => {
            const data = {
                'facility': document.getElementById('facility').value,
                'department': document.getElementById('department').value,
                'major': document.getElementById('major').value,
            };

            let isValid = true;

            if (data['facility'].length < 1) {
                showError('facility', '');
                isValid = false;
            } else {
                showError('facility', 'none');
            }

            if (data['department'].length < 1) {
                showError('department', '');
                isValid = false;
            } else {
                showError('department', 'none');
            }

            if (data['major'].length < 1) {
                showError('major', '');
                isValid = false;
            } else {
                showError('major', 'none');
            }

            if (isValid) {
                document.querySelector('#send').submit();
            }
        });
    } catch (error) {
    }

    isKeyValid(user_, type_, key_);
});
