package com.minmin.minoj.judge.strategy;

import com.minmin.minoj.model.entity.QuestionSubmit;
import com.minmin.minoj.judge.sandbox.model.JudgeInfo;
import org.springframework.stereotype.Service;

/**
 * 判题管理，简化调用，隐藏判断逻辑
 */
@Service
public class JudgeManager {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
