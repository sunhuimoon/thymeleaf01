package com.thymeleaf01.service;

import com.thymeleaf01.dto.QuestionDTO;
import com.thymeleaf01.enums.CommentTypeEnum;
import com.thymeleaf01.exception.CustomizeErrorCode;
import com.thymeleaf01.exception.CustomizeException;
import com.thymeleaf01.mapper.CommentMapper;
import com.thymeleaf01.mapper.QuestionMapper;
import com.thymeleaf01.model.Comment;
import com.thymeleaf01.model.Question;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            //回复问题
            QuestionDTO question= questionMapper.getById(comment.getParentId());
            if (question ==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(question.getId());
        }
    }
}
