package com.sopt.bbangzip.domain.user.service;

import com.sopt.bbangzip.domain.user.api.dto.response.MypageDto;
import com.sopt.bbangzip.domain.user.repository.UserRepository;
import com.sopt.bbangzip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRetriever userRetriever;
    private final UserLevelCalculator userLevelCalculator;
    private final UserRepository userRepository;

    @Transactional
    public MypageDto updateAndGetUserLevelStatus(Long userId) {
        User user = userRetriever.findByUserId(userId);

        // 유저의 현재 포인트 기반 레벨 정보 계산
        UserLevelCalculator.LevelInfo levelInfo = userLevelCalculator.calculateLevelInfo(user.getPoint());

        // 유저 레벨 업데이트 후 저장
        user.updateUserLevel(levelInfo.getLevel());
        userRepository.save(user);

        // 레벨별 상세 정보 생성
        List<MypageDto.LevelDetail> levelDetails = createLevelDetails(levelInfo.getLevel());

        return new MypageDto(
                user.getUserLevel(),
                user.getBadgeCount(),
                user.getPoint(),
                levelInfo.getMaxReward(),
                levelDetails
        );
    }

    /**
     * 레벨별 상세 정보 리스트 생성
     * @param currentLevel  유저의 현재 레벨
     * @return 레벨 상세 정보 리스트
     */
    private List<MypageDto.LevelDetail> createLevelDetails(int currentLevel) {
        List<MypageDto.LevelDetail> levelDetails = new ArrayList<>();

        for (int level = 1; level <= LEVEL_DETAILS.size(); level++) {
            LevelDetailData levelDetailData = LEVEL_DETAILS.get(level - 1);

            // 현재 레벨 이하일 경우 잠금 해제
            boolean isLocked = level > currentLevel;

            levelDetails.add(new MypageDto.LevelDetail(
                    levelDetailData.level,
                    levelDetailData.levelName,
                    levelDetailData.levelDescription,
                    levelDetailData.levelImage,
                    isLocked
            ));
        }

        return levelDetails;
    }


    /**
     * 레벨별 상세 정보
     */
    private static class LevelDetailData {
        private final int level;
        private final String levelName;
        private final String levelDescription;
        private final String levelImage;

        public LevelDetailData(int level, String levelName, String levelDescription, String levelImage) {
            this.level = level;
            this.levelName = levelName;
            this.levelDescription = levelDescription;
            this.levelImage = levelImage;;
        }
    }

    // 레벨별 상세 정보를 관리하는 상수 데이터
    private static final List<LevelDetailData> LEVEL_DETAILS = List.of(
            new LevelDetailData(1, "허름한 돗자리", "빵집을 시작한 초보 사장님!", "https://github.com/user-attachments/assets/4ade00bf-4384-4d0c-99d1-a0bb6e1b9a94"),
            new LevelDetailData(2, "평범한 돗자리", "평범한 돗자리를 얻은 사장님!", "https://github.com/user-attachments/assets/4ade00bf-4384-4d0c-99d1-a0bb6e1b9a94"),
            new LevelDetailData(3, "럭셔리 돗자리", "럭셔리 돗자리를 얻은 사장님!", "https://github.com/user-attachments/assets/4ade00bf-4384-4d0c-99d1-a0bb6e1b9a94")
    );
}
