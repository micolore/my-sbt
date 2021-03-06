package com.kubrick.sbt.web.api;

import com.kubrick.sbt.web.domain.entity.Menu;
import com.kubrick.sbt.web.mapper.MenuMapper;
import com.kubrick.sbt.web.service.RetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author k
 * @version 1.0.0 @ClassName CommonApi
 * @description: TODO
 * @date 2021/1/14 上午2:18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common")
public class CommonApi {

	private final RetryService retryService;

	private final MenuMapper menuMapper;

	/**
	 * @return */
	@RequestMapping(value = "/ms", method = RequestMethod.GET)
	public String ms() throws Exception {
		// retryService.retryHttp();
		// retryService.guavaRetry();

		Menu menu = menuMapper.selectById(1);
		System.out.println("menu" + menu.toString());

		return "ok";
	}

}
