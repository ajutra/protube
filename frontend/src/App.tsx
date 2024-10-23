import { useState } from 'react';
import logo from './assets/logo.svg';
import './App.css';
import axios from 'axios';

function App() {
  const [data, setData] = useState<any[] | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  const getVideoData = async () => {
    setLoading(true);
    try {
      const response = await axios.get('http://localhost:8080/videos');
      setData(response.data);
    } catch (error) {
      console.error('Error fetching data', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <a
          className="App-link"
          href="#"
          onClick={getVideoData}
        >
          API Call
        </a>
        {loading && <p>Loading...</p>}
        {data && (
        <div>
          <h2>Data from API:</h2>
            {data.map((item, index) => (
              <li key={index}>
                {JSON.stringify(item)}
              </li>
            ))}
        </div>
      )}
      </header>
    </div>
  );
}

export default App;
