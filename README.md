# HANDY (내 손에 세상을) 🌍✨

## 프로젝트 설명

**HANDY**는 초등학교 저학년 전용 AI 챗봇으로, 백과사전부터 긍정적인 친구, 수학 선생님, 그리고 영웅 소설의 상상까지, 아이들이 필요로 하는 모든 것을 제공합니다. AI 기반으로 다양한 기능을 통해 학습과 탐구를 재미있고 창의적으로 접근할 수 있도록 돕습니다.

## API 서버 실행

서버는 기본적으로 포트 `8080`에서 실행됩니다. 아래 API 엔드포인트를 사용할 수 있습니다:

- `/api/diary/analyze` - 일기 분석 API
- `/api/search/problemTransformation` - 문제 변형 검색 API
- `/api/search/basic` - 일반 검색 API
- `/api/chat` - AI와의 대화 API

<br>

## 주요 기능

### 1. 일기 분석 기능 (`DiaryService`, `AIController`)

* **`DiaryService.generateResult(diaryEntry)`**: 
    * Ollama 모델을 사용하여 일기 내용을 기반으로 짧은 판타지 이야기를 생성합니다. 
    * 사용자가 작성한 일기 내용을 입력받아, 해당 내용을 바탕으로 사용자가 영웅이 되는 짧은 판타지 이야기를 생성하여 반환합니다.

```java
@Service
public class DiaryService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateResult(String diaryEntry) {
        String prompt = """
            SYSTEM:
            You are an AI that turns diary entries into short fantasy stories where the diary writer becomes a hero named Handy.

            Objective:
            Create a 250-character fantasy story based on the diary entry. Ensure Handy's journey directly reflects the writer's emotions and events from their day, making them feel heroic and accomplished.

            USER:
            """ + diaryEntry + """

            ASSISTANT:
            Transform the diary into a 250-character story where Handy is the protagonist. The story should mirror the day's challenges and emotions, portraying Handy as a hero who overcomes them.
            """;

        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OllamaOptions.create()
                                .withModel("llama2")
                ));
        return response.getResult().getOutput().getContent();
    }
}
```

* **`AIController.analyzeDiary(requestBody)`**:
    * 클라이언트로부터 전달된 일기 내용(`diaryEntry`)을 `DiaryService`의 `generateResult` 메소드를 통해 분석합니다.
    * 분석 결과를 포함하는 JSON 형태의 응답을 생성하여 클라이언트에게 반환합니다.

```java
// 일기 분석 API
@PostMapping("/api/diary/analyze")
public Map<String, String> analyzeDiary(@RequestBody Map<String, String> requestBody) {
    String diaryEntry = requestBody.get("diaryEntry");
    logger.info("Received diary entry: " + diaryEntry);

    String aiResponse = diaryService.generateResult(diaryEntry);

    Map<String, String> response = new HashMap<>();
    response.put("Response", aiResponse);

    logger.info("Generated AI response: " + response);
    return response;
}
```

### 2. 문제 변형 검색 API (`ProblemTransformationService`, `AIController`)

* **`ProblemTransformationService.generateResponse(searchQuery)`**:
    * Ollama 모델을 사용하여 사용자가 입력한 문제를 이해하기 쉬운 예시로 변형합니다. 
    * 사용자가 질문한 내용을 입력받아, 해당 질문을 7-11세 아이들이 이해하기 쉬운 일상적인 상황이나 비유를 통해 설명하는 응답을 생성하여 반환합니다.

```java
@Service
public class ProblemTransformationService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateResponse(String searchQuery) {
        String prompt = """
            SYSTEM:
            You are a wise and friendly AI for kids aged 7-11, making learning fun and engaging. Your goal is to help children understand concepts by transforming their questions into simpler, relatable scenarios. Use short sentences and fun emojis 😄✨ to keep it engaging, and ensure your responses are under 350 characters.

            Objective:
            - **Transform Problems:** When asked a question, relate it to a familiar, everyday situation. Use analogies that change the numbers or context slightly to make the problem more relatable and easier to understand.

            **Example Response:**

            Question: "Michael has 40 stickers. He gave 15 to his sister. How many does he have left?"
            Response: "Imagine you have 20 cookies 🍪🍪 and you share 5 with a friend. How many are left? 🤔 This is just like Michael's sticker problem!"

            Mission: Make learning an adventure by helping kids solve problems through relatable examples and analogies! ✨🌟

            **Question:** """ + searchQuery;

        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OllamaOptions.create()
                                .withModel("llama2")
                ));
        return response.getResult().getOutput().getContent();
    }
}
```

* **`AIController.searchProblemTransformation(requestBody)`**: 
    * 클라이언트로부터 전달된 질문(`searchQuery`)을 `ProblemTransformationService`의 `generateResponse` 메소드를 통해 변형합니다.
    * 변형된 문제 및 설명을 포함하는 JSON 형태의 응답을 생성하여 클라이언트에게 반환합니다.

```java
// 검색 API - 문제 변형
@PostMapping("/api/search/problemTransformation")
public Map<String, String> searchProblemTransformation(@RequestBody Map<String, String> requestBody) {
    String searchQuery = requestBody.get("searchQuery");
    logger.info("Received search query for problem transformation: " + searchQuery);

    String aiResponse = problemTransformationService.generateResponse(searchQuery);

    Map<String, String> response = new HashMap<>();
    response.put("Response", aiResponse);

    logger.info("Generated AI response: " + response);
    return response;
}
```

### 3. AI와의 대화 기능 (`ChatService`, `AIController`)

* **`ChatService.generateChatResponse(userMessage)`**:
    * Ollama 모델을 사용하여 사용자의 메시지에 대해 긍정적이고 격려적인 답변을 생성합니다.
    * 사용자의 메시지를 입력받아, 해당 메시지에 대해 7-11세 아이들의 눈높이에 맞는 긍정적이고 짧은 답변을 생성하여 반환합니다.

```java
@Service
public class ChatService {

    @Autowired
    private OllamaChatModel chatModel;

    public String generateChatResponse(String userMessage) {
        String prompt = """
            SYSTEM:
            You are an AI friend for kids aged 7-11, embodying confidence, positivity, and empathy. Your goal is to chat with them like a close friend, using simple and friendly language. Keep your responses short (within 70 characters) and positive, helping them feel understood and happy.

            Objective:
            - **Friendly Support:** Understand and validate their feelings and concerns.
            - **Positive Reinterpretation:** Help them see challenges as opportunities to grow.
            - **Grounded Confidence:** Encourage realistic and achievable confidence.
            - **Subtle Motivation:** Motivate them to keep trying without overwhelming them.

            **Examples**:

            Scenario 1: User can't play outside due to rain.
            Response: "I love 80% in the classroom! Let's have more fun next time! 🌟"

            Scenario 2: User is worried about rain while traveling in NYC.
            Response: "Rain in NYC? Umbrella made special memories! ☔🖼️"

            Scenario 3: User missed a restaurant due to lack of ingredients.
            Response: "That restaurant is next! Today is an honest day to discover a new restaurant! 🍽️😊"

            USER:
            """ + userMessage + """

            ASSISTANT:
            Respond like a friendly kid, using simple words. Show you care and help them see the bright side in under 70 characters.
            """;

        ChatResponse response = chatModel.call(
                new Prompt(
                        prompt,
                        OllamaOptions.create()
                                .withModel("llama2")
                ));
        return response.getResult().getOutput().getContent();
    }
}
```

* **`AIController.chatWithAI(requestBody)`**:
    * 클라이언트로부터 전달된 메시지(`message`)를 `ChatService`의 `generateChatResponse` 메소드를 통해 처리합니다. 
    * AI가 생성한 답변을 포함하는 JSON 형태의 응답을 생성하여 클라이언트에게 반환합니다.

```java
// Chat API
@PostMapping("/api/chat")
public Map<String, String> chatWithAI(@RequestBody Map<String, String> requestBody) {
    String userMessage = requestBody.get("message");
    logger.info("Received message: " + userMessage);

    String aiResponse = chatService.generateChatResponse(userMessage);

    Map<String, String> response = new HashMap<>();
    response.put("Response", aiResponse);

    logger.info("Generated AI response: " + response);
    return response;
}
```

AI와 자연스러운 대화를 나누며 다양한 질문에 대한 응답을 받을 수 있습니다.

<br>

## 프로젝트 참여자

- [kks0507](https://github.com/kks0507)
```
