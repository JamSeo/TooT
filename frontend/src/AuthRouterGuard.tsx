import { Outlet } from "react-router-dom";
import { useIsSignedIn } from "./hooks/useIsSignedIn";
import CustomCircularProgress from "./common/circularProgress/CustomCircularProgress";

const AuthRouterGuard = () => {
  let { isSignedIn, isLoading } = useIsSignedIn();

  if (isLoading) return <CustomCircularProgress />;

  if (!isSignedIn) {
    alert("로그인 후 이용해주시기 바랍니다.");
    window.history.back();
    return null;
  }

  return <Outlet />;
};

export default AuthRouterGuard;
