import React from "react";
import {
  Box,
  Card,
  CardContent,
  CardHeader,
  colors,
  Divider,
  Grid,
  Typography,
} from "@material-ui/core";
import { Doughnut } from "react-chartjs-2";

export default function TotalDoughnutGraph({ data, title }) {
  const dataDoughnutGraph = {
    // labels: ["Covered", "Missed"],
    // text: "text",
    datasets: [
      {
        data,
        backgroundColor: [
          colors.blue[600],
          colors.red[600],
          colors.orange[600],
          colors.grey[500],
        ],
      },
    ],
  };

  return (
    <Box>
      <Typography
        variant="h6"
        style={{ display: "flex", justifyContent: "space-between" }}
      >
        <span>{title}</span>

        <span>
          {data[0]
            ? `${Math.round(
                (data[0] / data.reduce((pValue, cValue) => pValue + cValue)) *
                  10000
              ) / 100}%`
            : ""}
        </span>
      </Typography>
      <Doughnut height="200" width="200" data={dataDoughnutGraph} />
    </Box>
  );
}
