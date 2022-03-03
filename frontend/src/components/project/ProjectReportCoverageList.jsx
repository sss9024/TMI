import React, { useState } from "react";
import { makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import { Box, Collapse, IconButton, Typography } from "@material-ui/core";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import datetime from "../../utils/moment";
import { coverageAxios } from "../../utils/axios";

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
});

function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}

const rows = [];

const tableHead = [
  "App Name",
  "Build Datetime",
  "Line Cov.(%)",
  "Branch Cov.(%)",
  "Pass Rate(%)",
  "Elapsed Time(sec.)",
];

function Row({ row }) {
  const [open, setOpen] = useState(false);
  const [coverages, setCoverages] = useState([]);
  console.log(row);

  async function onClick() {
    setOpen(!open);
    // if (!Object.keys(coverages).length) {
    //   const response = await coverageAxios.all(row.id);
    //   setCoverages(response);
    //   console.log(response);
    // }
  }
  return (
    <>
      <TableRow>
        <TableCell>
          <IconButton aria-label="expand row" size="small" onClick={onClick}>
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell>{row.id}</TableCell>
        <TableCell>{datetime(row.datetime)}</TableCell>
        <TableCell>
          <Box>
            {row.totalLineCovCovered + row.totalLineCovMissed
              ? row.totalLineCovMissed /
                (row.totalLineCovCovered + row.totalLineCovMissed)
              : 100}
          </Box>
          <Box>Covered / Missed</Box>
          <Box>
            {row.totalLineCovCovered} / {row.totalLineCovMissed}
          </Box>
        </TableCell>
        <TableCell>
          <Box>
            {row.totalBranchCovCovered + row.totalBranchCovMissed
              ? row.totalBranchCovMissed /
                (row.totalBranchCovCovered + row.totalBranchCovMissed)
              : 100}
          </Box>
          <Box>Covered / Missed</Box>
          <Box>
            {row.totalBranchCovCovered} / {row.totalBranchCovMissed}
          </Box>
        </TableCell>
        <TableCell>
          <Box>
            {row.totalErrorCount +
            row.totalFailCount +
            row.totalRunCount +
            row.totalSkipCount
              ? row.totalRunCount /
                (row.totalErrorCount +
                  row.totalFailCount +
                  row.totalRunCount +
                  row.totalSkipCount)
              : 100}
          </Box>
          <Box>Run / Fail / Skip / Error</Box>
          <Box>
            {row.totalRunCount} / {row.totalFailCount} / {row.totalSkipCount} /{" "}
            {row.totalErrorCount}
          </Box>
        </TableCell>
        <TableCell>{row.totalElapsedTime}</TableCell>
      </TableRow>
      {/* Collapse */}
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={7}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box margin={1}>
              <Typography variant="h6" gutterBottom component="div">
                History
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    {tableHead.slice(1).map((head, index) => (
                      <TableCell key={index}>{head}</TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {/* {row.history.map((historyRow) => (
                    <TableRow key={historyRow.date}>
                      <TableCell component="th" scope="row">
                        {historyRow.date}
                      </TableCell>
                      <TableCell>{historyRow.customerId}</TableCell>
                      <TableCell align="right">{historyRow.amount}</TableCell>
                      <TableCell align="right">
                        {Math.round(historyRow.amount * row.price * 100) / 100}
                      </TableCell>
                    </TableRow>
                  ))} */}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </>
  );
}

export default function ProjectReportCoverageList({ data }) {
  const classes = useStyles();

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell />
            {tableHead.map((head) => (
              <TableCell>{head}</TableCell>
            ))}
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map((coverage, index) => (
            <Row key={index} row={coverage} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
