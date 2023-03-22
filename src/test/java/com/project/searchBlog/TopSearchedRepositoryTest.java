package com.project.searchBlog;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.project.searchBlog.domain.TopSearched;
import com.project.searchBlog.repository.TopSearchedRepository;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TopSearchedRepositoryTest {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	TopSearchedRepository topSearchedRepository;

	@Test
	public void RepositorySaveTest() {
		//given
		String keyword = "db 테스트";
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		String strDate = format.format(date);

		int count = 1;
		
		TopSearched searched = new TopSearched();
		searched.setKeyword(keyword);
		searched.setLastest_srch_date(strDate);
		searched.setCount(count);
		
		topSearchedRepository.save(searched);
	
		
		//when
		List<TopSearched> list = topSearchedRepository.findAll();
	
		//then
		TopSearched top1 = list.get(0);
		assertEquals(top1.getKeyword(), keyword);
		assertEquals(top1.getCount(), count);
	}
}
