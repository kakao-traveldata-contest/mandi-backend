package com.tourapi.mandi.domain.post.service;


import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    // 카테고리별 게시글을 페이지로 가져오는 메서드
    public Page<Post> getPostsByCategory(Category category, int page, int size) {
        // Pageable 객체 생성 (page와 size를 설정)
        Pageable pageable = PageRequest.of(page, size);


        // 카테고리별로 최신순으로 게시글을 페이징 처리하여 가져옴
        Page<Post> posts = postRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);

        // 카테고리별로 최신순으로 게시글을 페이징 처리하여 가져옴
        return posts;
    }
}
