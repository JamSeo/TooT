import Avatar from "@mui/joy/Avatar";

const UserRanking = ({
  index,
  user,
  userRank,
}: {
  index: number;
  user: {
    name: string;
    profileImage: string;
    bankruptcyNo: number;
    netProfit: number;
  };
  userRank: number;
}) => {
  return (
    <div className="flex items-center justify-around w-[80%] p-2 bg-lightYellow border-solid border rounded-lg border-gray-200">
      <div
        className={`text-[52px] align-middle ${
          userRank === 0 ? "text-first " : " "
        }${userRank === 1 ? "text-second " : " "}${
          userRank === 2 ? "text-third " : " "
        }`}
      >
        {userRank + 1}
      </div>
      <div className="align-middle">
        <Avatar
          src={user.profileImage}
          sx={{
            "--Avatar-size": "55px",
          }}
        />
      </div>
      <div className={`text-[28px] align-middle`}>{user.name}</div>
      <div
        className={`text-[28px] align-middle ${
          user.netProfit > 0 ? "text-stockRed" : "text-stockBlue"
        }`}
      >
        {user.netProfit.toLocaleString("ko-KR")}
        <span className="text-stockGray"> 원</span>
      </div>
      <div className={`text-[20px] align-middle text-stockGray`}>
        파산 {user.bankruptcyNo}회
      </div>
    </div>
  );
};

export default UserRanking;
