function createArticleElement(jArticle) {
    getElementByClassName('articles').appendChild(createArticleDiv(jArticle))
}

function createArticleDiv(jArticle) {
    let articleDiv = document.createElement('div')

    articleDiv.className = 'article'
    articleDiv.appendChild(createImageArticleDiv(jArticle))
    articleDiv.appendChild(createAboutArticleDiv(jArticle))

    return articleDiv
}

function createImageArticleDiv(jArticle) {
    let imageWrapper = document.createElement('div')

    imageWrapper.className = 'imageArticle'

    let image = document.createElement('img')

    const imgPath = jArticle.relativeImgPath.replace('|', '/')

    image.src = `${location.origin}/images/${imgPath}`

    imageWrapper.appendChild(image)

    return imageWrapper
}

function createAboutArticleDiv(jArticle) {
    let aboutArticleDiv = document.createElement('div')

    aboutArticleDiv.className = 'aboutArticle'
    aboutArticleDiv.appendChild(createHeaderAboutArticleDiv(jArticle))
    aboutArticleDiv.appendChild(createAnonsArticleDiv(jArticle))
    aboutArticleDiv.appendChild(createMoreArticleDiv(jArticle))

    return aboutArticleDiv
}

function createMoreArticleDiv(jArticle) {
    let moreDiv = document.createElement('div')

    moreDiv.className = 'more'
    moreDiv.innerHTML = '<a>Детальнее</a>'
    moreDiv.onclick = function () {
        localStorage.setItem('articleId', jArticle.id)
        document.location.href = location.origin + '/article/article.html'
    }

    return moreDiv
}

function createHeaderAboutArticleDiv(jArticle) {
    let anonsDiv = document.createElement('div')

    anonsDiv.className = 'anons'
    anonsDiv.innerHTML = jArticle.anons

    return anonsDiv
}

function createAnonsArticleDiv(jArticle) {
    let headerDiv = document.createElement('div')

    let h3 = document.createElement('h3')

    h3.innerHTML = jArticle.header

    headerDiv.className = 'header'
    headerDiv.appendChild(h3)

    return headerDiv
}

function getElementByClassName(className) {
    return document.getElementsByClassName(className)[0]
}

async function getArticles() {
    const response = await fetch('http://localhost:8080/articles');

    const data = await response.json();

    for (article of data) {
        createArticleElement(article)
    }
}

getArticles().then(null, null);