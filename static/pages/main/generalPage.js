var wrappers = document.getElementsByClassName("navigationWrapper")

for(elem of wrappers) {
    elem.firstElementChild .addEventListener("click", (e) => window.open('file:///D:/Owl/java_applications/fitness-service/static/pages/blog/blog.html', '_blank'))
}