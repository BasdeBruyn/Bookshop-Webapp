using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Backend.Validators;
using Newtonsoft.Json;

namespace Backend.Models {
	public class Order {
		
		public int Id { get; set; }
		
		[Required]
		[Name]
		[MaxLength(63)]
		[MinLength(2)]
		public string FirstName { get; set; }
		
		[Required]
		[Name]
		[MaxLength(63)]
		[MinLength(2)]
		public string LastName { get; set; }
		
		[Required]
		[PostalCode]
		[MaxLength(7)]
		public string PostalCode { get; set; }
		
		[Required]
		[HouseNr]
		[MaxLength(5)]
		public string HouseNr { get; set; }
		
		[Required]
		[Name]
		[MaxLength(63)]
		[MinLength(2)]
		public string StreetName { get; set; }
		
		[Required]
		[Name]
		[MaxLength(63)]
		[MinLength(2)]
		public string Residence { get; set; }
		
		[Required]
		[Phone]
		[MaxLength(12)]
		public string PhoneNr { get; set; }
		
		[Required]
		[MinValue(0)]
		public int TotalPrice { get; set; }
		
		public DateTime CurDate { get; set; }
		
		[Required]
		[UserExists]
		public int UserId { get; set; }
		
		[JsonIgnore]
		[ForeignKey("UserId")]
		public User User { get; set; }
		
		public Order() {}

		public Order(string firstName, string lastName, string postalCode, string houseNr, string streetName,
			string residence, string phoneNr, int totalPrice, int userId, int id = 0) {
			Id = id;
			FirstName = firstName;
			LastName = lastName;
			PostalCode = postalCode;
			HouseNr = houseNr;
			StreetName = streetName;
			Residence = residence;
			PhoneNr = phoneNr;
			TotalPrice = totalPrice;
			UserId = userId;
		}

		public override string ToString() {
			return $"{nameof(Id)}: {Id}, {nameof(FirstName)}: {FirstName}, {nameof(LastName)}: {LastName}" +
			       $", {nameof(PostalCode)}: {PostalCode}, {nameof(HouseNr)}: {HouseNr}" +
			       $", {nameof(StreetName)}: {StreetName}, {nameof(Residence)}: {Residence}" +
			       $", {nameof(PhoneNr)}: {PhoneNr}, {nameof(TotalPrice)}: {TotalPrice}, {nameof(CurDate)}: {CurDate}" +
			       $", {nameof(UserId)}: {UserId}";
		}
	}
}