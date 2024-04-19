let blog = document.getElementsByClassName('articlesWrapper')[0]

blog.onclick = function() {
    document.location.href = location.origin + '/blog'
}

let sign = document.getElementsByClassName('registrationOrLogin')[0]

sign.onclick = function() {
    document.location.href = location.origin + '/signin'
}