let blog = document.getElementsByClassName('articlesWrapper')[0]

blog.onclick = function() {
    document.location.href = location.origin + '/blog'
}

let sign = document.getElementsByClassName('registrationOrLogin')[0]

sign.onclick = function() {
    document.location.href = location.origin + '/signin'
}

let video = document.getElementsByClassName('videosWrapper')[0]

video.onclick = function() {
    document.location.href = location.origin + '/video/build'
}

let chat = document.getElementsByClassName('chatWrapper')[0]

chat.onclick = function() {
    document.location.href = location.origin + '/chat-app/build'
}