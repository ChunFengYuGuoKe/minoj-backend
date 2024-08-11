package com.minmin.minoj.judge.strategy;

import com.minmin.minoj.model.dto.question.JudgeCase;
import com.minmin.minoj.model.entity.Question;
import com.minmin.minoj.model.entity.QuestionSubmit;
import com.minmin.minoj.judge.sandbox.model.JudgeInfo;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private QuestionSubmit questionSubmit;
}
