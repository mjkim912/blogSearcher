package com.project.searchBlog.domain;

import lombok.Data;

@Data
public class Meta {
	private int total_count;
	private int pageable_count;
	private boolean is_end;
}
