import React from "react";
import { Link } from "react-router-dom";
import { Breadcrumbs, colors, Typography } from "@material-ui/core";

export default function MyBreadcrumbs() {
  return (
    <>
      <Breadcrumbs
        style={{ backgroundColor: colors.blue[300], padding: 5 }}
        aria-label="breadcrumb"
      >
        <Link color="inherit" to="/project">
          Project
        </Link>
        <Typography color="textPrimary">Detail</Typography>
      </Breadcrumbs>
    </>
  );
}
