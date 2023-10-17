import { code100 } from "./code100";
import { code200 } from "./code200";
import { code300 } from "./code300";
import { code400 } from "./code400";
import { code500 } from "./code500";
import { code600 } from "./code600";
import { code700 } from "./code700";
import { code800 } from "./code800";
import { code900 } from "./code900";

export const codeSwitch = (responseData:string[], userMessage:string, dispatch:any, navigate: any, userAuthContext:any) => {
  const code = parseInt(responseData[0]);
  switch (true) {
    case 100 <= code && code < 200 :
      code100(code, responseData, dispatch, navigate);
      break;
    case 200 <= code && code < 300:
      code200(code, responseData, dispatch, navigate);
      break;
    case 300 <= code && code < 400 :
      code300(code, dispatch, navigate);
      break;
    case 400 <= code && code < 500:
      code400(code, responseData, dispatch, navigate);
      break;
    case 500 <= code && code < 600:
      code500(code, dispatch, navigate);
      break;
    case 600 <= code && code < 700 :
      code600(code, dispatch, navigate);
      break;
    case 700 <= code && code < 800 :
      code700(code, dispatch, navigate, userAuthContext);
      break;
    case 800 <= code && code < 900 :
      code800(code, dispatch);
      break;
    case 900 <= code && code < 1000:
      code900(code, userMessage, dispatch);
      break;
  };
};
