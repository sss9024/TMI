import React from "react";

export default function imageDetail(props) {
  return (
    <div>
      <img src={props.url} style={{ maxWidth: "80vw", maxHeight: "80vh" }} />
    </div>
  );
}
