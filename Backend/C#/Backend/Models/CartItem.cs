using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Backend.Validators;
using Newtonsoft.Json;

namespace Backend.Models {
	public class CartItem {
		
		public int Id { get; set; }
		
		[Required]
		[UserExists]
		public int UserId { get; set; }
		
		[JsonIgnore]
		[ForeignKey("UserId")]
		public User User { get; set; }
		
		[Required]
		[ItemExists]
		public int ItemId { get; set; }
		
		[JsonIgnore]
		[ForeignKey("ItemId")]
		public Item Item { get; set; }

		public CartItem() {}

		public CartItem(int userId, int itemId, int id = 0) {
			Id = id;
			UserId = userId;
			ItemId = itemId;
		}

		public override string ToString() {
			return $"{nameof(Id)}: {Id}, {nameof(UserId)}: {UserId}, {nameof(ItemId)}: {ItemId}";
		}
	}
}