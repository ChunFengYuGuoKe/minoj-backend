package com.minmin.minoj.judge;

import cn.hutool.json.JSONUtil;
import com.minmin.minoj.common.ErrorCode;
import com.minmin.minoj.exception.BusinessException;
import com.minmin.minoj.judge.sandbox.SandBox;
import com.minmin.minoj.judge.sandbox.SandBoxFactory;
import com.minmin.minoj.judge.sandbox.SandBoxProxy;
import com.minmin.minoj.judge.sandbox.model.ExecuteCodeRequest;
import com.minmin.minoj.judge.sandbox.model.ExecuteCodeResponse;
import com.minmin.minoj.judge.strategy.JudgeContext;
import com.minmin.minoj.judge.strategy.JudgeManager;
import com.minmin.minoj.model.dto.question.JudgeCase;
import com.minmin.minoj.model.entity.Question;
import com.minmin.minoj.model.entity.QuestionSubmit;
import com.minmin.minoj.model.enums.QuestionSubmitStatusEnum;
import com.minmin.minoj.judge.sandbox.model.JudgeInfo;
import com.minmin.minoj.service.QuestionService;
import com.minmin.minoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {
    @Value("${sandbox.type:example}")
    private String type;

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1. 根据提交id获取题目信息，提交信息(代码等)
        // 防御性编程
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "问题不存在");
        }
        // 2. 如果判题状态不为waiting，说明已经开始判题，抛出异常，避免重复判题
        if (!Objects.equals(questionSubmit.getStatus(), QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "判题正在进行中");
        }
        // 3. 更新题目状态
        QuestionSubmit questionUpdate = new QuestionSubmit();
        questionUpdate.setId(questionSubmitId);
        questionUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新错误");
        }

        // 4. 调用代码沙箱，获取执行结果
        // 4.1 创建代码沙箱及代理
        SandBox sandBox = SandBoxFactory.newInstance(type);
        sandBox = new SandBoxProxy(sandBox);

        // 4.2 从提交信息中获取代码及语言
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        // 4.3 从题目信息中获取输入用例（String -> List -> inputList）
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 4.4 创建执行请求对象
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .inputList(inputList)
                .language(language)
                .build();
        ExecuteCodeResponse executeCodeResponse = sandBox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();

        // 5. 根据代码沙箱的运行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        // 5.1更新题目状态
        questionUpdate = new QuestionSubmit();
        questionUpdate.setId(questionSubmitId);
        questionUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新错误");
        }

        // 6. 封装执行结果
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);

        return questionSubmitResult;
    }
}
