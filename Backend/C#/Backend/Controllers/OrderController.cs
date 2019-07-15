using Backend.Authentication;
using Backend.Models;
using Backend.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http.Extensions;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers {
	// api/order
	[Route("api/[controller]")]
	[ApiController]
	public class OrderController : Controller {
		private readonly OrderService _service;

		public OrderController() {
			_service = new OrderService(this);
		}

		//GET api/orders
		[HttpGet]
		[Authorize]
		[AdminOnly]
		public IActionResult Get() {
			return _service.Get();
		}

		//GET api/orders/{id}
		[HttpGet("{id}")]
		[Authorize]
		public IActionResult Get(int id) {
			return _service.Get(id);
		}
		
		//GET api/orders/latest/{id}
		[HttpGet("latest/{id}")]
		[Authorize]
		public IActionResult GetLatest(int id) {
			return _service.GetLatest(id);
		}

		//POST api/orders
		[HttpPost]
		[Authorize]
		public IActionResult Post([FromBody] Order order) {
			return _service.Post(order, Request.GetEncodedUrl());
		}

		//PUT api/orders
		[HttpPut]
		[Authorize]
		public IActionResult Put([FromBody] Order order) {
			return _service.Put(order);
		}

		//DELETE api/orders/{id}
		[HttpDelete("{id}")]
		[Authorize]
		public IActionResult Delete(int id) {
			return _service.Delete(id);
		}
	}
}