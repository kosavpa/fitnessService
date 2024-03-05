var createArticleElement = function(jArticle) {
    let articlesDiv = document.getElementsByClassName('articles')

    let articleDiv = document.createElement('div')

    let imageArticleDiv = document.createElement('div')
    let imageArticle = document.createElement('img')

    imageArticle.src = 'http://localhost:8080/img/' + jArticle.relativeImgPath
    
    imageArticleDiv.className = 'imageArticle'
    imageArticleDiv.appendChild(imageArticle)

    let aboutArticleDiv = document.createElement('div')
    let headerDiv = document.createElement('div')
    let h3 = document.createElement('h3')

    h3.innerHTML = jArticle.header
    headerDiv.className = 'header'
    headerDiv.appendChild(h3)


    let anonsDiv = document.createElement('div')

    anonsDiv.className = 'anons'
    anonsDiv.innerHTML = jArticle.anons
    
    let moreDiv = document.createElement('div')

    moreDiv.className = 'more'
    moreDiv.innerHTML = '<a>Детальнее</a>'
    moreDiv.onclick = function() {
        localStorage.setItem('articleId', jArticle.id)
        document.location.href = location.origin + '/article/article.html'
    }

    aboutArticleDiv.className = 'aboutArticle'
    aboutArticleDiv.appendChild(headerDiv)
    aboutArticleDiv.appendChild(anonsDiv)
    aboutArticleDiv.appendChild(moreDiv)

    articleDiv.className = 'article'
    articleDiv.appendChild(imageArticleDiv)
    articleDiv.appendChild(aboutArticleDiv)

    articlesDiv[0].appendChild(articleDiv)
}

async function getArticles() {
    const response = await fetch('http://localhost:8080/articles');

    const data = await response.json();

    for(article of data) {
        createArticleElement(article)
    }
}
  
getArticles().then(null, null);