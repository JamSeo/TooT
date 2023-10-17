import KakaoLogin from "./../user/login/KakaoLogin";
import UserInfomation from "../user/login/UserInfomation";
import { useIsSignedIn } from "../../hooks/useIsSignedIn";
import CustomCircularProgress from "../../common/circularProgress/CustomCircularProgress";

const UserContainer = () => {
  let { isSignedIn, isLoading } = useIsSignedIn();

  if (isLoading) {
    return <CustomCircularProgress size="md" />;
  }

  return (
    <div className="col-span-1 row-span-1 drop-shadow-md">
      {isSignedIn ? <UserInfomation /> : <KakaoLogin />}
    </div>
  );
};

export default UserContainer;
