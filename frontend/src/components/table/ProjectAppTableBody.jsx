import {
  Box,
  IconButton,
  TableCell,
  TablePagination,
  TableRow,
} from "@material-ui/core";
import React, { useEffect, useState } from "react";
import { reportAxios } from "../../utils/axios";
import KeyboardArrowDownIcon from "@material-ui/icons/KeyboardArrowDown";
import KeyboardArrowUpIcon from "@material-ui/icons/KeyboardArrowUp";
import ProjectAppReportCollapse from "./ProjectAppReportCollapse";
import ProjectAppReportCell from "./ProjectAppReportCell";
import { TabContext } from "@material-ui/lab";

export default function ProjectAppTableBody({ app }) {
  const [reportList, setReportList] = useState([]);
  const [open, setOepn] = useState(false);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const onClick = () => {
    setOepn(!open);
  };

  useEffect(async () => {
    const responseData = await reportAxios.getListByAppId(app.id);
    console.log(responseData);
    setReportList(responseData);
    return () => {};
  }, []);

  return (
    <>
      <TableRow>
        <TableCell>
          <IconButton aria-label="expand row" size="small" onClick={onClick}>
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell>{app.title}</TableCell>
        {reportList.slice(0, 1).map((row, index) => (
          <ProjectAppReportCell key={index} row={row} />
        ))}
      </TableRow>
      {open ? (
        reportList
          .slice(page * rowsPerPage + 1, page * rowsPerPage + rowsPerPage + 1)
          .map((row, index) => (
            <TableRow>
              <TableCell />
              <TableCell />
              <ProjectAppReportCell key={index} row={row} />
            </TableRow>
          ))
      ) : (
        <></>
      )}
      {open && reportList.length > 2 ? (
        <>
          <TablePagination
            rowsPerPageOptions={[10, 25, 50, 100]}
            component="td"
            count={reportList.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onChangePage={handleChangePage}
            onChangeRowsPerPage={handleChangeRowsPerPage}
          />
          <TableRow>
            <TableCell />
            <TableCell />
            <TableCell />
            <TableCell />
            <TableCell />
            <TableCell />
            <TableCell />
          </TableRow>
        </>
      ) : (
        <></>
      )}
    </>
  );
}
