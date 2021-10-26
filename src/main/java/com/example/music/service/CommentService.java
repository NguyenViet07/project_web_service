package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.request.CommentRequest;
import com.example.music.model.Comment;

import com.example.music.model.User;
import com.example.music.repositories.CommentRepository;

import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public ResponseResult save(CommentRequest commentRequest, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            }

            Comment comment = new Comment();
            comment.setDescription(commentRequest.getDescription());
            comment.setUserId(user.getUserId());
            comment.setSongId(commentRequest.getSongId());

            commentRepository.save(comment);

            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult getListCommentBySongId(CommentRequest commentRequest) {
        try {
            PageRequest page = PageRequest.of(commentRequest.getPage() - 1, commentRequest.getSize());
            Page<Comment> commentPage = commentRepository.findAllBySongId(commentRequest.getSongId(), page );
            return ResponseResult.success(commentPage);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    @Transactional
    public ResponseResult deleteComment(Long commentId, String token) {
        try {
            // lấy thông tin user
            User user = tokenProvider.getUserIdFromJWT(token);

            if (user == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải đăng nhập");
                return new ResponseResult(responseCode);
            } else {
                Comment comment = commentRepository.findAllByCommentId(commentId);
                if (user.getRoleId() != 1l && user.getUserId() != comment.getUserId()) {
                    ResponseCode responseCode = ResponseCode.ERROR;
                    responseCode.setMessage("Bạn chỉ được xóa bình luận của mình");
                    return new ResponseResult(responseCode);
                }
                commentRepository.deleteAllByCommentIdAndUserId(commentId, user.getUserId());
                return ResponseResult.success(null);
            }
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

}
