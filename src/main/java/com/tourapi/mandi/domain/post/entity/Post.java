package com.tourapi.mandi.domain.post.entity;

import com.tourapi.mandi.domain.comment.entity.Comment;
import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.domain.user.entity.constant.Provider;
import com.tourapi.mandi.domain.user.entity.constant.Role;
import com.tourapi.mandi.global.util.AuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post  extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Setter
    @Lob
    @Column(name = "content")
    private String content;

    @Setter
    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Setter
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> postImageList;

    @Column(name = "like_cnt", nullable = false)
    private int likeCnt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> commentList = new HashSet<>();



    @Builder
    public Post(User user, Category category, String content, String title, List<PostImage> postImageList, int likeCnt,Set<Comment> commentList) {
        this.user = user;
        this.category = category;
        this.content = content;
        this.title = title;
        this.postImageList = postImageList;
        this.likeCnt = likeCnt;
        this.commentList = commentList;
    }
}
