import React, { useState } from "react";
import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";
import { userAxios } from "../../utils/axios";
import { useDispatch, useSelector } from "react-redux";
import { LOG_IN } from "../../redux/user";

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

export default function Login() {
  const classes = useStyles();
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");
  // const user = useSelector((state) => state.user);
  const dispatch = useDispatch();

  async function login() {
    const response = await userAxios.postLogin({
      id: id,
      pwd: password,
    });
    if (response) {
      delete response.password;
      dispatch(LOG_IN(response));
    } else {
      alert("Failed to login");
      setId("");
      setPassword("");
    }
  }

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <form className={classes.form} noValidate>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="id"
            label="EmployeeNumber"
            name="id"
            autoComplete="id"
            autoFocus
            value={id}
            onChange={({ target: { value } }) => setId(value)}
            onKeyPress={(e) => {
              if (e.key == "Enter") login();
            }}
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
            value={password}
            onChange={({ target: { value } }) => setPassword(value)}
            onKeyPress={(e) => {
              if (e.key == "Enter") login();
            }}
          />
          <Button
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={login}
          >
            Log In
          </Button>
        </form>
      </div>
    </Container>
  );
}
