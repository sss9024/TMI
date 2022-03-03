# React Convention

상태: Document
작성일시: 2021년 4월 15일 오후 2:07

# React Convention

## *확장자*

- render가 있을 경우 .jsx를 사용 (e.g. HomeView.jsx)
- 단순 유틸 모듈의 경우 .js를 사용 (e.g. axios.js)

---

## *Prettier 설정 코드*

```json
{
    "java.home": "C:\\Program Files\\Java\\jdk-15.0.1",
    "files.exclude": {
        "**/.classpath": true,
        "**/.project": true,
        "**/.settings": true,
        "**/.factorypath": true
    },
    "editor.suggestSelection": "first",
    "vsintellicode.modify.editor.suggestSelection": "automaticallyOverrodeDefaultValue",
    "workbench.iconTheme": "vscode-icons",
    "vsicons.dontShowNewVersionMessage": true,
    "terminal.integrated.shell.windows": "C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\powershell.exe",
    "liveServer.settings.donotShowInfoMsg": true,
     // Set the default
    "editor.formatOnSave": false,
    // Enable per-language
    "[javascript]": {
        "editor.formatOnSave": true,
        "editor.defaultFormatter": "esbenp.prettier-vscode"
    },    
    "[javascriptreact]": {
        "editor.formatOnSave": true,
        "editor.defaultFormatter": "esbenp.prettier-vscode"
    },    
    "[typescript]": {
        "editor.formatOnSave": true,
        "editor.defaultFormatter": "esbenp.prettier-vscode"
    },
    "[typescriptreact]": {
        "editor.formatOnSave": true,
        "editor.defaultFormatter": "esbenp.prettier-vscode"
    },
}
```

---

## ***Naming Convention***

## 

**메소드명** - camelCase

**함수명** - camelCase

**클래스명** - PascalCase

**변수명 -** camelCase

- ex) const realWord = 3;

**상수명**

- HOURS_IN_DAY
- 상수명은 `대문자`를 사용하고, 단어와 단어사이는 `_`로 연결한다.

## *React Component*

가급적 함수형 컴포넌트 사용

```jsx
// Functional component -> O
import React from 'react'

export default function HomeView() {
  return (
    <div>
      
    </div>
  )
}

// Class component -> X
import React, { Component } from 'react'

export default class HomeView extends Component {
  render() {
    return (
      <div>
        
      </div>
    )
  }
}
```

## 폴더 구조

react 파일은 src 폴더 내에서 작업한다

views 폴더에는 하나의 페이지 뷰를 렌더링하는 jsx 파일을 저장한다

src/views/report/ReportResultView.jsx

components 폴더에는 재사용 가능한 부분을 렌더링하는 jsx 파일을 저장한다

src/components/report/ResultTable.jsx

src/components/common/LogoutBtn.jsx