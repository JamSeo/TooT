const UserNoItem = ({itemName} : {itemName: string}) => {
  const userInfoString = localStorage.getItem("userInfo") ? localStorage.getItem("userInfo") : null;
  const userInfo = userInfoString ? JSON.parse(userInfoString) : null;
  const userName = userInfo?.name;
  return (<div className="h-[80%] flex items-center justify-center">
    <div className="text-[36px] text-gray-300">
      {userName}님의 {itemName}이 없습니다.
    </div>
  </div>)
};

export default UserNoItem;