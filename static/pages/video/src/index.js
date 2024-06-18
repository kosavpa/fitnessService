import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));

fetch('http://localhost:8080/videos-info')
  .then(response => {
    response.json().then(wrappers => {
      root.render(
        <React.StrictMode>
          <App infoWrappers={wrappers} />
        </React.StrictMode>
      );
    })
  });