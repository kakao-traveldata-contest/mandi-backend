package com.tourapi.mandi.domain.user.entity;

import com.tourapi.mandi.domain.comment.entity.Comment;
import com.tourapi.mandi.domain.course.entity.CoursePreference;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.user.entity.constant.Provider;
import com.tourapi.mandi.domain.user.entity.constant.Role;
import com.tourapi.mandi.global.util.AuditingEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;



@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tb")
public class User extends AuditingEntity {

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> posts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_preference_id")
    private CoursePreference coursePreference;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(length = 100, nullable = false, unique = true, name = "email")
    private String email;

    @Column(length = 255, nullable = false, name = "password")
    private String password;

    @Column(length = 30, nullable = false, name = "provider")
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(length = 30, nullable = false, name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter
    @Column(length = 10, nullable = false, name = "nickname", unique = true)
    private String nickname;

    @Setter
    @Column(length = 40, name = "description")
    private String description;

    @Setter
    @Column(length = 512, name = "img_url")
    private String imgUrl;

    @Builder
    public User(Long userId, String email, String password, Provider provider, Role role, String nickname, String description, String imgUrl) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.role = role;
        this.nickname = nickname;
        this.description = description;
        this.imgUrl=imgUrl;

    }

    //userId가 같으면 같은 객체라고 판별되도록 equals, hashCode 오버라이드
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User)obj;
        return Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }

    public void updatePreference(final CoursePreference coursePreference) {
        this.coursePreference = coursePreference;
    }
}
