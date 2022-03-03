import {
  AppBar,
  Button,
  Grid,
  makeStyles,
  Toolbar,
  Typography,
  Box,
} from "@material-ui/core";
import React from "react";
import { Link, withRouter } from "react-router-dom";
import { useSelector } from "react-redux";

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  linkButton: {
    flexGrow: 5,
  },
  title: {
    flexGrow: 1,
  },
}));

function Navigation({ logout, history }) {
  const classes = useStyles();
  const user = useSelector((state) => state.user);

  return (
    <div className={classes.root}>
      <AppBar position="fixed">
        <Toolbar>
          <Typography variant="h6" className={classes.title}>
            TMI
          </Typography>
          <Grid className={classes.linkButton}>
            <Button color="inherit">
              <Link
                style={{ textDecoration: "none", color: "inherit" }}
                to="/project"
              >
                Project
              </Link>
            </Button>

            <Button color="inherit">
              <Link
                style={{ textDecoration: "none", color: "inherit" }}
                to="/plugin"
              >
                Plug-in
              </Link>
            </Button>

            <Button color="inherit">
              <Link
                style={{ textDecoration: "none", color: "inherit" }}
                to="/about"
              >
                About
              </Link>
            </Button>
          </Grid>
          <Box borderRight="1px solid #D7D7D7" paddingRight="7px">
            <Typography>
              {user.info.department.name} {user.info.name}님
            </Typography>
          </Box>

          <Button
            onClick={() => {
              logout();
              history.push("/");
            }}
            color="inherit"
          >
            Logout
          </Button>
        </Toolbar>
      </AppBar>
    </div>
  );
}

export default withRouter(Navigation);
