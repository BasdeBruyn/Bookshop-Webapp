using System.ComponentModel.DataAnnotations;
using System.Linq;
using Backend.Models;

namespace Backend.Validators {
	public class UniqueEmailAttribute : ValidationAttribute {
		private new const string ErrorMessage = "Email already exists.";
		private readonly WebshopContext _context;

		public UniqueEmailAttribute() {
			_context = new WebshopContext();
		}

		protected override ValidationResult IsValid(object value, ValidationContext validationContext) {
			string email = (string) value;
			User user = (User) validationContext.ObjectInstance;
			User dbUser = _context.Users.SingleOrDefault(u => u.Email == email);

			if (dbUser == null || user.Id == dbUser.Id)
				return ValidationResult.Success;
			
			return new ValidationResult(ErrorMessage);
		}
	}
}