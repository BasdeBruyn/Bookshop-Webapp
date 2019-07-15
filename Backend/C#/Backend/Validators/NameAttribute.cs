using System.ComponentModel.DataAnnotations;
using System.Text.RegularExpressions;

namespace Backend.Validators {
	public class NameAttribute : ValidationAttribute {
		private const string Pattern = "[^a-zA-Z \\-']";
		private new const string ErrorMessage = "Contains invalid characters.";

		public NameAttribute() {
			base.ErrorMessage = string.Format(ErrorMessage);
		}

		public override bool IsValid(object value) {
			string name = (string) value;
			
			return !Regex.Match(name, Pattern).Success;
		}
	}
}