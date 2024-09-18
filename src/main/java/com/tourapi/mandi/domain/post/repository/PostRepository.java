package com.tourapi.mandi.domain.post.repository;

import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 카테고리별로 최신 게시글을 페이지로 가져오는 메서드
    @Query("select p from Post p join fetch p.user where p.category = :category")
    Page<Post> findByCategoryOrderByCreatedAtDesc(@Param("category") Category category, Pageable pageable);

    // 모든 게시글을 최신순으로 페이지로 가져오는 메서드
    @Query("select p from Post p join fetch p.user")
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);





    @Query("SELECT DISTINCT p FROM Post p " +
            "JOIN FETCH p.user u " +
            "LEFT JOIN FETCH p.postImageList pi " +
            "LEFT JOIN FETCH p.commentList c " +
            "LEFT JOIN FETCH c.childComments cc " +
            "WHERE p.postId = :postId")
    Optional<Post> findPostWithDetailsById(@Param("postId") Long postId);
}
