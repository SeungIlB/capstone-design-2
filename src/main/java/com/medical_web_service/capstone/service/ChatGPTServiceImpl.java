package com.medical_web_service.capstone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.medical_web_service.capstone.config.ChatGPTConfig;
import com.medical_web_service.capstone.dto.ChatCompletionDto;
import com.medical_web_service.capstone.dto.ChatRequestMsgDto;
import com.medical_web_service.capstone.dto.CompletionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ChatGPT Service 구현체
 *
 * @author : lee
 * @fileName : ChatGPTServiceImpl
 * @since : 12/29/23
 */
@Slf4j
@Service
public class ChatGPTServiceImpl implements ChatGPTService {

    private final ChatGPTConfig chatGPTConfig;

    public ChatGPTServiceImpl(ChatGPTConfig chatGPTConfig) {
        this.chatGPTConfig = chatGPTConfig;
    }

    @Value("${openai.url.model}")
    private String modelUrl;

    @Value("${openai.url.model-list}")
    private String modelListUrl;

    @Value("${openai.url.prompt}")
    private String promptUrl;

    @Value("${openai.url.legacy-prompt}")
    private String legacyPromptUrl;

    /**
     * 사용 가능한 모델 리스트를 조회하는 비즈니스 로직
     *
     * @return List<Map < String, Object>>
     */
    @Override
    public List<Map<String, Object>> modelList() {
        log.debug("[+] 모델 리스트를 조회합니다.");
        List<Map<String, Object>> resultList = null;

        // [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // [STEP2] 통신을 위한 RestTemplate을 구성합니다.
        ResponseEntity<String> response = chatGPTConfig
                .restTemplate()
                .exchange(modelUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        try {
            // [STEP3] Jackson을 기반으로 응답값을 가져옵니다.
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> data = om.readValue(response.getBody(), new TypeReference<>() {
            });

            // [STEP4] 응답 값을 결과값에 넣고 출력을 해봅니다.
            resultList = (List<Map<String, Object>>) data.get("data");
            for (Map<String, Object> object : resultList) {
                log.debug("ID: " + object.get("id"));
                log.debug("Object: " + object.get("object"));
                log.debug("Created: " + object.get("created"));
                log.debug("Owned By: " + object.get("owned_by"));
            }
        } catch (JsonMappingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.debug("JsonProcessingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultList;
    }

    /**
     * 모델이 유효한지 확인하는 비즈니스 로직
     *
     * @param modelName {}
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> isValidModel(String modelName) {
        log.debug("[+] 모델이 유효한지 조회합니다. 모델 : " + modelName);
        Map<String, Object> result = new HashMap<>();

        // [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // [STEP2] 통신을 위한 RestTemplate을 구성합니다.
        ResponseEntity<String> response = chatGPTConfig
                .restTemplate()
                .exchange(modelListUrl + "/" + modelName, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        try {
            // [STEP3] Jackson을 기반으로 응답값을 가져옵니다.
            ObjectMapper om = new ObjectMapper();
            result = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return result;
    }

    /**
     * ChatGTP 프롬프트 검색
     *
     * @param completionDto completionDto
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> legacyPrompt(CompletionDto completionDto) {
        log.debug("[+] 레거시 프롬프트를 수행합니다.");

        // [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // [STEP5] 통신을 위한 RestTemplate을 구성합니다.
        HttpEntity<CompletionDto> requestEntity = new HttpEntity<>(completionDto, headers);
        ResponseEntity<String> response = chatGPTConfig
                .restTemplate()
                .exchange(legacyPromptUrl, HttpMethod.POST, requestEntity, String.class);

        Map<String, Object> resultMap = new HashMap<>();
        try {
            ObjectMapper om = new ObjectMapper();
            // [STEP6] String -> HashMap 역직렬화를 구성합니다.
            resultMap = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultMap;
    }

    /**
     * 신규 모델에 대한 프롬프트
     *
     * @param chatCompletionDto {}
     * @return chatCompletionDto
     */
    @Override
    public Map<String, Object> prompt(ChatCompletionDto chatCompletionDto) {
        log.debug("[+] 신규 프롬프트를 수행합니다.");

        Map<String, Object> resultMap = new HashMap<>();

        // [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // [STEP5] 통신을 위한 RestTemplate을 구성합니다.
        HttpEntity<ChatCompletionDto> requestEntity = new HttpEntity<>(chatCompletionDto, headers);
        ResponseEntity<String> response = chatGPTConfig
                .restTemplate()
                .exchange(promptUrl, HttpMethod.POST, requestEntity, String.class);
        try {
            // [STEP6] String -> HashMap 역직렬화를 구성합니다.
            ObjectMapper om = new ObjectMapper();
            resultMap = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultMap;
    }
    @Override
    public Map<String, Object> recommendDiseases(String symptomDescription) {
        log.debug("[+] 증상에 맞는 질병을 추천합니다.");

        // ChatGPT에 전달할 메시지 생성
        ChatCompletionDto chatCompletionDto = new ChatCompletionDto();
        chatCompletionDto.setModel("gpt-3.5-turbo");  // 사용하려는 모델 선택

        // 시스템 메시지: GPT에게 역할을 설명
        ChatRequestMsgDto systemMessage = new ChatRequestMsgDto();
        systemMessage.setRole("system");
        systemMessage.setContent("당신은 경험이 풍부한 의사입니다. 사용자의 증상에 대해 정확하고 신뢰할 수 있는 정보를 제공해야 합니다. 가능한 질병과 함께 예방 방법, 치료 방법도 제공해야 합니다. 증상이 심각할 경우 병원에 가야 한다고 유도해 주세요.");

        // 사용자 메시지: 실제 증상 입력
        ChatRequestMsgDto userMessage = new ChatRequestMsgDto();
        userMessage.setRole("user");
        userMessage.setContent("사용자가 배가 아프다고 합니다. 증상에 기초하여 가능한 질병을 제시하고, 예방 방법과 치료 방법도 제공해 주세요. 증상: " + symptomDescription);

        // 메시지 리스트 생성
        List<ChatRequestMsgDto> messages = List.of(systemMessage, userMessage);

        // 모델과 메시지 설정
        chatCompletionDto.setMessages(messages);

        // ChatGPT API 호출
        Map<String, Object> response = prompt(chatCompletionDto);

        // 응답 처리 (예: 병원 방문 유도 메시지 추가)
        Map<String, Object> result = processResponse(response);

        return result;
    }

    /**
     * 응답을 처리하여, 병원 방문을 유도하는 메시지를 추가합니다.
     */
    private Map<String, Object> processResponse(Map<String, Object> response) {
        Map<String, Object> result = new HashMap<>();

        // 예시 응답 처리
        if (response != null) {
            // 질병, 예방, 치료, 병원 유도 정보를 처리
            String diseaseInfo = (String) response.get("disease_info"); // 가정된 필드
            String prevention = (String) response.get("prevention"); // 가정된 필드
            String treatment = (String) response.get("treatment"); // 가정된 필드

            // 병원 방문 유도
            String hospitalAdvice = "증상이 심각하거나 지속되면 병원에 방문해 주세요. 전문가의 진단을 받는 것이 중요합니다.";

            // 결과 저장
            result.put("disease_info", diseaseInfo);
            result.put("prevention", prevention);
            result.put("treatment", treatment);
            result.put("hospital_advice", hospitalAdvice);
        }

        return result;
    }

    @Override
    public Map<String, Object> legacyDiseasePrompt(String symptomDescription) {
        log.debug("[+] legacy 프롬프트를 사용하여 증상에 맞는 질병을 추천합니다.");

        // CompletionDto 객체 생성
        CompletionDto completionDto = new CompletionDto();
        completionDto.setPrompt("사용자가 배가 아프다고 합니다. 증상에 기초하여 가능한 질병을 제시해 주세요. 증상: " + symptomDescription);
        completionDto.setMax_tokens(150);  // 응답 길이 제한

        // legacyPrompt 호출
        Map<String, Object> response = legacyPrompt(completionDto);

        return response;
    }
}
