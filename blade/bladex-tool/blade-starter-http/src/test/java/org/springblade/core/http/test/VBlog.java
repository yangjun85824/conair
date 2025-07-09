package org.springblade.core.http.test;

import lombok.Getter;
import lombok.Setter;
import org.springblade.core.http.CssQuery;

/**
 * 热门博客
 */
@Getter
@Setter
public class VBlog {

	@CssQuery(value = "a", attr = "title")
	private String title;

	@CssQuery(value = "a", attr = "href")
	private String href;

	//1341阅/9评/4赞
	@CssQuery(value = "span", attr = "text", regex = "^\\d+")
	private Integer read;

	@CssQuery(value = "span", attr = "text", regex = "(\\d*).*/(\\d*).*/(\\d*).*", regexGroup = 2)
	private Integer ping;

	@CssQuery(value = "span", attr = "text", regex = "(\\d*).*/(\\d*).*/(\\d*).*", regexGroup = 3)
	private Integer zhan;

}
