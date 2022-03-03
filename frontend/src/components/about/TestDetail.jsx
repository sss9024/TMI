import React from "react";
import Markdown from "react-markdown";

export default function TestDetail() {
  return (
    <>
      <Markdown># Test 상세 페이지</Markdown>
      <Markdown>## Test History</Markdown>
      <Markdown>
        Test 상세 페이지에서는 Report의 Test History를 제공합니다. Report의 Test
        History는 선택된 Report로부터 최대 7개까지 Test History를 제공합니다.
      </Markdown>
      {/* <img src="./assets/images/project-detail/01.png" width="100%" /> */}
      <br />
      <Markdown>## Test 이력</Markdown>
      <Markdown>
        Report의 Test History를 클릭하면, Test 타입별로 count가 들어간 버튼과 각
        Test 결과 리스트를 보여줍니다. 사용자는 Test 타입별 버튼을 ON/OFF 하여
        Test의 타입을, 검색 창으로는 Test의 이름을 필터링하여 Test 리스트를
        조회할 수 있습니다.
      </Markdown>
      {/* <img src="./assets/images/project-detail/01.png" width="100%" /> */}
      <br />
    </>
  );
}
