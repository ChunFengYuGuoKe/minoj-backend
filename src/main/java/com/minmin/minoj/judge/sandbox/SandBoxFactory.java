package com.minmin.minoj.judge.sandbox;

import com.minmin.minoj.judge.sandbox.impl.ExampleSandBox;
import com.minmin.minoj.judge.sandbox.impl.RemoteSandBox;
import com.minmin.minoj.judge.sandbox.impl.ThirdPartySandBox;

/**
 * 使用静态工厂模式创建代码沙箱
 * 如果确定代码沙箱实例不会出现线程安全问题，可以使用单例工厂模式
 */
public class SandBoxFactory {
    /**
     * 根据接收参数创建具体代码沙箱实现
     * @param type 沙箱类型
     * @return
     */
    public static SandBox newInstance(String type) {
        switch (type){
            case "example":
                return new ExampleSandBox();
            case "remote":
                return new RemoteSandBox();
            case "thirdParty":
                return new ThirdPartySandBox();
            default:
                return new ExampleSandBox();
        }
    }
}
