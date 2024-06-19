export default function VideoFrame(props) {

  const vidios = props.infoWrappers.map(infoWrapper =>
    <div className="singleVideoWrapper">
      <video className="video" preload="none" autoPlay="false" controls="controls" poster={`http://localhost:8080/img/${infoWrapper.dirName}/${infoWrapper.imgFileName}`}>
        <source src={`http://localhost:8080/vid/${infoWrapper.dirName}/${infoWrapper.videoFileName}`} type="video/mp4" />
      </video>
    </div>
  );

  return (
    <div className="multiWrapper">
      {vidios}
    </div>
  );
}