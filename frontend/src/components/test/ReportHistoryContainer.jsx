import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Box from "@material-ui/core/Box";
import styled from "styled-components";

const useStyles = makeStyles((theme) => ({
  container: {
    display: "flex",
    alignItems: "center",
    textTransform: "none",
    paddingTop: "30%",
    paddingBottom: "30%",
  },
}));

const CircleButton = styled.button`
  background-color: ${(props) => props.color || "#22DD22"};
  min-width: 40px;
  min-height: 40px;
  border-radius: 50%;
  font-size: ${(props) => props.size || "75%"};
  text-align: center;
  color: white;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  display: inline-block;
  transition: all 0.2s ease-in-out;
  &:before {
    content: "";
    background-color: rgba(255, 255, 255, 0.5);
    height: 100%;
    width: 3em;
    display: block;
    position: absolute;
    top: 0;
    left: -4.5em;
    transform: skewX(-45deg) translateX(0);
    transition: none;
  }
  &:hover {
    background-color: ${(props) => props.color || "#22DD22"};
    color: #fff;
  }
  &:hover:before {
    transform: skewX(-45deg) translateX(13.5em);
    transition: all 0.5s ease-in-out;
  }
`;

export default function ReportHistoryContatiner(props) {
  const styles = useStyles();
  let time = props.date.split("T");
  let date = time[0].split("-");

  //props.percentage에 따라서 색을 다르게 출력
  return (
    <Box>
      <Box>
        {time.length > 0 && (
          <Box className={styles.container}>
            {props.percent >= 0.975 && props.testCount <= 9999 && (
              <CircleButton color="#22DD22">#{props.testCount}</CircleButton>
            )}
            {props.percent >= 0.9 &&
              props.percent < 0.975 &&
              props.testCount <= 9999 && (
                <CircleButton color="#FFAA00">#{props.testCount}</CircleButton>
              )}
            {props.percent < 0.9 && props.testCount <= 9999 && (
              <CircleButton color="#FF0000">#{props.testCount}</CircleButton>
            )}
            {props.percent >= 0.975 && props.testCount > 9999 && (
              <CircleButton color="#22DD22" size="50%">
                #{props.testCount}
              </CircleButton>
            )}
            {props.percent >= 0.9 &&
              props.testCount > 9999 &&
              props.percent < 0.975 && (
                <CircleButton color="#FFAA00" size="50%">
                  #{props.testCount}
                </CircleButton>
              )}
            {props.percent < 0.9 && props.testCount > 9999 && (
              <CircleButton color="#FF0000" size="50%">
                #{props.testCount}
              </CircleButton>
            )}
            <Box display="flex" flexDirection="column" marginLeft="5%">
              <Box fontSize="70%">
                {date[0][2]}
                {date[0][3]}/{date[1]}/{date[2]}
              </Box>
              <Box fontSize="70%">{time[1]}</Box>
            </Box>
          </Box>
        )}
      </Box>
    </Box>
  );
}

// 직은 페이지만 만들면됌
