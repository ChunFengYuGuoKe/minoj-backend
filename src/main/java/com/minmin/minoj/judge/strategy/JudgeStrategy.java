package com.minmin.minoj.judge.strategy;

import com.minmin.minoj.judge.sandbox.model.JudgeInfo;

/**
 * 判题策略接口
 */
public interface JudgeStrategy {
    /**
     * 根据判题上下文信息，使用不同的具体策略类进行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
