package com.monstersoftwarellc.virtualvote.account.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.monstersoftwarellc.virtualvote.account.Account;
import com.monstersoftwarellc.virtualvote.account.service.IAccountService;


/**
 * Handles requests for the account related stuff.
 */
@Controller
@RequestMapping(value="/account/*")
public class AccountController {
	
	@Autowired
	private IAccountService accountService;
	
	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value="create")
	public String home(Model model) {
		Account account = new Account();
		model.addAttribute("account", account );		
		return "/account/create";
	}
	
	/**
	 * NOTE: when performing {@Valid} you need to ensure the BindingResult is next in the parameter list.
	 * ALSO: @Valid can take the place of @ModelAttribute as well, so no need to specify that 
	 * @param person
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	public String createAccount(@Valid Account account, BindingResult result, Model model){
		// if errors stay on createUser page. 
		if(result.hasErrors()) {
			 return "/account/create";
		}
		// TODO: if we change the username we need to either update the spring security or force a logout
		accountService.save(account);
		// easy forwarding! 
		return "/index";
	}
	
	/**
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String listUsers(Model model) {
		Page<Account> accounts = accountService.getAccountRepository().findAll(new PageRequest(0, 10));
		model.addAttribute("accounts", accounts.getContent());
		return "/account/list"; 
	}
	
	/**
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping("view")
	public String viewUser(Model model, Principal principal) {
		Account account = accountService.getAccountRepository().findByLoginUsername(principal.getName());
		model.addAttribute("account", account);
		return "/account/create"; 
	}
	
}
