import logo from './assets/logo.svg';
import './App.css';
import { useEffect, useMemo, useState } from 'react';



function useCustomHook() {

  const [getter, setter] = useState(1)

  return getter * 2
}

function App() {
  const [value, setValue] = useState(0)

  function callme() {
    console.log(value);
    setValue(function setterFn(p) { return p + 1 })
  }


  useEffect(() => {

    // do something

  }, [deendencies])

  const data = useMemo(() => {

    return // complex calculations
  }, [dependencies])


  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <button onClick={() => callme()}>
          {value} Edit <code>src/App.tsx</code> and save to reload.
        </button>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
