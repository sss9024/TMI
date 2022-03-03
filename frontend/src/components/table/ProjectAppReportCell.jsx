import { Box, colors, TableCell, TableRow } from "@material-ui/core";
import datetime from "../../utils/moment";
import React from "react";

export default function ProjectAppReportCell({ row }) {
  const totalLineCovSum = row.totalLineCovCovered + row.totalLineCovMissed;
  const totalBranchCovSum =
    row.totalBranchCovCovered + row.totalBranchCovMissed;
  const totalTestCount =
    row.totalErrorCount +
    row.totalFailCount +
    row.totalRunCount +
    row.totalSkipCount;
  return (
    <>
      <TableCell>{datetime(row.datetime)}</TableCell>
      <TableCell>
        <Box>
          <span style={{ fontWeight: "bold" }}>
            {totalLineCovSum
              ? Math.round(
                  (row.totalLineCovCovered / totalLineCovSum) * 10000
                ) / 100
              : 100}
          </span>
        </Box>
        {/* <Box>
          <small style={{ color: colors.blue[500] }}>Covered</small> /{" "}
          <small style={{ color: colors.red[500] }}>Missed</small>
        </Box> */}
        <Box>
          <small style={{ color: colors.blue[500] }}>
            {row.totalLineCovCovered}
          </small>{" "}
          /{" "}
          <small style={{ color: colors.red[500] }}>
            {row.totalLineCovMissed}
          </small>
        </Box>
      </TableCell>
      <TableCell>
        <Box>
          <span style={{ fontWeight: "bold" }}>
            {totalBranchCovSum
              ? Math.round(
                  (row.totalBranchCovCovered / totalBranchCovSum) * 10000
                ) / 100
              : 100}
          </span>
        </Box>
        {/* <Box>
          <small style={{ color: colors.blue[500] }}>Covered</small> /{" "}
          <small style={{ color: colors.red[500] }}>Missed</small>
        </Box> */}
        <Box>
          <small style={{ color: colors.blue[500] }}>
            {row.totalBranchCovCovered}
          </small>{" "}
          /{" "}
          <small style={{ color: colors.red[500] }}>
            {row.totalBranchCovMissed}
          </small>
        </Box>
      </TableCell>
      <TableCell>
        <Box>
          <span style={{ fontWeight: "bold" }}>
            {totalTestCount
              ? Math.round((row.totalRunCount / totalTestCount) * 10000) / 100
              : 100}
          </span>
        </Box>
        {/* <Box>
          <small style={{ color: colors.blue[500] }}>Run</small> /{" "}
          <small style={{ color: colors.red[500] }}>Fail</small> / Skip / Error
        </Box> */}
        <Box>
          <small style={{ color: colors.blue[500] }}>{row.totalRunCount}</small>{" "}
          /{" "}
          <small style={{ color: colors.red[500] }}>{row.totalFailCount}</small>{" "}
          /{" "}
          <small style={{ color: colors.orange[500] }}>
            {row.totalSkipCount}
          </small>{" "}
          /{" "}
          <small style={{ color: colors.purple[500] }}>
            {row.totalErrorCount}
          </small>
        </Box>
      </TableCell>
      <TableCell>{row.totalElapsedTime}</TableCell>
    </>
  );
}
