import React from "react";
import { ResponsiveLine } from "@nivo/line";

export default function Line() {
  const data = [
    {
      id: "japan",
      color: "hsl(159, 70%, 50%)",
      data: [
        {
          x: "plane",
          y: 136,
        },
        {
          x: "helicopter",
          y: 62,
        },
        {
          x: "boat",
          y: 62,
        },
        {
          x: "train",
          y: 53,
        },
        {
          x: "subway",
          y: 219,
        },
        {
          x: "bus",
          y: 191,
        },
        {
          x: "car",
          y: 27,
        },
        {
          x: "moto",
          y: 295,
        },
        {
          x: "bicycle",
          y: 222,
        },
        {
          x: "horse",
          y: 294,
        },
        {
          x: "skateboard",
          y: 287,
        },
        {
          x: "others",
          y: 219,
        },
      ],
    },
    {
      id: "france",
      color: "hsl(291, 70%, 50%)",
      data: [
        {
          x: "plane",
          y: 73,
        },
        {
          x: "helicopter",
          y: 171,
        },
        {
          x: "boat",
          y: 67,
        },
        {
          x: "train",
          y: 32,
        },
        {
          x: "subway",
          y: 166,
        },
        {
          x: "bus",
          y: 270,
        },
        {
          x: "car",
          y: 192,
        },
        {
          x: "moto",
          y: 46,
        },
        {
          x: "bicycle",
          y: 211,
        },
        {
          x: "horse",
          y: 297,
        },
        {
          x: "skateboard",
          y: 3,
        },
        {
          x: "others",
          y: 167,
        },
      ],
    },
    {
      id: "us",
      color: "hsl(347, 70%, 50%)",
      data: [
        {
          x: "plane",
          y: 41,
        },
        {
          x: "helicopter",
          y: 259,
        },
        {
          x: "boat",
          y: 50,
        },
        {
          x: "train",
          y: 50,
        },
        {
          x: "subway",
          y: 266,
        },
        {
          x: "bus",
          y: 115,
        },
        {
          x: "car",
          y: 130,
        },
        {
          x: "moto",
          y: 134,
        },
        {
          x: "bicycle",
          y: 104,
        },
        {
          x: "horse",
          y: 239,
        },
        {
          x: "skateboard",
          y: 151,
        },
        {
          x: "others",
          y: 269,
        },
      ],
    },
    {
      id: "germany",
      color: "hsl(12, 70%, 50%)",
      data: [
        {
          x: "plane",
          y: 46,
        },
        {
          x: "helicopter",
          y: 51,
        },
        {
          x: "boat",
          y: 299,
        },
        {
          x: "train",
          y: 142,
        },
        {
          x: "subway",
          y: 178,
        },
        {
          x: "bus",
          y: 103,
        },
        {
          x: "car",
          y: 268,
        },
        {
          x: "moto",
          y: 205,
        },
        {
          x: "bicycle",
          y: 151,
        },
        {
          x: "horse",
          y: 265,
        },
        {
          x: "skateboard",
          y: 96,
        },
        {
          x: "others",
          y: 147,
        },
      ],
    },
    {
      id: "norway",
      color: "hsl(177, 70%, 50%)",
      data: [
        {
          x: "plane",
          y: 24,
        },
        {
          x: "helicopter",
          y: 224,
        },
        {
          x: "boat",
          y: 185,
        },
        {
          x: "train",
          y: 126,
        },
        {
          x: "subway",
          y: 53,
        },
        {
          x: "bus",
          y: 184,
        },
        {
          x: "car",
          y: 245,
        },
        {
          x: "moto",
          y: 101,
        },
        {
          x: "bicycle",
          y: 47,
        },
        {
          x: "horse",
          y: 128,
        },
        {
          x: "skateboard",
          y: 72,
        },
        {
          x: "others",
          y: 284,
        },
      ],
    },
  ];

  return (
    <ResponsiveLine
      data={data}
      margin={{ top: 20, right: 110, bottom: 100, left: 60 }}
      xScale={{ type: "point" }}
      yScale={{
        type: "linear",
        min: "auto",
        max: "auto",
        stacked: true,
        reverse: false,
      }}
      yFormat=" >-.2f"
      axisTop={null}
      axisRight={null}
      axisBottom={{
        orient: "bottom",
        tickSize: 5,
        tickPadding: 5,
        tickRotation: 0,
        legend: "transportation",
        legendOffset: 36,
        legendPosition: "middle",
      }}
      axisLeft={{
        orient: "left",
        tickSize: 5,
        tickPadding: 5,
        tickRotation: 0,
        legend: "count",
        legendOffset: -40,
        legendPosition: "middle",
      }}
      pointSize={5}
      pointColor={{ from: "color", modifiers: [] }}
      pointBorderWidth={2}
      pointBorderColor={{ from: "serieColor" }}
      pointLabelYOffset={-12}
      enableSlices="x"
      useMesh={true}
      legends={[
        {
          anchor: "bottom",
          direction: "row",
          justify: false,
          translateX: 0,
          translateY: 80,
          itemsSpacing: 0,
          itemDirection: "left-to-right",
          itemWidth: 80,
          itemHeight: 20,
          itemOpacity: 0.75,
          symbolSize: 12,
          symbolShape: "circle",
          symbolBorderColor: "rgba(0, 0, 0, .5)",
          effects: [
            {
              on: "hover",
              style: {
                itemBackground: "rgba(0, 0, 0, .03)",
                itemOpacity: 1,
              },
            },
          ],
        },
      ]}
    />
  );
}
