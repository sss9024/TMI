package com.tmi.tmi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TestCase {
    private String testCaseName; //테스트 케이스 이름
    private int testTime; //테스트에 걸린 시간
    private boolean isFail; //실패 했는지 아닌지 여부
    private String failDescription; //fail에 대한 이유
}
