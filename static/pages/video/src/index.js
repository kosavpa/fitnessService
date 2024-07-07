import React from 'react';
import ReactDOM from 'react-dom/client';
import VideoFrame from './videoHub';

const root = ReactDOM.createRoot(document.getElementById('root'));

fetch('http://localhost:8100/video/videos-info')
  .then(response => {
    response.json().then(wrappers => {
      root.render(
        <React.StrictMode>
          <VideoFrame infoWrappers={wrappers} />
        </React.StrictMode>
      );
    })
  });