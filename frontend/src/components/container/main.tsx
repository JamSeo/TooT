import { Route, Routes } from "react-router-dom";
import Example from "../main/Example";

const MainContainer = () => {
  return (
    <div className="col-span-3 row-span-3 bg-yellow-300">
      <Routes>
        <Route path="/" element={<Example />}></Route>
        <Route path="/stock" element={<Example />}></Route>
        <Route path="/user" element={<Example />}></Route>
        <Route path="/rank" element={<Example />}></Route>
        <Route path="/quiz" element={<Example />}></Route>
      </Routes>
    </div>
  );
}

export default MainContainer;