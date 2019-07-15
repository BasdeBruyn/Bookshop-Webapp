using System.Linq;
using Backend.Authentication;
using Backend.Models;
using Backend.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http.Extensions;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Controllers {
	// api/user
	[Route("api/[controller]")]
	[ApiController]
	public class UserController : Controller {
		private readonly UserService _service;

		public UserController() {
			_service = new UserService(this);
		}

		//GET api/users
		[HttpGet]
		[Authorize]
		[AdminOnly]
		public IActionResult Get() {
			return _service.Get();
		}

		//GET api/user/{id}
		[HttpGet("{id}")]
		[Authorize]
		public IActionResult Get(int id) {
			return _service.Get(id);
		}

		//GET api/user/email/{email}
		[HttpGet("email/{email}")]
		[Authorize]
		[AdminOnly]
		public IActionResult GetByEmail(string email) {
			return _service.GetByEmail(email);
		}

		//POST api/user
		[HttpPost]
		public IActionResult Post([FromBody] User user) {
			return _service.Post(user, Request.GetEncodedUrl());
		}

		//PUT api/user
		[HttpPut]
		[Authorize]
		public IActionResult Put([FromBody] User user) {
			return _service.Put(user);
		}

		//DELETE api/user/{id}
		[HttpDelete("{id}")]
		[Authorize]
		public IActionResult Delete(int id) {
			return _service.Delete(id);
		}
		
		//GET api/user/authenticate
		[HttpGet("authenticate")]
		[Authorize]
		public IActionResult Authenticate() {
			int id = int.Parse(User.Claims.First(c => c.Type == "Id").Value);
			return Get(id);
		}
	}
}