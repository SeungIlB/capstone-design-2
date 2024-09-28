package com.medical_web_service.capstone.controller;


import com.medical_web_service.capstone.dto.CommentDto;
import com.medical_web_service.capstone.entity.Comment;
import com.medical_web_service.capstone.service.CommentService;
import com.medical_web_service.capstone.service.UserDetailsImpl;
import com.medical_web_service.capstone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestParam Long userId, @RequestParam Long boardId,
                                                 @RequestBody CommentDto.CreateCommentDto createCommentDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long loggedInUserId = userService.getLoggedInUserId(userDetails);

        // 요청한 사용자와 현재 로그인한 사용자의 ID가 일치하지 않는 경우 권한이 없는 것으로 처리
        if (!userId.equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Comment createdComment = commentService.createComment(userId, boardId, createCommentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<Comment>> readComments(@PathVariable Long boardId) {
        List<Comment> comments = commentService.readComments(boardId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@RequestParam Long userId, @RequestParam Long boardId, @PathVariable Long commentId
            , @RequestBody CommentDto.UpdateCommentDto updateCommentDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long loggedInUserId = userService.getLoggedInUserId(userDetails);

        // 요청한 사용자와 현재 로그인한 사용자의 ID가 일치하지 않는 경우 권한이 없는 것으로 처리
        if (!userId.equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Comment updatedComment = commentService.updateComment(userId, boardId, commentId, updateCommentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestParam Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long loggedInUserId = userService.getLoggedInUserId(userDetails);

        // 요청한 사용자와 현재 로그인한 사용자의 ID가 일치하지 않는 경우 권한이 없는 것으로 처리
        if (!userId.equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

