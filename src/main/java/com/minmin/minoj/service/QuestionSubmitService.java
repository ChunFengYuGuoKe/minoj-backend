package com.minmin.minoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minmin.minoj.model.dto.question.QuestionQueryRequest;
import com.minmin.minoj.model.entity.Question;
import com.minmin.minoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minmin.minoj.model.entity.User;
import com.minmin.minoj.model.questionsubmit.QuestionSubmitAddRequest;
import com.minmin.minoj.model.questionsubmit.QuestionSubmitQueryRequest;
import com.minmin.minoj.model.vo.QuestionSubmitVO;
import com.minmin.minoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author minmin
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-06-18 21:30:45
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return 提交记录id
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
