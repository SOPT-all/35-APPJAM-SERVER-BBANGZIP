package com.sopt.bbangzip.domain.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**g
 * 레벨과 해당 레벨의 최대 포인트를 함께 반환하는 클래스
 */
@Getter
@RequiredArgsConstructor
public class LevelInfo {

    private final int level;
    private final int maxReward;

}