package com.minmin.minoj.judge;


import com.minmin.minoj.model.entity.QuestionSubmit;
import com.minmin.minoj.model.vo.QuestionSubmitVO;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
