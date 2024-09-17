package com.tourapi.mandi.domain.post.service;


import com.tourapi.mandi.domain.post.dto.PostDto;
import com.tourapi.mandi.domain.post.entity.Category;
import com.tourapi.mandi.domain.post.entity.Post;
import com.tourapi.mandi.domain.post.repository.PostRepository;
import com.tourapi.mandi.domain.post.util.PostMapper;
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
    public Page<PostDto> getPostsByCategory(Category category, int page, int size) {
        // Pageable 객체 생성 (page와 size를 설정)
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage;

        // 카테고리가 'ALL'인 경우 모든 게시글을 최신순으로 가져옴
        if (category == Category.ALL) {
            postPage = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        } else {
            // 특정 카테고리로 게시글을 최신순으로 가져옴
            postPage = postRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
        }

        // Post 엔티티를 PostDto로 변환
        return postPage.map(PostMapper::toPostDto);
    }
}
