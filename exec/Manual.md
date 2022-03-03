# TMI 프로젝트 실행 및 설정 Manual



## Plug-in

### 

### Maven Project

* Path : `/backend/tmi-maven-plugin`



```
mvn clean package
```



* 빌드 이후에 나온 jar 파일을 확인하고 싶은 프로젝트 아래 lib 폴더에 저장









## Main Server





### Spring Project

* Path : `/backend/TmiProject`

```shell
mvn clean package
cd target
nohup java -jar *.jar &
```





### Mysql

* Port : 3306

* Spring Project를 빌드하는 즉시 JPA에 의해 테이블이 생성되지만 그 전에 tmi 스키마는 미리 생성이 되어있어야 합니다.





### Nginx Setting

```
server {
    listen       80;
    listen  [::]:80;
    server_name  k4a201.p.ssafy.io;
    
    return 301 https://$host$request_uri;
}

server {
    listen      443 ssl;
    server_name k4a201.p.ssafy.io;
    set         $base /usr/share/nginx;
    index       index.html index.htm;

    ssl_certificate /etc/letsencrypt/archive/k4a201.p.ssafy.io/fullchain1.pem;
    ssl_certificate_key /etc/letsencrypt/archive/k4a201.p.ssafy.io/privkey1.pem;

    ssl_session_timeout 5m;
    ssl_protocols SSLv2 SSLv3 TLSv1 TLSv1.1 TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers on;

    location / {
        alias   $base/html/;
        try_files $uri $uri/ /index.html;
    }

    location /static/coverage/ {
        alias   $base/static/coverage/;
        try_files $uri $uri/ $uri.html $uri.html/ /index.html;
    }

    location /api/ {
        proxy_pass http://main_server:3000/api/;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header Host $http_host;
    }
}
```













## Data Server



### Spring Project

* Path : `/backend/TMI`

```
mvn clean package
cd target
nohup java -jar *.jar &
```



### MongoDB

* Port : 27017
* MongoDB에서 tmi database를 미리 만든 이후에 Data Server를 빌드해야합니다.





## Frontend React

* Path : `/frontend`

```shell
npm i
npm run build
```



