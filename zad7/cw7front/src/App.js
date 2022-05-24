import './App.css';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import Layout from "./components/Layout";
import MainPage from "./components/MainPage";
import Google from "./components/Google";
import Github from "./components/Github";
import {LoginContextProvider} from "./context/LoginContext";
import GoogleAccept from "./components/GoogleAccept";
import GithubAccept from "./components/GithubAccept";

function App() {
  return (
      <LoginContextProvider>
          <BrowserRouter>
              <Routes>
                  <Route path='/' element={<Layout/>}>
                      <Route index element={<MainPage/>}/>
                      <Route path='login/google' element={<Google/>}/>
                      <Route path='login/google/:token&:email' element={<GoogleAccept/>}/>
                      <Route path='login/github' element={<Github/>}/>
                      <Route path='login/github/:token&:email' element={<GithubAccept/>}/>
                  </Route>
              </Routes>
          </BrowserRouter>
      </LoginContextProvider>
  );
}

export default App;
