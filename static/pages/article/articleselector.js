var createArticleElement = function(jArticle) {
    let articlesDiv = document.getElementsByClassName('articleBody')


    let articleHeaderDiv = document.createElement('div')
    let articleHeaderTextH2 = document.createElement('h2')
    let articleHeaderDateDiv = document.createElement('div')

    articleHeaderTextH2.className = 'articleHeaderText'
    articleHeaderTextH2.innerHTML = jArticle.header
    articleHeaderDateDiv.className = 'articleHeaderDate'
    articleHeaderDateDiv.innerHTML = jArticle.date
    articleHeaderDiv.className = 'articleHeader'
    articleHeaderDiv.appendChild(articleHeaderTextH2)
    articleHeaderDiv.appendChild(articleHeaderDateDiv)


    let separatorHr = document.createElement('hr')

    separatorHr.className = 'separator'

    
    let articleTextDiv = document.createElement('div')

    articleTextDiv.className = 'articleText'
    articleTextDiv.innerHTML = jArticle.text

    articlesDiv[0].appendChild(articleHeaderDiv)
    articlesDiv[0].appendChild(separatorHr)
    articlesDiv[0].appendChild(articleTextDiv)
}

async function getArticles() {
    const response = await fetch('http://localhost:8080/article', {
        method: 'GET',
        headers: {
            'ArticleId': localStorage.getItem('articleId'),
            'Accept': 'application/json'}})

    createArticleElement(await response.json())

    localStorage.removeItem('articleId')
}
  
getArticles().then(null, null);