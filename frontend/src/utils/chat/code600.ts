import { setRespondingFalse } from "../../store/slices/chatInputSlice";

/* 600: 메인 & 튜토리얼 조회 */
export const code600 = async (code:number, dispatch:any, navigate:any) => {
  switch(code) {
    case 601:
      await dispatch(setRespondingFalse());
      navigate("/");
      break;
    case 602:
      await dispatch(setRespondingFalse());
      navigate("/tutorials");
  };
};