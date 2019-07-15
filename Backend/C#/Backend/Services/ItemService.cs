using System.Collections;
using System.Linq;
using AutoMapper;
using Backend.Models;
using Microsoft.AspNetCore.Mvc;

namespace Backend.Services {
	public class ItemService {
		private readonly WebshopContext _context;

		public ItemService() {
			_context = new WebshopContext();
		}

		public IActionResult Get() {
			ICollection dbItems = _context.Items.ToList();
			return new OkObjectResult(dbItems);
		}

		public IActionResult Get(int id) {
			Item dbItem = _context.Items.SingleOrDefault(u => u.Id == id);
			if (dbItem == null) 
				return new NotFoundResult();
			
			return new OkObjectResult(dbItem);
		}
		
		public IActionResult Post(Item item, string url) {
			item.Id = 0;
			
			_context.Items.Add(item);
			_context.SaveChanges();
			return new CreatedResult($"{url}/{item.Id}", item);
		}

		public IActionResult Put(Item item) {
			Item dbItem = _context.Items.SingleOrDefault(u => u.Id == item.Id);
			if (dbItem == null) 
				return new NotFoundResult();
			
			Mapper.Map(item, dbItem);
			_context.SaveChanges();
			return new NoContentResult();
		}

		public IActionResult Delete(int id) {
			Item dbItem = _context.Items.SingleOrDefault(u => u.Id == id);
			if (dbItem == null) 
				return new NotFoundResult();
			
			_context.Remove(dbItem);
			return new NoContentResult();
		}
	}
}