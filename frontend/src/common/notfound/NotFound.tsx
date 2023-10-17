import logo_dark from "./../../assets/images/logo/logo_dark.png";
import { styled } from "@mui/material/styles";
import Button, { ButtonProps } from "@mui/material/Button";
import { blue } from "@mui/material/colors";

const NotFound: React.FC = () => {
  return (
    <div className="h-full flex flex-col items-center justify-center">
      <img src={logo_dark} alt="logo_yellow" width={200} className="mb-4" />
      <h2 className="mb-4 text-lg font-bold text-slate-800">
        서비스 이용에 불편을 드려 죄송합니다.
      </h2>
      <p className="text-sm text-slate-600">
        요청하신 페이지가 존재하지 않거나 오류가 발생했습니다.
      </p>
      <p className="mb-4 text-sm text-slate-600">
        잠시 후에 다시 접속을 시도해 주십시오.
      </p>
      <a href="/">
        <ColorButton variant="contained" size="medium">
          홈으로
        </ColorButton>
      </a>
    </div>
  );
};

export default NotFound;

const ColorButton = styled(Button)<ButtonProps>(({ theme }) => ({
  color: theme.palette.getContrastText(blue[500]),
  backgroundColor: blue[400],
  "&:hover": {
    backgroundColor: blue[700],
    boxShadow: "none",
  },
  boxShadow: "none",
  width: 120,
}));
