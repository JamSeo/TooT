import { api } from "./api";

export const logout = async () => {
  await api.delete("/user/logout");  // Cookie 안 Refresh토큰 삭제
  localStorage.removeItem("userInfo");  // LocalStorage 안 사용자정보 삭제
  window.location.replace("/");
};
