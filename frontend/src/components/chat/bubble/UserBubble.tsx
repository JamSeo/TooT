const UserBubble = ({text}: {text:string}) => {
  return (<div className="whitespace-pre-line w-fit place-self-end p-[6px] mb-[5px] rounded-lg bg-slate-200 font-light text-[14px] leading-5 text-gray-700">{text}</div>);
};
export default UserBubble;