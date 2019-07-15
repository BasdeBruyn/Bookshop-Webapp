using System.ComponentModel.DataAnnotations;
using System.Text.RegularExpressions;

namespace Backend.Validators {
	public class PostalCodeAttribute : ValidationAttribute {
		private const string Pattern = "^[1-9][0-9]{3} ?[A-Z]{2}$";
		private new const string ErrorMessage = "Invalid postal code.";

		public PostalCodeAttribute() {
			base.ErrorMessage = ErrorMessage;
		}

		public override bool IsValid(object value) {
			string postalCode = (string) value;

			return Regex.Match(postalCode, Pattern).Success;
		}
	}
}