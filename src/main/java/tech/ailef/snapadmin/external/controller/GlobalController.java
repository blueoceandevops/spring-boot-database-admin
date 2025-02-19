/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */


package tech.ailef.snapadmin.external.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tech.ailef.snapadmin.external.SnapAdmin;
import tech.ailef.snapadmin.external.SnapAdminProperties;
import tech.ailef.snapadmin.external.exceptions.DbAdminException;
import tech.ailef.snapadmin.external.exceptions.DbAdminNotFoundException;
import tech.ailef.snapadmin.internal.UserConfiguration;

/**
 * This class registers some global ModelAttributes and exception handlers.
 * 
 */
@ControllerAdvice
public class GlobalController {

	@Autowired
	private SnapAdminProperties props;

	@Autowired
	private UserConfiguration userConf;
	
	@Autowired
	private SnapAdmin dbAdmin;
	
	@ExceptionHandler(DbAdminException.class)
	public String handleException(Exception e, Model model, HttpServletResponse response) {
		model.addAttribute("status", "");
		model.addAttribute("error", "Error");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("dbadmin_userConf", userConf);
		model.addAttribute("dbadmin_baseUrl", getBaseUrl());
		model.addAttribute("dbadmin_version", dbAdmin.getVersion());
		model.addAttribute("dbadmin_properties", props);
		return "other/error";
	}
	
	@ExceptionHandler(DbAdminNotFoundException.class)
	public String handleNotFound(Exception e, Model model, HttpServletResponse response) {
		model.addAttribute("status", "404");
		model.addAttribute("error", "Error");
		model.addAttribute("message", e.getMessage());
		model.addAttribute("dbadmin_userConf", userConf);
		model.addAttribute("dbadmin_baseUrl", getBaseUrl());
		model.addAttribute("dbadmin_version", dbAdmin.getVersion());
		model.addAttribute("dbadmin_properties", props);
		response.setStatus(404);
		return "other/error";
	}
	
	@ModelAttribute("dbadmin_version")
	public String getVersion() {
		return dbAdmin.getVersion();
	}
	
	/**
	 * A multi valued map containing the query parameters. It is used primarily
	 * in building complex URL when performing faceted search with multiple filters.
	 * @param request the incoming request
	 * @return multi valued map of request parameters
	 */
	@ModelAttribute("dbadmin_queryParams")
	public Map<String, String[]> getQueryParams(HttpServletRequest request) {
		return request.getParameterMap();
	}
	
	/**
	 * The baseUrl as specified in the properties file by the user
	 * @return
	 */
	@ModelAttribute("dbadmin_baseUrl")
	public String getBaseUrl() {
		return props.getBaseUrl();
	}
	
	/**
	 * The full request URL, not including the query string
	 * @param request
	 * @return
	 */
	@ModelAttribute("dbadmin_requestUrl")
	public String getRequestUrl(HttpServletRequest request) {
		return request.getRequestURI();
	}
	
	/**
	 * The UserConfiguration object used to retrieve values specified
	 * in the settings table.
	 * @return
	 */
	@ModelAttribute("dbadmin_userConf")
	public UserConfiguration getUserConf() {
		return userConf;
	}
	
	@ModelAttribute("dbadmin_properties")
	public SnapAdminProperties getProps() {
		return props;
	}
	
}

