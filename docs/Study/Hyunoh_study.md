# Hyunoh_study

## **Docker**

### **필수 패키지 설치**

`sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common`

### **GPG Key 인증**

`curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -`

ok 나오면 됩니다.

- GPG 란?

    GPG는 GNU Privacy Guard (GnuPG)의 줄임말로서 배포 파일의 인증을 확인하는데 사용되는 자유 소프트웨어 패키지를 말합니다. 예를 들면 Red Hat은 비밀키 (개인키)를 사용하여 패키지를 잠근 후 공개키를 사용하여 패키지를 잠금 해제 후 검증합니다. 만일 RPM 검증 과정에서 Red Hat에서 배포한 공개키가 비밀키와 일치하지 않는다면, 패키지가 변경되었을 수 있으므로 신뢰할 수 없습니다.

### **docker repository 등록**

`sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"`

### **docker 설치**

`sudo apt-get update && sudo apt-get install docker-ce docker-ce-cli containerd.io`

### **docker 설정**

- 시스템 부팅시 도커 시작

    `sudo systemctl enable docker && service docker start`

- 도커 확인

    `sudo service docker status` : active가 뜬다면 확인 완료!

### **젠킨스 설치**

- Jenkins 데이터 저장용 디렉토리 생성

    `sudo mkdir -p /mnt/image/jenkins-data`

- 생성된 볼륨을 Jenkins 컨테이너에 mount & 실행

    ```
    sudo docker run -d -u root -p 49000:8080 -p 51000:51000 -v /mnt/image/jenkins-data:/var/jenkins_home --name jenkins jenkins/jenkins;
    ```

- 최초 이후 젠킨스 시작

    `docker { start | restart | attach | .. } jenkins`

- 내가 저장한 디렉토리의 키 값 가져옴

    `localhost:49000`으로 접속 후,

    `sudo cat /mnt/image/jenkins-data/secrets/initialAdminPassword` 여기 키 값을 사용

- 권장 설치 선택
- url 설정은 알아서 / 나중에 하면 됨.
- manage Jenkins -> manage Plugins
    - Blue Ocean 설치
    - git lab 이라면 gitlab 설치
- 다시 젠킨스 메인 대쉬보드로 돌아가서 new Item
    - pipeline 선택.
    - 설정은 밑에 SCM pipline 선택하고 딴 것은 부가적.
    - 깃 허브 주소와 credentials에는 깃허브 아이디와 비밀번호 입력.
    - 그리고 jenkinsfile 경로 설정. 나중에 jenkinsfile을 만들예정.

### **github webhook 설정**

- 깃허브 settings에서 Webhooks 선택
    - http://<Jenkins서버 IP>:<port>/github-webhook/
    - application/json
    - Juste the push event 체크
    - add hook하고 끝.