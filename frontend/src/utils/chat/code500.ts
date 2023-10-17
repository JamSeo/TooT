import { setRespondingFalse } from "../../store/slices/chatInputSlice";

/* 500: 데일리 퀴즈 */
export const code500 = async (code:number, dispatch:any, navigate:any) => {
  switch(code){
    case 501:
      await dispatch(setRespondingFalse());
      navigate("/quiz");
      break;
  };
};