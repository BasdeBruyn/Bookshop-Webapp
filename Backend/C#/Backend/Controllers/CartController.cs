using Backend.Authentication;
using Backend.Models;
using Backend.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http.Extensions;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers {
	// api/cart
	[Route("api/[controller]")]
	[ApiController]
	public class CartController : Controller {
		private readonly CartService _service;

		public CartController() {
			_service = new CartService(this);
		}

		//GET api/cart
		[HttpGet]
		[Authorize]
		[AdminOnly]
		public IActionResult Get() {
			return _service.Get();
		}

		//GET api/cart/{id}
		[HttpGet("{id}")]
		[Authorize]
		public IActionResult Get(int id) {
			return _service.Get(id);
		}
		
		//GET api/cart/from/{id}
		[HttpGet("from/{id}")]
		[Authorize]
		public IActionResult GetCart(int id) {
			return _service.GetCart(id);
		}
		
		//GET api/cart/items/{id}
		[HttpGet("items/{id}")]
		[Authorize]
		public IActionResult GetItemsFromCart(int id) {
			return _service.GetItemsFromCart(id);
		}

		//POST api/cart
		[HttpPost]
		[Authorize]
		public IActionResult Post([FromBody] CartItem cartItem) {
			return _service.Post(cartItem,Request.GetEncodedUrl());
		}

		//PUT api/cart
		[HttpPut]
		[Authorize]
		public IActionResult Put([FromBody] CartItem cartItem) {
			return _service.Put(cartItem);
		}

		//DELETE api/cart/{id}
		[HttpDelete("{id}")]
		[Authorize]
		public IActionResult Delete(int id) {
			return _service.Delete(id);
		}
		
		//DELETE api/cart/from/{id}
		[HttpDelete("from/{id}")]
		[Authorize]
		public IActionResult EmptyCart(int id) {
			return _service.EmptyCart(id);
		}
	}
}