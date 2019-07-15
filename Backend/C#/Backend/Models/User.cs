using System.ComponentModel.DataAnnotations;
using Backend.Validators;

namespace Backend.Models {
	
	public class User {
		public int Id { get; set; }

		[Required]
		[EmailAddress]
		[UniqueEmail]
		[MaxLength(127)]
		public string Email { get; set; }

		[Required]
		[MinLength(5)]
		[MaxLength(255)]
		public string Password { get; set; }
		
		[Required]
		public bool IsAdmin { get; set; }

		public User() {}

		public User(string email, string password, bool isAdmin, int id = 0) {
			Id = id;
			Email = email;
			Password = password;
			IsAdmin = isAdmin;
		}

		public override string ToString() {
			return
				$"{nameof(Id)}: {Id}, {nameof(Email)}: {Email}, {nameof(Password)}: {Password}, {nameof(IsAdmin)}: {IsAdmin}";
		}
	}
}