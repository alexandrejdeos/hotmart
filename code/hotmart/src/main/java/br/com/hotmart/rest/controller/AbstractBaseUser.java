package br.com.hotmart.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractBaseUser {
	protected Log log = LogFactory.getLog(getClass().getSimpleName());
	
	
	/**
	 * Retorna o usuario logado no sistema.
	 * 
	 * @return {@link String}
	 */
	public String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return auth.getName();
		}
		return null;
	}

	/**
	 * 
	 * @description retorna o objeto de autenticação do spring, con isso conseguimos
	 *              obter as informações necessários do ususário.
	 * @return {@link Authentication}
	 */
	public Authentication getAuthentication() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			return auth;
		}
		return null;
	}
}
