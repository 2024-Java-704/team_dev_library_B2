package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Account;

@Aspect
@Component
public class CheckLoginAspect {

	@Autowired
	Account account;
	
	//???
	
	// 未ログインの場合メインページにリダイレクト
	@Around(
			"execution(* com.example.demo.controller.UserController.history(..)) ||"
			+ "execution(* com.example.demo.controller.UserController.myPage(..)) ||"
			+ "execution(* com.example.demo.controller.LibraryController.reserve(..)) ||"
			+ "execution(* com.example.demo.controller.LibraryController.lost(..))")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {

		if (account == null || account.getName() == null
				|| account.getName().length() == 0) {
			
			return "redirect:/login?err=login";
		}
		// Controller内のメソッドの実行
		return jp.proceed();
	}
	@Around(
			"execution(* com.example.demo.controller.admin.*.*(..))")
	public Object checkLoginAdmin(ProceedingJoinPoint jp) throws Throwable {
		
		if (account == null || account.getName() == null
				|| account.getName().length() == 0||account.getAuthority() != 9) {
			
			return "redirect:/admin/login?err=login";
		}
		// Controller内のメソッドの実行
		return jp.proceed();
	}
}



