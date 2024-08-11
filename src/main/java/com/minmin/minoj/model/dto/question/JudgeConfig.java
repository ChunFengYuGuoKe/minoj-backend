package com.minmin.minoj.model.dto.question;

import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;

    /**
     * 栈限制（KB）
     */
    private Long stackLimit;

    @Override
    public String toString() {
        return "时间" + timeLimit.toString();
    }
}
