package com.mybatis.test;

import java.util.List;

public interface IArticleOperation {

	public List<Article> getUserArticles(int userID);
}
