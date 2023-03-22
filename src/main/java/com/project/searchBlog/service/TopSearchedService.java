package com.project.searchBlog.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.searchBlog.domain.Documents;
import com.project.searchBlog.domain.NaverItem;
import com.project.searchBlog.domain.TopSearched;
import com.project.searchBlog.repository.TopSearchedRepository;

@Service
public class TopSearchedService {

	@Autowired
	TopSearchedRepository topSearchedRepository;
	
	public List<TopSearched> selectAll() {
		return topSearchedRepository.findAll();
	}
	
	public void save(String keyword) {
		TopSearched searched = topSearchedRepository.findByKeyword(keyword);
		
		Date now = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        String strDate = dateFormat.format(now);
        
        
		if (searched != null) {
			int count = searched.getCount();
			searched.setCount(count + 1);
			searched.setLastest_srch_date(strDate);
			
		} else {
			searched = new TopSearched();
			searched.setKeyword(keyword);
			searched.setCount(1);
			searched.setLastest_srch_date(strDate);
		}
		
		topSearchedRepository.save(searched);
	}
	
	public List<TopSearched> getTopSearched() {
		return topSearchedRepository.findTop10ByOrderByCountDesc();
	}

}
