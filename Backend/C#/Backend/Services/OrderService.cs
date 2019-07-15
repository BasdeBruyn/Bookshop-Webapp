using System;
using System.Collections;
using System.Linq;
using System.Security.Claims;
using AutoMapper;
using Backend.Authentication;
using Backend.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace Backend.Services {
	public class OrderService {
		private readonly WebshopContext _context;
		private readonly Controller _controller;
		private ClaimsPrincipal AuthUser => _controller.User;

		public OrderService(Controller controller) {
			_controller = controller;
			_context = new WebshopContext();
		}

		public IActionResult Get() {
			ICollection dbOrders = _context.Orders.ToList();
			return new OkObjectResult(dbOrders);
		}

		public IActionResult Get(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();
			
			Order dbOrder = _context.Orders.SingleOrDefault(o => o.Id == id);
			if (dbOrder == null) 
				return new NotFoundResult();
			
			return new OkObjectResult(dbOrder);
		}
		
		public IActionResult GetLatest(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();

			Order dbOrder = _context.Orders
				.Where(o => o.UserId == id)
				.OrderByDescending(o => o.CurDate)
				.Include(o => o.User)
				.FirstOrDefault();
			
			if (dbOrder == null) 
				return new NotFoundResult();
			
			return new OkObjectResult(dbOrder);
		}

		public IActionResult Post(Order order, string url) {
			if (!AuthService.ActionAuthorized(AuthUser, order.UserId))
				return new ForbidResult();

			order.Id = 0;
			order.CurDate = DateTime.Now;
			_context.Orders.Add(order);
			_context.SaveChanges();
			return new CreatedResult($"{url}/{order.Id}", order);
		}

		public IActionResult Put(Order order) {
			if (!AuthService.ActionAuthorized(AuthUser, order.UserId))
				return new ForbidResult();
			
			Order dbOrder = _context.Orders.SingleOrDefault(o => o.Id == order.Id);
			if (dbOrder == null) 
				return new NotFoundResult();
			
			Mapper.Map(order, dbOrder);
			_context.SaveChanges();
			return new NoContentResult();
		}

		public IActionResult Delete(int id) {
			if (!AuthService.ActionAuthorized(AuthUser, id))
				return new ForbidResult();
			
			Order dbOrder = _context.Orders.SingleOrDefault(o => o.Id == id);
			if (dbOrder == null) 
				return new NotFoundResult();
			
			_context.Remove(dbOrder);
			return new NoContentResult();
		}
	}
}