import React from "react";
import Markdown from "react-markdown";

export default function ProjectDetail() {
  return (
    <>
      <Markdown># Project 상세 페이지</Markdown>
      <br />
      <Markdown>## App 현황</Markdown>
      <Markdown>
        Project 상세 페이지에서는 해당 프로젝트에 속한 App의 현황을 파악할 수
        있습니다.
        <br /> 선택된 App들의 Line, Branch Coverage와 Pass Rate 및 최근 빌드
        시간을 확인할 수 있습니다.
      </Markdown>
      <br />
      <img src="./assets/images/project-detail/01.png" width="100%" />
      <br />
      <br />
      <Markdown>## App 생성</Markdown>
      <Markdown>
        CREATE NEW APP 버튼을 통해 해당 프로젝트에 속한 App을 추가할 수
        있습니다.
        <br /> App Name과 Git Repository URL을 입력하면 자동으로 해당 프로젝트에
        속한 App이 생성되며 이후 해당 App의 빌드 결과를 확인할 수 있습니다.
      </Markdown>
      <br />
      <img src="./assets/images/project-detail/02.png" width="100%" />
      <br />
      <br />
      <Markdown>## App history</Markdown>
      <Markdown>
        하나의 App 행을 클릭하면 해당 App의 빌드 시간별 history를 확인할 수
        있습니다.
        <br />
        해당 화면에서 Covage 또는 Pass Rate 열을 클릭하면 해당 이력의 상세
        내용을 확인할 수 있습니다.
      </Markdown>
      <br />
      <img src="./assets/images/project-detail/03.png" width="100%" />
      <br />
      <br />
      <Markdown>## App history group by class</Markdown>
      <Markdown>
        App history 화면에서 Coverage에 해당하는 값을 누르면 해당 빌드 시간의
        Coverage를 class별로 세분화하여 확인할 수 있습니다.
      </Markdown>
      <br />
      <img src="./assets/images/project-detail/04.png" width="100%" />
      <br />
      <br />
      <Markdown>## App coverage code</Markdown>
      <Markdown>
        Class별 세분화된 표에서 다시 행을 클릭하면 해당 class의 코드의
        하이라이팅된 code를 확인할 수 있습니다.
      </Markdown>
      <br />
      <img src="./assets/images/project-detail/05.png" width="100%" />
      <br />
      <Markdown>## Test result detail</Markdown>
      <Markdown>
        App history에서 pass rate에 해당하는 열을 선택하면 해당 빌드의 테스트
        상세 결과를 확인할 수 있습니다.
      </Markdown>
      <br />
      <img src="./assets/images/project-detail/06.png" width="100%" />
      <br />
    </>
  );
}
