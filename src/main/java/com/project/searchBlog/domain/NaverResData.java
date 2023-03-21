package com.project.searchBlog.domain;

import lombok.Data;

@Data
public class NaverResData {

	private String lastBuildDate;
	private int total;
	private int start;
	private int display;
	private NaverItem[] items;
	
	
}
