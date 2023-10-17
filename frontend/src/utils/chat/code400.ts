import { setRespondingFalse } from "../../store/slices/chatInputSlice";
import stockNameToId from "./stockNameToId";

/* 400: 유저 정보 조회 */
export const code400 = async (code: number, responseData: string[], dispatch:any, navigate:any) => {
  const userInfoString = localStorage.getItem("userInfo");
  const userInfo = userInfoString ? JSON.parse(userInfoString) : null;
  const userId = userInfo ? userInfo.id : null;
  switch(code){
    case 401:
      await dispatch(setRespondingFalse());
      navigate(`/user/${userId}/stock`);
      break;
    case 402:
      const stockName = responseData[1];
      const stockId = stockNameToId[stockName];
      await dispatch(setRespondingFalse());
      navigate(`/user/${userId}/stock/${stockId}`);
      break;
    case 403:
      await dispatch(setRespondingFalse());
      navigate(`/user/${userId}/bankrupt`);
      break;
    case 404:
      await dispatch(setRespondingFalse());
      navigate(`/user/${userId}/trade`);
      break;
  };
};
