let form = document.getElementsByClassName('signinRequestForm')[0]

if (location.pathname == 'signin') {
    form.setAttribute('method', 'GET')
    form.setAttribute('action', 'http://localhost:8080/signin')

    let repeatPasswordDiv = document.getElementsByClassName('repeatPasswordDiv')[0]

    repeatPasswordDiv.style.display = 'none'

    let regDiv = document.getElementsByClassName('regDiv')[0]

    regDiv.onclick = function () {
        document.location.href = location.origin + '/signup'
    }
} else if (location.pathname == 'signup') {
    form.setAttribute('method', 'POST')
    form.setAttribute('action', 'http://localhost:8080/signup')

    let repeatPasswordDiv = document.getElementsByClassName('regDiv')[0]

    repeatPasswordDiv.style.display = 'none'
}