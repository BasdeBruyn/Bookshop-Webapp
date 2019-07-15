using System.ComponentModel.DataAnnotations;
using System.Text.RegularExpressions;

namespace Backend.Validators {
	public class HouseNrAttribute : ValidationAttribute {
		private const string Pattern = "^([1-9][0-9]{0,2} ?[A-Za-z]?)$";
		private new const string ErrorMessage = "Invalid house number.";

		public HouseNrAttribute() {
			base.ErrorMessage = ErrorMessage;
		}

		public override bool IsValid(object value) {
			string houseNr = (string) value;

			return Regex.Match(houseNr, Pattern).Success;
		}
	}
}