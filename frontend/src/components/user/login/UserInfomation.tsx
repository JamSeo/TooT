import { useContext } from "react";
import { UserAuthContext } from "../../../App";
import { useQuery } from "react-query";
import { api } from "../../../utils/api";

import { useGetSearchParam } from "../../../hooks/useGetSearchParam";
import { sendKakaoFriendsCode } from "../../../utils/sendKakaoFriendsCode";

import { Tooltip, Zoom } from "@mui/material";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrophy } from "@fortawesome/free-solid-svg-icons";
import Avatar from "@mui/joy/Avatar";

const UserInfomation: React.FC = () => {
  // Access 토큰 가져오기
  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken as string;

  // 1. 카카오 인증코드 읽기
  const code = useGetSearchParam("code");
  // 2. 서버로 카카오 친구목록 Auth코드 전송
  if (code?.length) {
    sendKakaoFriendsCode(code, accessToken);
  }

  // 전체랭킹 가져오기(사용자 랭킹 가져오기 위함)
  const {
    data: rankingListData,
    isLoading: rankingListLoading,
    error: rankingListError,
  } = useQuery(
    ["ranking-list"],
    async () => {
      const response = await api.get("/rank/list", {
        headers: { accesstoken: accessToken },
      });
      return response?.data?.data;
    },
    { refetchInterval: 5000 }
  );

  // 사용자 정보 가져오기
  const {
    data: userInfoData,
    isLoading: userInfoLoading,
    error: userInfoError,
  } = useQuery(
    ["user-info"],
    async () => {
      const response = await api.get("/user/userinfo", {
        headers: { accesstoken: accessToken },
      });
      return response?.data;
    },
    { refetchInterval: 5000 }
  );

  // 사용자 랭킹 저장
  if (rankingListLoading || rankingListError) return <></>;
  let myRank = rankingListData?.myRank;

  // 사용자 정보 저장
  if (userInfoLoading || userInfoError) return <></>;
  let { profileImage, name, seedMoney, cash, totalValue } = userInfoData?.data;

  return (
    <div className="h-full overflow-hidden">
      <div className="h-1/3 w-full rounded-t-lg bg-point flex justify-center items-center">
        <Avatar alt="프로필사진" src={profileImage} size="lg" sx={{ mr: 2 }} />
        <p className="text-2xl font-semibold">{name}님</p>
        <div className="flex justify-center items-center relative ml-2">
          <FontAwesomeIcon
            className="text-first text-[37px]"
            icon={faTrophy}
          ></FontAwesomeIcon>
          {typeof myRank === "number" && (
            <p className="text-lg absolute bottom-2 flex justify-center items-center">
              {myRank + 1}
            </p>
          )}
        </div>
      </div>
      <div className="h-2/3 w-full rounded-b-lg bg-white px-10 py-4 flex flex-col justify-between">
        <div>
          <Tooltip
            arrow
            placement="top-end"
            TransitionComponent={Zoom}
            title={
              <div className="p-2">
                <div className="mb-1">{name}님의</div>
                <div>총 시드머니: {seedMoney.toLocaleString()}</div>
              </div>
            }
          >
            <div className="flex items-center justify-between mb-1">
              <div className="text-slate-500">보유 현금</div>
              <div>
                <span className="text-black text-lg">
                  {cash.toLocaleString()}
                </span>
                <span>원</span>
              </div>
            </div>
          </Tooltip>
          <div className="flex items-center justify-between">
            <div className="text-slate-500">평가 금액</div>
            <div>
              <span className="text-black text-lg">
                {(totalValue - cash).toLocaleString()}
              </span>
              <span>원</span>
            </div>
          </div>
        </div>
        <div className="flex justify-between items-end">
          <p className="text-lg font-semibold text-slate-500">총 자산</p>
          <p className="text-2xl font-regualr">
            <span className="text-black">{totalValue.toLocaleString()}</span>
            <span>원</span>
          </p>
        </div>
      </div>
    </div>
  );
};

export default UserInfomation;
