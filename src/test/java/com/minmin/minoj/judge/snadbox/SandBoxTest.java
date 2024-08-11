package com.minmin.minoj.judge.snadbox;

import com.minmin.minoj.judge.sandbox.SandBox;
import com.minmin.minoj.judge.sandbox.SandBoxFactory;
import com.minmin.minoj.judge.sandbox.SandBoxProxy;
import com.minmin.minoj.judge.sandbox.model.ExecuteCodeRequest;
import com.minmin.minoj.judge.sandbox.model.ExecuteCodeResponse;
import com.minmin.minoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SandBoxTest {
    @Value("${sandbox.type:example}")
    private String type;

    @Test
    void testSandBoxProxy() {
        SandBox sandBox = SandBoxFactory.newInstance(type);
        sandBox = new SandBoxProxy(sandBox);

        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"计算结果：\" + (a + b));\n" +
                "    }\n" +
                "}\n";
        List<String> inputList = new ArrayList<>();
        inputList.add("1 2");
        inputList.add("3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(QuestionSubmitLanguageEnum.JAVA.getValue())
                .build();
        ExecuteCodeResponse executeCodeResponse = sandBox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
}
}
