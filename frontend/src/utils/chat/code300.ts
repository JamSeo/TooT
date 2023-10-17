import { setRespondingFalse } from "../../store/slices/chatInputSlice";

/* 300: 랭킹 조회 */
export const code300 = async (code:number, dispatch:any, navigate:any) => {
  switch(code){
    case 301:
      await dispatch(setRespondingFalse());
      navigate("/ranking/total");
      break;
    case 302:
      await dispatch(setRespondingFalse());
      navigate("/ranking/friend");
  };
};