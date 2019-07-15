namespace Backend.Models {
	public class DtoUser {
		public int Id { get; set; }
		public string Email { get; set; }
		public bool IsAdmin { get; set; }

		public DtoUser(int id, string email, bool isAdmin) {
			Id = id;
			Email = email;
			IsAdmin = isAdmin;
		}
	}
}