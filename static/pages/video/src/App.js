function App(props) {

  const vidios = props.infoWrappers.map(infoWrapper =>
    <div>
      <video width="500px" height="700px" controls="controls" poster={`http://localhost:8080/img/${infoWrapper.dirName}/${infoWrapper.imgFileName}`}>
        <source src={`http://localhost:8080/vid/${infoWrapper.dirName}/${infoWrapper.videoFileName}`} type="video/mp4" />
      </video>
    </div>
    );

  return (
    <div>
      {vidios}
    </div>
  );
}

export default App;