import * as React from "react";
import {
  DataGrid,
  GridToolbarContainer,
  GridToolbarExport,
} from "@material-ui/data-grid";
import datetime from "../../utils/moment";
import {
  AppBar,
  Box,
  Button,
  IconButton,
  makeStyles,
  Toolbar,
  Typography,
} from "@material-ui/core";

import { useState } from "react";
import { coverageAxios, testAxios } from "../../utils/axios";
import ArrowBackIcon from "@material-ui/icons/ArrowBack";
import CloseIcon from "@material-ui/icons/Close";
import TestDetail from "../../components/test/TestDetail";

// totalBranchCovCovered: 40
// totalBranchCovMissed: 60
// totalElapsedTime: 1800
// totalErrorCount: 2
// totalFailCount: 1
// totalLineCovCovered: 100
// totalLineCovMissed: 60
// totalRunCount: 20
// totalSkipCount: 2

const useStyles = makeStyles((theme) => ({
  dataGrid: {},
}));

function CustomToolbar() {
  return (
    <GridToolbarContainer>
      <Box marginLeft="10px">
        <GridToolbarExport />
      </Box>
    </GridToolbarContainer>
  );
}

export default function CoverageDetail({ aid, title, data, close }) {
  const [classDetail, setClassDetail] = useState(false);
  const [classDetailData, setClassDetailData] = useState([]);
  const [selectedReport, setSelectedReport] = useState({});
  const [testDetail, setTestDetail] = useState(false);
  const classes = useStyles();

  // onCellClick={async (cell, event) => {
  //   event.preventDefault();
  //   if (cell.field !== "lineCov" && cell.field !== "branchCov") {
  //     console.log(cell.row.id);
  //     console.log(cell.row);
  //     setTestDetail(true);
  //     setSelectedReport(cell.row);
  //   } else {
  //     const responseData =
  //       await coverageAxios.getCoverageListByReportId(cell.row.id);
  //     console.log(responseData);
  //     setClassDetailData(responseData);
  //     setClassDetail(true);
  //     setSelectedReport(cell.row);
  //   }
  // }}

  const columns = [
    {
      field: "datetime",
      headerName: "Build Datetime",
      width: 200,
      renderCell: (params) => (
        <div>{datetime(params.getValue("datetime"))}</div>
      ),
    },
    {
      field: "detail",
      headerName: "Detail",
      width: 120,
      renderCell: (params) => (
        <>
          <span
            onClick={(event) => {
              event.preventDefault();
              setTestDetail(true);
              setSelectedReport(params.row);
            }}
            style={{
              color: "blue",
              textDecoration: "underline",
              fontWeight: "bold",
              cursor: "pointer",
              "&:hover": {
                cursor: "hand",
              },
            }}
          >
            Test
          </span>
          /
          <span
            onClick={async (event) => {
              event.preventDefault();
              const responseData = await coverageAxios.getCoverageListByReportId(
                params.row.id
              );
              setClassDetailData(responseData);
              setClassDetail(true);
              setSelectedReport(params.row);
            }}
            style={{
              color: "blue",
              textDecoration: "underline",
              fontWeight: "bold",
              cursor: "pointer",
              "&:hover": {
                cursor: "hand",
              },
            }}
          >
            Coverage
          </span>
        </>
      ),
    },
    {
      field: "blank",
      width: 0,
      renderCell: (params) => (
        <div style={{ width: "1px", backgroundColor: "#E0E0E0" }}>&nbsp;</div>
      ),
    },
    {
      field: "passRate",
      headerName: "Pass Rate(%)",
      width: 150,
      type: "number",
      valueGetter: (params) => {
        const run = params.getValue("totalRunCount");

        if (run) {
          return (
            (run /
              (run +
                params.getValue("totalFailCount") +
                params.getValue("totalErrorCount") +
                params.getValue("totalSkipCount"))) *
            100
          );
        }
        return 0;
      },
    },
    { field: "totalRunCount", headerName: "Run", width: 100, type: "number" },
    { field: "totalFailCount", headerName: "Fail", width: 100, type: "number" },
    {
      field: "totalErrorCount",
      headerName: "Error",
      width: 100,
      type: "number",
    },
    { field: "totalSkipCount", headerName: "Skip", width: 100, type: "number" },
    {
      field: "blank2",
      width: 0,
      renderCell: (params) => (
        <div style={{ width: "1px", backgroundColor: "#E0E0E0" }}>&nbsp;</div>
      ),
    },
    {
      field: "lineCov",
      headerName: "Line Cov.(%)",
      description: "Line Coverage (Covered / Missed)",
      width: 150,
      type: "number",
      valueGetter: (params) => {
        const covered = params.getValue("totalLineCovCovered");
        const missed = params.getValue("totalLineCovMissed");
        const total = covered + missed;

        if (total) {
          return Math.round((covered / total) * 10000) / 100;
        }
        return 100;
      },
    },
    {
      field: "branchCov",
      headerName: "Branch Cov.(%)",
      width: 150,
      type: "number",
      valueGetter: (params) => {
        const covered = params.getValue("totalBranchCovCovered");
        const missed = params.getValue("totalBranchCovMissed");
        const total = covered + missed;

        if (total) {
          return Math.round((covered / total) * 10000) / 100;
        }
        return 100;
      },
    },
    // {
    //   field: "totalLineCovCovered",
    //   headerName: "Line Covered",
    //   flex: 1,
    //   type: "number",
    // },
    // {
    //   field: "totalLineCovMissed",
    //   headerName: "Line Missed",
    //   flex: 1,
    //   type: "number",
    // },
    // {
    //   field: "totalBranchCovCovered",
    //   headerName: "Branch Covered",
    //   flex: 1,
    //   type: "number",
    // },
    // {
    //   field: "totalBranchCovMissed",
    //   headerName: "Branch Missed",
    //   flex: 1,
    //   type: "number",
    // },
  ];

  return (
    <>
      {classDetail ? (
        <>
          <AppBar position="static">
            <Toolbar>
              <IconButton
                edge="start"
                color="inherit"
                aria-label="menu"
                onClick={() => {
                  setClassDetail(false);
                  setClassDetailData([]);
                  setSelectedReport({});
                }}
              >
                <ArrowBackIcon />
              </IconButton>
              <Typography variant="h6" style={{ flexGrow: 1 }}>
                {title} history group by class &gt;{" "}
                {datetime(selectedReport.datetime)}
              </Typography>
              <IconButton
                edge="start"
                color="inherit"
                aria-label="menu"
                onClick={() => {
                  setClassDetail(false);
                  setClassDetailData([]);
                  setSelectedReport({});
                  close();
                }}
              >
                <CloseIcon />
              </IconButton>
            </Toolbar>
          </AppBar>
          <div
            style={{
              height: "92.5%",
              width: "100%",
            }}
          >
            <DataGrid
              components={{
                Toolbar: CustomToolbar,
              }}
              rows={classDetailData}
              columns={[
                {
                  field: "className",
                  headerName: "Class",
                  width: 250,
                  renderCell: (params) =>
                    params.getValue("highlightHtml") ? (
                      <span
                        style={{
                          color: "blue",
                          textDecoration: "underline",
                          cursor: "pointer",
                          "&:hover": {
                            cursor: "hand",
                          },
                        }}
                        onClick={(event) => {
                          event.preventDefault();
                          const newTab = window.open(
                            params.row.highlightHtml,
                            "_blank"
                          );
                          newTab.focus();
                        }}
                      >
                        {params.value}
                      </span>
                    ) : (
                      <span>{params.value}</span>
                    ),
                },
                {
                  field: "lineCov",
                  headerName: "Line Cov.(%)",
                  description: "Line Coverage (Covered / Missed)",
                  type: "number",
                  width: 160,
                  valueGetter: (params) => {
                    const covered = params.getValue("lineCovCovered");
                    const missed = params.getValue("lineCovMissed");
                    const total = covered + missed;

                    if (total) {
                      return Math.round((covered / total) * 10000) / 100;
                    }
                    return 100;
                  },
                },
                {
                  field: "lineCovCovered",
                  headerName: "Line Covered",
                  type: "number",
                  width: 160,
                },
                {
                  field: "lineCovMissed",
                  headerName: "Line Missed",
                  type: "number",
                  width: 160,
                },
                {
                  field: "branchCov",
                  headerName: "Branch Cov.(%)",
                  type: "number",
                  width: 160,
                  valueGetter: (params) => {
                    const covered = params.getValue("branchCovCovered");
                    const missed = params.getValue("branchCovMissed");
                    const total = covered + missed;

                    if (total) {
                      return Math.round((covered / total) * 10000) / 100;
                    }
                    return 100;
                  },
                },
                {
                  field: "branchCovCovered",
                  headerName: "Branch Covered",
                  width: 160,
                  type: "number",
                },
                {
                  field: "branchCovMissed",
                  headerName: "Branch Missed",
                  width: 160,
                  type: "number",
                },
              ]}
              // onCellClick={async (cell, event) => {
              //   event.preventDefault();
              //   const newTab = window.open(cell.row.highlightHtml, "_blank");
              //   newTab.focus();
              // }}
            />
          </div>
        </>
      ) : testDetail ? (
        <>
          <AppBar position="static">
            <Toolbar>
              <IconButton
                edge="start"
                color="inherit"
                aria-label="menu"
                onClick={() => {
                  setTestDetail(false);
                  setSelectedReport({});
                }}
              >
                <ArrowBackIcon />
              </IconButton>
              <Typography variant="h6" style={{ flexGrow: 1 }}>
                {title} history
              </Typography>
              <IconButton
                edge="start"
                color="inherit"
                aria-label="menu"
                onClick={() => {
                  setTestDetail(false);
                  setSelectedReport({});
                  close();
                }}
              >
                <CloseIcon />
              </IconButton>
            </Toolbar>
          </AppBar>
          <TestDetail
            aid={aid}
            id={selectedReport.id}
            title={title}
          ></TestDetail>
        </>
      ) : (
        <>
          <AppBar position="static">
            <Toolbar>
              <Typography variant="h6" style={{ flexGrow: 1 }}>
                {title} history
              </Typography>
              <IconButton
                edge="start"
                color="inherit"
                aria-label="menu"
                onClick={() => {
                  setClassDetail(false);
                  setClassDetailData([]);
                  setSelectedReport({});
                  close();
                }}
              >
                <CloseIcon />
              </IconButton>
            </Toolbar>
          </AppBar>
          <div style={{ height: "92.5%", width: "100%" }}>
            <DataGrid
              components={{
                Toolbar: CustomToolbar,
              }}
              rows={data}
              columns={columns}
              // onCellClick={async (cell, event) => {
              //   event.preventDefault();
              //   if (cell.field !== "lineCov" && cell.field !== "branchCov") {
              //     console.log(cell.row.id);
              //     console.log(cell.row);
              //     setTestDetail(true);
              //     setSelectedReport(cell.row);
              //   } else {
              //     const responseData =
              //       await coverageAxios.getCoverageListByReportId(cell.row.id);
              //     console.log(responseData);
              //     setClassDetailData(responseData);
              //     setClassDetail(true);
              //     setSelectedReport(cell.row);
              //   }
              // }}
            />
          </div>
        </>
      )}
    </>
  );
}
