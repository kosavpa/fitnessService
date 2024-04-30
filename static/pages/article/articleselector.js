var createArticleElement = function(jArticle) {
    let articlesDiv = getElementByClassName('articleBody')

    articlesDiv.appendChild(createArticleHeaderDiv(jArticle))
    articlesDiv.appendChild(createSeparator())
    articlesDiv.appendChild(createArticleTextDiv(jArticle))
}

function createArticleTextDiv(jArticle) {
    let articleTextDiv = document.createElement('div')

    articleTextDiv.className = 'articleText'
    articleTextDiv.innerHTML = jArticle.text

    return articleTextDiv
}

function createArticleHeaderDiv(jArticle) {
    let articleHeaderDiv = document.createElement('div')

    articleHeaderDiv.className = 'articleHeader'
    articleHeaderDiv.appendChild(createHeaderTextElement(jArticle))
    articleHeaderDiv.appendChild(createHeaderDateElement(jArticle))

    return articleHeaderDiv
}

function createSeparator() {
    let separatorHr = document.createElement('hr')

    separatorHr.className = 'separator'

    return separatorHr
}

function createHeaderTextElement(jArticle) {
    let articleHeaderTextH2 = document.createElement('h2')

    articleHeaderTextH2.className = 'articleHeaderText'
    articleHeaderTextH2.innerHTML = jArticle.header

    return articleHeaderTextH2
}

function createHeaderDateElement(jArticle) {
    let articleHeaderDateDiv = document.createElement('div')   
    
    articleHeaderDateDiv.className = 'articleHeaderDate'
    articleHeaderDateDiv.innerHTML = jArticle.date

    return articleHeaderDateDiv
}

function getElementByClassName(className) {
    return document.getElementsByClassName(className)[0]
}

async function getArticles() {
    const response = await fetch('http://localhost:8080/article', {
        method: 'GET',
        headers: {
            'ArticleId': localStorage.getItem('articleId'),
            'Accept': 'application/json'}})

    createArticleElement(await response.json())
}
  
getArticles().then(null, null);