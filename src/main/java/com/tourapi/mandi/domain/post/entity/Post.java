package com.tourapi.mandi.domain.post.entity;

import com.tourapi.mandi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "post_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "category", nullable = false)
//    private Category category;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PostImg> listPostImg;

    @Column(name = "like_cnt", nullable = false)
    private int likeCnt;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> commentList;
}
