import { useState, useEffect } from 'react';
import { getEnv } from '../utils/Env';

const VideoGrid = () => {
  const [someData, setSomeData] = useState([]);

  useEffect(() => {
    fetch(getEnv().API_BASE_URL + "/someEndpoint")
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log(data);
        setSomeData(data);
      });
  }, []);

  return (
    <div className="row g-4">
      {someData?.map((entity) => (
        "" + entity
      ))}
    </div>
  );
};

export default VideoGrid;
