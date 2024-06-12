import MainRouterController from "./Router/MainRouterController";
  

function App() {
  localStorage.setItem("loginHeader",false)

  return (
    <div className="App">
      <MainRouterController />
    </div>
  );
}

export default App;
