package com.tourapi.mandi.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.entity.PostImage;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.global.util.AuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "comment_tb")
@Getter
@NoArgsConstructor
public class Comment extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;  // 댓글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 댓글이 달린 게시글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;  // 상위 댓글 (대댓글의 경우)

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> childComments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", length = 500, nullable = false)
    @Setter
    private String content;  // 댓글 본문

    @Column(name = "like_cnt", nullable = false)
    @Setter
    private int likeCnt;  // 좋아요 수


    @Column(name = "is_deleted", nullable = false)
    @Setter
    private boolean isDeleted;


    @Builder
    public Comment(Post post, Comment parentComment, User user, String content, int likeCnt, boolean isDeleted) {
        this.post = post;
        this.parentComment = parentComment;
        this.user = user;
        this.content = content;
        this.likeCnt = likeCnt;
        this.isDeleted = isDeleted;
    }

}
