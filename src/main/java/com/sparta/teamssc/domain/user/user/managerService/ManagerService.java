package com.sparta.teamssc.domain.user.user.managerService;

import com.sparta.teamssc.domain.user.user.dto.request.ApproveManagerRequestDto;
import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;
import org.springframework.data.domain.Page;

public interface ManagerService {

    void signupApproval(Long userId);

    void signupRefusal(Long userId);

    Page<PendSignupResponseDto> getPendSignup(int page, String email);

    void approveManager(ApproveManagerRequestDto approveManagerRequestDto);
}
