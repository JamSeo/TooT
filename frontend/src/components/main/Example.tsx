import { Outlet } from "react-router-dom";

const Example = () => {
  return (
    <div className="w-full h-full min-h-0">
      <Outlet />
    </div>
  );
};

export default Example;
