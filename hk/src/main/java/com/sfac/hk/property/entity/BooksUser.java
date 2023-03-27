package com.sfac.hk.property.entity;

import javax.persistence.*;

/**
 * @Description BooksUser
 * @Author JiangHu
 * @Date 2023/2/14 15:36
 */
@Entity
@Table(name = "economy_books_user")
public class BooksUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int userId;
	private int booksId;

	public BooksUser() {
	}

	public BooksUser(int userId, int booksId) {
		this.userId = userId;
		this.booksId = booksId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBooksId() {
		return booksId;
	}

	public void setBooksId(int booksId) {
		this.booksId = booksId;
	}
}
