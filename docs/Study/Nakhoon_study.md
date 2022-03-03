# 210415 Jenkins Study



## Jenkins 개념

* 소프트웨어 개발 시 지속적으로 통합 서비스를 제공하는 툴
* 일상 작업을 자동화 가능
* repo에 대한 지속적인 통합과 전달 환경 구축
* 오픈소스 자동화 서버



## Jenkins 동작 방식

Jenkins는 주요 운영체제용 자바 8 WAR 아카이브와 설치 패키지, 홈브루(Homebrew) 패키지, 도커 이미지, 그리고 [소스코드](https://github.com/jenkinsci/jenkins) 형태로 사용할 수 있다. 소스코드는 대부분 자바이며, 몇 개의 그루브(Groovy), 루비(Ruby), 그리고 앤틀러(Another Tool For Language Recognition, ANTLR) 파일이 들어 있다.

Jenkins WAR를 단독으로 또는 톰캣 같은 자바 애플리케이션 서버에서 서버렛(Serverlet)으로 실행할 수 있다. 둘 가운데 어느 경우든, 웹 사용자 인터페이스(UI)를 생성하며 REST API에 대한 호출을 받아들인다.



# 210419 JaCoCo Study



## Code Coverage 란?

* 소프트웨어 테스트를 논할 때 얼마나 테스트가 충분한가를 나타내는 지표 중 하나다.
* 소프트웨어 테스트를 진행했을 때 코드 자체가 얼마나 실행되었는 지
* 코드는 Statement(구문), Condition(조건), Decision(결정)으로 이루어지는 데 이 구조를 얼마나 커버했는 가
* Statement 커버리지 : 가장 일반적으로 사용되는 커버리지
* Condition 커버리지 : 내부 조건이 참 혹은 거짓이면 충족되는 커버리지



## 1. pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>me.wony</groupId>
	<artifactId>classLoaderTest</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>
	<name>classLoaderTest</name>
	<url>http://maven.apache.org</url>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<maven.compiler.source>11</maven.compiler.source>
		<maven.compiler.target>11</maven.compiler.target>
	</properties>
	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>3.8.1</version>
			<scope>test</scope>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.jacoco</groupId>
				<artifactId>jacoco-maven-plugin</artifactId>
				<version>0.8.4</version>
				<executions>
					<execution>
						<goals>
							<goal>prepare-agent</goal>
						</goals>
					</execution>
					<execution>
						<id>report</id>
						<phase>prepare-package</phase>
						<goals>
							<goal>report</goal>
						</goals>
					</execution>
					<execution>
						<id>jacoco-check</id>
						<goals>
							<goal>check</goal>
						</goals>
						<configuration>
							<rules>
								<rule>
									<element>PACKAGE</element>
									<limits>
										<limit>
											<counter>LINE</counter>
											<value>COVEREDRATIO</value>
											<minimum>0.50</minimum>
										</limit>
									</limits>
								</rule>
							</rules>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
</project>
```

* Jacoco와 Junit을 dependency로 추가



## 2. EclEmma 플러그인 설치

* `http://update.eclemma.org/`를 입력하여 EclEmma 플러그인을 설치
* 프로젝트 마우스 우측 `coverage as` 클릭
* 측정 예시

![img](https://blog.kakaocdn.net/dn/pah9x/btqyZYoJ2bV/r6dMWpIk2amMCDpiZ8ikl0/img.png)