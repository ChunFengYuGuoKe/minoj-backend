package com.minmin.minoj.judge.sandbox.impl;

import com.minmin.minoj.judge.sandbox.SandBox;
import com.minmin.minoj.judge.sandbox.model.ExecuteCodeRequest;
import com.minmin.minoj.judge.sandbox.model.ExecuteCodeResponse;
import com.minmin.minoj.model.enums.JudgeInfoMessageEnum;
import com.minmin.minoj.model.enums.QuestionSubmitStatusEnum;
import com.minmin.minoj.judge.sandbox.model.JudgeInfo;

import java.util.List;

/**
 * 示例代码沙箱（仅用于跑通流程）
 */
public class ExampleSandBox implements SandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(199L);
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
