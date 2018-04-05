package br.com.hotmart.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import br.com.hotmart.response.User;

@Api(value = "login") 
@RestController
@RequestMapping(value = {"/login"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends AbstractBaseUser{
	
	@ApiOperation(value = "Obter o nome do usuário logado" )
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/getLoginUser")
	public @ResponseBody ResponseEntity<User> getLoginUser(){
		String name = super.getUsername();
		if (name != null) {
			return ResponseEntity.ok().body(new User(name));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}
