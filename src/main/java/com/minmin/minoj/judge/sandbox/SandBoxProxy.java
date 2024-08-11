package com.minmin.minoj.judge.sandbox;

import com.minmin.minoj.judge.sandbox.model.ExecuteCodeRequest;
import com.minmin.minoj.judge.sandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SandBoxProxy implements SandBox{
    private final SandBox sandBox;

    public SandBoxProxy(SandBox sandBox) {
        this.sandBox = sandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = sandBox.executeCode(executeCodeRequest);
        log.info("返回信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
