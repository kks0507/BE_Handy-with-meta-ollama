# HANDY (내 손에 세상을) 🌍✨

## 프로젝트 설명

**HANDY**는 초등학교 저학년 전용 AI로, 백과사전부터 긍정적인 친구, 수학 선생님, 그리고 영웅 소설의 상상까지, 아이들이 필요로 하는 모든 것을 제공합니다. AI 기반으로 다양한 기능을 통해 학습과 탐구를 재미있고 창의적으로 접근할 수 있도록 돕습니다.

##. API 서버 실행

서버는 기본적으로 포트 `8080`에서 실행됩니다. 아래 API 엔드포인트를 사용할 수 있습니다:

- `/api/diary/analyze` - 일기 분석 API
- `/api/search/problemTransformation` - 문제 변형 검색 API
- `/api/search/basic` - 일반 검색 API
- `/api/chat` - AI와의 대화 API

## 주요 기능

### 1. 일기 분석 기능

```java
@PostMapping("/api/diary/analyze")
public Map<String, String> analyzeDiary(@RequestBody Map<String, String> requestBody) {
    String diaryEntry = requestBody.get("diaryEntry");
    String aiResponse = diaryService.generateResult(diaryEntry);
    Map<String, String> response = new HashMap<>();
    response.put("Response", aiResponse);
    return response;
}
```

이 기능은 일기 내용을 분석하고, 이를 바탕으로 AI가 생성한 응답을 반환합니다.

### 2. 문제 변형 API

```java
@PostMapping("/api/search/problemTransformation")
public Map<String, String> searchProblemTransformation(@RequestBody Map<String, String> requestBody) {
    String searchQuery = requestBody.get("searchQuery");
    String aiResponse = problemTransformationService.generateResponse(searchQuery);
    Map<String, String> response = new HashMap<>();
    response.put("Response", aiResponse);
    return response;
}
```

사용자가 제공한 문제를 AI가 변형하여 이해하기 쉽게 바꿔줍니다.

### 3. AI와의 대화 기능

```java
@PostMapping("/api/chat")
public Map<String, String> chatWithAI(@RequestBody Map<String, String> requestBody) {
    String userMessage = requestBody.get("message");
    String aiResponse = chatService.generateChatResponse(userMessage);
    Map<String, String> response = new HashMap<>();
    response.put("Response", aiResponse);
    return response;
}
```

AI와 자연스러운 대화를 나누며 다양한 질문에 대한 응답을 받을 수 있습니다.

## 프로젝트 참여자

- [kks0507](https://github.com/kks0507)
```
