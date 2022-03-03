import {
  Box,
  Collapse,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography,
} from "@material-ui/core";
import React from "react";
import ProjectAppReportCell from "./ProjectAppReportCell";

export default function ProjectAppReportCollapse({ open, reportList }) {
  return (
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
                  <TableCell />
                  <TableCell />
                  <TableCell />
                  <TableCell />
                  {[
                    "App Name",
                    "Build Datetime",
                    "Line Cov.(%)",
                    "Branch Cov.(%)",
                    "Pass Rate(%)",
                    "Elapsed Time(sec.)",
                  ]
                    .slice(1)
                    .map((h, index) => (
                      <TableCell key={index}>{h}</TableCell>
                    ))}
                </TableRow>
              </TableHead>
              <TableBody>
                {reportList.map((row, index) => (
                  <TableRow key={index}>
                    <TableCell />
                    <TableCell />
                    <TableCell />
                    <TableCell />
                    <ProjectAppReportCell row={row} />
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </Box>
        </Collapse>
      </TableCell>
    </TableRow>
  );
}
