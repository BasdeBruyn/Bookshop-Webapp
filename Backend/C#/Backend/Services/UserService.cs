using System;
using System.Collections;
using System.Linq;
using System.Security.Claims;
using AutoMapper;
using Backend.Authentication;
using Backend.Models;
using Microsoft.AspNetCore.Mvc;
using AuthenticationException = Backend.Authentication.AuthenticationException;

namespace Backend.Services {
	public class UserService : IUserService {
		private readonly WebshopContext _context;
		private readonly Controller _controller;
		private ClaimsPrincipal AuthUser => _controller.User;

		public UserService() {
			_controller = null;
			_context = new WebshopContext();
		}

		public UserService(Controller controller) {
			_controller = controller;
			_context = new WebshopContext();
		}

		public IActionResult Get() {
			ICollection users = _context.Users
					.Select(user => new DtoUser(user.Id, user.Email, user.IsAdmin))
					.ToList();
			return new OkObjectResult(users);
		}

		public IActionResult Get(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();
			
			User dbUser = _context.Users.SingleOrDefault(u => u.Id == id);
			if (dbUser == null) 
				return new NotFoundResult();

			DtoUser dtoUser = new DtoUser(dbUser.Id, dbUser.Email, dbUser.IsAdmin);
			
			return new OkObjectResult(dtoUser);
		}

		public IActionResult GetByEmail(string email) {
			User dbUser = _context.Users.SingleOrDefault(u => u.Email == email);
			
			if (dbUser != null && !AuthService.ActionAuthorized(AuthUser, dbUser.Id))
				return new ForbidResult();
			
			if (dbUser == null) 
				return new NotFoundResult();
			
			DtoUser dtoUser = new DtoUser(dbUser.Id, dbUser.Email, dbUser.IsAdmin);
			
			return new OkObjectResult(dtoUser);
		}
		
		public IActionResult Post(User user, string url) {
			string hashedPassword = AuthService.HashPassword(user.Password);
			user.Password = hashedPassword;

			user.Id = 0;
			if (!AuthService.IsAdmin(AuthUser))
				user.IsAdmin = false;
			
			_context.Users.Add(user);
			_context.SaveChanges();
			
			DtoUser dtoUser = new DtoUser(user.Id, user.Email, user.IsAdmin);
			
			return new CreatedResult($"{url}/{dtoUser.Id}", dtoUser);
		}

		public IActionResult Put(User user) {
			if (!AuthService.ActionAuthorized(AuthUser, user.Id))
				return new ForbidResult();
			
			User dbUser = _context.Users.SingleOrDefault(u => u.Id == user.Id);
			if (dbUser == null) 
				return new NotFoundResult();

			if (!AuthService.IsAdmin(AuthUser))
				user.IsAdmin = false;
			
			Mapper.Map(user, dbUser);
			
			string hashedPassword = AuthService.HashPassword(user.Password);
			dbUser.Password = hashedPassword;
			
			_context.SaveChanges();
			return new NoContentResult();
		}

		public IActionResult Delete(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();
			
			User dbUser = _context.Users.SingleOrDefault(u => u.Id == id);
			if (dbUser == null) 
				return new NotFoundResult();
			
			_context.Remove(dbUser);
			return new NoContentResult();
		}

		public User Authenticate(string email, string password) {
			try {
				User user = _context.Users
					.SingleOrDefault(u => u.Email == email);
			
				if (user == null || !AuthService.ValidatePassword(password, user.Password))
					return null;

				return user;
			}
			catch (Exception e) {
				throw new AuthenticationException(e);
			}
		}
	}
	
	public interface IUserService {
		User Authenticate(string email, string password);
	}
}