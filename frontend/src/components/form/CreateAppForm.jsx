import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import { Button } from "@material-ui/core";
import { appAxios } from "../../utils/axios";
import { useParams } from "react-router";

const useStyles = makeStyles((theme) => ({
  root: {
    "& > *": {
      // margin: theme.spacing(1),
      // width: "25ch",
    },
  },
}));

export default function CreateAppForm({ close, appList, setAppList }) {
  const params = useParams();
  const [appName, setAppName] = useState("");
  const [gitUrl, setGitUrl] = useState("");
  const classes = useStyles();

  async function onClick() {
    if (appName && gitUrl) {
      const response = await appAxios.postAppByProjectId(params.id, {
        title: appName,
        gitUrl,
      });
      setAppList([...appList, response]);
      close();
    }
  }

  return (
    <>
      <form className={classes.root} noValidate autoComplete="off">
        <TextField
          style={{ width: "100%" }}
          onChange={(event) => setAppName(event.target.value)}
          label="App Name"
        />
        <TextField
          style={{ width: "100%" }}
          onChange={(event) => setGitUrl(event.target.value)}
          label="Git Repository URL"
        />
        <div style={{ textAlign: "right" }}>
          <Button onClick={onClick}>Create</Button>
        </div>
      </form>
    </>
  );
}
