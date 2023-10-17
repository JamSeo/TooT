import { Outlet } from "react-router-dom"

const MainUserContainer = () => {
    return (
    <div className="w-full h-full min-h-0">
        <Outlet />
    </div>);
};

export default MainUserContainer;