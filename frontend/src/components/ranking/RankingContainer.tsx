import { useContext } from "react";
import { useQuery } from "react-query";
import { UserAuthContext } from "../../App";
import { REACT_APP_SERVER, REDIRECT_URI } from "../../utils/api";
import axios from "axios";

import Ranking from "./Ranking";
import UserRanking from "./UserRanking";
import Title from "../../common/etc/Title";
import CustomCircularProgress from "../../common/circularProgress/CustomCircularProgress";

import { Helmet } from "react-helmet";

const RankingContainer = ({ title }: { size: string; title: string }) => {
  const userAuthContext = useContext(UserAuthContext);
  const accessToken = userAuthContext?.accessToken;
  let rankingAPI = `${REACT_APP_SERVER}/rank/list`;

  if (title === "친구 랭킹") {
    rankingAPI += "/kakao";
  }

  const { data, isLoading, isError } = useQuery(["ranking-list", title], async () => {
    const response = await axios.get(rankingAPI, {
      headers: {
        accesstoken: accessToken,
      },
    });

    // 카카오 친구목록 공개 미동의? 동의 화면으로 이동
    if (response?.data.success === false) {
      const CLIENT_ID = "d1fc52f81b5a4dd2f6ae29b5fb7d6932";
      const kakaoFriendsUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code&scope=friends`;
      return (window.location.href = kakaoFriendsUrl);
    }

    return response?.data?.data;
  });

  if (isLoading || isError) {
    return <CustomCircularProgress />;
  }

  return (
    <div className="w-full h-full p-8 min-h-0">
      <Helmet>
        <title>{`TooT - ${title}`}</title>
      </Helmet>
      <div className="flex">
        <Title title={title} isTrophy={true} />
      </div>
      <div className="w-full flex justify-center mt-2.5 mb-8">
        <UserRanking index={0} user={data?.myInfo} userRank={data?.myRank} />
      </div>
      <hr />
      <div className="w-[95%] h-[70%] h-min-0">
        <Ranking size="big" rankingList={data?.list} />
      </div>
    </div>
  );
};

export default RankingContainer;
