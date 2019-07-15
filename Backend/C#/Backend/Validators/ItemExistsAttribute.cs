using System.ComponentModel.DataAnnotations;
using System.Linq;
using Backend.Models;

namespace Backend.Validators {
	public class ItemExistsAttribute : ValidationAttribute {
		private new const string ErrorMessage = "Item id doesn't exists.";
		private readonly WebshopContext _context;

		public ItemExistsAttribute() {
			_context = new WebshopContext();
			base.ErrorMessage = ErrorMessage;
		}

		public override bool IsValid(object value) {
			int id = (int) value;
			Item item = _context.Items.SingleOrDefault(i => i.Id == id);
			
			return item != null;
		}
	}
}