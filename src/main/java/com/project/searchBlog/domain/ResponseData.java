package com.project.searchBlog.domain;

import lombok.Data;

@Data
public class ResponseData {

	private Meta meta;
	private Documents[] documents;
	
}
