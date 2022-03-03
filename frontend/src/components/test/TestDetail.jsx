import React, { useEffect, useState } from "react";
import Box from "@material-ui/core/Box";
import Grid from "@material-ui/core/Grid";
import TestItemContainer from "./TestItemContainer";
import ReportHistoryContainer from "./ReportHistoryContainer";
import { makeStyles } from "@material-ui/core/styles";
import { reportAxios, testAxios } from "../../utils/axios";
import datetime from "../../utils/moment";
import styled from "styled-components";
import { colors } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
  container: {
    display: "flex",
    alignItems: "center",
    textAlign: "center",
  },
}));

export default function TestDetail(props) {
  const styles = useStyles();
  const [idx, setIdx] = useState(-1);
  const [report, setReport] = useState([]);
  const [reportHistory, setReportHistory] = useState([]);
  const [tests, setTests] = useState([]);
  const [searchField, setSearchField] = useState("");
  const [filteredTests, setFilteredTests] = useState([]);
  const [condition, setCondition] = useState([]);

  useEffect(async () => {
    console.log(props.id);
    const reportResponse = await reportAxios.getListByAppId(props.aid);
    setReport(reportResponse);

    let selectedReportList = [];
    for (let i = 0; i < reportResponse.length; i++) {
      if (reportResponse[i].id == props.id) {
        for (let j = i; j <= i + 6; j++) {
          if (reportResponse.length <= j) continue;
          if (selectedReportList.length >= 7) break; // max length

          let string = JSON.stringify(reportResponse[j]);
          string = string.replace("}", `,"index":${j}}`);
          selectedReportList.push(JSON.parse(string));
        }
        break;
      }
    }
    console.log(selectedReportList);

    setReportHistory(selectedReportList);
    setIdx(0);
    getReportData(selectedReportList[0].id, 0);
  }, []);

  useEffect(() => {
    setFilteredTests(() => tests.filter(testFilter));
  }, [searchField, condition, tests]);

  async function getReportData(id, index) {
    await testAxios
      .getListByReportId(id)
      .then((res) => {
        setTests(res);
        console.log(index);
        setIdx(index);
        setCondition("1111");
        console.log(id);
      })
      .catch((err) => {
        console.log(err);
      });
  }

  const reports = reportHistory.map((data, id) => (
    <Box
      onClick={() =>
        getReportData(data.id, data.index - reportHistory[0].index)
      }
      key={id}
    >
      <ReportHistoryContainer
        testCount={report.length - data.index}
        date={data.datetime}
        percent={
          data.totalRunCount == 0
            ? 0
            : data.totalRunCount /
              (data.totalRunCount +
                data.totalErrorCount +
                data.totalFailCount +
                data.totalSkipCount)
        }
        targetPercent={0.7}
      ></ReportHistoryContainer>
    </Box>
  ));

  const testList = filteredTests.map((data, index) => (
    <Box
      style={{
        marginTop: "1%",
        marginBottom: "1%",
      }}
      key={index}
    >
      <TestItemContainer
        name={data.name}
        time={data.elapsedTime}
        resultType={data.type}
        errorType={data.errorType}
        errorMessage={data.errorMessage}
      />
    </Box>
  ));

  function testFilter(obj) {
    if (
      obj.type == "pass" &&
      condition[0] == "1" &&
      obj.name.toLowerCase().includes(searchField.toLowerCase())
    )
      return true;
    if (
      obj.type == "fail" &&
      condition[1] == "1" &&
      obj.name.toLowerCase().includes(searchField.toLowerCase())
    )
      return true;
    if (
      obj.type == "error" &&
      condition[2] == "1" &&
      obj.name.toLowerCase().includes(searchField.toLowerCase())
    )
      return true;
    if (
      obj.type == "skip" &&
      condition[3] == "1" &&
      obj.name.toLowerCase().includes(searchField.toLowerCase())
    )
      return true;
    return false;
  }
  return (
    <Box
      display="flex"
      marginTop="2%"
      zIndex="1"
      style={{
        height: "90%",
        overflowY: "scroll",
      }}
    >
      <Box alignItems="center" textAlign="Left">
        <Box display="flex" marginLeft="30px" marginTop="15%" minHeight="22%">
          <Box
            style={{
              borderLeft: "2px solid black",
              zIndex: "0",
              position: "relative",
              left: "70px",
            }}
          ></Box>
          <Box
            style={{
              width: "14px",
              borderTop: "2px solid black",
              borderBottom: "2px solid black",
              zIndex: "0",
              position: "relative",
              left: "62px",
            }}
          ></Box>
          <Box
            display="flex"
            justifyContent="space-between"
            flexDirection="column"
            zIndex="1"
            position="relative"
            left="35px"
          >
            {reports}
          </Box>
        </Box>
      </Box>
      <Box
        className={styles.container}
        width="80%"
        marginLeft="7%"
        marginRight="1%"
        display="flex"
        flexDirection="column"
      >
        {idx >= 0 && (
          <Box
            width="100%"
            display="flex"
            justifyContent="space-between"
            marginTop="1%"
          >
            <Box fontWeight="bold">
              Report #{report.length - reportHistory[idx].index} -{" "}
              {datetime(reportHistory[idx].datetime)} (
              {reportHistory[idx].totalElapsedTime} ms)
            </Box>
            <input
              type="search"
              placeholder="filter"
              onChange={(e) => setSearchField(e.target.value)}
            ></input>
          </Box>
        )}
        {idx >= 0 && (
          <Box
            className={styles.container}
            fontSize="200%"
            marginTop="5px"
            style={{ cursor: "pointer" }}
            width="100%"
          >
            <Box
              className={
                condition[0] == "1" ? "typeButtonActive" : "typeButton"
              }
              flexGrow={reportHistory[idx].totalRunCount + 1}
              onClick={getPassList}
            >
              <Box
                style={{
                  backgroundColor: "#77EE77",
                }}
              >
                Pass
              </Box>
              <Box
                style={{
                  backgroundColor: "#22DD22",
                }}
              >
                {reportHistory[idx].totalRunCount}
              </Box>
            </Box>
            <Box
              className={
                condition[1] == "1" ? "typeButtonActive" : "typeButton"
              }
              flexGrow={reportHistory[idx].totalFailCount + 1}
              onClick={getFailList}
            >
              <Box
                style={{
                  backgroundColor: colors.red[400],
                }}
              >
                Fail
              </Box>
              <Box
                style={{
                  backgroundColor: colors.red[600],
                }}
              >
                {reportHistory[idx].totalFailCount}
              </Box>
            </Box>
            <Box
              className={
                condition[2] == "1" ? "typeButtonActive" : "typeButton"
              }
              flexGrow={reportHistory[idx].totalErrorCount + 1}
              onClick={getErrorList}
            >
              <Box
                style={{
                  backgroundColor: colors.orange[400],
                }}
              >
                Error
              </Box>
              <Box
                style={{
                  backgroundColor: colors.orange[600],
                }}
              >
                {reportHistory[idx].totalErrorCount}
              </Box>
            </Box>
            <Box
              className={
                condition[3] == "1" ? "typeButtonActive" : "typeButton"
              }
              flexGrow={reportHistory[idx].totalSkipCount + 1}
              onClick={getSkipList}
            >
              <Box
                style={{
                  backgroundColor: colors.grey[400],
                }}
              >
                Skip
              </Box>
              <Box
                style={{
                  backgroundColor: colors.grey[500],
                }}
              >
                {reportHistory[idx].totalSkipCount}
              </Box>
            </Box>
          </Box>
        )}
        {/* 스크롤 가능하게 만들기 */}
        <Box
          style={{
            position: "relative",
            width: "100%",
            zIndex: "0",
          }}
        >
          {testList}
        </Box>
      </Box>
    </Box>
  );

  function getPassList() {
    if (condition[0] == "1")
      setCondition("0" + condition[1] + condition[2] + condition[3]);
    else setCondition("1" + condition[1] + condition[2] + condition[3]);
  }

  function getFailList(e) {
    if (condition[1] == "1")
      setCondition(condition[0] + "0" + condition[2] + condition[3]);
    else setCondition(condition[0] + "1" + condition[2] + condition[3]);
  }

  function getErrorList(e) {
    if (condition[2] == "1")
      setCondition(condition[0] + condition[1] + "0" + condition[3]);
    else setCondition(condition[0] + condition[1] + "1" + condition[3]);
  }

  function getSkipList(e) {
    if (condition[3] == "1")
      setCondition(condition[0] + condition[1] + condition[2] + "0");
    else setCondition(condition[0] + condition[1] + condition[2] + "1");
  }
}
