import { api } from "./api";

export const sendKakaoFriendsCode = async (
  code: string,
  accessToken: string
) => {
  try {
    await api.get(`/rank/reissue/kakao?code=${code}`, {
      headers: {
        accesstoken: accessToken,
      },
    });
    window.location.href = "/ranking/friend";
  } catch (error) {
    console.error(
      "위치: sendKakaoFriendsCode.ts, 카카오 친구 인증코드 서버전송 실패"
    );
  }
};
