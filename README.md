# Basic Full-text Search Engine
This service provides a solution for performing full-text searches in English book texts. It allows users to search for phrases or specific words, returning accurate results.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

## Installation
1. Clone the repository:
```bash
 git clone https://github.com/yourusername/yourproject.git
```

2. Install dependencies:
```bash
  ./gradlew build 
```

3. Run the service:
```bash
  ./gradlew bootRun
```

## Usage
1. Create a new index:
```bash
curl --location 'http://localhost:8080/api/v1/index' \
--header 'Content-Type: application/json' \
--data '{
    "language" : "EN",
    "libraryInfo" : {
        "name" : "test"
    }
}'
```
The service returns the JSON with index ID:
```json
{
  "id": "test"
}
```

2. Add new documents to index:
```bash
curl --location 'http://localhost:8080/api/v1/index/test' \
--header 'Content-Type: application/json' \
--data '{
    "id": "5",
    "name": "The Science of Stress Management",
    "author": "Dr. Robert T. Keller",
    "content": "Stress is a natural response to perceived threats, but chronic stress can lead to physical and mental health issues. Psychology provides tools to manage stress effectively, such as cognitive-behavioral techniques, relaxation exercises, and time management strategies. Identifying stressors is the first step in addressing them. Whether through mindfulness meditation, physical activity, or seeking social support, reducing stress is essential for overall well-being. Understanding the psychology behind stress empowers individuals to develop healthier coping mechanisms and build resilience against life’s inevitable pressures.",
    "page": 5
}'
```
The fields "id" and "page" are not required. 
The service returns new document ID or ID from JSON. 

3. Search for phrases or specific words in a index.

The service can search for
+ words without or with 1-2 mistakes.
```bash
curl --location --request GET 'http://localhost:8080/api/v1/index/test' \
--header 'Content-Type: application/json' \
--data '{
"text" : "strateies"
}'
```
+ phrase without mistakes in words.
```bash
curl --location --request GET 'http://localhost:8080/api/v1/index/test' \
--header 'Content-Type: application/json' \
--data '{
"text" : "Stress is a natural response"
}'
```
+ parts of a phrase. 
```bash
curl --location --request GET 'http://localhost:8080/api/v1/index/test' \
--header 'Content-Type: application/json' \
--data '{
"text" : "Stress is response"
}'
```

The service returns the list of results:
```json
{
  "documents": [
    {
      "id": "5",
      "name": "The Science of Stress Management",
      "author": "Dr. Robert T. Keller",
      "page": 5
    }
  ],
  "size": 1
}
```


## License
This project is licensed under the [MIT License](LICENSE).