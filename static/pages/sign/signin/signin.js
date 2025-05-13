var base64 = window.Base64

setUpForSignin()

function setUpForSignin() {
    getElementByClassName('regDiv').onclick = function () {
        document.location.href = location.origin + '/signup'
    }

    getElementByClassName('sbmt').addEventListener("click", signinSbmtEventHandler)
}

function signinSbmtEventHandler(evnt) {
    evnt.preventDefault()

    const form = new FormData(getElementByClassName('sign_in_up_RequestForm'))

    if (!formIsValid(form)) {
        alert(
            'Проверьте пароль или имя пользователя. ' +
            'Имя пользователя может содержать буквыи английского алфавита и цифры. ' +
            'Длина обоих полей может содержать четыре и более символов.')

        return false
    }

    (function () {
        fetch('http://localhost:8100/auth/signin',
            {
                method: 'GET',
                headers: { 'Authorization': 'Basic ' + getAuth(form) }
            })
            .then(response => {
                if (response.status == 200) {
                    handleSigninOk(response)
                } else {
                    handleNotOk(response)
                }
            })
    }())
}

function getAuth(form) {
    return base64.encode(form.get('usrnm') + ':' + form.get('pwd'))
}

function formIsValid(form) {
    return !form.get('usrnm')
        || form.get('usrnm').length >= 4
        || new RegExp('[a-zA-Z0-9]+').test(form.get('usrnm'))
        || !form.get('pwd')
        || form.get('pwd').length >= 4
}

function handleNotOk(response) {
    if (response.status == 401) {
        alert(
            'Проверьте выши учетные данные. ' +
            'Имя пользователя может содержать буквыи английского алфавита и цифры. ' +
            'Длина обоих полей может содержать четыре и более символов.')
    } else {
        alert('Что-то пошло не так, попробуйте выполнить запрос позже ещё раз')
    }
}

function handleSigninOk(response) {
    response.text()
    .then(jwt => {
        let expire = new Date(JSON.parse(base64.decode(jwt.split('.')[1])).exp * 1000)
    
        document.cookie = "authToken = " + jwt + "; path = /; sameSite=None; expires = " + expire.toGMTString()
    
        document.location.href = location.origin + '/'
    })
}

function getElementByClassName(classname) {
    return document.getElementsByClassName(classname)[0]
}