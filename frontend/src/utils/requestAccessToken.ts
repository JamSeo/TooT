import { api } from "./api";

/** 새로고침 마다 서버로 AccessToken 요청 */
export const requestAccessToken = async () => {
  try {
    let response = await api.get<{ data: string }>("/user/refresh");
    if (response?.headers?.accesstoken) {
      return response.headers.accesstoken;
    }
  } catch {
    console.error("위치: requestAccessToken.ts, 서버 access토큰요청 실패");
  }
  return undefined;
};
