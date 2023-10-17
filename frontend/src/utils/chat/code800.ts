import { setRespondingFalse } from "../../store/slices/chatInputSlice";

/* 800: 기타 */
export const code800 = async (code:number, dispatch:any) => {
  switch(code){
    case 800:
      await dispatch(setRespondingFalse());
      break;
  };
};
