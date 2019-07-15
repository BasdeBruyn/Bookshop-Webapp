using System.Collections;
using System.Linq;
using System.Security.Claims;
using AutoMapper;
using Backend.Authentication;
using Backend.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace Backend.Services {
	public class CartService {
		private readonly WebshopContext _context;
		private readonly Controller _controller;
		private ClaimsPrincipal AuthUser => _controller.User;

		public CartService(Controller controller) {
			_controller = controller;
			_context = new WebshopContext();
		}

		public IActionResult Get() {
			ICollection dbCartItems = _context.CartItems.ToList();
			
			return new OkObjectResult(dbCartItems);
		}

		public IActionResult Get(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();
			
			CartItem dbCartItem = _context.CartItems.SingleOrDefault(u => u.Id == id);
			if (dbCartItem == null) 
				return new NotFoundResult();
			
			return new OkObjectResult(dbCartItem);
		}

		public IActionResult GetCart(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();
			
			ICollection cartItems = _context.CartItems
				.Where(ci => ci.UserId == id)
				.ToList();

			return new OkObjectResult(cartItems);
		}

		public IActionResult GetItemsFromCart(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();
			
			ICollection items = _context.CartItems
				.Where(ci => ci.UserId == id)
				.Select(ci => ci.Item)
				.ToList();
			
			return new OkObjectResult(items);
		}

		public IActionResult Post(CartItem cartItem, string url) {
			if (!AuthService.ActionAuthorized(AuthUser, cartItem.UserId))
				return new ForbidResult();

			cartItem.Id = 0;
			_context.CartItems.Add(cartItem);
			_context.SaveChanges();
			
			return new CreatedResult($"{url}/{cartItem.Id}", cartItem);
		}

		public IActionResult Put(CartItem cartItem) {
			if (!AuthService.ActionAuthorized(AuthUser, cartItem.UserId))
				return new ForbidResult();
			
			CartItem dbCartItem = _context.CartItems.SingleOrDefault(u => u.Id == cartItem.Id);
			if (dbCartItem == null) 
				return new NotFoundResult();
			
			Mapper.Map(cartItem, dbCartItem);
			_context.SaveChanges();
			
			return new NoContentResult();
		}

		public IActionResult Delete(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();
			
			CartItem dbCartItem = _context.CartItems.SingleOrDefault(u => u.Id == id);
			if (dbCartItem == null) 
				return new NotFoundResult();
			
			_context.Remove(dbCartItem);
			
			return new NoContentResult();
		}

		public IActionResult EmptyCart(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();
			
			DbSet<CartItem> dbCartItems = _context.CartItems; 
			dbCartItems.RemoveRange(dbCartItems.Where(ci => ci.UserId == id));

			_context.SaveChanges();
			
			return new NoContentResult();
		}
	}
}