## Kebob 팀원 소개

|K003|K015|K047|K060|
|:---:|:---:|:---:|:---:|
|김민석|김혜성|이효동|최진형|

## 서비스 소개

### ⏰ 개밥타임
"개밥줬어?" 아이디어를 차용하여 개발한 서비스입니다. 개(혹은 다른 반려동물)를 키우는 집에서 가족 구성원끼리 밥을 누가 언제 줬는지 쉽게 공유하고 밥 주는 것을 상기시켜 주기 위한 모바일 앱입니다.

### 🐶 앱 아이콘
![dog-time-screenshot](https://user-images.githubusercontent.com/39328846/134578805-60c230c5-8bcf-4227-8e93-864e1a9bb96a.jpg)

### 💡 핵심 기능
 - 밥 주는 시간 설정
 - 푸쉬 알림을 통해 밥시간 알림
 - 밥을 누가 언제 줬는지 기록

### 📕 사용 기술
- Android
- Glide
- Lottie
- Firebase Realtime Database
- Firebase Cloud Messaging

### 🖼 화면 구성
1. **가족 등록 페이지**
    - 가족 구성원 별 이름 및 프로필 이미지 설정
    <img src="https://user-images.githubusercontent.com/62787596/134621449-7f972ea5-5549-4729-a462-48648276417d.png" alt="가족선택" style="width:300px;"/>

1. **메인 페이지**
    - FCM을 이용해 급식 시간 푸시 알림
    - 급식 여부 체크해 DB 업데이트
    <img src="https://user-images.githubusercontent.com/62787596/134621488-5a1f72b7-28cb-422e-8d0d-527a0c5e582a.png" alt="초기화면" style="width:300px;"/>
    <img src="https://user-images.githubusercontent.com/62787596/134621628-4d5246ce-91ed-4287-bb51-ff3683149e99.png" alt="간식 등록 후 메인화면" style="width:300px;"/>
    <img src="https://user-images.githubusercontent.com/62787596/134621777-c5cd9ded-9863-40de-98c3-a2c74a55ddbd.png" alt="식사 지급 알림" style="width:300px;"/>
    <img src="https://user-images.githubusercontent.com/62787596/134621821-cc3ff0c2-095c-4a0d-8125-70a5dee062f0.png" alt="약, 식사 시간 알림" style="width:300px;"/>

1. **급식 시간 관리 페이지**
    - 급식 삭제 및 등록
    <img src="https://user-images.githubusercontent.com/62787596/134621693-fbd2b44c-3851-4071-b88d-c73cb44a82b5.png" alt="급식 관리 페이지" style="width:300px;"/>

1. **급식 시간 등록 페이지**
    - 급식 종류와 시간 설정해 DB에 추가
    <img src="https://user-images.githubusercontent.com/62787596/134621536-9a6e81de-88fb-4343-9c09-8e84754e56f9.png" alt="아침등록" style="width:300px;"/>

### 👩‍👩‍👧‍👧 역할 분담
- 김민석: UI/UX 디자인, 푸시 알림 기능 구현, 애니메이션 구현
- 김혜성: 급식 시간 관리 페이지 구현
- 이효동: 메인 페이지 구현
- 최진형: 가족 등록 페이지 구현, DB 구축

### 🎨 시연 영상


- 접속
https://user-images.githubusercontent.com/62787596/134624132-44f4eecb-4c85-4393-bc34-cc41beb4ea48.mov

- 삭제
https://user-images.githubusercontent.com/62787596/134624167-1b167747-401e-4202-a278-eb1f0e24a062.mov

- 식사 추가
https://user-images.githubusercontent.com/62787596/134624318-61ab84d1-efcc-42d3-9460-5d111e112238.mov

- 식사 지급
https://user-images.githubusercontent.com/62787596/134624340-bd2b8128-fcfa-40dd-8104-b0293c291c6e.mov

- 식사 지급 알림 
https://user-images.githubusercontent.com/62787596/134624389-1b03d343-4730-4ea9-a322-0135590a2875.mov
