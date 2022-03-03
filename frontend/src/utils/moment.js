import React from "react";
import Moment from "react-moment";

export default function datetime(dt) {
  return <Moment format="YYYY/MM/DD HH:mm:ss">{dt}</Moment>;
}
