import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Paper from "@material-ui/core/Paper";
import Box from "@material-ui/core/Box";

const useStyles = makeStyles((theme) => ({
  container: {
    paddingLeft: "2%",
    paddingRight: "2%",
    paddingTop: "1%",
    paddingBottom: "1%",
    backgroundColor: "black",
  },
}));

export default function TestErrorContainer(props) {
  const styles = useStyles();

  return (
    // 1. 색칠하는 부분 4가지로 나누기
    // 2. Fail, Error일 때 DropDown 버튼 or OnClick 추가하기
    // 네모 칸만 만들면 된다
    <Box
      style={{
        marginTop: "1%",
        marginBottom: "1%",
      }}
    >
      <Paper>
        <Box className={styles.container}>
          <Box
            style={{
              textAlign: "left",
              fontSize: "120%",
              color: "#44FF44",
            }}
          >
            {props.name}
          </Box>

          <Box
            style={{
              textAlign: "left",
              fontSize: "100%",
              color: "#FFFFFF",
            }}
          >
            {props.content}
          </Box>
        </Box>
      </Paper>
    </Box>
  );
}
