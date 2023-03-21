package com.project.searchBlog.api;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class WebClientService {

	public WebClient getKakaoWebClient() {
		
		return WebClient.builder()
				.filter(errorHandler())
				.baseUrl("https://dapi.kakao.com")
				.defaultHeader("Authorization", "KakaoAK 9a8b02ffe3dd5ef0889bb65eb70dd633")
				.build();
				
	}
	
	public WebClient getNaverWebClient() {
		
		return WebClient.builder()
				.filter(errorHandler())
				.baseUrl("https://openapi.naver.com")
				.defaultHeader("X-Naver-Client-Id", "x6clTFJ4eG0A0VWr0xYJ")
				.defaultHeader("X-Naver-Client-Secret", "6yxlQ1WosO")
				.build();
				
	}
	
	private static ExchangeFilterFunction errorHandler() {
	    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
	    	
	        if (clientResponse.statusCode().is5xxServerError()) {
	        	return clientResponse.bodyToMono(String.class)
	                    .flatMap(errorBody -> Mono.error(new ApiException(clientResponse.rawStatusCode(), errorBody)));
	            
	        } else if (clientResponse.statusCode().is4xxClientError()) {
	        	return clientResponse.bodyToMono(String.class)
	                    .flatMap(errorBody -> Mono.error(new ApiException(clientResponse.rawStatusCode(), errorBody)));
	            
	        } else {
	            return Mono.just(clientResponse);
	        }
	    });
	}
}
