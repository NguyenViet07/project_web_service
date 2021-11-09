package com.example.music.service;

import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.ResponsePageDTO;
import com.example.music.dto.request.CommentRequest;
import com.example.music.dto.response.CommentResponse;
import com.example.music.dto.response.UserViewDto;
import com.example.music.model.Comment;

import com.example.music.model.User;
import com.example.music.repositories.CommentRepository;

import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import com.example.music.untils.RepositoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

            if (commentRequest.getDescription().isEmpty() || commentRequest.getDescription() == null) {
                ResponseCode responseCode = ResponseCode.ERROR;
                responseCode.setMessage("Bạn phải nhập bình luận");
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
            List<Object[]> commentPage = commentRepository.findAllBySongId(commentRequest.getSongId() );
            return getListComment(commentPage);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }
    }

    public ResponseResult getListComment(List<Object[]> currentPage) {
        if (currentPage != null) {
            List<CommentResponse> rs = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            CommentResponse tinyItem;
            int index;
            for (Object[] row : currentPage) {
                index = 0;
                tinyItem = new CommentResponse();
                tinyItem.setCommentId(RepositoryUtil.getLongFromBigInteger(row[index++]));
                tinyItem.setDescription(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setCreateDate(RepositoryUtil.getTimestampToString(row[index++], dateFormat));
                tinyItem.setUserName(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setImage(RepositoryUtil.getStringByValue(row[index++]));
                rs.add(tinyItem);
            }
            return ResponseResult.success(rs);
        }
        return ResponseResult.success(null);
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
