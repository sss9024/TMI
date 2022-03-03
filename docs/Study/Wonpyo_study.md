## Jenkins

### Executing w/ docker-compose

```
version: '3'

services:
  jenkins:
    privileged: true
    image: jenkins/jenkins:lts
    container_name: jenkins
    user: root
    ports:
      - 8080:8080
      - 50000:50000
    volumes:
      - ./jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
```

Docker를 통한 Jenkins를 실행하고, Container에서 Docker를 실행하기 위해서 privileged를 true로 선언하고 docker socket을 바인딩해준다.



## JaCoCo

### pom.xml

```xml
<project>
    <build>
		<plugins>
			<plugin>
				<groupId>org.jacoco</groupId>
				<artifactId>jacoco-maven-plugin</artifactId>
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
				</executions>
			</plugin>
		</plugins>
	</build>
</project>
```

