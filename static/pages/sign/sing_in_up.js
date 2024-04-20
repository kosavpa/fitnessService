if (document.referrer == 'http://localhost/sign/') {
    getElementByClassName('sbmt').setAttribute('value', 'Регистрация')

    getElementByClassName('regDiv').style.display = 'none'
} else {
    getElementByClassName('repeatPasswordDiv').style.display = 'none'

    getElementByClassName('regDiv').onclick = function () {
        document.location.href = location.origin + '/signup'
    }

    getElementByClassName('sbmt').setAttribute('value', 'Вход')

    getElementByClassName('sbmt').addEventListener("click", function (evnt) {
        evnt.preventDefault()

        const form = new FormData(getElementByClassName('sign_in_up_RequestForm'))

        if (!form.get('usrnm') || form.get('usrnm').length < 4 || !form.get('pwd') || form.get('pwd').length < 4) {
            alert('Что за дерьмовые значения?')

            return false
        }

        (function () {
            fetch('http://localhost:8080/signin',
                {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Basic ' + btoa(form.get('usrnm') + ':' + form.get('pwd'))
                    }
                }).then(response => response.text())
                .then(jwt => {
                    console.log(jwt)

                    let payload = jwt.split('.')[1]

                    console.log(payload)

                    let base64 = window.Base64

                    console.log(JSON.parse(base64.decode(payload)))
                })
        }())
    })
}

function getElementByClassName(classname) {
    return document.getElementsByClassName(classname)[0]
}