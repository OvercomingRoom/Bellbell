package com.overcomingroom.bellbell.usernotification.service;

import com.overcomingroom.bellbell.exception.CustomException;
import com.overcomingroom.bellbell.exception.ErrorCode;
import com.overcomingroom.bellbell.member.domain.entity.Member;
import com.overcomingroom.bellbell.member.domain.service.MemberService;
import com.overcomingroom.bellbell.response.ResponseCode;
import com.overcomingroom.bellbell.usernotification.domain.dto.UserNotificationRequestDto;
import com.overcomingroom.bellbell.usernotification.domain.dto.UserNotificationResponseDto;
import com.overcomingroom.bellbell.usernotification.domain.entity.UserNotification;
import com.overcomingroom.bellbell.usernotification.repository.UserNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 알림 서비스를 제공하는 클래스입니다.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserNotificationService {

  private final UserNotificationRepository userNotificationRepository;
  private final MemberService memberService;

  /**
   * 사용자 알림을 생성하는 메서드입니다.
   *
   * @param accessToken 사용자의 액세스 토큰
   * @param dto         알림 생성 요청 DTO
   * @return 응답 코드
   */
  public ResponseCode createUserNotification(String accessToken, UserNotificationRequestDto dto) {
    Member member = memberService.getMember(accessToken);
    userNotificationRepository.save(
        new UserNotification(dto.getContent(), dto.getTime(), dto.getDay(), member));

    return ResponseCode.USER_NOTIFICATION_CREATE_SUCCESSFUL;
  }

  /**
   * 사용자의 모든 알림을 가져오는 메서드입니다.
   *
   * @param accessToken 사용자의 액세스 토큰
   * @return 사용자 알림 목록 DTO
   */
  public List<UserNotificationResponseDto> getUserNotifications(String accessToken) {
    Member member = memberService.getMember(accessToken);
    List<UserNotification> userNotifications = userNotificationRepository.findAllByMember(member)
        .orElseThrow(() -> new CustomException(
            ErrorCode.NOT_EXISTS_USER_NOTIFICATION));
    List<UserNotificationResponseDto> userNotificationResponseDtos = new ArrayList<>();
    for (UserNotification userNotification : userNotifications) {
      UserNotificationResponseDto dto = new UserNotificationResponseDto();
      dto.setId(userNotification.getId());
      dto.setContent(userNotification.getContent());
      dto.setTime(userNotification.getTime());
      dto.setDay(userNotification.getDay());
      userNotificationResponseDtos.add(dto);
    }
    return userNotificationResponseDtos;
  }

  /**
   * 사용자의 특정 알림을 삭제하는 메서드입니다.
   *
   * @param accessToken    사용자의 액세스 토큰
   * @param notificationId 삭제할 알림의 ID
   * @return 응답 코드
   */
  public ResponseCode deleteUserNotification(String accessToken, Long notificationId) {
    Member member = memberService.getMember(accessToken);
    UserNotification userNotification = userNotificationRepository.findById(notificationId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_USER_NOTIFICATION));
    if (!userNotification.getMember().equals(member)) {
      throw new CustomException(ErrorCode.ACCESS_DENIED);
    }
    userNotificationRepository.delete(userNotification);
    return ResponseCode.USER_NOTIFICATION_DELETE_SUCCESSFUL;
  }
}