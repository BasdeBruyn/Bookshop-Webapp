using System.ComponentModel.DataAnnotations;

namespace Backend.Validators {
	public class MinValueAttribute : ValidationAttribute {
		private readonly int _minValue;
		private new const string ErrorMessage = "Value must be at least {0}.";

		public MinValueAttribute(int minValue) {
			_minValue = minValue;
			base.ErrorMessage = string.Format(ErrorMessage, minValue);
		}

		public override bool IsValid(object value) {
			return (int) value >= _minValue;
		}
	}
}