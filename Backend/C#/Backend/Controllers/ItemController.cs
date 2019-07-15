using Backend.Authentication;
using Backend.Models;
using Backend.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http.Extensions;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers {
	// api/item
	[Route("api/[controller]")]
	[ApiController]
	public class ItemController : Controller {
		private readonly ItemService _service;

		public ItemController() {
			_service = new ItemService();
		}

		//GET api/item
		[HttpGet]
		public IActionResult Get() {
			return _service.Get();
		}

		//GET api/item/{id}
		[HttpGet("{id}")]
		public IActionResult Get(int id) {
			return _service.Get(id);
		}

		//POST api/item
		[HttpPost]
		[Authorize]
		[AdminOnly]
		public IActionResult Post([FromBody] Item item) {
			return _service.Post(item, Request.GetEncodedUrl());
		}

		//PUT api/item
		[HttpPut]
		[Authorize]
		[AdminOnly]
		public IActionResult Put([FromBody] Item item) {
			return _service.Put(item);
		}

		//DELETE api/item/{id}
		[HttpDelete("{id}")]
		[Authorize]
		[AdminOnly]
		public IActionResult Delete(int id) {
			return _service.Delete(id);
		}
	}
}