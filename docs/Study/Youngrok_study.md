Jenkins

* CI(Continuous Integration) ?

  소프트웨어 개발 시 지속적으로 통합 서비스를 제공하는 툴

* CD(Continuous Delivery) ?

  지속적인 배포 -> 코드베이스가 항상 배포 가능한 상태에 있음을 보장

* Build란?

  서버에 올릴 수 있는 상태로 만드는 것

  서버에 올려 사용자가 사용할 수 있게 한 것은 배포 라고 한다.

* 빌드를 자동화 하는 이유?

  빌드는 주기적으로 자주 진행해야하는 업무이고, 빌드하는 일에 소요되는 시간이 많기때문에 해당 작업을 자동화해서 개발자의 피로를 줄일 수 있게 해준다.

* Jenkins?

  빌드를 자동화해주는 툴(Maven or Gradle)을 사용중이라면 안 쓸 이유가 없다!

  대표적인 기능

  * 대쉬보드 제공 

  여러가지 배포 작업의 상황을 모니터링 할 수 있습니다.

  * 배포 스크립트를 실행해주기

  배포 스크립트를 개발자 로컬에서도 실행할 수 있는데 젠킨스로 스케쥴링 해줌

Jenkins에 Maven 설치 방법

1. Jenkins 의 전체 설정 > Global Tool Configuration 로 이동하여 Maven 부분에 아래와 같이 설정합니다. 

   ![img](https://blog.kakaocdn.net/dn/eFYDFT/btqwxR6wkei/2YpoSnijCGJuvteosoDWYK/img.png)

2. 프로젝트에서 설정

![img](https://blog.kakaocdn.net/dn/HYGZT/btqwyQy3YXT/VrscrhFZraKCvSECfbhk00/img.png)

 

JUnit

자바용 테스트 도구

```java
package lee.junit;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
 
import org.junit.Test;
 
public class CalculatorTest {
   
  //@Test(timeout=5000)  : 시간단위 : 밀리초
  //@Test(expected=RuntimeException.class) : RuntimeException이 발생해야 성공
  //@Ignore(value=”test”)
   
  //@Before 해당 테스트 클래스의 객체를 초기화하는 작업
  //@After 해당 테스트  실행 수 실행
   
  //@BeforeClass 테스트 클래스 실행 전 한번 실행
  //@AfterClass 테스트 클래스 실행 후 한번 실행
  @Test
  public void testSum( ) {
    Calculator cal = new Calculator();
    int result = cal.sum(10, 20);
    assertEquals(20,  result, 10);
  }
 
}

```

```java
assert.ArrayEquals(a, b); 

 - 배열 A와 B가 일치함을 확인한다.


assert.Equals(a, b);

 - 객체 A와 B가 일치함을 확인한다.


assert.Equals(a, b, c);

 - 객체 A와 B가 일치함을 확인한다.

 - a: 예상값, b:결과값, c: 오차범위


assert.Same(a, b); 

 - 객체 A와 B가 같은 객임을 확인한다.


assert.True(a); 

 - 조건 A가 참인가를 확인한다.


assert.NotNull(a); 

 - 갹채 A가 null이 아님을 확인한다.

```

* 테스트 실패 시 나오는 화면

  ![img](https://t1.daumcdn.net/cfile/tistory/996648475A7D508C13)

* 테스트 성공 시 나오는 화면

  ![img](https://t1.daumcdn.net/cfile/tistory/991EB0475A7D508D1B)



```
@BeforeClass

 - 테스트 클래스 수행 시 단위 테스트 메소드 보다 먼저 딱 한 번 수행되어야 할 경우 지정

 ex) DB 연결 시 드라이버 로딩 부분 지정



@AfterClass

 - 단위테스트 함수들이 다 수행되고 맨 마지막에 수행되어야 할 경우 지정

 ex) DB 연결 후 마지막에 드라이버를 반납하는 부분


@Before
 - @Test 메서드가 실행되기 전 실행
 
@After
 - @Test 메세드가 실행된 후 실행
```



Jacoco

- Java Code Coverage의 약자
- Junit 테스트의 결과를 바탕으로 커버리지 결과를 리포트 해주는 툴
- 커버리지 결과를 약 80% 이상으로 잡음



JacocoController.java

```java
package com.example.jacocotest; 

import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController; 

@RestController 
public class JacocoController { 

	@GetMapping("/test") 
	public String test(@RequestParam int n) { 
		if (n >= 0) { 
			return "hello"; 
		} else { 
			return "world"; 
        } 
    } 
}
```

JacocoControllerTest.java

```java
package com.example.jacocotest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; 
import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.web.servlet.MockMvc; 

@SpringBootTest 
@AutoConfigureMockMvc 
public class JacocoControllerTest { 
	@Autowired 
	private MockMvc mvc; 
	
	@Test 
	public void test() throws Exception { 
		mvc.perform(get("/test") 
			.param("n", "3")) 
			.andExpect(content().string("hello")) 
			.andExpect(status().isOk()); 
    }
}
```

Jacoco 테스트 결과

![img](https://blog.kakaocdn.net/dn/bE05tj/btqT6G2Byhd/1SDlx3kMPFdEjR0BJExeT1/img.png)

Controller 커버리지 결과

![img](https://blog.kakaocdn.net/dn/b0okVr/btqUcA76lzY/ExOP5Fu9u0HAw2clKEOaFk/img.png)

각 색상의 의미

* 초록색 : 테스트가 진행된 부분
* 노란색 : 조건, 결정 커버리지가 모두 충족되지 않은 부분
* 빨강색 : 커버리지가 진행되지 않은 부분



