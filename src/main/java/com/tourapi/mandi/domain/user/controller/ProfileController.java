package com.tourapi.mandi.domain.user.controller;

import com.tourapi.mandi.domain.user.dto.NicknameValidationRequestDto;
import com.tourapi.mandi.domain.user.service.UserService;
import com.tourapi.mandi.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "프로필 API 목록")
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Validated
public class ProfileController {
    private final UserService userService;

    @Operation(summary = "중복 닉네임 검증")
    @PostMapping("/check-nickname")
    public ResponseEntity<ApiUtils.ApiResult<Boolean>> checkNicknameDuplication(
            @RequestBody @Valid NicknameValidationRequestDto requestDto
    ) {
        return ResponseEntity.ok(ApiUtils.success(userService.checkNicknameDuplication(requestDto.nickname())));
    }
}
