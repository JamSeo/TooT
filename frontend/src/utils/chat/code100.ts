import { setRespondingFalse } from "../../store/slices/chatInputSlice";
import stockNameToId from "./stockNameToId";

/* 100: 주식 조회 */
export const code100 = async (code:number, responseData:string[], dispatch:any, navigate:any) => {
  switch(code) {
    case 101:
      await dispatch(setRespondingFalse());
      navigate("/stock/all");
      break;
    case 102:
      const stockName:string = responseData[1];
      const stockId = stockNameToId[stockName];
      await dispatch(setRespondingFalse());
      navigate(`/stock/${stockId}`);
      break;
  };
};