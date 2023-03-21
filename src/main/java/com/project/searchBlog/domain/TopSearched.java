package com.project.searchBlog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="tb_top_searched")
public class TopSearched {

	@Id
	@GeneratedValue
	private int id;
	private String keyword;
	private int count;
	private String lastest_srch_date;
	
}
