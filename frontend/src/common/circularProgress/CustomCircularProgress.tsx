import CircularProgress from "@mui/joy/CircularProgress";

interface IcustomCircularProgress {
  size?: "sm" | "md" | "lg";
}

/** 로딩중 화면 */
const CustomCircularProgress: React.FC<IcustomCircularProgress> = ({
  size = "lg",
}) => {
  return (
    <div className="w-full h-full flex justify-center items-center opacity-25">
      <CircularProgress variant="plain" color="neutral" size={size} />
    </div>
  );
};

export default CustomCircularProgress;
