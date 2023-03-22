package com.project.searchBlog.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.project.searchBlog.api.ApiException;
import com.project.searchBlog.api.WebClientService;
import com.project.searchBlog.domain.Documents;
import com.project.searchBlog.domain.NaverItem;
import com.project.searchBlog.domain.NaverResData;
import com.project.searchBlog.domain.ResponseData;
import com.project.searchBlog.domain.TopSearched;
import com.project.searchBlog.service.TopSearchedService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class SearchController {

	
	@Autowired
	WebClientService webClientService;
	
	@Autowired
	TopSearchedService topSearchedService;
	
	@GetMapping("/")
	public String search() {
		return "search";
	}
	
	@ResponseBody
	@PostMapping("/searchKeyword")
	public Map<String, Object> searchKeyword(
			@RequestBody Map<String, Object> param
			) throws WebClientResponseException {
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			
			Mono<ResponseData> result = webClientService.getKakaoWebClient()
					.get()
					.uri(builder -> builder.path("/v2/search/blog")
	                        .queryParam("query", param.get("keyword"))
	                        .queryParam("sort", param.get("sort"))
	                        .queryParam("size", param.get("size"))
	                        .queryParam("page", param.get("page"))
	                        .build()
	                )
					.exchangeToMono(response ->
					response.bodyToMono(ResponseData.class)
					.map(validReq -> {
						if (response.statusCode().is2xxSuccessful()) {
							System.out.println("API 요청에 성공했습니다.");
							return validReq;
						}
						
						throw new WebClientResponseException(
                        		response.rawStatusCode(), 
                        		String.format("5xx 외부 시스템 오류. %s", response.bodyToMono(String.class)), 
                        		response.headers().asHttpHeaders(), null, null);
						})
					);
					
			
			ResponseData res = result.share().block();
			
			topSearchedService.save(param.get("keyword").toString());
			
			mp.put("ret", 1);
			mp.put("api", "kakao");
			mp.put("meta", res.getMeta());
			mp.put("documents", res.getDocuments());
		
		} catch(ApiException apiException){
			String sort = param.get("sort").toString();
			if (sort.equals("accuracy")) {
				sort = "sim";
			} else {
				sort = "date";
			}
			
			mp = NaverApi(param, sort);
		}
		
		return mp;
	}
	
	@ResponseBody
	@GetMapping("/getTopSearched")
	public Map<String, Object> getTopSearched() {
		Map<String, Object> mp = new HashMap<String, Object>();
		
		List<TopSearched> list = topSearchedService.getTopSearched();
		
		mp.put("list", list);
		
		return mp;
	}
	
	
	private Map<String, Object> NaverApi(Map<String, Object> param, String sort) {
		Map<String, Object> mp = new HashMap<String, Object>();
		
		try {
			
			Mono<NaverResData> result = webClientService.getNaverWebClient()
					.get()
					.uri(builder -> builder.path("/v1/search/blog.json")
	                        .queryParam("query", param.get("keyword"))
	                        .queryParam("sort", sort)
	                        .queryParam("display", param.get("size"))
	                        .queryParam("start", param.get("page"))
	                        .build()
	                )
					.exchangeToMono(response ->
					response.bodyToMono(NaverResData.class)
					.map(validReq -> {
						if (response.statusCode().is2xxSuccessful()) {
							System.out.println("API 요청에 성공했습니다.");
							return validReq;
						}
						
						throw new WebClientResponseException(
                        		response.rawStatusCode(), 
                        		String.format("5xx 외부 시스템 오류. %s", response.bodyToMono(String.class)), 
                        		response.headers().asHttpHeaders(), null, null);
						})
					);
					
			
			NaverResData res = result.share().block();
			
			topSearchedService.save(param.get("keyword").toString());
			List<Documents> blogList = parseNaverApiJson(res.getItems());
			
			mp.put("ret", 1);
			mp.put("api", "naver");
			mp.put("documents", blogList);
		
		} catch(ApiException apiException){
			mp.put("ret", -1);
			mp.put("msg", apiException.getMessage());
		}
		
		return mp;
	}
	
	private List<Documents> parseNaverApiJson(NaverItem[] items) {
		
		List<Documents> list = new ArrayList<Documents>();
		
		
		for (int i = 0; i < items.length; i++) {
			NaverItem item = items[i];
			
			Documents doc = new Documents();
			doc.setBlogname(item.getBloggername());
			doc.setContents(item.getDescription());
			doc.setTitle(item.getTitle());
			doc.setUrl(item.getLink());
			doc.setDatetime(item.getPostdate());
			
			list.add(doc);
		}
		
		return list;
	}

}
