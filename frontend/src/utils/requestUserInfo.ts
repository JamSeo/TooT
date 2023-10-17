import { api } from "./api";
import { Idata } from "../interface/IauthUserContext";

/** 서버로 사용자정보 요청 */
export const requestUserInfo = async (accesstoken: string) => {
  try {
    let response = await api.get<Idata>("/user/userinfo", {
      headers: {
        accesstoken: accesstoken,
      },
    });
    return response?.data?.data;
  } catch {
    console.error("requestUserInfo.ts, 사용자정보 요청실패");
  }
  return undefined;
};
