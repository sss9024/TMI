import React, { useEffect, useState } from "react";
import { Line } from "react-chartjs-2";
import { colors, Box, Typography } from "@material-ui/core";

export default function ProjectAppPassRateGraphs({ data, title }) {
  const [instructionCov, setInstructionCov] = useState({
    covered: 0,
    missed: 0,
  });
  useEffect(() => {
    return () => {};
  }, []);

  const dataLineGraph = {
    labels: [
      "2021-04-15",
      "2021-04-18",
      "2021-04-27",
      "2021-05-02",
      "2021-05-03",
    ],
    datasets: [
      {
        label: "Pass Rate",
        // yAxisID: "B",
        data: [45, 68, 65, 89, 93],
        borderColor: colors.blue[500],
      },
      // {
      //   type: "bar",
      //   yAxisID: "A",
      //   label: "Instruction Covered",
      //   data: [150, 380, 680, 850, 930],
      //   backgroundColor: colors.deepPurple[50],
      //   borderColor: colors.deepPurple[500],
      //   borderWidth: 1,
      // },
      // {
      //   type: "bar",
      //   yAxisID: "A",
      //   label: "Instruction Missed",
      //   data: [850, 620, 320, 150, 70],
      //   backgroundColor: colors.red[50],
      //   borderColor: colors.red[500],
      //   borderWidth: 1,
      // },
      // {
      //   type: "bar",
      //   yAxisID: "A",
      //   label: "Branch Covered",
      //   data: [50, 400, 550, 790, 880],
      //   backgroundColor: colors.indigo[50],
      //   borderColor: colors.indigo[500],
      //   borderWidth: 1,
      // },
      // {
      //   type: "bar",
      //   yAxisID: "A",
      //   label: "Branch Missed",
      //   data: [950, 600, 450, 210, 120],
      //   backgroundColor: colors.red[50],
      //   borderColor: colors.red[500],
      //   borderWidth: 1,
      // },
      // {
      //   label: "Line",
      //   data: [93.8, 98.1, 92.6, 91.7, 91.2],
      //   borderColor: colors.blue[500],
      // },
      // {
      //   label: "Complexity",
      //   data: [96.3, 92.5, 86.7, 94.4, 93.3],
      //   borderColor: colors.lightBlue[500],
      // },
      // {
      //   label: "Method",
      //   data: [91.5, 89.7, 91.3, 96.7, 96.3],
      //   borderColor: colors.cyan[500],
      // },
    ],
  };
  const optionsLineGraph = {
    scales: {
      yAxes: [
        {
          id: "A",
          type: "linear",
          position: "left",
        },
        {
          id: "B",
          type: "linear",
          position: "right",
        },
      ],
    },
  };

  return (
    <Box>
      <Typography variant="h5">{title}</Typography>
      <Line
        height="200"
        width="400"
        data={dataLineGraph}
        options={optionsLineGraph}
      />
    </Box>
  );
}
