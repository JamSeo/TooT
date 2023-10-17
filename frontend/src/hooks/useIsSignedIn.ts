import { useContext, useState, useEffect } from "react";
import { UserAuthContext } from "../App";

export const useIsSignedIn = () => {
  const userContext = useContext(UserAuthContext);
  const [isLoading, setIsLoading] = useState(true);
  const isSignedIn = !!userContext?.accessToken;

  useEffect(() => {
    // accessToken 확인 후 로딩 상태 업데이트
    setIsLoading(false);
  }, [userContext]);

  return { isSignedIn, isLoading };
};
