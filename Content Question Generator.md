# 🧠 Content Question Generator

A Spring Boot-based backend service that uses **Google's Gemini Pro API** to generate AI-driven questions from educational content. The application supports RESTful API integration, stores results in **MongoDB**, and saves generated content as **Markdown files** locally for easy access and tracking.

---

## 🚀 Features

- ✨ Generate MCQs or descriptive questions using Google Gemini API
- ⚙️ Built using Spring Boot and WebClient
- 🛢️ MongoDB integration for persistent storage
- 📝 Auto-saves generated questions in `.md` format
- 🌐 REST API endpoint for seamless frontend/backend integration
- 🔐 Environment-based configuration for secure API key usage

---

## 📦 Tech Stack

| Layer         | Technology                     |
|---------------|-------------------------------|
| Backend       | Spring Boot                    |
| API Client    | Spring WebClient               |
| External API  | Gemini Pro (Google GenAI)      |
| Database      | MongoDB (Spring Data)          |
| JSON Handling | Jackson                        |
| File Output   | Markdown (`.md`)               |

---

## 🛠️ Project Structure

content-question-generator/
├── controller/
│ └── QuestionGeneratorController.java
├── dto/
│ └── QuestionRequest.java
├── model/
│ └── QuestionDocument.java
├── repository/
│ └── QuestionRepository.java
├── service/
│ └── QuestionGeneratorService.java
├── resources/
│ └── application.properties
├── generated-notes/
└── README.md


---

## 📬 API Documentation

### Endpoint

POST /api/questions/generate


### Request Headers

Content-Type: application/json


### Request Body

```json
{
  "content": "Generate multiple-choice questions from this content: Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible."
}

Curl Example

curl -X POST http://localhost:8080/api/questions/generate \
-H "Content-Type: application/json" \
-d '{"content":"Explain how AI works in a few words"}'

🔐 Environment Configuration

Set your Gemini API key in application.properties or as an environment variable:

GEMINI_KEY=your_actual_google_gemini_api_key

✅ Setup Instructions
1. Clone the Repo

git clone https://github.com/your-username/content-question-generator.git
cd content-question-generator

2. Add API Key

export GEMINI_KEY=your_actual_api_key

3. Run the App

./mvnw spring-boot:run

🧪 Testing

Basic unit tests available using JUnit and Mockito in the src/test directory.
📝 Sample Output

Markdown files saved inside:

/generated-notes/generated_question_YYYYMMDD_HHmmss_SSS.md

👨‍💻 Author

Kumar Kankam
🚀 Back-End Developer | Java | Spring Boot | RESTful APIs | MongoDB | Linux | Google Gemini API Integration
GitHub | LinkedIn
