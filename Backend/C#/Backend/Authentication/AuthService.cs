using System;
using System.Linq;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;

namespace Backend.Authentication {
	public static class AuthService {
		
		public static bool ActionAuthorized(ClaimsPrincipal principal, int userId) {
			int id = int.Parse(principal.Claims.First(c => c.Type == "Id").Value);
			bool isAdmin = IsAdmin(principal);

			return userId == id || isAdmin;
		}

		public static bool IsAdmin(ClaimsPrincipal principal) {
			Claim isAdmin = principal.Claims.FirstOrDefault(c => c.Type == "Admin");
			return isAdmin != null && bool.Parse(isAdmin.Value);
		}

		public static string HashPassword(string password, string salt = null) {
			bool createSalt = salt == null;

			if (createSalt)
				salt = GetRandomSalt();

			string combinedPassword = string.Concat(password, salt);
			SHA512Managed sha512 = new SHA512Managed();
			byte[] bytes = Encoding.UTF8.GetBytes(combinedPassword);
			byte[] hash = sha512.ComputeHash(bytes);
			
			if (createSalt)
				return Convert.ToBase64String(hash) + ":" + salt;

			return Convert.ToBase64String(hash);
		}
		
		public static bool ValidatePassword(string enteredPassword, string storedPassword) {
			string storedHash = storedPassword.Split(":")[0];
			string storedSalt = storedPassword.Split(":")[1];
			
			string hash = HashPassword(enteredPassword, storedSalt);
			
			return string.Equals(storedHash, hash);
		}

		private static string GetRandomSalt(int size = 16) {
			RNGCryptoServiceProvider random = new RNGCryptoServiceProvider();
			byte[] salt = new byte[size]; 
			random.GetBytes(salt);
			
			return Convert.ToBase64String(salt);
		}
	}
}