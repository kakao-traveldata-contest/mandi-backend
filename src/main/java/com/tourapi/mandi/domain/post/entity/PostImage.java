package com.tourapi.mandi.domain.post.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_image_tb")
@Getter
@NoArgsConstructor
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id")
    private Long postImageId;  // 이미지 ID

    @ManyToOne(fetch = FetchType.LAZY)  // N:1 관계 설정
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // 게시글과의 연관관계

    @Column(name = "url", length = 512, nullable = false)
    private String url;  // 이미지 URL
}
