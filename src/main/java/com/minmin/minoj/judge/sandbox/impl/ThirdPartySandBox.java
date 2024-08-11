package com.minmin.minoj.judge.sandbox.impl;

import com.minmin.minoj.judge.sandbox.SandBox;
import com.minmin.minoj.judge.sandbox.model.ExecuteCodeRequest;
import com.minmin.minoj.judge.sandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（第三方代码沙箱的接口）
 */
public class ThirdPartySandBox implements SandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
