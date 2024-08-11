package com.minmin.minoj.judge.sandbox.model;

import lombok.Data;

/**
 * 执行信息
 */
@Data
public class JudgeInfo {
    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 执行消耗内存
     */
    private long memory;

    /**
     * 执行消耗时间
     */
    private long time;
}
