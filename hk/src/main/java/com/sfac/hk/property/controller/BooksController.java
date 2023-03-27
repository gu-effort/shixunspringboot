package com.sfac.hk.property.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.hk.common.vo.Result;
import com.sfac.hk.common.vo.Search;
import com.sfac.hk.property.entity.Books;
import com.sfac.hk.property.service.BooksService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

/**
 * @Description BooksController
 * @Author JiangHu
 * @Date 2023/2/14 16:06
 */
@RestController
@RequestMapping("/api/economy")
public class BooksController {

	@Autowired
	private BooksService booksService;

	/**
	 * 127.0.0.1/api/economy/books ---- post
	 * {"userId":1,"name":"时间","booksType":"家庭账簿","defaultBooks":true,
	 * "members":[{"id":1}, {"id":2}]}
	 */
	@PostMapping(value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<Books> insertBooks(@RequestBody Books books) {
		return booksService.insertBooks(books);
	}

	/**
	 * 127.0.0.1/api/economy/books ---- put
	 * {"id":2, "userId":1,"name":"时间1","booksType":"家庭账簿","defaultBooks":true,
	 * 	"members":[{"id":3}, {"id":4}]}
	 */
	@PutMapping(value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Result<Books> updateBooks(@RequestBody Books books) {
		return booksService.updateBooks(books);
	}



	/**
	 * 127.0.0.1/api/economy/books/3 ---- delete
	 */
	@DeleteMapping(value = "/books/{id}")
	public Result<Object> deleteBooksById(@PathVariable int id) {
		return booksService.deleteBooksById(id);
	}

	/**
	 * 127.0.0.1/api/economy/books/2 ---- get
	 */
	@GetMapping(value = "/books/{id}")
	public Books getBooksById(@PathVariable int id) {
		return booksService.getBooksById(id);
	}

	/**
	 * 127.0.0.1/api/economy/booksList ---- post
	 * {"currentPage":1, "pageSize":5, "sort":"id", "direction":"desc", "keyword":""}
	 */
	@PostMapping(value="booksList", consumes = MediaType.APPLICATION_JSON_VALUE)
	public PageInfo<Books> getBooksListBySearch(@RequestBody Search search) {
		return booksService.getBooksListBySearch(search);
	}
}
