import { logout } from "../../utils/logout";

import Button from "@mui/joy/Button";
import LogoutIcon from "@mui/icons-material/Logout";

const LogoutButton: React.FC = () => {

  return (
    <Button
      size="sm"
      variant="soft"
      color="neutral"
      startDecorator={<LogoutIcon />}
      onClick={logout}
    >
      로그아웃
    </Button>
  );
};

export default LogoutButton;
