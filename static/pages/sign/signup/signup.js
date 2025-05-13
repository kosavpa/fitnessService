var base64 = window.Base64

setUpForSignup()

function setUpForSignup() {
    getElementByClassName('sbmt').addEventListener("click", signupSbmtEventHandler)
}

function signupSbmtEventHandler(evnt) {
    evnt.preventDefault()

    const form = new FormData(getElementByClassName('sign_in_up_RequestForm'))

    if (!formIsValid(form)) {
        alert(
            'Проверьте пароль или имя пользователя. ' +
            'Имя пользователя может содержать буквыи английского алфавита и цифры. ' +
            'Длина обоих полей может содержать четыре и более символов. ' +
            'Кроме того пароль и повтор пароля должны совпадать.')

        return false
    }

    (function () {
        fetch('http://localhost:8100/auth/signup', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(new User(form.get('usrnm'), form.get('pwd')))
        })
            .then(response => {
                if (response.status == 201) {
                    handleSignupOk()
                } else {
                    handleNotOk(response)
                }
            })
    }())
}

function handleSignupOk() {
    document.location.href = location.origin + '/signin'
}

function formIsValid(form) {
    isCommonFieldsValid = !form.get('usrnm')
        || form.get('usrnm').length >= 4
        || new RegExp('[a-zA-Z0-9]+').test(form.get('usrnm'))
        || !form.get('pwd')
        || form.get('pwd').length >= 4

    return isCommonFieldsValid && form.get('pwd') === form.get('rpwd')
}

function handleNotOk(response) {
    if (response.status == 400) {
        response.text()
        .then(serverResponse => {
            if (serverResponse == 'This username is used!') {
                alert('Пользователь с таким именем уже существует, используйте другой.')
            } else {
                alert(
                    'Проверьте пароль или имя пользователя. ' +
                    'Имя пользователя может содержать буквыи английского алфавита и цифры. ' +
                    'Длина обоих полей может содержать четыре и более символов. ' +
                    'Кроме того пароль и повтор пароля должны совпадать.')
            }
        })
    } else {
        alert('Что-то пошло не так, попробуйте выполнить запрос позже ещё раз.')
    }
}

function getElementByClassName(classname) {
    return document.getElementsByClassName(classname)[0]
}

class User {
    username
    password

    constructor(username, password) {
        this.username = username
        this.password = password
    }
}