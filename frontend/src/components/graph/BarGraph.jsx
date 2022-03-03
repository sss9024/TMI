import React from "react";
import { colors } from "@material-ui/core";
import { Bar } from "react-chartjs-2";

export default function BarGraph() {
  const dataBarGraph = {
    labels: ["instruction", "branch", "line", "complexity", "method"],
    datasets: [
      {
        label: ["Covered"],
        data: [41, 2, 10, 6, 5],
        backgroundColor: colors.blue[50],
        borderColor: colors.blue[500],
        borderWidth: 1,
      },
      {
        label: ["Missed"],
        data: [4, 0, 2, 1, 1],
        backgroundColor: colors.red[50],
        borderColor: colors.red[500],
        borderWidth: 1,
      },
    ],
  };
  return <Bar data={dataBarGraph} />;
}
