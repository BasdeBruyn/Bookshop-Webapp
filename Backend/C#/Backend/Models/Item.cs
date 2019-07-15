using System.ComponentModel.DataAnnotations;
using Backend.Validators;

namespace Backend.Models {
	public class Item {
		
		public int Id { get; set; }

		[Required]
		[MaxLength(127)]
		public string Name { get; set; }

		[Required]
		[MaxLength(4095)]
		public string Description { get; set; }

		[Required]
		[MaxLength(511)]
		[Url]
		public string Url { get; set; }
		
		[Required]
		public bool Available { get; set; }
		
		[Required]
		[MinValue(0)]
		public int Price { get; set; }

		public Item() {}

		public Item(int price, string name, string description, string url, bool available, int id = 0) {
			Id = id;
			Price = price;
			Name = name;
			Description = description;
			Url = url;
			Available = available;
		}

		public override string ToString() {
			return $"{nameof(Id)}: {Id}, {nameof(Price)}: {Price}, {nameof(Name)}: {Name}, {nameof(Description)}" +
			       $": {Description}, {nameof(Url)}: {Url}, {nameof(Available)}: {Available}";
		}
	}
}