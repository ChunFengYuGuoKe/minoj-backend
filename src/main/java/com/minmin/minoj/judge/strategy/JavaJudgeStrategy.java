package com.minmin.minoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.minmin.minoj.model.dto.question.JudgeCase;
import com.minmin.minoj.model.dto.question.JudgeConfig;
import com.minmin.minoj.model.entity.Question;
import com.minmin.minoj.model.enums.JudgeInfoMessageEnum;
import com.minmin.minoj.judge.sandbox.model.JudgeInfo;

import java.util.List;
import java.util.Optional;

public class JavaJudgeStrategy implements JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        // 获取执行消耗时间和内存
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        long memory = Optional.of(judgeInfo.getMemory()).orElse(0L);
        long time = Optional.of(judgeInfo.getTime()).orElse(0L);
        // 获取执行输入输出
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        // 获取题目
        Question question = judgeContext.getQuestion();
        // 获取用例列表
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        // 定义判题结果信息
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());

        // 5.1 判断沙箱输出数是否和预期相等
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        // 5.2 依次判断每个输入对应的输出是否符合
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // 5.3 判断是否满足其他执行限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        if (memory > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        if (time > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }

        return judgeInfoResponse;
    }
}
