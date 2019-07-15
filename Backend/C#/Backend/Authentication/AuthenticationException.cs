using System;

namespace Backend.Authentication {
	public class AuthenticationException : Exception {
		private new const string Message = "An error occured during authentication.";
		
		public AuthenticationException(Exception innerException) 
			: base(Message, innerException) {}
	}
}