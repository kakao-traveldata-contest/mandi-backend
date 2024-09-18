package com.tourapi.mandi.domain.comment.entity;

import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.global.util.AuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment_tb")
@Getter
@Setter
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
    private List<Comment> childComments = new ArrayList<>();  // 대댓글 리스트

    @Column(name = "content", length = 500, nullable = false)
    private String content;  // 댓글 본문

    @Column(name = "like_cnt", nullable = false)
    private int likeCnt;  // 좋아요 수
}
