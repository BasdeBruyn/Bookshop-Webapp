using System.ComponentModel.DataAnnotations;
using System.Linq;
using Backend.Models;

namespace Backend.Validators {
	public class UserExistsAttribute : ValidationAttribute {
		private new const string ErrorMessage = "User id doesn't exists.";
		private readonly WebshopContext _context;

		public UserExistsAttribute() {
			_context = new WebshopContext();
			base.ErrorMessage = ErrorMessage;
		}

		public override bool IsValid(object value) {
			int id = (int) value;
			User user = _context.Users.SingleOrDefault(u => u.Id == id);
			
			return user != null;
		}
	}
}